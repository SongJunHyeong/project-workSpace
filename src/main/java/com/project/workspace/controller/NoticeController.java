package com.project.workspace.controller;

import com.project.workspace.domain.repository.NoticeRepository;
import com.project.workspace.domain.vo.NoticeVO;
import com.project.workspace.domain.vo.PageableDTO;
import com.project.workspace.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/notice/*")
public class NoticeController {

    private final NoticeRepository noticeRepository;
    private final NoticeService noticeService;

    @GetMapping("/faq")
    public void faq(){

    }

    @GetMapping("/noticeList")
    public void noticeList(@PageableDefault(page = 0, size = 10, sort = "noticeNum", direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<NoticeVO> noticeVOs = noticeService.noticeList(pageable);
        PageableDTO pageableDTO = new PageableDTO(pageable,(int)noticeVOs.getTotalElements());
        model.addAttribute("noticeVOS", noticeVOs);
        model.addAttribute("pageableDTO", pageableDTO);
    }

    @GetMapping("/noticeDetail")
    public void noticeDetail(@RequestParam("noticeNum") Long noticeNum, Model model){
        NoticeVO noticeVO = noticeRepository.findById(noticeNum).get();

        model.addAttribute("noticeVO", noticeVO);
    }

    @GetMapping("/noticeRegister")
    public void noticeRegister(){

    }

    @PostMapping("/noticeRegister")
    public RedirectView noticeRegister(NoticeVO noticeVO, RedirectAttributes rttr){
        Long noticeNum = noticeRepository.save(noticeVO).getNoticeNum();
        rttr.addAttribute("noticeNum", noticeNum);
        return new RedirectView("noticeDetail");
    }

    @GetMapping("/noticeUpdate")
    public void noticeUpdate(){

    }
}
