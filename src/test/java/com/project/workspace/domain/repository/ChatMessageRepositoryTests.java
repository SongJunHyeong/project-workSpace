package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.ChatMessageVO;
import com.project.workspace.domain.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class ChatMessageRepositoryTests {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Test
    @Transactional
    public void deleteTest(){

        chatMessageRepository.deleteChat("한서현한서현");


    }
}
