// 라운지 반복

function lounge(){
    let str = "";

    for (let i = 0; i < loungeVOs.length;i++) {
        str += '<div class="replySection" data-reply="' + loungeVOs[i].loungeNum + '">';
        str += '<div class="replyWrap">';
        str += '<div class="replyTool">';
        str += '<div class="replyTop">';
        str += '<div class="replyLeft">';
        str += '<a href="https://letspl.me/people/%EC%83%81%EC%95%84%EC%95%BC">';
        str += '<div class="proWrap">';
        str += '<img src="/images/여.png">';
        str += '</div>';
        str += '</a>';
        str += '</div>';
        str += '<div class="replyRight">';
        str += '<p class="proNames">' + loungeUserNickNames[i];
        str += '<ul class="hoverUl">';
        str += '<li class="hoverLi">';
        str += '<a href="/people/%EC%83%81%EC%95%84%EC%95%BC">프로필 상세</a>';
        str += '</li>';
        str += '<li class="hoverLi">';
        str += '<a>1 : 1 대화</a>';
        str += '</li>';
        str += '<li class="hoverLi">';
        str += '<a>모임초대</a>';
        str += '</li>';
        str += '</ul>';
        str += '</p>';
        str += '<span class="time">' + moment(loungeVOs[i].loungeDate).format('YYYY MM DD HH:mm:ss') + '</span>';
        str += '</div>';
        str += '</div>';
        str += '<div class="replyMiddle">';
        str += '<div class="replyLeft"></div>';
        str += '<div class="replyContents">';
        str += '<textarea class="txtarea" rows="1" maxlength="1000" readonly>' + loungeVOs[i].loungeContent + '</textarea>';
        str += '<div class="replyBtnWrap">';
        str += "<button class='activeInputBtnss'>수정완료</button>";
        str += '<p class="txtBtn modify">';
        str += '<img class="txtBtnImg"src="/images/modify.png">';
        str += '</p>';
        str += '<div class="sell">｜</div>';
        str += '<p class="txtBtn remove">';
        str += '<img class="txtBtnImg" src="/images/remove.png">';
        str += '</p>';
        str += '</div>';
        str += '</div>';
        str += '<div class="replyBottom">';
        str += '<a class="replyOpen">댓글 열기</a>';
        str += '<div class="replyBtmRight">';
        str += '<div class="rpyCountDiv">';
        str += '<img class="rpyImg" src="/images/msg.png">';
        str += '<span class="replyCount">' + loungeRepliesNum[i] + '</span>';
        str += '</div>';
        str += '<div class="rpyLikeDiv">';
        str += '<img class="rpyImg" src="/images/likeup.png">';
        str += '<span class="replyLike">' + loungeLikesNum[i] +'</span>';
        str += '</div>';
        str += '</div>';
        str += '</div>';
        str += '</div>';
        str += '</div>';
        str += '<div class="rpyAtiveWrap">';
        str += '</div>';
        str += '</div>';

        //댓글의 댓글 수정,삭제 여기서부터
        str += "<div class='replyBtnWraps'>";
        str += "<p class='txtBtns modifys'>수정</p>";
        str += '<div class="sell">｜</div>';
        str += "<p class='txtBtns removes'>삭제</p>";
        str += "<button class='activeInputBtns'>수정완료</button>";
        str += "</div>";
        //여기까지

        str += '</div>';
    }

    $("div.inputText").html(str);
}

<!--   **댓글 수정 삭제**             -->
// <div class="replyBtnWraps">
//         <p class="txtBtns modifys">수정</p>
//         <button class="activeInputBtns">수정완료</button>
//         <div class="sell">｜</div>
//     <p class="txtBtns removes">삭제</p>
//         </div>
<!--                **여기까지-->

//글수정완료버튼



lounge();

