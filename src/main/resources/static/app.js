let stompClient = null;

const APPLICATION_ENDPOINT = '/chat-service';
const TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QGVtYWlsLmNvbSIsInJvbGUiOiJNYW5hZ2VyIiwiZXhwIjoxNjY5MjU3OTIyMDY3fQ.jwfSYaCG69_fEn2YjyXzh_ACgXG66PFWvOddEpb9Bv0";
const ACCESS_PARAMETER = "?access_token=" + TOKEN;
const STOMP_HTTP_ENDPOINT = APPLICATION_ENDPOINT + '/stomp-endpoint' + ACCESS_PARAMETER;
const ALL_MESSAGES_HTTP_ENDPOINT = APPLICATION_ENDPOINT + "/messages" + ACCESS_PARAMETER;

const CREATE_MESSAGE_SOCKET_ENDPOINT ='/app/message';
const MESSAGES_TOPIC = '/topic/messages';

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#messages").html("");
}

function connect() {
    let socket = new SockJS(STOMP_HTTP_ENDPOINT);
    stompClient = Stomp.over(socket);

    let subscribeFunction = function (message) {
        showMessage(JSON.parse(message.body));
    }

    let placeDBMessages = function () {
        let successCallback = function (data) {
            data.forEach(showMessage);
        };

        $.get(ALL_MESSAGES_HTTP_ENDPOINT, successCallback);
    }

    let postConnectActions = function (frame) {
        setConnected(true);
        console.log('Connected! Frame:', frame);
        stompClient.subscribe(MESSAGES_TOPIC, subscribeFunction);
        placeDBMessages();
    }

    stompClient.connect({}, postConnectActions);
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    let inputValue = $("#name").val();
    let request = {'messageText': inputValue, 'jwtToken': TOKEN};
    let requestJson = JSON.stringify(request);
    stompClient.send(CREATE_MESSAGE_SOCKET_ENDPOINT, {}, requestJson);
}

function showMessage(message) {
    let row = `<tr><td>${message.messageText}</td><td>${message.creationDate}</td></tr>`;
    $("#messages").append(row);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendMessage();
    });
});
