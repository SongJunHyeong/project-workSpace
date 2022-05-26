/* chatting */

$("div.chattingOpen").on("click", function () {
    $(this).removeClass("open");
    $("div.chatting").addClass("open");
})

$("div.foldChatButton").on("click", function () {
    $(this).parents("div.chatting").removeClass("open");
    $("div.chattingOpen").addClass("open");
})


//userNickName 세션 담기
let myName = document.getElementById("userNickName").value;

sessionStorage.setItem("mineSession", myName);


//세션가져오기
let v = sessionStorage.getItem("mineSession");

const chatListli = document.getElementById('chatList')
const chatListlis = document.querySelectorAll('#chatList')
//세션값 append
document.getElementById("ppp").append("내아이디:" + v)


//전체 회원 목록 ※필요없슴※

let result = "";
$.ajax({
    url: "/list",
    dataType: "json",
    type: "get",
    async: false,
    success: function (data) {
        result = data
        console.log(result)

    },
    error: function () {
        alert("회원 목록 가져오기 실패");
    }

});


//회원목록 맵핑
let lili = document.querySelectorAll('.lili');
let ulclass = document.querySelector('#ulclass')
// ?는 준비됐니?
result?.map((v) => {
    ulclass.innerHTML +=
        `<li class="lili">${v}</li>`


})


//클릭시 해당 아이디와 내 아이디 url 파싱

ulclass.addEventListener("click", function (e) {


    if (e.target.tagName === "LI") {

        let id = {
            //v는 자기 세션아이디
            // e.타켓은 누른 상대방 아이디
            senderId: v,
            receiverId: e.target.innerText
        }
        let namev = v + e.target.innerText;
        let name = {
            roomName: v + e.target.innerText
        }

        //세션주기
        sessionStorage.setItem("innerText", e.target.innerText);


        //채팅방 존재 검사 후  succ => 기존이름생성 err=> 새로운 이름 생성
        $.ajax({
            type: "POST",
            url: `/chatHistory/${v}/${e.target.innerText}`,
            contentType: "application/json",

            success: function () {

                let result = "";
                $.ajax({
                    url: `/connectRoom/${v}/${e.target.innerText}`,
                    dataType: "json",
                    type: "get",
                    async: false,
                    success: function (data) {
                        result = data
                        let roomNames = result[0].roomName;

                        makeRoom(roomNames);
                    },

                });
            },
            error: function () {

                let roomNames = v + e.target.innerText

                makeRoom(roomNames);
            }
        });
    }
});


//채팅내역있으면 보여주기

let chatList = [];
$.ajax({
    url: `/chatList/${v}/${v}`,
    dataType: "json",
    type: "get",
    async: false,
    success: function (data) {
        chatList = data

    },
    error: function () {
        console.log(this.url)
        alert("채팅 내역 가져오기 실패");
    }
});


//채팅방에서 내이름 빼기
function removesession() {
    //얘는 변수 바꿔도댐
    let nametd = document.querySelectorAll('.nametd');

    for (i = 0; i < nametd.length; i++) {

        let receiverName = nametd[i].innerText
        let matchname = receiverName.match(v)
        let finl = receiverName.replace(matchname, '');

        nametd[i].innerText = finl

    }

}


//채팅내역 렝스가 0 이면  존재하지않음 출력  => 0이상 방이름 생성
chatList.length === 0 ? chatListli.innerHTML += '<li class="nametd">채팅내역이 존재하지 않음</li>' :
    chatList?.map((v) => {


        chatListli.innerHTML += `
                <li class="nametd"  >${v.roomName}</li>
                `


        removesession()

    })
let nametd = document.querySelectorAll('.nametd');

for (i = 0; i < nametd.length; i++) {
    nametd[i].addEventListener('click', function (e) {

        sessionStorage.setItem("innerText", e.target.innerText);
        sss = e.target.innerText


        $.ajax({
            type: "POST",
            url: `/chatHistory/${v}/${sss}`,
            contentType: "application/json",

            success: function () {

                let result = "";
                $.ajax({
                    url: `/connectRoom/${v}/${sss}`,
                    dataType: "json",
                    type: "get",
                    async: false,
                    success: function (data) {
                        result = data
                        console.log(result)
                        let roomNames = result[0].roomName;

                        makeRoom(roomNames);
                    },

                });
            }
        });
    })
}

//방생성 하기!
const makeRoom = (roomNames) => {
    $.ajax({
        type: "POST",
        url: ` room/new `,
        data: roomNames,
        contentType: "application/json",
        success: function () {
            sessionStorage.setItem("namev", roomNames);
            console.log(roomNames)
            window.open(`/rooms/${roomNames}`, "_blank", "채팅", "width:300px  height: 400px, top=10, left=10");
        },
        error: function () {
            console.log(roomNames)
            alert("방 생성 실패");
        }
    })
}