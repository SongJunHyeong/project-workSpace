package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.ProjectVO;
import com.project.workspace.domain.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProjectRepository extends JpaRepository<ProjectVO,Long> {
    public List<ProjectVO> findTop4ByOrderByProjectNumDesc();
    public List<ProjectVO> findTop3ByOrderByProjectReadCountDesc();
    public List<ProjectVO> findAllByOrderByProjectNumDesc();
    public List<ProjectVO> findByUserVO(UserVO userVO);
}