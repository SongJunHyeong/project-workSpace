package com.project.workspace.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.bytebuddy.build.Plugin;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Generated;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "tbl_lounge_reply")
@ToString(exclude = {"loungeVO", "userVO"})
@Getter @Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class LoungeReplyVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lounge_reply_num")
    private Long loungeReplyNum;
    @Column(name = "lounge_reply_content")
    private String loungeReplyContent;
    @Column(name = "lounge_reply_date")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Generated(GenerationTime.INSERT)
    @Temporal(TemporalType.TIMESTAMP)
    private Date loungeReplyDate;
    @Column(name = "lounge_reply_status")
    private String loungeReplyStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lounge_num")
    private LoungeVO loungeVO;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_num")
    private UserVO userVO;

    @Builder
    public LoungeReplyVO(Long loungeReplyNum, String loungeReplyContent, String loungeReplyDate, LoungeVO loungeVO, UserVO userVO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.loungeReplyNum = loungeReplyNum;
        this.loungeReplyContent = loungeReplyContent;
        try {
            if(loungeReplyDate!=null){this.loungeReplyDate = sdf.parse(loungeReplyDate);}
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.loungeVO = loungeVO;
        this.userVO = userVO;
    }

}
