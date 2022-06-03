package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.ChatMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ChatMessageRepository extends JpaRepository<ChatMessageVO, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from chat_message where room_name =:roomName", nativeQuery= true)
    void deleteChat(@Param("roomName") String roomName);

}
