package com.project.workspace.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Table(name = "tbl_story_tag")
@ToString(of = {"storyTagNum","tagName"})
@Getter @Setter
@NoArgsConstructor
public class StoryTagVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_tag_num")
    private Long storyTagNum;
    @Column(name = "tag_name")
    private String tagName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "story_num")
    private StoryVO storyVO;

    @Builder
    public StoryTagVO(String tagName, StoryVO storyVO) {
        this.tagName = tagName;
        this.storyVO = storyVO;
    }
}
