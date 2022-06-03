// 정보, 스토리 클릭시 변하게 만들기 (userview , userview2)

//작성된 스토리가 없으면 기본적으로 .storyNone 보여주기

$(".nav").each(function (i, nav) {
    $(nav).on("click", function () {
        $(".activePage").hide();
        $($(".activePage").get(i)).show();
        $(".nav").removeClass("active");
        $(nav).addClass("active");
    })
})

$(".userFollowerImgs").each(function (i, img) {
    $(img).on("mouseover", function () {
        $($(".hoverName").get(i)).show();
    })
    $(img).on("mouseout", function () {
        $($(".hoverName").get(i)).hide();
    })
})

// 스토리 선택시 진행중인 프로젝트/스터디, 나를 구독중인 사람 안나오게하기




//채팅 연결
document.getElementById('userProPinkBtn').addEventListener('click', function () {
        other = document.getElementById('userProName').innerText;

        document.querySelector('.chattingOpen').classList.remove("open");
        document.querySelector('.chatting').classList.add("open");

        처음대화(mysession, other);
        document.getElementById('chatId').innerText = other;
    }
)
