package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.UserExpVO;
import com.project.workspace.domain.vo.UserTagVO;
import com.project.workspace.domain.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserExpRepository extends JpaRepository<UserExpVO, Long> {
    public List<UserExpVO> findByUserVO(UserVO userVO);
    public void deleteAllByUserVO(UserVO userVO);
}