package server.Websocket;

import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public static ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Integer> lobbies = new ConcurrentHashMap<>();


    public void add(String visitorName, Session session, Integer gameID) {
        var connection = new Connection(visitorName, session);
        connections.put(visitorName, connection);
        lobbies.put(visitorName, gameID);

    }

    public void remove(String visitorName) {
        connections.remove(visitorName);
        lobbies.remove(visitorName);
    }

    public void broadcast(String excludeVisitorName, ServerMessage notification, Integer gameID) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.visitorName.equals(excludeVisitorName)) {
                    if (Objects.equals(lobbies.get(c.visitorName), gameID))
                        c.send(notification.toString());
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.visitorName);
        }
    }

    public void messageForYou(String VisitorName, ServerMessage notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (c.visitorName.equals(VisitorName)) {
                    c.send(notification.toString());
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.visitorName);
        }
    }


}