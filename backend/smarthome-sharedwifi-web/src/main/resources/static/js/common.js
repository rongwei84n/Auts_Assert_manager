// 布局自适应
var height=$(window).height();
$(".wrap").css("min-height",height+'px');

var popT=(height-415)/2+'px';
$(".pop").css("top",popT);

var code_url;
var order_id;
var userAgent=window.navigator.userAgent;
$(".fee").text(online_time_unit_price);
$(".p2-tips").text('完成打赏将享受'+online_time_unit+'小时高速上网');
$(".page-3 span").text('可享受'+online_time_unit+'小时高速上网');

//    portal页点击红包打赏上网
$(".btn-online").click(function () {
    $(".login-left").hide();
    $(".state").show();
    $(".page-1").removeClass("page-show");
    $(".page-2").addClass("page-show");
    makeOrder();  //下单
});

// 二维码过期
$("#refresh").click(function () {
    $("#code").empty().css("opacity","1");
    $("#load-fail").hide();
    $("#refresh").hide();
    $("#loading").show();
    $("#tip").text("二维码加载中...");
    makeOrder();  //下单
});

// 弹窗
$(".agreement").click(function () {
    $(".pop").css("display","block");
});

$(".close").click(function () {
   $(".pop").css("display","none");
});

// 下单
function makeOrder() {
    var dataJson=JSON.stringify({
        "device_mac": device_mac,
        "online_time": "2",
        "online_time_unit": online_time_unit,
        "online_time_unit_price": online_time_unit_price,
        "router_mac": router_mac,
        "sign": sign
    });
    $.ajax({
        url: "/sharedwifi/v1/h5web/unifiedorder",
        headers: {
            "userAgent" : userAgent+"phicomm pc"
        },
        contentType : "application/json;charset=UTF-8",
        async: false,
        type: "post",
        dataType: "json",
        data: dataJson,
        success: function (data) {
            if(data.err_code==0 && data.result.code_url!=null){
                console.log(data.result.order_id);
                code_url=data.result.code_url;
                order_id=data.result.order_id;
                qrcodeFn(code_url);
                setTimeout('checkOrder(order_id)',5000);
            }else{
                $("#load-fail").show();
                $("#refresh").show();
                $("#tip").text("二维码加载失败，请刷新");
                console.log("二维码加载失败");
            }
        }
    });
}

//url转二维码
function qrcodeFn(code_url) {
    $("#code").empty();
    $("#loading").hide();
    $("#code").qrcode({
        render: "canvas",
        width: 100,
        height:100,
        text: code_url
    });
    console.log(code_url);
    $("#tip").text("扫一扫，微信支付");
}

// 查询订单
function checkOrder(order_id) {
    var dataStr=JSON.stringify({
        "device_mac": device_mac,
        "order_id": order_id,
        "router_mac": router_mac
    });
    $.ajax({
        url: "/sharedwifi/v1/h5web/queryorder",
        contentType : "application/json;charset=UTF-8",
        async: false,
        type: "post",
        dataType: "json",
        data:dataStr,
        success: function (data) {
            console.log(data.result.ret_code);
            switch (data.result.ret_code){
                case 0:
                    // $("#tip").text("正在放行设备");
                    onlineFn();
                    break;
                case 10012:
                    setTimeout('checkOrder(order_id)',3000);
                    break;
                case 10015:
                    setTimeout('checkOrder(order_id)',3000);
                    break;
                case 2:
                    $("#tip").text("二维码已失效，请刷新");
                    $("#code").css("opacity",".2");
                    $("#refresh").show();
                    break;
                default:
                    $(".state").hide();
                    $(".login-left").show();
                    $(".page-2").removeClass("page-show");
                    $(".page-4").addClass("page-show");
                    console.log("支付失败");
                    break;
            }

        }
    });
}
var timer;
var n=0;
function onlineFn() {
// 永久放行
    var onlineUrl="http://"+router_ip+":"+router_port+"/sharedwifi/auth?device_ip="+device_ip+"&device_mac="+device_mac+"&order_id="+order_id+"&stage=login";
    console.log(onlineUrl);
// 临时放行
//     var onlineUrlTemp="http://"+router_ip+":"+router_port+"/sharedwifi/extend_auth?device_mac="+device_mac+"&message=allow-guest&sign="+sign+"&timestamp="+timestamp;
//     console.log(onlineUrlTemp);

    $.ajax({
        url: onlineUrl,
        async: false,
        type: "get",
        dataType: "json",
        success : function(data){
            console.log(data.auth,n);
            if( $(".page-2").hasClass("page-show")){
                $(".page-2").removeClass("page-show");
                $(".state").hide();
                $(".login-left").show();
            }
            if(data.auth==1){
                $(".page-3").addClass("page-show");
                setTimeout("window.location.href='https://mall.phicomm.com/webmall.php?channel_type=PHICOMM'",2000);
            }else if(data.auth==0){
                if(!$(".page-5").hasClass("page-show")) {
                    $(".page-5").addClass("page-show");
                }
                if(n==1440){
                    clearTimeout(timer);
                    // $(".page-5").removeClass("page-show");
                    // $(".page-4").addClass("page-show");
                    console.log("放行失败");
                }else{
                    n++;
                    timer=setTimeout("onlineFn();",5000);
                }
            }
            console.log(n);
        },
        error : function(data){
            console.log("error");
        }
    });
}
