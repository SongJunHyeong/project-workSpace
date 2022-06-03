package com.project.workspace.domain.dao;

import com.project.workspace.domain.vo.ChatMessageMyBatisVO;
import com.project.workspace.domain.vo.ChatMessageVO;
import com.project.workspace.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageDAO {
    private final ChatMessageMapper chatMessageMapper;

    public void insertRoom(String senderId, String receiverId, String roomName){
        chatMessageMapper.insertRoom(senderId,receiverId,roomName);
    }

    public List<ChatMessageMyBatisVO> getChatList(String senderId, String receiverId){
        return chatMessageMapper.getChatList(senderId, receiverId);
    }
    public List<ChatMessageVO> getMyChatList(String senderId, String receiverId){
        return chatMessageMapper.getMyChatList(senderId, receiverId);
    }

    public List<ChatMessageVO> getConnectRoomName(String senderId, String receiverId){
        return chatMessageMapper.getConnectRoomName(senderId, receiverId);
    }

    public boolean getChatHistory(String senderId, String receiverId ){
        if (chatMessageMapper.getChatHistory(senderId, receiverId) != 0){
            return true;
            }
        else {
            return false;
        }

    }





}
