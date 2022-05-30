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
@Table(name = "tbl_user_exp")
@Getter @Setter
@ToString(of = {"expNum","expDate","expValue","expHistory"})
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class UserExpVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exp_num")
    private Long expNum;
    @Column(name = "exp_date")
    @Generated(GenerationTime.INSERT)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expDate;
    @Column(name = "exp_value")
    private int expValue;
    @Column(name = "exp_history")
    private String expHistory;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private UserVO userVO;

   @Builder
    public UserExpVO( int expValue,String expDate, String expHistory, UserVO userVO) {
       SimpleDateFormat sdf = new SimpleDateFormat();
        this.expValue = expValue;
       try {
           if(expDate!=null){this.expDate = sdf.parse(expDate);}
       } catch (ParseException e) {;}
        this.expHistory = expHistory;
        this.userVO = userVO;
    }
}
