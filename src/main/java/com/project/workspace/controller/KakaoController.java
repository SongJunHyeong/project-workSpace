package com.project.workspace.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.workspace.domain.repository.UserInterestRepository;
import com.project.workspace.domain.repository.UserPortfolioRepository;
import com.project.workspace.domain.repository.UserRepository;
import com.project.workspace.domain.vo.*;
import com.project.workspace.service.KakaoLogOut;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@RestController
@Controller
@RequiredArgsConstructor // 컨트롤러 만들때 꼭 넣어주는걸로 합시다.
@Slf4j
public class KakaoController {

    private final UserRepository userRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserPortfolioRepository userPortfolioRepository;
    private final KakaoLogOut kakaoLogOut;



    @GetMapping("/login")
    public RedirectView kakaoLogin(String code, Model model, HttpServletRequest req, RedirectAttributes rttr){ //Data를 리턴해주는 컨트롤러

        //POST방식으로 key = value 데이터를 요청 (카카오쪽으로)
        //RestTemplate란 Rest API 호출이후 응답을 받을 때까지 기다리는 동기 방식
        //HTTP 서버와의 통신을 단순화하고 Restful 원칙을 지킨다. (json, xml을 쉽게 응답 답을수 있음.)
        RestTemplate rt = new RestTemplate();

//        URL을 변수로써 상황에 맞게 달라지게한다.
        String url = "";

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
        HttpSession session = req.getSession();
        HttpHeaders headers2 = new HttpHeaders();
        session.setAttribute("access_Token", oauthToken.getAccess_token());
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


        UUID uuid = UUID.randomUUID();
        String userCode= uuid.toString().split("-")[0];


        rttr.addFlashAttribute("userId", kakaoProfile.getKakao_account().getEmail());
        rttr.addFlashAttribute("userNickName", kakaoProfile.getKakao_account().getProfile().getNickname());
        rttr.addFlashAttribute("userCode", userCode);
        rttr.addFlashAttribute("userProfileURL", kakaoProfile.getProperties().getProfile_image());

//       유저 넘버를 가져올 방법이 닉네임으로 db에서 셀렉트하는 방법밖에 없다.
        String userId = kakaoProfile.getKakao_account().getEmail();


        UserVO userVO = userRepository.findByUserId(userId);

        if(userVO == null){

            url = "joinForm";

        }else{

            session.setAttribute("userNum", userVO.getUserNum());
            session.setAttribute("profile", userVO);
            String userNickName = userVO.getUserNickName();
            model.addAttribute("userNickName", userNickName);


            url = "main/index";
        }

        return new RedirectView(url);
    }

    @PostMapping("/joinForm")
    public String join(UserVO userVO, Model model, HttpServletRequest req){
        System.out.println(userVO.toString());
        userRepository.save(userVO);
        List<Long> userNumList = userRepository.findAll().stream().map(UserVO::getUserNum).collect(Collectors.toList());
        Collections.reverse(userNumList);
        Long userNum = userNumList.get(0);
//        log.info(userNum.toString());
//        log.info(userNumList.toString());

        model.addAttribute("userNum", userNum);

        HttpSession session = req.getSession();

        session.setAttribute("userNum", userNum);

        return "user/joinSuccess";
    }

