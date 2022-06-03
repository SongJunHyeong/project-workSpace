package com.project.workspace.service;

import com.project.workspace.domain.dao.LoungeDAO;
import com.project.workspace.domain.repository.LoungeLikeRepository;
import com.project.workspace.domain.repository.LoungeReplyRepository;
import com.project.workspace.domain.repository.LoungeRepository;
import com.project.workspace.domain.vo.LoungeLikeVO;
import com.project.workspace.domain.vo.LoungeReplyVO;
import com.project.workspace.domain.vo.LoungeVO;
import com.project.workspace.domain.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoungeServiceImpl implements LoungeService{
    private final LoungeDAO loungeDAO;
    private final LoungeRepository loungeRepository;
    private final LoungeReplyRepository loungeReplyRepository;
    private final LoungeLikeRepository loungeLikeRepository;

    @Override
    public List<LoungeVO> findLoungeAll() {
        return loungeRepository.findAll();
    }

    @Override
    public LoungeVO findId(Long loungeNum) {
        return loungeRepository.findById(loungeNum).get();
    }

    @Override
    public String insertLounge(String loungeContent, UserVO userNum) {
        loungeRepository.save(LoungeVO.builder().userVO(userNum).loungeContent(loungeContent).build());
        return "success";
    }

    @Override
    public String insertReply(String replyContent, UserVO userNum, LoungeVO loungeNum) {
        loungeReplyRepository.save(LoungeReplyVO.builder().loungeReplyContent(replyContent).loungeVO(loungeNum).userVO(userNum).build());
        return "success";
    }

    @Override
    public String deleteLounge(Long loungeNum) {
        loungeRepository.deleteById(loungeNum);
        return "success";
    }

    @Override
    public String deleteReply(Long loungeReplyNum) {
        loungeReplyRepository.deleteById(loungeReplyNum);
        return "success";
    }

    @Override
    public String updateLounge(Long loungeNum, String loungeContent) {
        UserVO userVO = loungeRepository.findById(loungeNum).get().getUserVO();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        LoungeVO loungeVO= loungeRepository.save(LoungeVO.builder().loungeContent(loungeContent).loungeDate(sdf.format(date)).loungeNum(loungeNum).userVO(userVO).build());
        return "success";
    }

    @Override
    public String updateReply(Long loungeReplyNum, String loungeReplyContent) {
        UserVO userVO = loungeReplyRepository.findById(loungeReplyNum).get().getUserVO();
        LoungeVO loungeVO = loungeReplyRepository.findById(loungeReplyNum).get().getLoungeVO();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        LoungeReplyVO loungeReplyVO = loungeReplyRepository.save(LoungeReplyVO.builder().loungeVO(loungeVO).loungeReplyNum(loungeReplyNum).loungeReplyContent(loungeReplyContent).userVO(userVO).loungeReplyDate(sdf.format(date)).build());
        return "success";
    }
}
