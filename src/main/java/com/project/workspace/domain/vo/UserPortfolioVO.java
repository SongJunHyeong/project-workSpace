package com.project.workspace.domain.vo;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Table(name = "tbl_portfolio")
@Getter
@Setter
@ToString(of = {"portNum","portUrl"})
@NoArgsConstructor
public class UserPortfolioVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "port_num")
    private Long portNum;
    @Column(name = "port_url")
    private String portUrl;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private UserVO userVO;

    @Builder
    public UserPortfolioVO(String portUrl, UserVO userVO) {
        this.portUrl = portUrl;
        this.userVO = userVO;
    }
}