    @PostMapping("/joinSuccess")
    public String joinUpdate(Long userNum, String userLocation, String userOnOff, String userTime, String interest, Model model){
        log.info("유저넘버입니다~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+userNum);
        log.info("유저관심사입니다~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + interest);
        UserVO userVO = userRepository.findById(userNum).get();
        log.info("유저브이오 투스트링입니다~~~~~~~~~~~~~~~" + userVO.toString());
        userVO.setUserLocation(userLocation);
        userVO.setUserOnOff(userOnOff);
        userVO.setUserTime(userTime);
        userVO.setUserPoint(200l);
        userRepository.save(userVO);
        String[] interestAr = interest.split(",");
        for (int i = 0; i<interestAr.length; i++) {
            userInterestRepository.save(UserInterestVO.builder().interest(interestAr[i]).userVO(userVO).build());
        }

        model.addAttribute("userNum", userNum);

//        userVO.setUserLocation();
//        userVO.setUserTime();



        return "user/joinPlus";
    }

    @PostMapping("/joinPlus")
    public String joinUpdateFinal(Long userNum, String userSubSkill, String userSubDetail, String userSubLevel, Long userPrice, String userContent, String portUrl, HttpServletRequest req){
        UserVO userVO = userRepository.findById(userNum).get();
        log.info("유저 서브 스킬~~~~~~~" + userSubSkill);
        log.info("유저 서브 디테일~~~~~~~" + userSubDetail);
        log.info("유저 서브 레벨~~~~~~~" + userSubLevel);
        log.info("유저 프라이스~~~~~~~" + userPrice);
        log.info("유저 컨텐트~~~~~~~" + userContent);
        log.info("유저 포트폴리오~~~~~~~" + portUrl);
        userVO.setUserSubSkill(userSubSkill);
        userVO.setUserSubDetail(userSubDetail);
        userVO.setUserSubLevel(userSubLevel);
        userVO.setUserPrice(userPrice);
        userVO.setUserContent(userContent);
        userVO.setUserPoint(400l);
        userVO.setSocialType("0");
        userRepository.save(userVO);

        String[] portUrlAr = portUrl.split(",");
        for (int i = 0; i<portUrlAr.length; i++) {
            log.info(portUrlAr[i]);
            userPortfolioRepository.save(UserPortfolioVO.builder().portUrl(portUrlAr[i]).userVO(userVO).build());
        }

        HttpSession session = req.getSession();
        session.setAttribute("userNum",userNum);

        return "main/index";
    }

    @GetMapping("/joinLater")
    public RedirectView joinLater(Long userNum, HttpServletRequest req){
        HttpSession session = req.getSession();
        session.setAttribute("userNum",userNum);
        return new RedirectView("main/index");
    }

    //    로그아웃 컨트롤러
    @GetMapping("/logOut")
    public RedirectView logOut(HttpServletRequest req){
        HttpSession session = req.getSession();
        session.invalidate();
        return new RedirectView("main/index");
    }

//    @GetMapping("/logOut")
//    public RedirectView logOut(HttpServletRequest req){
//        HttpSession session = req.getSession();
////        String access_Token = (String)session.getAttribute("access_Token");
////        Long userNum = (Long)session.getAttribute("userNum");
//
////        if(access_Token != null && !"".equals(access_Token)){
////            KakaoLogOut.kakaoLogout(access_Token);
////            log.info("access_Token 삭제 전" + (String)session.getAttribute("access_Token"));
////            log.info("userNum 삭제 전"+session.getAttribute("userNum").toString());
//            session.invalidate();
////            log.info("access_Token 삭제 후" + (String)session.getAttribute("access_Token"));
////            log.info("userNum 삭제 후"+session.getAttribute("userNum").toString());
////        }else{
////            System.out.println("access_Token is null");
////            return "redirect:/";
////        }
//
////        session.invalidate();
//        return new RedirectView("main/index");
//    }

    //    아임포트 사용시 포인트 올라가는 컨트롤러
    @GetMapping("/charge")
    public RedirectView charge(HttpServletRequest req, Long price, Long workPoint){
        HttpSession session = req.getSession();
        Long userNum = (Long)session.getAttribute("userNum");

        UserVO userVO = userRepository.findByUserNum(userNum);
        Long userPoint = userVO.getUserPoint() + price;
        Long userExp = userVO.getUserExp() + workPoint;

        userVO.setUserPoint(userPoint);
        userVO.setUserExp(userExp);

        userRepository.save(userVO);

        return new RedirectView("user/payment");
    }

//    카카오페이
//    @RequestMapping("/kakaoPay")
//    @ResponseBody
//    public String kakaoPay(){
//        try {
//            URL address = new URL("https://kapi.kakao.com/v1/payment/ready");
//            HttpURLConnection connection = (HttpURLConnection)address.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Authorization", "KakaoAK 592bc06590564de8184d8613164618f4");
//            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//            //          서버에 전달할게 있는가 없는가
//            connection.setDoOutput(true);
//            String parameter = "cid=TC0ONETIME&partner_order_id=partner_order_id&partner_user_id=partner_user_id&item_name=bitCoin&quantity=1&total_amount=2200&vat_amount=200&tax_free_amount=0&approval_url=http://localhost:7777/kakaoPay&fail_url=http://localhost:7777/fail&cancel_url=http://localhost:7777/cancel";
//            OutputStream outputStream = connection.getOutputStream();
//            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
//            dataOutputStream.writeBytes(parameter);
//            dataOutputStream.close();
//
//            int result = connection.getResponseCode();
//
//            InputStream inputStream;
//            if(result == 200){
//                inputStream = connection.getInputStream();
//            }else{
//                inputStream = connection.getErrorStream();
//            }
//
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            return bufferedReader.readLine();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//
//        return "{\"result\":\"NO\"}";
//    }


//    @PostMapping("/uploadAjaxAction")
//    @ResponseBody
//    public UserVO uploadAjaxPost(MultipartFile uploadFile) {
//        String uploadFolder = "C:/upload";
//        UserVO userVO = new UserVO();
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
//        userVO.setUserImgName(uploadFileName);
//        userVO.setUserImgUuid(uuid.toString());
//        userVO.setUserImgPath(uploadFolderPath);
//
//        //저장할 경로와 파일의 이름을 File객체에 담는다.
//        File saveFile = new File(uploadPath, uuid.toString() + "_" + uploadFileName);
//
//        try {
//            //설정한 경로에 해당 파일을 업로드한다.
//            uploadFile.transferTo(saveFile);
//
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
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