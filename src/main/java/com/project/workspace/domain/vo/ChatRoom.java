package com.project.workspace.domain.vo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Slf4j
public class ChatRoom {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoom create(String name){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = name;
        chatRoom.name = name;
        return chatRoom;
    }

    public void handleMessage(WebSocketSession session, ChatMessage chatMessage,
                              ObjectMapper objectMapper) throws IOException {
        if(chatMessage.getType() == MessageType.ENTER){
            sessions.add(session);
            chatMessage.setMessage("채팅이 시작되었습니다.");

        }
        else if(chatMessage.getType() == MessageType.LEAVE){
            sessions.remove(session);
//            chatMessage.setMessage(chatMessage.getWriter() + "님이 퇴장하셨습니다.");
        }
        else{
            chatMessage.setMessage(chatMessage.getWriter() + " : " + chatMessage.getMessage());
        }
        send(chatMessage,objectMapper);
    }

    private void send(ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.
                writeValueAsString(chatMessage.getMessage()));
        for(WebSocketSession sess : sessions){
            sess.sendMessage(textMessage);
        }
    }
}
