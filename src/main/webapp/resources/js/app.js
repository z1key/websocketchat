var stompClient = null;

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
        stompClient.subscribe('/topic/all', function (message) {
            var content = JSON.parse(message.body);
            showGreeting(content.sender, content.receivers, content.content);
        });
    }, function (error) {
        alert(error.headers.message)
    });
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
    stompClient.send("/app/publishAll", {}, JSON.stringify({receivers: [],'content': $("#message").val()}));
    $("#message").val('');
    $("#message").focus();
}

function showGreeting(sender, receivers, message) {
    $("#conversation ul").append(
    '<li>\
        <span class="sender">' + sender + ': </span>\
        <span class="receiver">' + receivers.toString() + '</span>\
        <span class="message">' + message + '</span>\
    </li>');
}

$(function () {
    $( "#disconnect" ).click(function() { window.location.href = "/logout"; });
    $( "#publish" ).click(function() { sendMessage(); });
    connect();
});