// 프로젝트/스터디 양식 선택
$(".choice").each(function (i, radio) {
    $(radio).on("click", function (e) {
        $(".make").hide();
        $($(".make").get(i)).show();
    })
})

$("#projectSubmit").on("click", function () {
    $("#projectContent").val(editor.getHTML());
    $("#projectOnOff").val($("#onOffSelect").val());
    $("#projectLocation").val($("#onOffResult").val());
    $("#projectPlatform").val($("#platformResult").val());
    $(".mainSkill").each(function (i, skill) {
        let str = "";
        str += "<input type='hidden' name='projectMainSkill' id='projectMainSkill' value='" + $(skill).val() + "'>";
        str += "<input type='hidden' name='projectSubSkill' id='projectSubSkill' value='" + $($(".subSkill").get(i)).val() + "'>";
        str += "<input type='hidden' name='projectCount' id='projectCount' value='" + $($(".countNumber").get(i)).text() + "'>";
        $("#projectPersonData").append(str);
    })

    $("#skillResult a").each(function (i, skill) {
        if ($(skill).text()) {
            let str = "<input type='hidden' name='projectSkill' id ='projectSkill' value='" + $(skill).text() + "'>";
            $("#projectSkillData").append(str);
        }
    })
    $("#type").val("project");
    document.projectForm.submit();
})

function skillChange() {
    $(".mainSkill").each(function (i, select) {
        $(select).on("change", function () {
            let person_a = ["UI/UX기획", "게임기획", "프로젝트 매니저", "하드웨어(제품) 기획"];
            let person_b = ["그래픽디자인", "UI/UX디자인", "3D디자인", "하드웨어(제품) 디자인", "(디자인)기타"];
            let person_c = ["IOS", "안드로이드", "웹프론트엔드", "웹퍼블리셔", "크로스플랫폼"];
            let person_d = ["웹 서버", "블록체인", "AI", "DB/빅데이터/DS", "게임 서버"];
            let person_e = ["사업기획", "마케팅", "영업", "재무/회계", "전략/컨설팅", "투자/고문", "(사업)그외"];
            let person_f = ["작가/블로거", "인플루언서/스트리머", "작곡(사운드)", "영상", "운영", "QA", "기타"];

            var d = "";

            let target = document.getElementsByClassName("subSkill");

            target[i].options.length = 0;
            if ($(select).val() == "기획") d = person_a;
            else if ($(select).val() == "디자인") d = person_b;
            else if ($(select).val() == "프론트엔드개발") d = person_c;
            else if ($(select).val() == "백엔드개발") d = person_d;
            else if ($(select).val() == "사업") d = person_e;
            else if ($(select).val() == "기타") d = person_f;

            for (x in d) {
                let opt = document.createElement("option");
                opt.value = d[x];
                opt.innerHTML = d[x];
                target[i].appendChild(opt);
            }
        })
    })
}





//대표이미지 업로드 클릭
$("#imgUploadBtn").on("click", function () {
    let input = $("#imgUpload");
    input.click();
});

// $("#onOffResult2").on("change",function(){
//     $("#studyLocation").val($(this).val());
// })
// $("#studyOnOffSelect").on("change",function(){
//     $("#studyOnOff").val($(this).val());
// })
// $("#studyPartSelect").on("change",function () {
//     $("#studyPart").val($(this).val());
// })

$("#studySubmit").on("click", function () {
    $("#studyLocation").val($("#onOffResult2").val());
    $("#studyOnOff").val($("#studyOnOffSelect").val());
    $("#studyPart").val($("#studyPartSelect").val());
    $("#studyMax").val($("#maxCount").val());
    $("#studyContent").val($("#contents").text());
    $(".skillTag2").each(function (i,tag) {
        let str="";
        if ($(tag).text()) {
            str+="<input type='hidden' name='studyKeyword' value='"+$(tag).text()+"'>"
            $("#keywordData").append(str);
        }
    })
    $("#type").val("study");
    document.studyForm.submit();
})

// 모임지역 선택
function onOffChange(e) {
    var onOff_both = ["상관없음", "서울특별시", "경기도", "부산광역시", "인천광역시", "대구광역시", "경상남도", "경상북도", "충청남도", "충청북도", "전라남도", "전라북도", "광주광역시", "강원도", "울산광역시", "제주특별자치도", "세종특별자치시"];
    var onOff_on = ["상관없음"];
    var onOff_off = ["서울특별시", "경기도", "부산광역시", "인천광역시", "대구광역시", "경상남도", "경상북도", "충청남도", "충청북도", "전라남도", "전라북도", "광주광역시", "강원도", "울산광역시", "제주특별자치도", "세종특별자치시"];
    var target = document.getElementById("onOffResult");

    if (e.value == "온라인/오프라인 모두 가능") var d = onOff_both;
    else if (e.value == "온라인만 가능") var d = onOff_on;
    else if (e.value == "오프라인만 가능") var d = onOff_off;

    target.options.length = 0;

    for (x in d) {
        var opt = document.createElement("option");
        opt.value = d[x];
        opt.innerHTML = d[x];
        target.appendChild(opt);
    }
}