//댓글 열고 닫기 만들기
$(".replyOpen").on("click", function() {
    let loungeNum = 0;
    if ($(this).parents(".replyTool").next().css("display") == "block") {
        $(this).parents(".replyTool").next().hide();
        $(this).html("댓글 열기");
    } else {
        let rpyAtiveWrap = $(this).parents(".replyTool").next();

                loungeNum = $(this).parents(".replySection").data("reply");

                getList(rpyAtiveWrap, loungeNum);

                // loungeService.getList(
                //     loungeNum
                //     , function (userNickNames, replies) {
                //         if (replies == null || replies.length == 0) {
                //             str += "<div class='activeInput'>";
                //             str += "<div class='activInputPro'>";
                //             str += "<img  class='activInputImg' src='/images/여.png'>";
                //             str += "</div>";
                //             str += "<div class='activeInputTxt'>";
                //             str += "<textarea maxlength='500' rows='2' placeholder='댓글을 작성해주세요' class='activeInputTextArea'></textarea>";
                //             str += "<button class='activeInputBtn'>등록</button>";
                //             str += "</div>";
                //             str += "</div>";
                //
                //
                //             $(rpyAtiveWrap).html(str);
                //             return;
                //         }
                //         for (let i = 0; i < replies.length; i++) {
                //             str += "<div class='replyActive'>";
                //             str += "<div class='activeWrap'>";
                //             str += "<div class='activeTop'>";
                //             str += "<div class='activeLeft'>";
                //             str += "<a href='#'>";
                //             str += "<div class='activeLeftPro'>";
                //             str += "<img src='/images/여.png'>";
                //             str += "</div>";
                //             str += "</a>";
                //             str += "</div>";
                //             str += "<div class='activeRight'>";
                //             str += "<div class='activeProfile'>";
                //             str += "<p class='activeProName'>" + userNickNames[i];
                //             str += "<ul class='hoverUl'>";
                //             str += "<li class='hoverLi'>";
                //             str += "<a href='/people/%EC%83%81%EC%95%84%EC%95%BC'>프로필 상세</a>";
                //             str += "</li>";
                //             str += "<li class='hoverLi'>";
                //             str += "<a>1 : 1 대화</a>";
                //             str += "</li>";
                //             str += "<li class='hoverLi'>";
                //             str += "<a>모임초대</a>";
                //             str += "</li>";
                //             str += "</ul>";
                //             str += "</p>";
                //             str += "<span class='activeDate'>" + replies[i].loungeReplyDate + "</span>";
                //             str += "</div>";
                //             str += "<div class='activeTxt'>";
                //             str += "<textarea class='activeTextArea' maxlength='500' placeholder='댓글을 작성해주세요' rows='2' readonly>" + replies[i].loungeReplyContent + "</textarea>";
                //             str += "</div>";
                //             str += "</div>";
                //             str += "</div>";
                //             str += "</div>";
                //             str += "</div>";
                //         }
                //
                //         str += "<div class='activeInput'>";
                //         str += "<div class='activInputPro'>";
                //         str += "<img  class='activInputImg' src='/images/여.png'>";
                //         str += "</div>";
                //         str += "<div class='activeInputTxt'>";
                //         str += "<textarea maxlength='500' rows='2' placeholder='댓글을 작성해주세요' class='activeInputTextArea'></textarea>";
                //         str += "<button class='activeInputBtn'>등록</button>";
                //         str += "</div>";
                //         str += "</div>";
                //
                //
                //         $(rpyAtiveWrap).html(str);
                //     })

        $(this).parents(".replyTool").next().show();
        $(this).html("댓글 닫기");
            }
        })

