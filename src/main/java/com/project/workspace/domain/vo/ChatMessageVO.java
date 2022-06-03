package com.project.workspace.domain.vo;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"messageNum", "message", "chatTime", "chatFile"})
@Table(name = "chat_message")
@DynamicInsert
public class ChatMessageVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_num")
    private Long messageNum;

    @Column(name = "message")
    private String message;

    @Column(name = "chat_time")
    private String chatTime;

    @Column(name = "chat_file")
    private String chatFile;


    @Column(name = "room_name")
    private String roomName;

//    @OneToOne
//    @JoinColumn(name = "sender_id")
    @Column(name = "sender_id")
    private String senderId;

//    @OneToOne
//    @JoinColumn(name = "receiver_id")

    @Column(name = "receiver_id")
    private String receiverId;

    @Builder

    public ChatMessageVO(Long messageNum, String message, String chatTime, String chatFile, String roomName, String senderId, String receiverId) {
        this.messageNum = messageNum;
        this.message = message;
        this.chatTime = chatTime;
        this.chatFile = chatFile;
        this.roomName = roomName;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}
