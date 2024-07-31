package com.knowway.chat.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, CopyOnWriteArrayList<WebSocketSession>> sessionsMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String departmentStoreId = getDepartmentStoreId(session);
        sessionsMap.computeIfAbsent(departmentStoreId, k -> new CopyOnWriteArrayList<>()).add(session);

    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String departmentStoreId = getDepartmentStoreId(session);
        session.getAttributes().put("departmentStoreId", departmentStoreId);
        for (WebSocketSession webSocketSession : sessionsMap.computeIfAbsent(departmentStoreId, k -> new CopyOnWriteArrayList<>())) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String departmentStoreId = getDepartmentStoreId(session);
        sessionsMap.getOrDefault(departmentStoreId, new CopyOnWriteArrayList<>()).remove(session);
    }

    private String getDepartmentStoreId(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        return (String) attributes.get("departmentStoreId");
    }
}