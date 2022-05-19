package com.project.workspace.domain.vo;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Component
@Table(name = "tbl_user")
@Getter
@Setter
@ToString(exclude = {"ports", "tags", "alerts", "interests", "users", "projects", "projectMembers","projectLikes","stories","replies","series","storyReplies","studies","studyMembers","lounges","loungeLikes" ,"loungeReplies","exps","points"})
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class UserVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_num")
    private Long userNum;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_content")
    private String userContent;
    @Column(name = "user_gender")
    private String userGender;
    @Column(name = "user_location")
    private String userLocation;
    @Column(name = "user_nick_name")
    private String userNickName;
    @Column(name = "user_phone")
    private String userPhone;
    @Column(name = "user_main_skill")
    private String userMainSkill;
    @Column(name = "user_main_detail")
    private String userMainDetail;
    @Column(name = "user_main_level")
    private String userMainLevel;
    @Column(name = "user_sub_skill")
    private String userSubSkill;
    @Column(name = "user_sub_detail")
    private String userSubDetail;
    @Column(name = "user_sub_level")
    private String userSubLevel;
    @Column(name = "user_on_off")
    private String userOnOff;
    @Column(name = "user_time")
    private String userTime;
    @Column(name = "user_code")
    private String userCode;
    @Column(name = "user_level")
    private Long userLevel;
    @Column(name = "user_exp")
    private Long userExp;
    @Column(name = "user_point")
    private Long userPoint;
    @Column(name = "user_price")
    private Long userPrice;
    @Column(name = "social_type")
    private String socialType;
    @Column(name = "user_status")
    private String userStatus;
    @Column(name = "user_img_uuid")
    private String userImgUuid;
    @Column(name = "user_img_name")
    private String userImgName;
    @Column(name = "user_img_path")
    private String userImgPath;

    @OneToMany(mappedBy = "userVO")
    private List<UserPortfolioVO> ports = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<UserTagVO> tags = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<UserAlertVO> alerts = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<UserInterestVO> interests = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "following_user")
    private List<UserFollowVO> users = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<ProjectVO> projects = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<ProjectMemberVO> projectMembers = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<ProjectLikeVO> projectLikes = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<StoryVO> stories = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<StoryReplyVO> replies = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<StorySeriesVO> series = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<StoryLikeVO> storyReplies = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<StudyVO> studies = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<StudyMemberVO> studyMembers = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<LoungeVO> lounges = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<LoungeLikeVO> loungeLikes = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<LoungeReplyVO> loungeReplies = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<UserExpVO> exps = new ArrayList<>();
    @OneToMany(mappedBy = "userVO")
    private List<UserPointVO> points = new ArrayList<>();

    @Builder
    public UserVO(String userId, String userContent, String userGender, String userLocation, String userNickName, String userPhone, String userMainSkill, String userMainDetail, String userMainLevel, String userSubSkill, String userSubDetail, String userSubLevel, String userOnOff, String userTime, Long userPrice, String socialType, String userStatus, String userImgUuid, String userImgName, String userImgPath, String userCode) {
        this.userId = userId;
        this.userContent = userContent;
        this.userGender = userGender;
        this.userLocation = userLocation;
        this.userNickName = userNickName;
        this.userPhone = userPhone;
        this.userMainSkill = userMainSkill;
        this.userMainDetail = userMainDetail;
        this.userMainLevel = userMainLevel;
        this.userSubSkill = userSubSkill;
        this.userSubDetail = userSubDetail;
        this.userSubLevel = userSubLevel;
        this.userOnOff = userOnOff;
        this.userTime = userTime;
        this.userPrice = userPrice;
        this.socialType = socialType;
        this.userStatus = userStatus;
        this.userImgUuid = userImgUuid;
        this.userImgName = userImgName;
        this.userImgPath = userImgPath;
        this.userCode = userCode;
    }

}