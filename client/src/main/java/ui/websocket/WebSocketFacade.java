package ui.websocket;

import javax.websocket.*;
import java.net.URI;
import com.google.gson.Gson;
import ui.ResponseException;
import webSocketMessages.serverMessages.ServerMessage;


public class WebSocketFacade extends Endpoint {

    private final Session session;
    NotificationHandler notificationHandler;

    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);

                    // implement what happens on the three notifcation types
                    //load_game
                    //error
                    //notification

                    notificationHandler.notify();
                }
            });
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        
    }
}
