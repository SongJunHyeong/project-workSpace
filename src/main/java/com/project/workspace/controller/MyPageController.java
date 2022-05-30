package com.project.workspace.controller;

import com.project.workspace.domain.repository.*;
import com.project.workspace.domain.vo.*;
import com.project.workspace.service.StoryService;
import com.project.workspace.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;


@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

    private final UserRepository userRepository;
    private final UserPortfolioRepository userPortfolioRepository;
    private final UserTagRepository userTagRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserAlertRepository userAlertRepository;
    private final UserFollowRepository userFollowRepository;
    private final ProjectLikeRepository projectLikeRepository;
    private final UserExpRepository userExpRepository;
    private final UserPointRepository userPointRepository;
    private final StoryService storyService;
    private final StoryRepository storyRepository;
    private final ProjectRepository projectRepository;
//    private  final ProjectMemberRepository projectMemberRepository;

    private final UserService userService;


    @GetMapping("/myPage")
    public String myPage(Model model, HttpServletRequest request) {
        log.info("====================================================================");
        HttpSession session = request.getSession();
        Long userNum = (Long) session.getAttribute("userNum");
        UserVO userVO = userRepository.getById(userNum);
        List<ProjectVO> projectVO = projectRepository.findByUserVO(userVO);

//        UserVO userVO = userRepository.findById(userNum).get();
        List<UserPortfolioVO> userPortfolioVO = userPortfolioRepository.findByUserVO(userVO);
        List<UserTagVO> userTagVO = userTagRepository.findByUserVO(userVO);
        List<UserInterestVO> userInterestVO = userInterestRepository.findByUserVO(userVO);
        List<String> interests = new ArrayList<>();
        for (UserInterestVO a : userInterestVO) {
            interests.add(a.getInterest());
        }
        List<ProjectLikeVO> projectLikeVO = projectLikeRepository.findByUserVO(userVO);
        List<UserExpVO> userExpVO = userExpRepository.findByUserVO(userVO);
        List<UserPointVO> userPointVO = userPointRepository.findByUserVO(userVO);
        List<UserAlertVO> userAlertVO = userAlertRepository.findByUserVO(userVO);

        List<UserFollowVO> userFollowVO = userFollowRepository.findByFollowingUser(userNum);

        List<StoryVO> allStoryList = storyRepository.findByUserVO(userVO);

        List<ProjectVO> projectList = projectRepository.findByUserVO(userVO);
        List<ProjectLikeVO> projectLikeList = projectLikeRepository.findByUserVO(userVO);


        model.addAttribute("userVO", userVO);
        model.addAttribute("userPortfolioVO", userPortfolioVO);
        model.addAttribute("userTagVO", userTagVO);
        model.addAttribute("interests", interests);
        model.addAttribute("projectLikeVO", projectLikeVO);
        model.addAttribute("userExpVO", userExpVO);
        model.addAttribute("userPointVO", userPointVO);
        model.addAttribute("userFollowVO", userFollowVO);
        model.addAttribute("userAlertVO", userAlertVO);
        model.addAttribute("allStoryList", allStoryList);
        model.addAttribute("projectList", projectList);
        model.addAttribute("projectLikeList", projectLikeList);



        log.info("------------------------------------");
        log.info("==================유저================================");
        log.info(userVO.toString());
        log.info(userPointVO.toString());
        log.info(userAlertVO.toString());
        log.info(userFollowVO.toString());
        log.info("===================스토리================================");
        allStoryList.stream().forEach(storyVO -> log.info(storyVO.toString()));
        log.info("===================프로젝트================================");
        log.info(projectList.toString());
        log.info(projectLikeList.toString());
        log.info("------------------------------------");


        return "/myPage/myPage";
    }

    //   매개변수로 필요한 VO를 받는다
    @PostMapping("/modify")
    @Transactional
    public RedirectView userModify(HttpServletRequest request,UserVO userVO, UserTagVO userTagVO, UserPortfolioVO userPortfolioVO,UserInterestVO userInterestVO) {

        HttpSession session = request.getSession();
        userVO.setUserNum((Long) session.getAttribute("userNum"));
        System.out.println(userVO.toString());
        userRepository.save(userVO);
//
////        유저태그VO
        List<UserTagVO> userTagVOs = new ArrayList<>();
        userTagRepository.deleteAllByUserVO(userVO);
        String[] tagNames = userTagVO.getTagName().split(",");
        for (int i = 0; i < tagNames.length; i++) {
            UserTagVO tag = new UserTagVO();
            tag.setTagName(tagNames[i]);
            tag.setUserVO(userVO);
            userTagVOs.add(tag);
        }
        Stream.of(userTagVOs).map(user -> user.toString()).forEach(log::info);
        userTagVOs.stream().forEach(user -> userTagRepository.save(user));
//
//        //        유저 포트폴리오
        List<UserPortfolioVO> userPort = new ArrayList<>();
        userPortfolioRepository.deleteByUserVO(userVO);
        String[] portUrls = userPortfolioVO.getPortUrl().split(",");
        for (int i = 0; i < portUrls.length; i++) {
            UserPortfolioVO urls = new UserPortfolioVO();
            urls.setPortUrl(portUrls[i]);
            urls.setUserVO(userVO);
            userPort.add(urls);
        }

        Stream.of(userPort).map(port -> port.toString()).forEach(log::info);
//        userPort.stream().forEach(portfolioVO -> userPortfolioRepository.save(portfolioVO));
        userPort.stream().forEach(port -> userPortfolioRepository.save(port));
//
//
////        유저관심사 ->interests이거안뽑힘
        List<UserInterestVO> interests = new ArrayList<>();
        log.info("list"+interests.toString());
        userInterestRepository.deleteByUserVO(userVO);
        log.info("inte"+userInterestVO.toString());
        String[] userInterest = userInterestVO.getInterest().split(",");

        for (int i = 0; i < userInterest.length; i++) {
            UserInterestVO interestName = new UserInterestVO();
            interestName.setInterest(userInterest[i]);
            interestName.setUserVO(userVO);
            interests.add(interestName);
        }
        Stream.of(interests).map(interest -> interest.toString()).forEach(log::info);
        interests.stream().forEach(interest -> userInterestRepository.save(interest));


//

        return new RedirectView("myPage");
//        return "/myPage/myPage";
    }




}