function onOffChange2(e) {
    var onOff_both = ["상관없음", "서울특별시", "경기도", "부산광역시", "인천광역시", "대구광역시", "경상남도", "경상북도", "충청남도", "충청북도", "전라남도", "전라북도", "광주광역시", "강원도", "울산광역시", "제주특별자치도", "세종특별자치시"];
    var onOff_on = ["상관없음"];
    var onOff_off = ["서울특별시", "경기도", "부산광역시", "인천광역시", "대구광역시", "경상남도", "경상북도", "충청남도", "충청북도", "전라남도", "전라북도", "광주광역시", "강원도", "울산광역시", "제주특별자치도", "세종특별자치시"];
    var target = document.getElementById("onOffResult2");

    if (e.value == "온라인/오프라인 모두 가능") var d = onOff_both;
    else if (e.value == "온라인만 가능") var d = onOff_on;
    else if (e.value == "오프라인만 가능") var d = onOff_off;

    target.options.length = 0;

    for (x in d) {
        var opt = document.createElement("option");
        opt.value = d[x];
        opt.innerHTML = d[x];
        target.appendChild(opt);
    }
}


//모집인원 추가
// let count = 1;
$("#personAddBtn").click(function () {
    let length = $("#personWrap").children().length;

    if (length != 9) {
        let str = "";

        str += "<div class='selectBoxWrap'>";
        str += "<div class='selectBox'>";
        str += "<select class='mainSkill'>";
        str += "<option>기획</option>";
        str += "<option>디자인</option>";
        str += "<option>프론트엔드개발</option>";
        str += "<option>백엔드개발</option>";
        str += "<option>사업</option>";
        str += "<option>기타</option>";
        str += "</select>";
        str += "</div>";
        str += "<div class='selectBox'>";
        str += "<select class='subSkill personResult'>";
        str += "<option>UI/UX 기획</option>";
        str += "<option>게임 기획</option>";
        str += "<option>프로젝트 매니저</option>";
        str += "<option>하드웨어(제품) 기획</option>";
        str += "</select>";
        str += "</div>";
        str += "<div class='plusMinusBtnWrap'>";
        str += "<button type='button' class='imageBtn minus'></button>";
        str += "<div class='countNumber'>1</div>";
        str += "<button type='button' class='imageBtn plus'></button>";
        str += "</div>";
        str += "</div>";

        $("#personWrap").append(str);
    }
    skillChange();
});


//모집인원 삭제
$("#personDeleteBtn").click(function () {
    if ($("#personWrap").children().length != 1) {
        let div = $("#personWrap").children().last();
        div.remove();
    }

});

//모집인원 분야별 인원설정
const number = $(".countNumber");
const plus = $(".plus");
const minus = $(".minus");

// console.log(number.text());

$("div#personWrap").on("click", "button.plus", function (e) {
    e.preventDefault();
    let current = $(this).prev().text();
    if (current < 9) {
        $(this).prev().html(parseInt(current) + 1);
    }
})

$("div#personWrap").on("click", "button.minus", function (e) {
    e.preventDefault();
    let current = $(this).next().text();
    if (current > 1) {
        $(this).next().text(parseInt(current) - 1);
    }
})


// const number = document.getElementById("countNumber");
// const plus = document.getElementById("plus");
// const minus = document.getElementById("minus");
//
// // console.log(number.innerText);
// // console.log(plus);
// // console.log(minus);
//
// plus.onclick = () => {
//     let current = parseInt(number.innerText, 10);
//     if(parseInt(number.innerText) <= 8){
//         number.innerText = current + 1;
//     }
// };
//
// minus.onclick = () => {
//     let current = parseInt(number.innerText, 10);
//     if(parseInt(number.innerText) > 1){
//         number.innerText = current - 1;
//     }
// }

// $("div#personWrap").on("click", "button.plus", function (e) {
//     console.log("들어옴")
//     e.preventDefault();
//     let current = parseInt(number.innerText, 10);
//     if(parseInt(number.innerText) <= 8){
//         number.innerText = current + 1;
//     }
// });
//
// $("div#personWrap").on("click", "button.minus", function (e) {
//     e.preventDefault();
//     let current = parseInt(number.innerText, 10);
//     if(parseInt(number.innerText) > 1){
//         number.innerText = current - 1;
//     }
// });


//참고자료 추가
$("#addReferenceBtn").click(function () {
    let length = $("#referenceWrap").children().length;

    if (length != 5) {
        let str = "";

        str += "<div>";
        str += "<input type='text' class='projectUrl' name='projectUrl' placeholder='예) http://workspace.me'>";
        str += "</div>";

        $("#referenceWrap").append(str);
    }else{
        alert("5개 까지만 가능합니다.")
    }
});

//모집인원 삭제
$("#deleteReferenceBtn").click(function () {
    let div = $("#referenceWrap").children().last();
    div.remove();

});

