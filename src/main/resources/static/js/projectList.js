// banner-button
let arrows = document.querySelectorAll("button.banner-button");
let li = document.querySelectorAll("li.slide");
let banner = document.querySelector("ul.slider");
let count = 0;

// banner
arrows.forEach((arrow) => {
    arrow.addEventListener("click", function () {
        let arrowType = arrow.classList[1];
        if (arrowType === 'prev-arrow' && count !== 0) {
            count--;
            $(".slide").removeClass("selected");
            li[count].classList.add("selected");
        } else if (arrowType === 'next-arrow' && count !== li.length - 1) {
            count++;
            $(".slide").removeClass("selected");
            li[count].classList.add("selected");
        } else {
            if (count === li.length - 1) {
                count = 0;
                $(".slide").removeClass("selected");
                li[count].classList.add("selected");
            } else {
                count = li.length - 1;
                $(".slide").removeClass("selected");
                li[count].classList.add("selected");
            }
        }

        banner.style.transform = "translate3d(-" + count * 100 + "%, 0px, 0px)";
    });
});

// Auto Slide
// setInterval(function() {
//     if (count == li.length - 1) {
//         count = 0;
//         $(".slide").removeClass("selected");
//         li[count].classList.add("selected");
//     }else {
//         count++;
//     }
//     banner.style.transform = "translate3d(-" + count * 100 + "%, 0px, 0px)";
// }, 5000);



// recruitModel display
$("div.recruitStatus").on("mouseover", function () {
    $(this).parent().children().closest("div.recruitModel").css("display", "block");
})

$("div.recruitStatus").on("mouseout", function () {
    $(this).parent().children().closest("div.recruitModel").css("display", "none");
})

$("div.recruitModel").on("mouseover", function () {
    $(this).css("display", "block");
})

$("div.recruitModel").on("mouseout", function () {
    $(this).css("display", "none");
})

// heart click

// move content

// move site


$(".filters").each(function (i,filter) {
$(filter).on("change",function () {
    let formData = new FormData();
    $($(".filter").get(i)).val($(this).val());
    if($("#checkFilter").is(":checked")==true){
        $($(".filter").get(2)).val("1");
    }else{
        $($(".filter").get(2)).val("0")
    }

    formData.append("locationFilter", $($(".filter").get(0)).val());
    formData.append("skillFilter", $($(".filter").get(1)).val());
    formData.append("statusFilter", $($(".filter").get(2)).val());

    $.ajax({
        url: "/project/projectFilter",
        data: formData,
        type: "POST",
        // 현재 설정된 헤더 설정을 기본 방식으로 변경하지 못하도록 false로 설정
        processData: false,
        contentType: false,
        success: function (result) {
            console.log(result)
        }
    });
})
})

// String locationFilter;
// String skillFilter;
// Long statusFilter;