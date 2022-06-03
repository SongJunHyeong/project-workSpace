package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {
    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatRoom findRoomById(String name) {

        return chatRoomMap.get(name);
    }

    public ChatRoom createChatRoom(String name) {

        ChatRoom chatRoom = ChatRoom.create(name);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}