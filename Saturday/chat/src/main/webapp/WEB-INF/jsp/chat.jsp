<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Chat</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Micha Kops">

    <script src="resource/js/jquery-1.10.2.min.js"></script>

    <link href="resource/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }
    </style>
    <link href="resource/css/bootstrap-responsive.css" rel="stylesheet">

    <script>
        var wsocket;
        var serviceLocation = "ws://localhost:8082/lb3/chat";
        var $message;
        var $chatWindow;

        function onMessageReceived(evt) {
            var msg = JSON.parse(evt.data);
            var $messageLine = $('<tr class="row"><td class="col-md-3">' + msg.received
                    + '</td><td class="label label-info col-md-3">' + msg.sender
                    + '</td><td class="message badge col-md-6">' + msg.message
                    + '</td></tr>');
            $chatWindow.append($messageLine);
        }
        function sendMessage() {
            var msg = '{"message":"' + $message.val() + '", "sender":"", "received":""}';
            wsocket.send(msg);
            $message.val('').focus();
        }

        function connectToChatserver() {
            wsocket = new WebSocket(serviceLocation);
            wsocket.onmessage = onMessageReceived;
        }

        function leaveRoom() {
            wsocket.close();
            $chatWindow.empty();
            window.location.href = 'logout'
        }

        $(document).ready(function () {
            $message = $('#message');
            $chatWindow = $('#response');
            connectToChatserver();
            $('#do-chat').submit(function (evt) {
                evt.preventDefault();
                sendMessage();
            });

            $('#leave-room').click(function () {
                leaveRoom();
            });
        });

    </script>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <form id="do-chat">
                <table id="response" class="table table-bordered"></table>
                <fieldset>
                    <legend>Enter your message..</legend>
                    <div class="controls">
                        <input type="text" class="input-block-level" placeholder="Your message..." id="message"
                               style="height:60px"/>
                        <input type="submit" class="btn btn-large btn-block btn-primary"
                               value="Send message"/>
                        <button class="btn btn-large btn-block" type="button" id="leave-room">Leave
                            room
                        </button>
                    </div>
                </fieldset>
            </form>
        </div>
        <div class="col-md-2"></div>
    </div>
</div>
<div class="container">
</div>
</body>
</html>