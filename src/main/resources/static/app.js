let stompClient = null;

const STOMP_ENDPOINT = '/stomp-endpoint';
const APPLICATION_ENDPOINT = '/app';
const CREATE_MESSAGE_ENDPOINT = APPLICATION_ENDPOINT + '/message';
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
    let socket = new SockJS(STOMP_ENDPOINT);
    stompClient = Stomp.over(socket);

    let subscribeFunction = function (message) {
        showMessage(JSON.parse(message.body));
    }

    let placeDBMessages = function () {
        let successCallback = function (data) {
            data.forEach(showMessage);
        };

        $.get('/messages', successCallback);
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
    let request = {'messageText': inputValue};
    let requestJson = JSON.stringify(request);
    stompClient.send(CREATE_MESSAGE_ENDPOINT, {}, requestJson);
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
