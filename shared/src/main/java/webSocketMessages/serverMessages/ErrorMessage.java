package webSocketMessages.serverMessages;

public class ErrorMessage extends ServerMessage{
    public String getErrorMessage() {
        return errorMessage;
    }

    String errorMessage;
    public ErrorMessage(String message) {
        super(ServerMessageType.ERROR, message);
        errorMessage = message;
    }

}
