package com.project.workspace.service;

import com.project.workspace.domain.vo.ChatMessageMyBatisVO;
import com.project.workspace.domain.vo.ChatMessageVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatMessageService {
    public void insertRoom(String senderId, String receiverId, String roomName);
    public List<ChatMessageMyBatisVO> getChatList(String senderId, String receiverId);
    public List<ChatMessageVO> getMyChatList(String senderId, String receiverId);
    public List<ChatMessageVO> getConnectRoomName(String senderId, String receiverId);
    public boolean getChatHistory(String senderId, String receiverId);
    public void deleteChat(String roomName);



}