//        userPointVO[i].pointDate;

//        List<UserPointVO> pointTables = new ArrayList<>();
//        for(int i=1; i<=pointTables.size(); i++){ //반복문을 수행하면서 리스트에 데이터 삽입
//            Date pointDate = Date.valueOf(i) +  "[pointDate]";;
//            int pointValue = pointTables.valueOf(i) + "[pointValue]";
//            String pointHistory = String.valueOf(i) + "[pointHistory]";
//            String pointStatus = String.valueOf(i) + "[pointStatus]";
//
//            //객체를 리스트에 삽입
//            UserPointVO userPointVOs = new UserPointVO(pointDate, pointValue, pointHistory, pointStatus);
//            pointTables.add(userPointVOs);
//        }


//    @PostMapping("/myPage")
//    public String myPage(UserVO userVO, Model model){
//        log.info("------------------------------------");
//        log.info(userVO.toString());
//        log.info("------------------------------------");
//
//        return "/myPage/myPage";
//    }

//    @PostMapping("/mypage")
//    public String userEdit(Long userNum) {
//        UserVO userVO = userRepository.findById(userNum).get();
////        List<UserPortfolioVO> userPortfolioVO = userPortfolioRepository.findByUserVO(userVO);
////        List<UserTagVO> userTagVO = userTagRepository.findByUserVO(userVO);
////        List<UserInterestVO> userInterestVO = userInterestRepository.findByUserVO(userVO);
////        List<String> interests = new ArrayList<>();
////        for (UserInterestVO a : userInterestVO){
////            interests.add(a.getInterest());
////        }
////        model.addAttribute("userVO", userVO);
////        model.addAttribute("userPortfolioVO", userPortfolioVO);
////        model.addAttribute("userTagVO", userTagVO);
////        model.addAttribute("interests", interests);
//
//
////        userRepository.(userVO.getUsername(), form.getName(), form.getEmail());
////        userVO.save(userVO.getUserNickName(),userVO.getUserPhone(),userVO.getUserMainSkill(),userVO.getUserMainDetail(),userVO.getUserMainLevel(),
////        userVO.getUserSubSkill(),userVO.getUserSubDetail(),userVO.getUserSubLevel(),userVO.getUserOnOff(),userVO.getUserTime(),userVO.getUserContent(),
////        userVO.getUserPrice(),userVO.getUserImgName(),userVO.getUserImgPath(),userVO.getUserImgUuid());
////        currentMember.setUsername(form.getName());
////        currentMember.setEmail(form.getEmail());
//
//        userVO.setUserNickName(userVO.getUserNickName()); //닉네임
//        userVO.setUserPhone(userVO.getUserPhone()); //폰
//        userVO.setUserMainSkill(userVO.getUserMainSkill()); //메인스킬
//        userVO.setUserMainDetail(userVO.getUserMainDetail()); //메인스킬상세
//        userVO.setUserMainLevel(userVO.getUserMainLevel()); //메인스킬레벨
//        userVO.setUserSubSkill(userVO.getUserSubSkill());  //세부스킬
//        userVO.setUserSubDetail(userVO.getUserSubDetail()); //세부상세
//        userVO.setUserSubLevel(userVO.getUserSubLevel()); //세부스킬레벨
//        userVO.setUserPrice(userVO.getUserPrice()); //대화가격
//        userVO.setUserOnOff(userVO.getUserOnOff()); //오프라인온라인
//        userVO.setUserTime(userVO.getUserTime()); //시간
//        userVO.setUserContent(userVO.getUserContent()); //소개
//        userVO.setUserImgName(userVO.getUserImgName());
//        userVO.setUserImgPath(userVO.getUserImgPath());
//        userVO.setUserImgUuid(userVO.getUserImgUuid());
//
//        return "/myPage/myPage";
//    }

