package com.project.workspace.controller;

import com.project.workspace.domain.repository.ChatRoomRepository;
import com.project.workspace.domain.repository.UserRepository;
import com.project.workspace.domain.vo.ChatRoom;
import com.project.workspace.domain.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;


    //user목록 가져오기
    @GetMapping("/list")
    @ResponseBody
    public List<String> List(){
        List<String> list = userRepository.findAll().stream().map(UserVO::getUserNickName).collect(Collectors.toList());
        log.info("@@@@@@@@닉네임 리스트@@@@@@@@@@@");
        log.info(list.toString());
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        return list;
    }

    @GetMapping("/rooms")
    public String rooms(Model model) {
        model.addAttribute("rooms", chatRoomRepository.findAllRoom());
        return "fix/chat";
    }

    @GetMapping("/rooms/{id}")
    public String room(@PathVariable String id, Model model) {
        ChatRoom room = chatRoomRepository.findRoomById(id);
        model.addAttribute("room", room);
        log.info("++++++++++++++++++++++++++++++48++++++++++++++++");
        log.info(id);
        return "chatRoom";
    }

    @GetMapping("/new")
    public String make(Model model) {
        ChatRoomForm form = new ChatRoomForm();
        model.addAttribute("form", form);
        return "newRoom";
    }

    @PostMapping("/room/new")
    public String makeRoom(@RequestBody String name) {

        chatRoomRepository.createChatRoom(name);
        log.info("*******************************************************");
        log.info("방 만들기 컨트롤러 들어옴");
        log.info(name);
        log.info("*******************************************************");

        return "fix/chat";
    }


}
