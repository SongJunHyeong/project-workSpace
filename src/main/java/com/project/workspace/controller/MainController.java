package com.project.workspace.controller;

import com.project.workspace.domain.repository.ProjectRepository;
import com.project.workspace.domain.repository.UserRepository;
import com.project.workspace.domain.vo.ProjectFilter;
import com.project.workspace.domain.vo.ProjectVO;
import com.project.workspace.domain.vo.UserFilter;
import com.project.workspace.domain.vo.UserVO;
import com.project.workspace.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/main/*")
public class MainController {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final UserServiceImpl userService;

    @GetMapping("/admin")
    public void admin(){

    }
    @GetMapping("/index")
    public void index(Model model, HttpServletRequest req){

        List<ProjectVO> projectList = projectRepository.findTop4ByOrderByProjectNumDesc();

        model.addAttribute("projectList",projectList);
    }
    @GetMapping("/userDetail")
    public String userDetail(Long userNum, Model model){
        UserVO userVO = userRepository.getById(userNum);
        log.info(userVO.toString());
        model.addAttribute("user",userVO);
        return "main/userDetail";
    }
    @GetMapping("/userList")
    public void userList(Model model){
        Random random = new Random();
        UserVO todayPick = userRepository.findTop8ByOrderByUserNumDesc().get(random.nextInt(8));
        List<UserVO> userList = userRepository.findTop12ByOrderByUserNumDesc();

        model.addAttribute("todayPick",todayPick);
        model.addAttribute("userList",userList);
    }

    @PostMapping("/userFilter")
    @ResponseBody
    public List<UserVO> userFilter(UserFilter userFilter) {
        log.info(userFilter.toString());
        List<UserVO> userList = userService.getUserList(userFilter);

        return userList;
    }

    @GetMapping("/privacyTerm")
    public void privacyTerm(){

    }
    @GetMapping("/refundTerm")
    public void refundTerm(){

    }
    @GetMapping("/serviceTerm")
    public void serviceTerm(){

    }
    @GetMapping("/tradeTerm")
    public void tradeTerm(){

    }
}
