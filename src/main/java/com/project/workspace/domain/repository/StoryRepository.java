package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.ProjectVO;
import com.project.workspace.domain.vo.StoryVO;
import com.project.workspace.domain.vo.UserAlertVO;
import com.project.workspace.domain.vo.UserVO;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.awt.print.Pageable;
import java.util.List;

public interface StoryRepository extends JpaRepository<StoryVO, Long>{
    List<StoryVO> findAllByUserVOAndStoryNumNot(UserVO userVO, Long storyNum);
    List<StoryVO> findAllByStoryNumNot(Long storyNum);
    List<StoryVO> findTop4ByOrderByStoryReadCountDesc();
    List<StoryVO> findTop10ByUserVO_UserNum(Long userNum);
    public List<StoryVO> findByUserVO(UserVO userVO);

}



