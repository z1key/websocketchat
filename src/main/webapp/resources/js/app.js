String.prototype.insertAt=function(index, string) {
    return this.substr(0, index) + string + this.substr(index);
};

var i_am;
var stompClient = null;
var username_pattern = "[%]\u2063";

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/websocketchat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        function onSubscribe(message, callback) {
            message = JSON.parse(message.body);
            callback(message);
        }
        stompClient.subscribe('/topic/all', function (message) {
            onSubscribe(message, onMessageReceiveCallback);
        });
        stompClient.subscribe('/user/queue/online', function (message) {
            onSubscribe(message, onlineUsersCallback);
        });
        stompClient.subscribe('/system/online', function (message) {
            onSubscribe(message, onlineUsersCallback);
        });
        stompClient.subscribe('/system/event', function (message) {
            onSubscribe(message, onSystemEventCallback)
        });
    }, function (error) {
        alert(error.headers.message)
    });
}

function onMessageReceiveCallback(message) {
    var receiver_html = '';
    for (var user in message.receivers) {
        receiver_html.append('<span class="receiver">[' + user + ']</span>')
    }
    $("#conversation ul").append(
        '<li>\
            <span class="sender">' + message.sender + ': </span>' + receiver_html + '\
            <span class="message">' + message.content + '</span>\
    </li>');
}

function onSystemEventCallback(message) {
    var message_type = message.type;
    var content = message.content;
    switch (message_type) {
        case 'LOGGED_IN' :
            userLogged(content);
            break;
        case 'LOGGED_OUT':
            userLogout(content);
            break;
        case 'BANNED':
            break;
        case 'KICKED':
            break;
        case 'WARNING':
            break;
        case 'SYSTEM_EVENT':
            break;
        default:
            throw new Error("Unknown message type.")
    }
}

function onlineUsersCallback(message) {

}
function userAlreadyExists(user) {
    var $container = $('ul#users');
    return $container.contents().filter(function () {
            return $(this).text() === user;
        }).length != 0;
}

function userLogged(user) {
    if (!userAlreadyExists(user)) {
        $('ul#users').append('<li>' + user + '</li>')
    }
}
function userLogout(user) {
    if (userAlreadyExists(user)) {
        $('ul#users').contents().filter(function () {
            return $(this).text() === user;
        }).remove();
    }
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();

    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/login", {}, JSON.stringify({'name': $("#name").val()}));
}

function sendMessage() {
    var input_content = $("#message").val();
    var receivers = [];
    var splitted = input_content.split(/\u2063/g);
    for (var i = 0; i < splitted.length - 1; i++) {
        var user = splitted[i].replace(/\[|]/g, '');
        receivers.push(user);
    }
    var message = splitted[splitted.length - 1];
    stompClient.send("/app/publish", {}, JSON.stringify({receivers: receivers, content: message}));
    $("#message").val('');
    $("#message").focus();

}

function showSystemMessage(message) {
    $("#conversation ul").append(
        '<li>\
        <span class="system">' + message + '</span>\
    </li>');
}
function addRecipient(user_item) {
    var user_name = $(user_item).text();
    var input = $("#message");
    var input_content = $("#message").val();
    var last_username_pos = input_content.indexOf("\u2063");
    var is_user_already_added = input_content.indexOf(user_name) != -1;
    if (!is_user_already_added) {
        input.val(input_content.insertAt(last_username_pos, username_pattern.replace("%", user_name)));
    }
}

$(function () {
    $("#disconnect").click(function () {
        window.location.href = "/logout";
    });
    $("#publish").click(function () {
        sendMessage();
    });
    $("ul#users").on('click', 'li', function () {
        addRecipient(this);
    });
    connect();
});