package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserVO, Long> {
    public UserVO findByUserId(String userId);
    public UserVO findByUserNum(Long userNum);
    public List<UserVO> findTop8ByOrderByUserNumDesc();
    public List<UserVO> findTop12ByOrderByUserNumDesc();
}