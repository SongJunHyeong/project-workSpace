package com.project.workspace.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.workspace.domain.repository.UserRepository;
import com.project.workspace.domain.vo.KakaoProfile;
import com.project.workspace.domain.vo.OAuthToken;
import com.project.workspace.domain.vo.ProjectVO;
import com.project.workspace.domain.vo.UserVO;
import com.project.workspace.service.UserService;
import com.project.workspace.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

//@RestController
@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    private final UserRepository userRepository;

//    private final UserService userService;


//    KakaoAPI kakaoApi = new KakaoAPI();
//
////    쿼리스트링 code의 값은 매개변수로 쉽게 받을 수 있다.
//    @RequestMapping(value="/login")
//    public ModelAndView login(@RequestParam("code") String code, HttpSession session) {
//        ModelAndView mav = new ModelAndView();
//        // 1번 인증코드 요청 전달
//        String accessToken = kakaoApi.getAccessToken(code);
//        // 2번 인증코드로 토큰 전달
//        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
//
//        System.out.println("login info : " + userInfo.toString());
//
//        if(userInfo.get("email") != null) {
//            session.setAttribute("userId", userInfo.get("email"));
//            session.setAttribute("accessToken", accessToken);
//        }
//        mav.addObject("userId", userInfo.get("email"));
//        mav.setViewName("index");
//        return mav;
//    }
//
//    @RequestMapping(value="/logout")
//    public ModelAndView logout(HttpSession session) {
//        ModelAndView mav = new ModelAndView();
//
//        kakaoApi.kakaoLogout((String)session.getAttribute("accessToken"));
//        session.removeAttribute("accessToken");
//        session.removeAttribute("userId");
//        mav.setViewName("index");
//        return mav;
//    }

    @GetMapping("/login")
    public String kakaoLogin(String code, Model model){ //Data를 리턴해주는 컨트롤러

        //POST방식으로 key = value 데이터를 요청 (카카오쪽으로)
        //RestTemplate란 Rest API 호출이후 응답을 받을 때까지 기다리는 동기 방식
        //HTTP 서버와의 통신을 단순화하고 Restful 원칙을 지킨다. (json, xml을 쉽게 응답 답을수 있음.)
        RestTemplate rt = new RestTemplate();

//        HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

//        HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "fd3ba5c3c1994f15929fb85ba0dbe764");
        params.add("redirect_uri", "http://localhost:7777/login");
        params.add("code", code);

        //HttpBody값과 HttpHeader를 하나의 오브젝트에 담기.
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

//        Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity response = rt.exchange(
          "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue((String)(response.getBody()), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

//        System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+ oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        //Http 요청하기 - Post방식으로 - 그리고 response변수의 응답 받음.
        ResponseEntity response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );

//        System.out.println(response2.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue((String)(response2.getBody()), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        //User 오브젝트 :
        System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
        System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        System.out.println("카카오 닉네임 : " + kakaoProfile.getKakao_account().getProfile().getNickname());
        System.out.println("카카오 프로필사진 : " + kakaoProfile.getProperties().getProfile_image());

        System.out.println("워크스페이스 유저네임 : " + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
        System.out.println("워크스페이스 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        UUID uuid = UUID.randomUUID();
        String userCode= uuid.toString().split("-")[0];
        System.out.println("워크스페이스 패스워드 : "+userCode);
//        UserVO userVO = new UserVO();
//        userVO.setUserId(kakaoProfile.getKakao_account().getEmail());
//        userVO.setUserNick_name(kakaoProfile.getKakao_account().getProfile().getNickname());
//        userVO.setUserImgName(kakaoProfile.getProperties().getProfile_image());

//        userRepository.save(UserVO.builder().userId(kakaoProfile.getKakao_account().getEmail()));
//        List<UserVO> users = userRepository.findAll();
//        ArrayList<String> userCodes = new ArrayList<>();
//        for (int i=0;i<users.size();i++){
//            userCodes.add(users.get(i).getUserCode());
//        }

//        HashSet<String> set = new HashSet<String>(Arrays.asList(userCodes))


//        userVO.setUserCode(userCode);

//       userRepository.save(userVO);

       model.addAttribute("userId", kakaoProfile.getKakao_account().getEmail());
       model.addAttribute("userNickName", kakaoProfile.getKakao_account().getProfile().getNickname());
       model.addAttribute("userCode", userCode);
       model.addAttribute("userProfileURL", kakaoProfile.getProperties().getProfile_image());

//        if(정보가 있다면){
//            return "main/index";
//        }

        return "user/joinForm";
    }

    @PostMapping("/joinForm")
    public String join(UserVO userVO){
        System.out.println(userVO.toString());
        userRepository.save(userVO);

       return "user/joinSuccess";
    }

//    @PostMapping("/uploadAjaxAction")
//    @ResponseBody
//    public UserVO uploadAjaxPost(MultipartFile uploadFile) {
//        String uploadFolder = "C:/upload";
//        UserVO userVO = new UserVO();
//
//        System.out.println("11111111111111111111111");
////        UUID(Universally unique identifier) : 범용 고유 식별자
////        네트워크 상에서 각각의 개체들을 식별하기 위하여 사용되었다.
////        중복될 가능성이 거의 없다고 인정되기 때문에 많이 사용된다.
////        UUID의 개수는 10의 38승입니다.
//
//        UUID uuid = UUID.randomUUID();
//        String uploadFileName = null;
//
//        String uploadFolderPath = getPath();
//        File uploadPath = new File(uploadFolder, uploadFolderPath);
//        if (!uploadPath.exists()) {
//            uploadPath.mkdirs();
//        }
//        log.info("-------------------------");
//        log.info("Upload File Name : " + uploadFile.getOriginalFilename());
//        log.info("Upload File Path : " + uploadFolderPath);
//        log.info("Upload File Size : " + uploadFile.getSize());
//
//        uploadFileName = uploadFile.getOriginalFilename();
//
//        System.out.println("22222222222222222222222222");
//
//        userVO.setUserImgName(uploadFileName);
//        userVO.setUserImgUuid(uuid.toString());
//        userVO.setUserImgPath(uploadFolderPath);
//
//        //저장할 경로와 파일의 이름을 File객체에 담는다.
//        File saveFile = new File(uploadPath, uuid.toString() + "_" + uploadFileName);
//
//        System.out.println("33333333333333333333333333333");
//
//        try {
//            //설정한 경로에 해당 파일을 업로드한다.
//            uploadFile.transferTo(saveFile);
//
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//
//        System.out.println("444444444444444444444444");
//        return userVO;
//    }
//
//    @GetMapping("/display")
//    @ResponseBody
//    public byte[] getFile(String fileName) throws IOException {
//        return FileCopyUtils.copyToByteArray(new File("C:/upload/" + fileName));
//    }
//
//    private String getPath() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        Date today = new Date();
//        return sdf.format(today);
//    }
}
