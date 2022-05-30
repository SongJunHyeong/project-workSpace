package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.ProjectMemberVO;
import com.project.workspace.domain.vo.ProjectVO;
import com.project.workspace.domain.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMemberVO, Long> {
//    public List<ProjectMemberVO> findByUser(ProjectVO projectVO);
}
