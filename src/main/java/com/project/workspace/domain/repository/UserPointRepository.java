package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.UserPointVO;
import com.project.workspace.domain.vo.UserTagVO;
import com.project.workspace.domain.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPointRepository extends JpaRepository<UserPointVO, Long> {
    public List<UserPointVO> findByUserVO(UserVO userVO);
    public void deleteAllByUserVO(UserVO userVO);
}
