package com.project.workspace.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "tbl_userAlert")
@Getter @Setter
@ToString(exclude = {"userVO"})
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class UserAlertVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_num")
    private Long alertNum;
    @Column(name = "alert_part")
    private String alertPart;
    @Column(name = "numbers")
    private Long numbers;
    @Column(name = "alert_content")
    private String alertContent;
    @Column(name = "alert_date")
    @Generated(GenerationTime.INSERT)
    @Temporal(TemporalType.TIMESTAMP)
    private Date alertDate;



    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_num")
    private UserVO userVO;

    @Builder
    public UserAlertVO(String alertPart, Long numbers, String alertContent, UserVO userVO, String alertDate) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        this.alertPart = alertPart;
        this.numbers = numbers;
        this.alertContent = alertContent;
        this.userVO = userVO;
        try {
            if(alertDate!=null){this.alertDate = sdf.parse(alertDate);}
        } catch (ParseException e) {;}
    }
}
