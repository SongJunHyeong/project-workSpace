package com.project.workspace.mapper;

import com.project.workspace.domain.vo.ChatMessageMyBatisVO;
import com.project.workspace.domain.vo.ChatMessageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    public void insertRoom(String senderId, String receiverId, String roomName);
    public List<ChatMessageMyBatisVO> getChatList(String senderId, String receiverId);
    public List<ChatMessageVO> getMyChatList(String senderId, String receiverId);
    public List<ChatMessageVO> getConnectRoomName(String senderId, String receiverId);
    public int getChatHistory(String senderId, String receiverId);
}
