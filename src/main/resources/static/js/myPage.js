/*네비게이션 이동*/
$($(".navBorder").get(0)).css("border-bottom", "1px solid #00704a");
$(".nav").each(function (i, nav) {
    $(nav).on("click", function (e) {
        e.preventDefault();
        $(".pageNav").hide();
        $(".navBorder").css("border-bottom", "1px solid lightgray");
        $($(".pageNav").get(i)).show();
        $($(".navBorder").get(i)).css("border-bottom", "1px solid #00704a");
    })
})


/*포인트 충전/사용 이동*/
$($(".pointNav").get(0)).css("border", "1px solid #00704a");
$(".pointNav").each(function (i, nav) {
    $(nav).on("click", function () {
        $(".pointTable").hide();
        $(".pointNav").css("border", "1px solid lightgray")
        $($(".pointTable").get(i)).show();
        $($(".pointNav").get(i)).css("border", "1px solid #00704a");
    })
})

// <div id="portfolio" class="marginBottom">
//     <div id="url" class="flex a1">
//     <input type="text" class="able" placeholder="URL을 입력해주세요">
//     <input id="plusUrl" type="button" class="able" value="추가">
//     </div>
//     </div>


/*정보페이지 - 포트폴리오 추가*/
let counts = 1
$("#portfolio").on("click", "#plusUrl", function () {
    let length = $("#portfolio").children().length;
    if (length != 5) {
        counts++
        let input = $($(this).parent().children()[0]);
        let str = "";
        str += "<div id=\"url\" class=\"flex\">"
        str += "<input type='text' name='portUrl' class='able' placeholder='URL을 입력해주세요' value='" + input.val() + "'>";
        str += "<input id='deleteUrl' type='button' class='able a" + counts + "' value='삭제'>";
        str += "</div>"
        $("div#portfolio").append(str);
        input.val("");
        if (length == 4) {
            $(this).replaceWith("<input id='deleteUrl' type='button' class='able' value='삭제'>");
        }
    }
})

/*정보페이지 - 포트폴리오 삭제*/
$("#portfolio").on("click", "#deleteUrl", function () {
    $(this).parent().remove();
    counts--
    if (counts == 0) {
        counts++
        let str = "";
        str += "<div id=\"url\" class=\"flex\">"
        str += "<input type='text' name='portUrl' class='able' placeholder='URL을 입력해주세요' value=''>";
        str += "<input id='plusUrl' type='button' class='able a" + counts + "' value='추가'>";
        str += "</div>"
        $("div#portfolio").append(str);
        counts = 1
    }
})


//스터디 분야 선택

$("#skill").on("keydown", $("#keyword"), function (e) {
    let check = false;

    if(e.keyCode == 13) {
        e.preventDefault();
        if ($("a.skillTag").length ==5) {
            alert("5개까지 등록가능합니다.");
        }else{
        $(".skillTag").each(function (i, tag) {
            if ($("#keyword").val().toLowerCase() === $(tag).text()) {
                alert("이미 등록한 키워드입니다.");
                check = true;
            }
        })
        let str = "";
        str += "<a class='skillTag'>" + $("#keyword").val().toLowerCase() + "</a>";

        if (check == false) {
            $(".skillResult").append(str);
        }}
        $("#keyword").val("");
    }
})

//스터디 분야 삭제
$("div.skillResult").on("click", "a.skillTag", function () {
    $(this).remove();
})


function personChange(e) {
    let d = "";
    let person_a = ["UI/UX기획", "게임기획", "프로젝트 매니저", "하드웨어(제품) 기획"];
    let person_b = ["그래픽디자인", "UI/UX디자인", "3D디자인", "하드웨어(제품) 디자인", "(디자인)기타"];
    let person_c = ["IOS", "안드로이드", "웹프론트엔드", "웹퍼블리셔", "크로스플랫폼"];
    let person_d = ["웹 서버", "블록체인", "AI", "DB/빅데이터/DS", "게임 서버"];
    let person_e = ["사업기획", "마케팅", "영업", "재무/회계", "전략/컨설팅", "투자/고문", "(사업)그외"];
    let person_f = ["작가/블로거", "인플루언서/스트리머", "작곡(사운드)", "영상", "운영", "QA", "기타"];
    let target = $(e).next();

    if (e.value == "기획") d = person_a;
    else if (e.value == "디자인") d = person_b;
    else if (e.value == "프론트엔드개발") d = person_c;
    else if (e.value == "백엔드개발") d = person_d;
    else if (e.value == "사업") d = person_e;
    else if (e.value == "기타") d = person_f;

    target.html("");

    $(d).each(function (i, opt) {
        let str = "";
        str += "<option>" + opt + "</option>"
        target.append(str);
    })
}

function onOffChange(e) {
    let d = "";
    let onOff_on = ["상관없음"];
    let onOff_off = ["상관없음", "서울특별시", "경기도", "부산광역시", "인천광역시", "대구광역시", "경상남도", "경상북도", "충청남도", "충청북도", "전라남도", "전라북도", "광주광역시", "강원도", "울산광역시", "제주특별자치도", "세종특별자치시"];
    let target = $(e).next();

    if (e.value == "b") d = onOff_on;
    else if (e.value == "a" || e.value == "c") d = onOff_off;

    target.html("");

    $(d).each(function (i, opt) {
        let str = "";
        str += "<option>" + opt + "</option>"
        target.append(str);
    })
}


//관심분야
$("div#interestWrap p").each(function (i, tag) {
    for (let i = 0; i < interests.length; i++) {
        if ($(tag).text() == interests[i]) {
            $(tag).addClass("active");
        }
    }
    $(tag).on("click", function () {
        if ($('p.active').length < 5) {
            if ($(tag).attr("class") == "active") {
                $(tag).removeClass("active");
            } else {
                $(tag).addClass("active");
            }
        } else {
            if ($(tag).attr("class") == "active") {
                $(tag).removeClass("active");
            } else {
                alert("이미 5개를 선택하셨습니다.")
            }
        }

    })
})

$("button#sss").on("click", function (e) {
    e.preventDefault();
    $(".skillTag").each(function (i,tag) {
        let str="";
        if ($(tag).text()) {
            str = "<input type='hidden' name='tagName' value='"+$(tag).text()+"'>"
            $("#frm").append(str);
        }
    })

    $("p.active").each(function (i, p) {
        let str="";
        if ($(p).text()) {
            str = "<input type='hidden' name='interest' value='"+$(p).text()+"'>"
            $("#frm").append(str);

        }
    })


    $("input[name='userMainSkill']").val($("select#mainSkill2").val());
    $("input[name='userLocation']").val($("select#sido").val());
    $("input[name='userMainDetail']").val($("select#mainDetail2").val());
    $("input[name='userMainLevel']").val($("select#skillLevel2").val());
    $("input[name='userSubSkill']").val($("select#mainSkill3").val());
    $("input[name='userSubDetail']").val($("select#subSkill3").val());
    $("input[name='userSubLevel']").val($("select#skillLevel3").val());
    $("input[name='userOnOff']").val($("select#onOff").val());
    $("input[name='userPrice']").val($("select#valPoint").val());
    $("input[name='userTime']").val($("select#week").val());
    $("input[name='userContent']").val($("textarea#contents").val());




    $('#frm').submit();

})


