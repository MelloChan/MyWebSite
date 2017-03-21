/**
 * Created by Administrator on 2016/11/20.
 */
// var ws=new WebSocket("ws://"+location.host+":8888");
ws.onopen=function () {//WebSocket创建成功时被触发
    var content=$(".content").val();
    if(content==null|| content==""){
        ws.send("Null");
    }else {
        ws.send(content);
    }
};
ws.onmessage=function (evt) {//当客户端收到服务器端发来的消息时 被触发 参数evt为服务器端传递过来的消息
    document.getElementById("span").innerHTML="当前在线："+evt.data;
    $("#content").html("当前在线:"+evt.data);
};

ws.onclose=function (evt) {//当客户端收到服务器端发来的关闭连接请求时被触发
    alert("您已被强制下线!"+evt.data);
};

ws.onerror=function (evt) {//连接、处理、接受、发送数据失败时触发onerror
    alert("连接异常！"+evt.data);
};
/*异步回调*/