let limit = 12;
let plusCount = 1;

$(".filters").each(function (i, filter) {
    $(filter).on("change", function () {
        let formData = new FormData();
        $($(".filter").get(i)).val($(this).val());
        formData.append("locationFilter", $($(".filter").get(0)).val());
        formData.append("skillFilter", $($(".filter").get(1)).val());
        formData.append("limit", limit * plusCount);

        $.ajax({
            url: "/main/userFilter",
            data: formData,
            type: "POST",
            // 현재 설정된 헤더 설정을 기본 방식으로 변경하지 못하도록 false로 설정
            processData: false,
            contentType: false,
            success: function (result) {
                userList(result);
            }
        });
    })
})


function userList(result) {
    let str = ""
    $(result).each(function (i, user) {
        str += '<li class="userGrid">'
        str += '<img src="/images/letspler_new_mark.png" class="red">'
        str += '<div class="userGridtop">'
        str += '<div class="userIn" style="display: flex;">'
        str += '<div class="userGridimg">'
        str += '<img src="/images/여.png" style="width: 50px; object-fit: cover;">'
        str += '</div>'
        str += '<a class="like"></a>'
        str += '</div>'
        str += '<div class="usertool">'
        str += '<div class="user1">'
        str += '<div class="userLvIcon">'
        str += '<img  class="lvIcon" src="/images/level_6.png">'
        str += '</div>'
        str += '<div class="userGridName">'
        str += '<p class="ugname" id="ugname">박상아'
        str += '<ul class="idHoverMenu" id="idHoverMenu">'
        str += '<li>'
        str += '<a>프로필 상세</a>'
        str += '</li>'
        str += '<li>'
        str += '<a>1:1 대화</a>'
        str += '</li>'
        str += '<li>'
        str += '<a>모임 초대</a>'
        str += '</li>'
        str += '</ul>'
        str += '</p>'
        str += '</div>'
        str += '<div class="usercheckIcon">'
        str += '<img class="checkIcon" src="/images/ic-certi-red.png">'
        str += '</div>'
        str += '</div>'
        str += '<div class="userInn">'
        str += '<div class="bitandheart">'
        str += '<div class="bitdiv">'
        str += '<div class="bitimg">'
        str += '<img src="/images/BBBB.png" class="bitIcon">'
        str += '</div>'
        str += '<div class="bittag">'
        str += '<p class="bitptag">3</p>'
        str += '</div>'
        str += '</div>'
        str += '<div class="heartdiv">'
        str += '<div class="heartimg">'
        str += '<img src="/images/ico_like.png" class="heartIcon"></div>'
        str += '<div class="hearttag">'
        str += '<p class="heartptag">1</p>'
        str += '</div>'
        str += '</div>'
        str += '</div>'
        str += '</div>'
        str += '</div>'
        str += '</div>'
        str += '<div class="userinfo">'
        str += '<div class="bon"><span class="info">[본캐]UI/UX기획</span><span class="num">중수</span></div>'
        str += '<div class="boo"><span class="info">[부캐]게임기획</span><span class="num">초보</span></div>'
        str += '</div>'
        str += '<div class="usertag">'
        str += '<ul class="ulusertag">'
        str += '<li class="liusertag">#IT</li>'
        str += '<li class="liusertag">#UIUX</li>'
        str += '<li class="liusertag">#프론트</li>'
        str += '<li class="liusertag">#디자인</li>'
        str += '<li class="liusertag">#기획</li>'
        str += '</ul>'
        str += '<p class="join">진행중인 모임이 없습니다.</p>'
        str += '</div>'
        str += '<div class="userGridbottom">'
        str += '<img class="btimg" src="/images/BBBB.png">'
        str += '<p class="btchat">BIt Chat</p>'
        str += '</div>'
        str += '</li>'
    });

    $("ul.userGridList").html(str);


}

$(".ugname").on("mouseover", function () {
    $(this).parent().children().closest(".idHoverMenu").css("display", "block");
})

$(".ugname").on("mouseout", function () {
    $(this).parent().children().closest(".idHoverMenu").css("display", "none");
})

$(".idHoverMenu").on("mouseover", function () {
    $(this).css("display", "block");
})

$(".idHoverMenu").on("mouseout", function () {
    $(this).css("display", "none");
})

$(".ugname").next().find("a").on("click", function () {
    console.log($(this).text());
})


//채팅 시작하기
document.getElementById('startChat').addEventListener('click', function () {
        other = document.getElementById('ugname').innerText;

        document.querySelector('.chattingOpen').classList.remove("open");
        document.querySelector('.chatting').classList.add("open");
        console.log(document.getElementsByClassName('userId')[0].innerText)


        처음대화(mysession, other);
        document.getElementById('chatId').innerHTML = other;

    }
)