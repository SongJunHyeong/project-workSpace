package com.project.workspace.controller;

import com.project.workspace.domain.repository.ChatMessageRepository;
import com.project.workspace.domain.vo.ChatMessageMyBatisVO;
import com.project.workspace.domain.vo.ChatMessageVO;
import com.project.workspace.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageService chatMessageService;


    //room name 저장
    @PostMapping("/roomSave/{senderId}/{receiverId}/{roomName}")
    public void roomSave(@RequestBody @PathVariable("senderId") String senderId, @PathVariable("receiverId") String receiverId, @PathVariable("roomName") String roomName) {
        chatMessageService.insertRoom(senderId, receiverId, roomName);
    }

    // 채팅 메세지 저장
    @PostMapping("/chatSave/{senderId}/{receiverId}")
    public void chatSave(@RequestBody ChatMessageVO chatMessageVO, @PathVariable("senderId") String senderId, @PathVariable("receiverId") String receiverId) {
//        chatMessageVO.setSenderId(userRepository.findById(senderId).get());
//        chatMessageVO.setReceiverId(userRepository.findById(receiverId).get());
        chatMessageRepository.save(chatMessageVO);
    }

    //나의 채팅방 이름 가져오기
    @GetMapping("/chatList/{senderId}/{receiverId}")
    @ResponseBody
    public List<ChatMessageVO> chatList(@PathVariable("senderId") String senderId, @PathVariable("receiverId") String receiverId) {
        return chatMessageService.getMyChatList(senderId, receiverId);
    }

    //특정인과의 채팅방 이름 조회
    @GetMapping("/connectRoom/{senderId}/{receiverId}")
    @ResponseBody
    public List<ChatMessageVO> connectRoom(@PathVariable("senderId") String senderId, @PathVariable("receiverId") String receiverId) {
        return chatMessageService.getConnectRoomName(senderId, receiverId);
    }

    //채팅 한적이 있나요? 상대방과의 채팅 이력 체크
    @PostMapping("/chatHistory/{senderId}/{receiverId}")
    public ResponseEntity chatHistory(@PathVariable("senderId") String senderId, @PathVariable("receiverId") String receiverId) {
        if (chatMessageService.getChatHistory(senderId, receiverId)) {
            log.info("대화 이력 있음");
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    //채팅 total message 가져오기
    @GetMapping("/messageList/{senderId}/{receiverId}")
    @ResponseBody
    public List<ChatMessageMyBatisVO> messageList(ChatMessageVO chatMessageVO, @PathVariable("senderId") String senderId, @PathVariable("receiverId") String receiverId) {
//        chatMessageVO.setChatUserVO(chatUserRepository.findById(senderId).get());
//        chatMessageVO.setChatUserVO1(chatUserRepository.findById(receiverId).get());
        return chatMessageService.getChatList(senderId, receiverId);
    }


    //룸네임으로 채팅 삭제
    @ResponseBody
    @PostMapping("/deleteChat/{roomName}")
    public void deleteChat(@PathVariable("roomName") String roomName){
        chatMessageService.deleteChat(roomName);
    }


}
