package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.UserInterestVO;
import com.project.workspace.domain.vo.UserTagVO;
import com.project.workspace.domain.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInterestRepository extends JpaRepository<UserInterestVO, Long> {
    public List<UserInterestVO> findByUserVO(UserVO userVO);
    public void deleteByUserVO(UserVO userVO);
}
