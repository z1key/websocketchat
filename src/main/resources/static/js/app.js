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
        stompClient.subscribe('/topic/all', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
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
    stompClient.send("/app/publishAll", {}, JSON.stringify({'content': $("#message").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $( "#disconnect" ).click(function() { window.location.href = "/logout"; });
    $( "#publish" ).click(function() { sendMessage(); });
    connect();
});