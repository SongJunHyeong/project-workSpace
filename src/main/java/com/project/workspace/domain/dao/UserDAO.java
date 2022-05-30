package com.project.workspace.domain.dao;

import com.project.workspace.domain.vo.*;
import com.project.workspace.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class UserDAO {
    private final UserMapper userMapper;

    public List<UserVO> getList(){return userMapper.getList();}
    public UserVO getInfo(Long userNum){return userMapper.getInfo(userNum);}

    public void insertPortfolio(Long userNum, String portUrl){userMapper.insertPortfolio(userNum,portUrl);}
    public List<UserPortfolioVO> getPortList(){return userMapper.getPortList();}
    public UserPortfolioVO getPortInfo(Long userNum){return userMapper.getPortInfo(userNum);}

    public void insertUserTag(Long userNum,String tagName){userMapper.insertUserTag(userNum,tagName);}
    public List<UserTagVO> getTagList(){return userMapper.getTagList();}

    public List<UserInterestVO> getInterestList(){return userMapper.getInterestList();}
    public void insertInterest(Long userNum, String interest){userMapper.insertInterest(userNum,interest);}

    public List<UserFollowVO> getFollowList(){return userMapper.getFollowList();}
    public void insertFollow(Long followerUser, Long followingUser){userMapper.insertFollow(followerUser,followingUser);}

    public List<UserAlertVO> getAlertList(){return userMapper.getAlertList();}
    public void insertAlert(Long userNum, String alertPart, Long numbers, String alertType){userMapper.insertAlert(userNum,alertPart,numbers,alertType);}

    public List<UserExpVO> getExpList(){return userMapper.getExpList();}
    public void insertExp(Long userNum, Date expDate, int expValue, String expHistory){userMapper.insertExp(userNum,expDate,expValue,expHistory);}

    public List<UserPointVO> getPointList(){return userMapper.getPointList();}
    public void insertPoint(Long userNum, Date pointDate, int pointValue, String pointHistory, String pointStatus){userMapper.insertPoint(userNum,pointDate,pointValue,pointHistory,pointStatus);}

    public List<UserVO> getUserList(UserFilter userFilter){return userMapper.getUserList(userFilter);}

}


