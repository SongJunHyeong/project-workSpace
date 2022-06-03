package com.project.workspace.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
public class StoryReplyDTO {
    private List<UserVO> userVOs;
    private List<StoryReplyVO> replies;

    public StoryReplyDTO() {;}
}
