package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.UserAlertVO;
import com.project.workspace.domain.vo.UserInterestVO;
import com.project.workspace.domain.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAlertRepository extends JpaRepository<UserAlertVO, Long> {
    public List<UserAlertVO> findByUserVO(UserVO userVO);
    public void deleteByUserVO(UserVO userVO);
}
