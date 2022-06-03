// 라운지 반복

function lounge() {
    let str = "";

    for (let i = 0; i < loungeVOs.length; i++) {
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
        str += '<a href="/main/userDetail?userNum='+ userVOs[i].userNum +'">프로필 상세</a>';
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
        if (userNum == userVOs[i].userNum) {
            str += '<div class="replyBtnWrap">';
            str += '<p class="txtBtn modify">';
            str += '<img class="txtBtnImg"src="/images/modify.png">';
            str += '</p>';
            str += '<div class="sell">｜</div>';
            str += '<p class="txtBtn remove">';
            str += '<img class="txtBtnImg" src="/images/remove.png">';
            str += '</p>';
            str += '</div>';
        }
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
        str += '<span class="replyLike">' + loungeLikesNum[i] + '</span>';
        str += '</div>';
        str += '</div>';
        str += '</div>';
        str += '</div>';
        str += '</div>';
        str += '<div class="rpyAtiveWrap">';
        str += '</div>';
        str += '</div>';
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
$(".replyOpen").on("click", function () {
    let loungeNum = 0;
    if ($(this).parents(".replyTool").next().css("display") == "block") {
        $(this).parents(".replyTool").next().hide();
        $(this).html("댓글 열기");
    } else {
        let rpyAtiveWrap = $(this).parents(".replyTool").next();

        loungeNum = $(this).parents(".replySection").data("reply");

        getList(rpyAtiveWrap, loungeNum);

        $(this).parents(".replyTool").next().show();
        $(this).html("댓글 닫기");
    }
})

function getList(html, loungeNum) {
    let str = "";
    console.log(html);
    console.log(loungeNum);

    loungeService.getList(
        loungeNum
        , function (userVO, replies) {
            console.log(userVO);
            if (replies == null || replies.length == 0) {
                str += "<div class='activeInput'>";
                str += "<div class='activInputPro'>";
                str += "<img  class='activInputImg' src='/images/여.png'>";
                str += "</div>";
                str += "<div class='activeInputTxt'>";
                if (userNum == null) {
                    str += "<textarea maxlength='500' rows='2' placeholder='로그인 후 댓글 작성이 가능합니다' class='activeInputTextArea noSessionId'></textarea>";
                } else {
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
                str += "<div class='replyActive' data-num='" + replies[i].loungeReplyNum + "'>";
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
                str += "<p class='activeProName'>" + userVO[i].userNickName;
                str += "<ul class='hoverUl'>";
                str += "<li class='hoverLi'>";
                str += "<a href='/main/userDetail?userNum="+ userVO[i].userNum + "'>프로필 상세</a>";
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
                if (userNum == userVO[i].userNum) {
                    str += '<div class="replyBtnWraps">';
                    str += '<p class="txtBtns modifys">수정</p>';
                    str += '<div class="sell">｜</div>';
                    str += '<p class="txtBtns removes">삭제</p>';
                }
                str += '</div>';
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
            } else {
                str += "<textarea maxlength='500' rows='2' placeholder='댓글을 작성해주세요' class='activeInputTextArea'></textarea>";
            }
            str += "<button class='activeInputBtn'>등록</button>";
            str += "</div>";
            str += "</div>";


            $(html).html(str);
        })
}

//라운지 글작성
$(".loungeBtn1").on("click", function (e) {
    e.preventDefault();
    let loungeContent = $("div.loungeInputWrap textarea").val();
    if (!loungeContent) {
        alert("글을 입력하세요");
    } else {
        loungeService.insertLounge({
            loungeContent: loungeContent, userNum: userNum
        }, function (result) {
            $(".modalStory4").css("display", "block");
        })
    }
})

$(".xBtns").on("click", function () {
    location.reload();
})

$(".loungeBtn2").on("click", function () {
    $(".modalWrapOpen").css("display", "block");
})

$(".closeBtn").on("click", function () {
    $(".modalWrapOpen").hide();
})

$(".whiteBtn").on("click", function () {
    $(".modalWrapOpen").hide();
})
$(".redBtn").on("click", function () {
    $(".modalWrapOpen").hide();
    $(".modalWindow:first").show();
})

// 라운지 좋아요
$(".inputText").on("click", ".rpyLikeDiv", function () {
    let likeTable = $(this);
    if (userNum == null) {
        $(".modalWrapOpen").show();
        return;
    } else {
        let loungeNum = $(this).parents(".replySection").data("reply");

        $.ajax({
            type: "GET",
            url: "/lounge/likeLounge/" + loungeNum + "/" + userNum,
            success: function (result) {
                let split = result.split(" ");
                console.log(split[1]);
                console.log(likeTable.next());
                console.log($(likeTable).next());
                if (split[0] === "success") {
                    likeTable.find(".rpyImg").css("filter", "invert(62%) sepia(79%) saturate(2914%) hue-rotate(323deg) brightness(100%) contrast(84%)");
                    likeTable.find(".replyLike").text(split[1]);
                } else {
                    likeTable.find(".rpyImg").css("filter", "unset");
                    if (!split[1]) {
                        likeTable.find(".replyLike").text("0");
                    } else {
                        likeTable.find(".replyLike").text(split[1]);
                    }
                }
            },
            error: function (xhr, status, error) {
                // if (error) {
                // }
            }
        })
    }
})

// 댓글 추가
$(".loungeTool").on("click", ".activeInputBtn", function (e) {
    e.preventDefault();
    let replyContent = $(this).prev().val();
    let rpyAtiveWrap = $(this).parents(".rpyAtiveWrap");
    let loungeNum = $(this).parents(".replySection").data("reply");
    if (!replyContent) {
        alert("댓글을 입력하세요");
        return;
    } else {
        loungeService.insertReply({
            replyContent: replyContent, userNum: userNum, loungeNum: loungeNum
        }, function (result) {
            getList(rpyAtiveWrap, loungeNum);
        })
    }
})

// 라운지 삭제
$("p.remove").on("click", function () {
    if ($(this).hasClass("active") === false) {
        let loungeNum = $(this).parents(".replySection").data("reply");
        loungeService.deleteLounge(
            loungeNum
            , function (result) {
                $(".modalStory6").css("display", "block");
            })
    } else {
        // $(this).parents("div.replyBtnWrap").prev().attr("readonly", true);
        // $(this).parents("div.replyBtnWrap").prev().removeClass("modify");
        // $(this).removeClass("active");
        // $(this).siblings("p.modify").removeClass("active");
        // $(this).text("");
        // $(this).siblings("p.modify").text("");
        // $(this).find("img").css("display", "block");
        // $(this).html("<img class='txtBtnImg' src='/images/remove.png'>");
        // $(this).siblings("p.modify").html("<img class='txtBtnImg' src='/images/modify.png'>");
        location.reload();
    }
})

// 라운지 수정
$("p.modify").on("click", function () {
    if ($(this).hasClass("active") === false) {
        $(this).parents("div.replyBtnWrap").prev().attr("readonly", false);
        $(this).parents("div.replyBtnWrap").prev().addClass("modify");
        $(this).addClass("active");
        $(this).siblings("p.remove").addClass("active");
        $(this).text("완료");
        $(this).siblings("p.remove").text("취소");
    } else {
        let loungeNum = $(this).parents(".replySection").data("reply");
        let loungeContent = $(this).parents("div.replyBtnWrap").prev().val();

        loungeService.updateLounge({
            loungeNum: loungeNum, loungeContent: loungeContent
        }, function (result) {
            $(".modalStory5").css("display", "block");
        })
    }
})

// 라운지 댓글 수정
$(".loungeTool").on("click", "p.modifys", function () {
    console.log("들어옴");
    if ($(this).hasClass("active") === false) {
        $(this).parents("div.replyBtnWraps").prev().attr("readonly", false);
        $(this).parents("div.replyBtnWraps").prev().addClass("modify");
        $(this).addClass("active");
        $(this).siblings("p.removes").addClass("active");
        $(this).text("완료");
        $(this).siblings("p.removes").text("취소");
    } else {
        let loungeReplyNum = $(this).parents(".replyActive").data("num");
        let loungeReplyContent = $(this).parents("div.replyBtnWraps").prev().val();

        loungeService.updateReply({
            loungeReplyNum: loungeReplyNum, loungeReplyContent: loungeReplyContent
        }, function (result) {
            $(".modalStory5").css("display", "block");
        })
    }
})

// 라운지 댓글 삭제
$(".loungeTool").on("click", "p.removes", function () {
    if ($(this).hasClass("active") === false) {
        let loungeReplyNum = $(this).parents(".replyActive").data("num");
        loungeService.deleteReply(
            loungeReplyNum
            , function (result) {
                $(".modalStory6").css('display', 'block');
            })
    } else {
        // $(this).parents("div.replyBtnWraps").prev().attr("readonly", true);
        // $(this).parents("div.replyBtnWraps").prev().removeClass("modify");
        // $(this).removeClass("active");
        // $(this).siblings("p.modifys").removeClass("active");
        // $(this).text("삭제");
        // $(this).siblings("p.modifys").text("수정");
        location.reload();
    }
})

// 유저넘이 없을 시 로그인 불가
$(".loungeTool").on("click", ".noSessionId", function () {
    $(".modalWrapOpen").show();
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