function getList(html, loungeNum){
    let str = "";
    console.log(html);
    console.log(loungeNum);

    loungeService.getList(
        loungeNum
        , function (userNickNames, replies) {
            if (replies == null || replies.length == 0) {
                str += "<div class='activeInput'>";
                str += "<div class='activInputPro'>";
                str += "<img  class='activInputImg' src='/images/여.png'>";
                str += "</div>";
                str += "<div class='activeInputTxt'>";
                if (userNum == null) {
                    str += "<textarea maxlength='500' rows='2' placeholder='로그인 후 댓글 작성이 가능합니다' class='activeInputTextArea noSessionId'></textarea>";
                }else {
                    str += "<textarea maxlength='500' rows='2' placeholder='댓글을 작성해주세요' class='activeInputTextArea'></textarea>";
                }
                str += "<button class='activeInputBtn'>등록</button>";
                str += "</div>";
                str += "</div>";


                $(html).html(str);
                return;
            }
            for (let i = 0; i < replies.length; i++) {
                console.log(replies[i].loungeReplyDate);
                str += "<div class='replyActive'>";
                str += "<div class='activeWrap'>";
                str += "<div class='activeTop'>";
                str += "<div class='activeLeft'>";
                str += "<a href='#'>";
                str += "<div class='activeLeftPro'>";
                str += "<img src='/images/여.png'>";
                str += "</div>";
                str += "</a>";
                str += "</div>";
                str += "<div class='activeRight'>";
                str += "<div class='activeProfile'>";
                str += "<p class='activeProName'>" + userNickNames[i];
                str += "<ul class='hoverUl'>";
                str += "<li class='hoverLi'>";
                str += "<a href='/people/%EC%83%81%EC%95%84%EC%95%BC'>프로필 상세</a>";
                str += "</li>";
                str += "<li class='hoverLi'>";
                str += "<a>1 : 1 대화</a>";
                str += "</li>";
                str += "<li class='hoverLi'>";
                str += "<a>모임초대</a>";
                str += "</li>";
                str += "</ul>";
                str += "</p>";
                str += "<span class='activeDate'>" + moment(replies[i].loungeReplyDate).format('YYYY MM DD HH:mm:ss') + "</span>";
                str += "</div>";
                str += "<div class='activeTxt'>";
                str += "<textarea class='activeTextArea' maxlength='500' placeholder='댓글을 작성해주세요' rows='2' readonly>" + replies[i].loungeReplyContent + "</textarea>";
                str += "</div>";
                str += "</div>";
                str += "</div>";
                str += "</div>";
                str += "</div>";
            }

            str += "<div class='activeInput'>";
            str += "<div class='activInputPro'>";
            str += "<img  class='activInputImg' src='/images/여.png'>";
            str += "</div>";
            str += "<div class='activeInputTxt'>";
            if (userNum == null) {
                str += "<textarea maxlength='500' rows='2' placeholder='로그인 후 댓글 작성이 가능합니다' class='activeInputTextArea noSessionId'></textarea>";
            }else {
                str += "<textarea maxlength='500' rows='2' placeholder='댓글을 작성해주세요' class='activeInputTextArea'></textarea>";
            }
            str += "<button class='activeInputBtn'>등록</button>";
            str += "</div>";
            str += "</div>";


            $(html).html(str);
        })
}


// $(".replyOpen").each(function (i,open) {
//     let loungeNum = 0;
//     $(open).on("click", function () {
//         if ($($(".rpyAtiveWrap").get(i)).css("display") == "block") {
//             $($(".rpyAtiveWrap").get(i)).hide();
//             $(open).html("댓글 열기");
//         } else {
//             loungeNum = $(this).data("reply")
//             loungeService.getList(
//                 loungeNum
//             , function (userNickNames, replies) {
//                 let str = "";
//                 if (replies == null || replies.length == 0) {
//                     console.log("들어옴");
//                     return;
//                 }
//                 str += "<div class='rpyAtiveWrap'>"
//
//                 for(let j =0;j<replies.size;j++) {
//                     str += "<div class='replyActive'>";
//                     str += "<div class='activeWrap'>";
//                     str += "<div class='activeTop'>";
//                     str += "<div class='activeLeft'>";
//                     str += "<a href='#'>";
//                     str += "<div class='activeLeftPro'>";
//                     str += "<img src='/images/여.png'>";
//                     str += "</div>";
//                     str += "</a>";
//                     str += "</div>";
//                     str += "<div class='activeRight'>";
//                     str += "<div class='activeProfile'>";
//                     str += "<p class='activeProName'>userNickNames";
//                     str += "<ul class='hoverUl'>";
//                     str += "<li class='hoverLi'>";
//                     str += "<a href='/people/%EC%83%81%EC%95%84%EC%95%BC'>프로필 상세</a>";
//                     str += "</li>";
//                     str += "<li class='hoverLi'>";
//                     str += "<a>1 : 1 대화</a>";
//                     str += "</li>";
//                     str += "<li class='hoverLi'>";
//                     str += "<a>모임초대</a>";
//                     str += "</li>";
//                     str += "</ul>";
//                     str += "</p>";
//                     str += "<span class='activeDate'>22.05.02 08:43</span>";
//                     str += "</div>";
//                     str += "<div class='activeTxt'>";
//                     str += "<textarea class='activeTextArea' maxlength='500' placeholder='댓글을 작성해주세요' rows='2' readonly>반갑습니다.</textarea>";
//                     str += "</div>";
//                     str += "</div>";
//                     str += "</div>";
//                     str += "</div>";
//                     str += "</div>";
//                 }
//
//                 str += "<div class='activeInput'>"
//                 str += "<div class='activInputPro'>"
//                 str += "<img  class='activInputImg' src='/images/여.png'>"
//                 str += "</div>"
//                 str += "<div class='activeInputTxt'>"
//                 str += "<textarea maxlength='500' rows='2' placeholder='댓글을 작성해주세요' class='activeInputTextArea'></textarea>"
//                 str += "<button class='activeInputBtn'>등록</button>"
//                 str += "</div>"
//                 str += "</div>"
//                 str += "</div>"
//
//                 $($(".rpyAtiveWrap").get(i)).html(str);
//                 $($(".rpyAtiveWrap").get(i)).show();
//                 $(open).html("댓글 닫기");
//             })
//         }
//     });
// })