//    @PostMapping("/modify")
//    public RedirectView modify(UserVO userVO, Criteria criteria, RedirectAttributes rttr){
//        String result = null;
//
////        Redirect로 전송 시
////        addAttribute()를 사용하면 컨트롤러에 파라미터가 전달되고 그걸 통해서 화면으로 간다.
////        addFlashAttribute()를 사용하면 컨트롤러에 전달되지 않고 바로 화면으로 간다.
//        rttr.addAttribute("result", userService.modify(userVO) ? "success" : "failure");
//        rttr.addAttribute("pageNum", criteria.getPageNum());
//        rttr.addAttribute("amount", criteria.getAmount());
//        rttr.addAttribute("type", criteria.getType());
//        rttr.addAttribute("keyword", criteria.getKeyword());
//        return new RedirectView("list");
//    }

//    @PostMapping("/modify")
//    public String userEdit(UserVO.UserForm form, BindingResult result, @AuthenticationPrincipal User currentMember) {
//        if(result.hasErrors()) {
//            return "redirect:/modify";
//        }
//
//        userService.updateInfo(currentMember.getUsername(), form.getName(), form.getEmail());
//        currentMember.setUsername(form.getName());
//        currentMember.setEmail(form.getEmail());
//
//        return "redirect:/modify";
//    }




//        유저관심사
//        List<UserInterestVO> interests = new ArrayList<>();
//        userInterestRepository.deleteAllByUserVO(userVO);
//        String[] uginterest = userInterestVO.getInterest().split(",");
//        for (int i = 0; i < uginterest.length; i++) {
//            UserInterestVO interestName = new UserInterestVO();
//            interestName.setInterest(uginterest[i]);
//            interestName.setUserVO(userVO);
//            interests.add(interestName);
//        }
//        Stream.of(interests).map(user -> user.toString()).forEach(log::info);
//        interests.stream().forEach(user -> userInterestRepository.save(user));

//       UserVO userVos = userService.getInfo(1L);
//        userVO = userService.getInfo(1L);
//        userVO.setUserId(userVO.getUserId());
//        userVO.setUserNickName(userVO.getUserNickName()); //닉네임
//        userVO.setUserPhone(userVO.getUserPhone()); //폰
//        userVO.setUserMainSkill(userVO.getUserMainSkill()); //메인스킬
//        userVO.setUserMainDetail(userVO.getUserMainDetail()); //메인스킬상세
//        userVO.setUserMainLevel(userVO.getUserMainLevel()); //메인스킬레벨
//        userVO.setUserSubSkill(userVO.getUserSubSkill());  //세부스킬
//        userVO.setUserSubDetail(userVO.getUserSubDetail()); //세부상세
//        userVO.setUserSubLevel(userVO.getUserSubLevel()); //세부스킬레벨
//        userVO.setUserPrice(userVO.getUserPrice()); //대화가격
//        userVO.setUserOnOff(userVO.getUserOnOff()); //오프라인온라인
//        userVO.setUserTime(userVO.getUserTime()); //시간
//        userVO.setUserContent(userVO.getUserContent()); //소개
//        userVO.setUserImgName(userVO.getUserImgName());
//        userVO.setUserImgPath(userVO.getUserImgPath());
//        userVO.setUserImgUuid(userVO.getUserImgUuid());

