package com.project.workspace.domain.vo;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"messageNum", "message", "chatTime", "chatFile"})
@DynamicInsert
public class ChatMessageMyBatisVO {
    private Long messageNum;
    private String message;
    private String chatTime;
    private String chatFile;
    private String roomName;
    private String senderId;
    private String receiverId;


    @Builder
    public ChatMessageMyBatisVO(Long messageNum, String message, String chatTime, String chatFile, String roomName, String senderId, String receiverId) {
        this.messageNum = messageNum;
        this.message = message;
        this.chatTime = chatTime;
        this.chatFile = chatFile;
        this.roomName = roomName;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}
