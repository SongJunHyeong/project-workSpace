package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.ProjectLikeVO;
import com.project.workspace.domain.vo.UserPortfolioVO;
import com.project.workspace.domain.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectLikeRepository extends JpaRepository<ProjectLikeVO, Long> {
    public List<ProjectLikeVO> findByUserVO(UserVO userVO);
    public void deleteByUserVO(UserVO userVO);
}