//        model.addAttribute("userVO", userVos);


//    @RequestMapping(value = "/modify", method = RequestMethod.POST)
//    public void userUpdate(UserVO userVO, Model model) {
//        System.out.println(userVO.getUserName());
//        System.out.println(userVO.getUserId());
//    }

//    List<UserPortfolioVO> userPorts = userPortfolioRepository.findByUserVO(userVO);
//    List<UserPortfolioVO> userPort = new ArrayList<>();
//    String[] portUrls = userPortfolioVO.getPortUrl().split(",");
//        for(
//                int i = 0;
//                i<portUrls.length;i++)
//
//        {
//        if (i < userPorts.size()) {
//        UserPortfolioVO urls = userPorts.get(i);
//        urls.setPortUrl(portUrls[i]);
//        userPort.add(urls);
//        } else if (i > userPorts.size() - 1) {
//        UserPortfolioVO urls = new UserPortfolioVO();
//        urls.setPortUrl(portUrls[i]);
//        urls.setUserVO(userVO);
//        userPort.add(urls);
//        }
//        }
//
//        log.info(""+userPort.toString());
//        System.out.println(userPort.toString());
//        Stream.of(userPorts).
//
//        map(port ->port.toString()).
//
//        forEach(log::info);
//        userPorts.stream().
//
//        forEach(portfolioVO ->userPortfolioRepository.save(portfolioVO));
//
//
//        userPortfolioRepository.save(userPortfolioVO);
//
//    @PostMapping("/modify")
//    @Transactional
////    유저 브이오, 유저태그를 받는다.
//    public RedirectView userModify(UserVO userVO, UserTagVO userTagVO, UserPortfolioVO userPortfolioVO) {
//        //유저브이오에서 유저넘을 셋해준다.
//        userVO.setUserNum(1L);
////        userPortfolioVO.getUserVO(userPortfolioVO.setPortNum(1L));
//        //출력한번해보고
//        System.out.println(userRepository.save(userVO).toString());
////        userRepository.save(userVO);
//
////        유저태그VO
//        //리스트타입의 유저태그브이오를 userTags로 담아서 내용에 유저태그레포지토리에있는findbyuservo에서uservo를받는것을 선언.
////        List<UserTagVO> userTags = userTagRepository.findByUserVO(userVO);
//        List<UserTagVO> userTagVOs = new ArrayList<>();
//        userTagRepository.deleteAllByUserVO(userVO);
//        String[] tagNames = userTagVO.getTagName().split(",");
//        for (int i = 0; i < tagNames.length; i++) {
//            UserTagVO tag = new UserTagVO();
//            tag.setTagName(tagNames[i]);
//            tag.setUserVO(userVO);
//            userTagVOs.add(tag);
//        }
//
//        Stream.of(userTagVOs).map(user ->user.toString()).forEach(log::info);
//        userTagVOs.stream().forEach(user -> userTagRepository.save(user));


        //        유저 포트폴리오
//        List<UserPortfolioVO> userPort = new ArrayList<>();
//        userPortfolioRepository.deleteAllByUserVO(userVO);
//        String[] portUrls = userPortfolioVO.getPortUrl().split(",");
//        for( int i = 0;i<portUrls.length;i++){
//            UserPortfolioVO urls = new UserPortfolioVO();
//            urls.setPortUrl(portUrls[i]);
//            urls.setUserVO(userVO);
//            userPort.add(urls);
//        }
//
//        Stream.of(userPort).map(port ->port.toString()).forEach(log::info);
//
//        userPort.stream().forEach(portfolioVO ->userPortfolioRepository.save(portfolioVO));
//
//
//        userPortfolioRepository.save(userPortfolioVO);
//
//
//        return new RedirectView("myPage?userNum=1");