// //출시 플랫폼 추가
// $("#platformResult").change(function () {
//     let state = $("#platformResult option:selected").val();
//     $($(".results").get(state)).show();
// })
//
//
// //출시 플랫폼 삭제
// $("a.results").each(function (i, result) {
//     $(result).on("click", function () {
//         $(result).hide();
//     })
// })


//토스트 에디터
const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    height: '600px',
    initialEditType: 'markdown',
    previewStyle: 'vertical',
    initialValue: '1. 프로젝트의 시작 동기\n   ' +
        ' - 왜 이 서비스를 만드시고 싶은지 적어주세요 \n' +
        ' (ex - 기존에 찾기 어려운 여행정보를 한번에 모아서 보여줄 예정입니다) \n' +
        ' - 어떤 사용자들을 타겟하고 있는지 적어주세요 \n' +
        ' (ex - 혼자 여행하는 것에 꺼리낌이 없는 30대 이상의 미혼 남녀를 대상으로 합니다) \n' +
        '\n' +
        '2. 회의 진행/모임 방식\n' +
        ' - 1주에 몇번정도 회의나 모임을 진행할 계획인가요? \n' +
        ' (ex - 1주일에 1회/2회 정도 정기적으로 회의합니다)\n' +
        ' - 온/오프라인 회의 진행시 진행방식을 적어주세요 \n' +
        ' (ex - 온라인은 줌을 활용하고, 오프라인은 강남역 카페등을 대관할예정입니다, 커뮤니케이션은 슬랙을 위주로 사용합니다 )\n   ' +
        '3. 그외 자유기재 \n' +
        '\n' +
        '-  2번까지의 내용을 포함할 수 있도록 작성해주세요(형식은 달라도 상관없습니다)\n' +
        '-  신청시 기타사항과 질문내용 등을 삭제한 후 답변만 등록해주세요. \n' +
        '   (그외의 내용은 자유롭게 기입해주세요(영상/이미지 포함) \n' +
        '-  상세 검수가이드라인은 공지사항을 참고해주세요. https://workspace/notice/noticeDetail/1'
});


//스터디 분야 선택
let input = $("#keyword");
let input2 = $("#keyword2");

$("#skill").on("keydown", input, function (e) {
    let check = false;

    if (e.keyCode == 13) {
        e.preventDefault();
        $(".skillTag1").each(function (i, tag) {

            if (input.val().toLowerCase() === $(tag).text()) {
                alert("이미 등록한 키워드입니다.");
                check = true;
            }
        })
        let str = "";
        str += "<a class='skillTag1'>" + input.val().toLowerCase() + "</a>";

        if (check == false) {
            $("#skillResult").append(str);
        }
        input.val("");
    }
})
$("#skill2").on("keydown", input2, function (e) {
    let check = false;

    if (e.keyCode == 13) {
        e.preventDefault();
        if ($(".skillTag2").length > 10) {
            alert("최대 10개만 입력 가능합니다.")
            return;
        } else {
            $(".skillTag2").each(function (i, tag) {

                if (input2.val().toLowerCase() === $(tag).text()) {
                    alert("이미 등록한 키워드입니다.");
                    check = true;
                }
            })
            let str = "";
            str += "<a class='skillTag2'>" + input2.val().toLowerCase() + "</a>";

            if (check == false) {
                $("#skillResult2").append(str);
            }
            input2.val("");
        }
    }
})

//스터디 분야 삭제
$("#skillResult").on("click", "a.skillTag", function () {
    $(this).remove();
})
$("#skillResult2").on("click", "a.skillTag2", function () {
    $(this).remove();
})


$(document).ready(function () {

    skillChange();


    function showUploadFile(uploadResult) {
        let str = "";
        let projectImg = uploadResult.projectImg;
        let projectImgPath = uploadResult.projectImgPath;
        let projectImgUuid = uploadResult.projectImgUuid;

        str += "<input type='hidden' name='projectImg' value='" + projectImg + "'>"
        str += "<input type='hidden' name='projectImgPath' value='" + projectImgPath + "'>"
        str += "<input type='hidden' name='projectImgUuid' value='" + projectImgUuid + "'>"

        $("#projectData").append(str);
        $(".thumbnailBox").css("background-image", "url('/project/display?fileName=" + projectImgPath + "/" + projectImgUuid + "_" + projectImg + "')");
        $(".thumbnailBox").css("background-size", "cover");
        $(".thumbnailImage2").hide();
    }

    $("input[type='file']").change(function (e) {
        let inputFile = $("input[name='projectImg']");
        let files = e.target.files;
        let formData = new FormData();

        formData.append("uploadFile", files[0]);
        $.ajax({
            url: "/project/uploadAjaxAction",
            data: formData,
            type: "POST",
            // 현재 설정된 헤더 설정을 기본 방식으로 변경하지 못하도록 false로 설정
            processData: false,
            contentType: false,
            success: function (result) {
                showUploadFile(result);
            }
        });
    });


})






















