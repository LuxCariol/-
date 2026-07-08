package com.platform.util;

import com.platform.entity.Message;
import com.platform.mapper.MessageMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Map<Long, WebSocketSession> onlineUsers = new ConcurrentHashMap<>();

    @Autowired
    private MessageMapper messageMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long userId = (Long) session.getAttributes().get("userId");
        if (userId != null) {
            onlineUsers.put(userId, session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> msgMap = objectMapper.readValue(message.getPayload(), Map.class);

        Message chatMessage = new Message();
        chatMessage.setFromUser(Long.valueOf(msgMap.get("from").toString()));
        chatMessage.setToUser(Long.valueOf(msgMap.get("to").toString()));
        chatMessage.setContent(msgMap.get("content").toString());
        chatMessage.setCreateTime(LocalDateTime.now());

        if (msgMap.containsKey("taskId")) {
            chatMessage.setTaskId(Long.valueOf(msgMap.get("taskId").toString()));
        }

        // 持久化到数据库
        messageMapper.insert(chatMessage);

        // 推送给接收方
        WebSocketSession toSession = onlineUsers.get(chatMessage.getToUser());
        if (toSession != null && toSession.isOpen()) {
            toSession.sendMessage(new TextMessage(message.getPayload()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long userId = (Long) session.getAttributes().get("userId");
        if (userId != null) {
            onlineUsers.remove(userId);
        }
    }
}
