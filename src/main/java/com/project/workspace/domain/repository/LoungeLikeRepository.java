package com.project.workspace.domain.repository;

import com.project.workspace.domain.vo.LoungeLikeVO;
import com.project.workspace.domain.vo.LoungeReplyVO;
import com.project.workspace.domain.vo.LoungeVO;
import com.project.workspace.domain.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoungeLikeRepository extends JpaRepository<LoungeLikeVO, Long> {
    LoungeLikeVO findByUserVO_UserNumAndLoungeVO_LoungeNum(Long userNum, Long loungeNum);
    void deleteByUserVO_UserNumAndLoungeVO_LoungeNum(Long userNum, Long loungeNum);
    List<LoungeLikeVO> findAllByLoungeVO_LoungeNum(Long loungeNum);
}
