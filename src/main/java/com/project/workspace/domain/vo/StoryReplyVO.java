package com.project.workspace.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Component
@Table(name = "tbl_story_reply")
@ToString(exclude = {"userVO","storyVO"})
@Getter @Setter
@NoArgsConstructor
@DynamicInsert
public class StoryReplyVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_reply_num")
    private Long storyReplyNum;
    @Column(name = "story_reply")
    private String storyReply;
    @Column(name = "reply_time")
    @Generated(GenerationTime.INSERT)
    @Temporal(TemporalType.TIMESTAMP)
    private Date replyTime;
    @Column(name = "status")
    private String status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_num")
    private UserVO userVO;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "story_num")
    private StoryVO storyVO;

    @Builder
    public StoryReplyVO(Long storyReplyNum, String storyReply, UserVO userVO, StoryVO storyVO, String replyTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.storyReplyNum = storyReplyNum;
        this.storyReply = storyReply;
        this.userVO = userVO;
        this.storyVO = storyVO;
        try {
            if(replyTime!=null){this.replyTime = sdf.parse(replyTime);}
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
