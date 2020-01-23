
function post() {
    var questionId = $("#question_id").val();
    console.log(questionId);
    var content = $("#comment_content").val();
    console.log(content);

    if (!content){
        alert("不能回复空内容")
        return ;
    }


    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        dataType:"json",
        data:JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success:function (response) {
            console.log(response)
            if (response.code == 200){
                // $("#comment_section").hide();
                alert("发送成功");
                // window.document.reload();
                window.location.reload();
            }else{
                if (response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=e2d964df476086c0e719&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        //为什么要弄这么一个标识呢？因为当其他页面登录和推出登录时，这边页面也能感知到而不用通过服务端提醒（太麻烦）
                        window.localStorage.setItem("closable",true);
                    }
                }else if (response.code ==  2007){

                }else {
                    alert(response.message);
                }
            }
        },
        dataType:"json"
    });
}