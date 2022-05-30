package com.project.workspace.domain.vo;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Table(name = "tbl_user_tag")
@Getter @Setter
@ToString(of = {"tagNum","tagName"})
@NoArgsConstructor
public class UserTagVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_num")
    private Long tagNum;
    @Column(name = "tag_name")
    private String tagName;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private UserVO userVO;

   @Builder
    public UserTagVO(String tagName, UserVO userVO) {
        this.tagName = tagName;
        this.userVO = userVO;
    }
}
