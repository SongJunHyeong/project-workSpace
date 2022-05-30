package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.UserFollowVO;
import com.project.workspace.domain.vo.UserFollowID;
import com.project.workspace.domain.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollowVO, UserFollowID> {
    public List<UserFollowVO> findByFollowingUser (Long followingUser);
//    public void deleteByUserVO(UserVO userVO);
}
