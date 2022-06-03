package com.project.workspace.service;

import com.project.workspace.domain.vo.LoungeLikeVO;
import com.project.workspace.domain.vo.LoungeVO;
import com.project.workspace.domain.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface LoungeService {
    public List<LoungeVO> findLoungeAll();
    public LoungeVO findId(Long loungeNum);
    public String insertLounge(String loungeContent, UserVO userNum);
    public String insertReply(String replyContent, UserVO userNum, LoungeVO loungeNum);
    public String deleteLounge(Long loungeNum);
    public String deleteReply(Long loungeReplyNum);
    public String updateLounge(Long loungeNum, String loungeContent);
    public String updateReply(Long loungeReplyNum, String loungeReplyContent);
}
