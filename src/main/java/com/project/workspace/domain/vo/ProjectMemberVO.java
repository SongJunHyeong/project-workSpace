package com.project.workspace.domain.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_project_member")
@Getter
@ToString(exclude = {"projectVO", "userVO"})
@NoArgsConstructor
public class ProjectMemberVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_num")
    private Long projectMemberNum;
    @Column(name = "project_part")
    private String projectPart;
    @Column(name = "project_motive")
    private String projectMotive;
    @Column(name = "project_apply_date")
    private Date projectApplyDate;
    @Column(name = "project_member_status")
    private String projectMemberStatus;

    @ManyToOne
    @JoinColumn(name = "project_num")
    private ProjectVO projectVO;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private UserVO userVO;

    @Builder
    public ProjectMemberVO(String projectPart, String projectMotive, String projectMemberStatus, ProjectVO projectVO, UserVO userVO) {
        this.projectPart = projectPart;
        this.projectMotive = projectMotive;
        this.projectMemberStatus = projectMemberStatus;
        this.projectVO = projectVO;
        this.userVO = userVO;
    }
}
