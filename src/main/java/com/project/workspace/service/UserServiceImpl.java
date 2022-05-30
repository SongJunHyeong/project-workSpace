package com.project.workspace.service;


import com.project.workspace.domain.dao.UserDAO;
import com.project.workspace.domain.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public List<UserVO> getList() {
        return userDAO.getList();
    }

    @Override
    public UserVO getInfo(Long userNum) {
        return userDAO.getInfo(userNum);
    }

    @Override
    public void insertPortfolio(Long userNum, String portUrl) {
        userDAO.insertPortfolio(userNum, portUrl);
    }

    @Override
    public UserPortfolioVO getPortInfo(Long userNum) {
        return userDAO.getPortInfo(userNum);
    }

    @Override
    public List<UserPortfolioVO> getPortList() {
        return userDAO.getPortList();
    }

    @Override
    public void insertUserTag(Long userNum, String tagName) {
        userDAO.insertUserTag(userNum, tagName);
    }

    @Override
    public List<UserTagVO> getTagList() {
        return userDAO.getTagList();
    }

    @Override
    public void insertInterest(Long userNum, String interest) {
        userDAO.insertInterest(userNum, interest);
    }

    @Override
    public List<UserInterestVO> getInterestList() {
        return userDAO.getInterestList();
    }

    @Override
    public void insertFollow(Long followerUser, Long followingUser) {
        userDAO.insertFollow(followerUser, followingUser);
    }

    @Override
    public List<UserFollowVO> getFollowList() {
        return userDAO.getFollowList();
    }

    @Override
    public void insertAlert(Long userNum, String alertPart, Long numbers, String alertType) {
        userDAO.insertAlert(userNum, alertPart, numbers, alertType);
    }

    @Override
    public List<UserAlertVO> getAlertList() {
        return userDAO.getAlertList();
    }

    @Override
    public void insertExp(Long userNum, Date expDate, int expValue, String expHistory) {
        userDAO.insertExp(userNum, expDate, expValue, expHistory);
    }

    @Override
    public List<UserExpVO> getExpList() {
        return userDAO.getExpList();
    }

    @Override
    public void insertPoint(Long userNum, Date pointDate, int pointValue, String pointHistory, String pointStatus) {
        userDAO.insertPoint(userNum, pointDate,pointValue,pointHistory,pointStatus);
    }

    @Override
    public List<UserPointVO> getPointList() {
        return userDAO.getPointList();
    }
    public List<UserVO> getUserList(UserFilter userFilter) {return userDAO.getUserList(userFilter);}
}
