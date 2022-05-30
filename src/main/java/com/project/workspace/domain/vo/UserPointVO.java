package com.project.workspace.domain.vo;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Component
@Table(name = "tbl_user_point")
@Getter @Setter
@ToString(of = {"pointNum","pointDate","pointValue","pointHistory","pointStatus"})
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class UserPointVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_num")
    private Long pointNum;
    @Column(name = "point_date")
    @Generated(GenerationTime.INSERT)
    @Temporal(TemporalType.TIMESTAMP)
    private Date pointDate;
    @Column(name = "point_value")
    private int pointValue;
    @Column(name = "point_history")
    private String pointHistory;
    @Column(name = "point_status")
    private String pointStatus;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private UserVO userVO;

   @Builder
    public UserPointVO(int pointValue, String pointDate, String pointHistory, String pointStatus, UserVO userVO) {
       SimpleDateFormat sdf = new SimpleDateFormat();
        this.pointValue = pointValue;
       try {
           if(pointDate!=null){this.pointDate = sdf.parse(pointDate);}
       } catch (ParseException e) {;}
       this.pointHistory = pointHistory;
        this.pointStatus = pointStatus;
        this.userVO = userVO;
    }
}
