package com.project.workspace.domain.vo;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Table(name = "tbl_user_interest")
@Getter
@Setter
@ToString(of = {"interestNum","interest"})
@NoArgsConstructor

public class UserInterestVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_num")
    private Long interestNum;
    @Column(name = "interest")
    private String interest;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private UserVO userVO;

    @Builder
    public UserInterestVO(String interest, UserVO userVO) {
        this.interest = interest;
        this.userVO = userVO;
    }
}
