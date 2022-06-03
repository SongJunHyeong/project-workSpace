package com.project.workspace.controller;

import com.project.workspace.domain.repository.LoungeLikeRepository;
import com.project.workspace.domain.repository.LoungeReplyRepository;
import com.project.workspace.domain.repository.LoungeRepository;
import com.project.workspace.domain.vo.*;
import com.project.workspace.service.LoungeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Store;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoungeController {

    private final LoungeService loungeService;
    private final LoungeRepository loungeRepository;
    private final LoungeReplyRepository loungeReplyRepository;
    private final LoungeLikeRepository loungeLikeRepository;

    @GetMapping("/lounge")
    public String lounge(Model model) {
        List<LoungeVO> loungeVOs = loungeService.findLoungeAll();

        List<String> loungeUserNickNames = loungeVOs.stream()
                .map(loungeVO -> loungeVO.getUserVO().getUserNickName())
                .collect(Collectors.toList());

        List<String> userLikeNickNames = loungeVOs.stream()
                .sorted(comparing(this::getLikeSize).reversed())
                .filter(loungeVO -> loungeVO.getLikes().size() > 0)
                .limit(3)
                .map(loungeVO -> loungeVO.getUserVO().getUserNickName())
                .collect(Collectors.toList());

        List<String> userReplyNickNames = loungeVOs.stream()
                .sorted(comparing(this::getReplySize).reversed())
                .filter(loungeVO -> loungeVO.getReplies().size() > 0)
                .limit(3)
                .map(loungeVO -> loungeVO.getUserVO().getUserNickName())
                .collect(Collectors.toList());

        List<Integer> loungeLikesNum = loungeVOs.stream().map(loungeVO -> loungeVO.getLikes().size())
                .collect(Collectors.toList());

        List<Integer> loungeRepliesNum = loungeVOs.stream().map(loungeVO -> loungeVO.getReplies().size())
                .collect(Collectors.toList());

        Collections.reverse(loungeVOs);
        Collections.reverse(loungeUserNickNames);
        Collections.reverse(loungeLikesNum);
        Collections.reverse(loungeRepliesNum);

        List<UserVO> userVOs = loungeVOs.stream().map(UserVO -> UserVO.getUserVO()).collect(Collectors.toList());
        model.addAttribute("loungeVOs", loungeVOs);
        model.addAttribute("userVOs", userVOs);
        model.addAttribute("loungeLikesNum", loungeLikesNum);
        model.addAttribute("loungeRepliesNum", loungeRepliesNum);
        model.addAttribute("loungeUserNickNames", loungeUserNickNames);
        model.addAttribute("userLikeNickNames", userLikeNickNames);
        model.addAttribute("userReplyNickNames", userReplyNickNames);
        return "lounge/lounge";
    }

    private int getLikeSize(LoungeVO loungeVO) {
        return loungeVO.getLikes().size();
    }
    private int getReplySize(LoungeVO loungeVO) {
        return loungeVO.getReplies().size();
    }

    // 댓글 보기
    @ResponseBody
    @GetMapping("/lounge/reply/{loungeNum}")
    public LoungeReplyDTO likeAndReply(@PathVariable("loungeNum") Long loungeNum) {
        LoungeVO loungeVO = loungeService.findId(loungeNum);
        List<LoungeReplyVO> replies = loungeVO.getReplies();
        List<UserVO> userVO = replies.stream().map(UserVO -> UserVO.getUserVO()).collect(Collectors.toList());
        Collections.reverse(userVO);
        Collections.reverse(replies);

        return new LoungeReplyDTO(userVO,replies);
    }

    // 라운지 글작성
    @ResponseBody
    @GetMapping("/lounge/{loungeContent}/{userNum}")
    public String insertLounge(@PathVariable("loungeContent") String loungeContent, @PathVariable("userNum") UserVO userNum){
        return loungeService.insertLounge(loungeContent, userNum);
    }

    // 댓글 작성
    @ResponseBody
    @GetMapping("/lounge/loungeInsert/{replyContent}/{userNum}/{loungeNum}")
    public String insertReply(@PathVariable("replyContent") String replyContent, @PathVariable("userNum") UserVO userNum, @PathVariable("loungeNum") LoungeVO loungeNum){
        return loungeService.insertReply(replyContent, userNum, loungeNum);
    }

    //라운지 삭제
    @ResponseBody
    @GetMapping("/lounge/loungeDelete/{loungeNum}")
    public String deleteLounge(@PathVariable("loungeNum") Long loungeNum){
        return loungeService.deleteLounge(loungeNum);
    }

    //라운지 댓글 삭제
    @ResponseBody
    @GetMapping("/lounge/loungeDeleteReply/{loungeReplyNum}")
    public String deleteReply(@PathVariable("loungeReplyNum") Long loungeReplyNum){
        return loungeService.deleteReply(loungeReplyNum);
    }

    //라운지 수정
    @ResponseBody
    @GetMapping("/lounge/loungeUpdate/{loungeNum}/{loungeContent}")
    public String updateLounge(@PathVariable("loungeNum") Long loungeNum, @PathVariable("loungeContent") String loungeContent){
        return loungeService.updateLounge(loungeNum, loungeContent);
    }

    // 댓글 수정
    @ResponseBody
    @GetMapping("/lounge/loungeUpdateReply/{loungeReplyNum}/{loungeReplyContent}")
    public String updateReply(@PathVariable("loungeReplyNum") Long loungeReplyNum, @PathVariable("loungeReplyContent") String loungeReplyContent){
        return loungeService.updateReply(loungeReplyNum, loungeReplyContent);
    }

    @ResponseBody
    @Transactional
    @GetMapping("/lounge/likeLounge/{loungeNum}/{userNum}")
    public String likeStory(@PathVariable("userNum") Long userNum, @PathVariable("loungeNum") Long loungeNum){
        LoungeLikeVO byUserVOAndLoungeVO = loungeLikeRepository.findByUserVO_UserNumAndLoungeVO_LoungeNum(userNum, loungeNum);
        if(byUserVOAndLoungeVO != null){
            loungeLikeRepository.deleteByUserVO_UserNumAndLoungeVO_LoungeNum(userNum, loungeNum);
            return "fail";
        }
        UserVO userVO = new UserVO();
        userVO.setUserNum(userNum);
        LoungeVO loungeVO = new LoungeVO();
        loungeVO.setLoungeNum(loungeNum);
        loungeLikeRepository.save(LoungeLikeVO.builder().loungeVO(loungeVO).userVO(userVO).build());
        int likeSize = loungeLikeRepository.findAllByLoungeVO_LoungeNum(loungeNum).size();
        return "success "+likeSize;
    }
}