//라운지 글작성
$(".loungeBtn1").on("click", function (e) {
    e.preventDefault();
    let loungeContent = $("div.loungeInputWrap textarea").val();
    if(!loungeContent){
        alert("글을 입력하세요");
    }else{
        loungeService.insertLounge({
            loungeContent:loungeContent, userNum:userNum
        }, function (result) {
            alert(result);
           location.reload();
        })
    }
})

$(".loungeTool").on("click", ".activeInputBtn" , function (e) {
    e.preventDefault();
    let replyContent = $(this).prev().val();
    let rpyAtiveWrap = $(this).parents(".rpyAtiveWrap");
    let loungeNum = $(this).parents(".replySection").data("reply");
    if(!replyContent){
        alert("댓글을 입력하세요");
        return;
    }else{
        loungeService.insertReply({
            replyContent:replyContent, userNum:3, loungeNum:loungeNum
        }, function (result) {
            alert(result);
            getList(rpyAtiveWrap, loungeNum);
        })
    }
})

// 라운지 삭제
$("p.remove").on("click",function () {
    let loungeNum = $(this).parents(".replySection").data("reply");
    loungeService.deleteLounge(
        loungeNum
    , function (result) {
        alert(result);
        location.reload();
    })
})

// 라운지 수정
$("p.modify").on("click", function(){
    $(this).parents("div.replyBtnWrap").prev().attr("readonly", false);
    $(this).parents("div.replyBtnWrap").prev().addClass("modify");
})

// 임시 수정 버튼

$("span.time").on("click", function(){
    let loungeContent = $(this).parents("div.replyTop").next().find("textarea").val();
    let loungeNum = $(this).parents("div.replySection").data("reply");
    console.log(loungeContent);
    console.log(loungeNum);
    loungeService.updateLounge({
        loungeNum:loungeNum,loungeContent:loungeContent
    }, function (result) {
        alert(result);
        location.reload();
    })

})

// 좋아요
$(".rpyLikeDiv").on("click", function () {
    $(this).find(".rpyImg").css("filter","invert(62%) sepia(79%) saturate(2914%) hue-rotate(323deg) brightness(100%) contrast(84%)");
})

//프로필 상세보기(댓글의댓글)
$(".activeProName").on("mouseover", function () {
    $(this).next().show();
})

$(".activeProName").on("mouseout", function () {
    $(this).next().hide();
})
$(".hoverUl").on("mouseover", function () {
    $(this).show();
})

$(".hoverUl").on("mouseout", function () {
    $(this).hide();
})

////프로필 상세보기(댓글)
$(".proNames").on("mouseover", function () {
    $(this).next().show();
})

$(".proNames").on("mouseout", function () {
    $(this).next().hide();
})
$(".hoverUl").on("mouseover", function () {
    $(this).show();
})

$(".hoverUl").on("mouseout", function () {
    $(this).hide();
})
