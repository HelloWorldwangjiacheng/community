// require('/js/jquery-3.4.1.min')

/**
*提交回复，type=1是问题
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}
/**
 *提交评论,type=2是评论
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    console.log(commentId);
    var commentContent = $("#input-" + commentId).val();
    console.log(commentContent);
    comment2target(commentId, 2, commentContent);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容")
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            console.log(response)
            if (response.code == 200) {
                // $("#comment_section").hide();
                alert("发送成功");
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=e2d964df476086c0e719&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        //为什么要弄这么一个标识呢？因为当其他页面登录和推出登录时，这边页面也能感知到而不用通过服务端提醒（太麻烦）
                        window.localStorage.setItem("closable", true);
                    }
                } else if (response.code == 2007) {

                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

/**
 *   展开二级评论
 */
function collapseComments(e) {
    console.log("我被点击");
    var dataId = e.getAttribute("data-id");
    var comments = $("#comment-" + dataId);

    if (!comments.hasClass("in")) {
        var subCommentContainer = $("#comment-"+dataId);

        if (subCommentContainer.children().length != 1){
            // 展开二级评论
            comments.addClass("in");
            e.classList.add("active");
        }else{
            $.getJSON("/comment/"+dataId,function (data) {
                console.log(data);
                //大的data是一个包含code,message,data
                //data.data才是一个数组，里面每一条都是一个评论对象，一条评论
                $.each(data.data.reverse(),function (index,comment) {
                    // var avatarElement = $("<img/>",{
                    //     "class":"media-object img-rounded",
                    //     "src":comment.user.avatarUrl
                    // })
                    //左边头像部分的标签
                    var mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }))
                    //右边文字部分的标签
                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu",
                    }).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format("YYYY-MM-DD hh:mm")
                    })));
                    //上面使用了moment.js格式化日期，因为在js不能用之前的格式化日期的方式了
                    //将左边和右边的标签都串联起来，整合成一个标签
                    var mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    //最后再将上面的mediaElement放到那个循环的comments里面，进行循环展示
                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                comments.addClass("in");
                e.classList.add("active");
            });
        }



    } else {
        comments.removeClass("in");
        e.classList.remove("active")
    }
    // var collapse = e.getAttribute("data-collapse");
    // if (collapse){
    //     comments.removeClass("in");
    //     e.removeAttribute("data-collapse");
    // }else {
    //     //展开二级评论
    //     comments.addClass("in");
    //     //标记二级评论展开状态
    //     e.setAttribute("data-collapse","in");
    // }
}

/**
 * 点击标签添加到输入框中
 */
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    console.log(previous)
    if (previous.indexOf(value) == -1){
        if (previous){
            $("#tag").val(previous+','+value);
        }else {
            $("#tag").val(value);
        }
    }
}

function showSelectTag() {
    $("#select-tag").show();
}