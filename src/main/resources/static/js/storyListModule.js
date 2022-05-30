let storyService = (function () {
    function selectList(storyPart, callback) {
        console.log(storyPart);
        $.ajax({
            type:"GET",
            url:"/story/selectList/" + storyPart,
            success: function (storyDTO) {
                console.log(storyDTO);
                if (callback) {
                    console.log("callback 들어옴");
                    callback(storyDTO.storyVO, storyDTO.userVO, storyDTO.storyTagVOs, storyDTO.storyLikeSize, storyDTO.storyReplySize);
                }
            },
            error: function (xhr, status, error) {
                if (error) {
                }
            }
        })
    }

    function getList(storyNum, callback){
        $.ajax({
            type:"GET",
            url:"/story/getList/" + storyNum,
            success: function (loungeReplyDTO) {
                if (callback) {
                    callback(loungeReplyDTO.userNickNames, loungeReplyDTO.replies);
                }
            },
            error: function (xhr, status, error) {
                if (error) {
                }
            }
        });
    }

    function insertReply(param, callback) {
        $.ajax({
            type:"GET",
            url:"/story/storyInsert/"+ param.storyReply + "/" + param.userNum + "/" + param.storyNum,
            success: function (result) {
                if (callback) {
                    callback(result);
                }
            },
            error: function (xhr, status, error) {
                if (error) {
                }
            }
        })
    }

    function resetReply(storyNum, callback){
        $.ajax({
            type:"GET",
            url:"/story/resetReply/" + storyNum,
            success: function (result) {
                if (callback) {
                    callback(result);
                }
            },
            error: function (xhr, status, error) {
                if (error) {
                }
            }
        });
    }

    return {selectList: selectList, getList:getList, insertReply:insertReply,resetReply:resetReply}
})();