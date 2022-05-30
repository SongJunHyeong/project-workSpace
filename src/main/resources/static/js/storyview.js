// 댓글 열고 닫기 
$(".contentsBtnReply").on("click", function () {
    if ($(".rpyAtiveWrap").css("display") == "block") {
        $(this).html("댓글 열기");
        $(".rpyAtiveWrap").hide();
    } else {
        $(this).html("댓글 닫기");
        $(".rpyAtiveWrap").show();
        let storyNum = $(".storyViewContentsWrap").data("num");

        getList(storyNum);
    }
})

// 댓글 등록
$(".storyViewWrap").on("click", ".activeInputBtn", function (e) {
    e.preventDefault();
    if (userNum != null) {
        let storyReply = $(this).prev().val();
        let storyNum = $(".storyViewContentsWrap").data("num");
        if (!storyReply) {
            alert("댓글을 입력하세요");
            return;
        } else {
            storyService.insertReply({
                storyReply: storyReply, userNum: userNum, storyNum: storyNum
            }, function (result) {
                alert(result);
                getList(storyNum);
                storyService.resetReply(
                    storyNum, function (result) {
                        console.log($("span.bannerStatusTxt:nth-child(2)"));
                        $("p.bannerStatusMsgDiv span.bannerStatusTxt").text(result);
                    }
                )
            })
        }
    }
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

// 사람 좋아요 누르기
$("div.contentsProHeart").on("click", function () {
    if (userNum == null) {
        $(".modalWrapOpen").show();
        return;
    } else {
        let storyNum = $(".storyViewContentsWrap").data("num");

        $.ajax({
            type: "GET",
            url: "/story/likeStory/" + storyNum + "/" + userNum,
            success: function (result) {
                if (result === "success") {
                    $("div.heart").css("background-image", "url('/images/redheart.png')");
                } else {
                    $("div.heart").css("background-image", "url('/images/grayheart.png')");
                }
            },
            error: function (xhr, status, error) {
                // if (error) {
                // }
            }
        })
    }
})


// 댓글 리스트 불러오기
function getList(storyNum) {
    let str = "";

    storyService.getList(
        storyNum
        , function (userNickNames, replies) {

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

                $(".rpyAtiveWrap").html(str);
                return;
            }

            for (let i = 0; i < replies.length; i++) {
                str += "<div class='replyActive' data-num='" + replies[i].storyReplyNum + "'>";
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
                str += "<a href='/main/userDetail/" + replies[i].storyReplyNum + "'>프로필 상세</a>";
                str += "</li>";
                str += "<li class='hoverLi'>";
                str += "<a>1 : 1 대화</a>";
                str += "</li>";
                str += "<li class='hoverLi'>";
                str += "<a>모임초대</a>";
                str += "</li>";
                str += "</ul>";
                str += "</p>";
                str += "<span class='activeDate'>" + moment(replies[i].replyTime).format('YYYY MM DD HH:mm:ss') + "</span>";
                str += "</div>";
                str += "<div class='activeTxt'>";
                str += "<textarea class='activeTextArea' maxlength='500' placeholder='댓글을 작성해주세요' rows='2' readonly>" + replies[i].storyReply + "</textarea>";
                str += "</div>";
                str += "</div>";
                str += "</div>";
                str += "</div>";
                str += "</div>";
            }

            //로그인 검사해야함

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
            $(".rpyAtiveWrap").html(str);
        })
}

//댓글 수정,삭제
// str += "<div class='replyBtnWraps'>";
// str += "<p class='txtBtns modifys'>수정</p>";
// str += '<div class="sell">｜</div>';
// str += "<p class='txtBtns removes'>삭제</p>";
// str += "<button class='activeInputBtns'>수정완료</button>";
// str += "</div>";

$(".rpyAtiveWrap").on("click", ".noSessionId", function () {
    $(".modalWrapOpen").show();
})

// 스토리 삭제 모달
$(".remove").on("click", function () {
    $(".modalStory3").css('display', 'block');
});

$(".mdBtnRemoves").on("click", function () {
    let storyNum = $(".storyViewContentsWrap").data("num");

    $.ajax({
        type: "GET",
        url: "/story/deleteStory/" + storyNum,
        success: function () {
            $(".modalStory3").css('display', 'none');
            $(".modalStory2").css('display', 'block');
        },
        error: function (xhr, status, error) {

        }
    })
});

$(".xBtns").on("click", function () {
    location.href = "/story/storyList";
});
$(".xIcons3").on("click", function () {
    $("div.modalStory3").hide();
});
$(".mdBtnBacks").on("click", function () {
    $("div.modalStory3").hide();
});

//프로필 상세보기 
$(".rpyAtiveWrap").on("mouseover", ".activeProName", function () {
    $(this).next().show();
})

$(".rpyAtiveWrap").on("mouseout", ".activeProName", function () {
    $(this).next().hide();
})
$(".rpyAtiveWrap").on("mouseover", ".hoverUl", function () {
    $(this).show();
})

$(".rpyAtiveWrap").on("mouseout", ".hoverUl", function () {
    $(this).hide();
})
