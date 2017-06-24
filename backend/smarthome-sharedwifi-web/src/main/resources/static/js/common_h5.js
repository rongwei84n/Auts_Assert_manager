//    获取url
var trade_state=0;
function GetRequest() {
    var url = location.search; //获取url中"?"符后的支付情况
    var theRequest = new Object();
    if (url.indexOf("trade_state") != -1) {
        $(".page-1").removeClass("page-show");
        $(".page-5").addClass("page-show");
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
        }
        trade_state=1;
    }
    return theRequest;
}
GetRequest();

// 布局自适应
function SelfAdaption() {
    var htmlwidth = $("html").width();
    if(htmlwidth >= 640) {
        $("html").css({
            "font-size" : "40px"
        });
        $(".wrap").css({
            "width": "640px"
        })
    } else {
        if(htmlwidth <= 320) {
            $("html").css({
                "font-size" : "20px"
            });
            $(".wrap").css({
                "width": "320px"
            })
        } else {
            $("html").css({
                "font-size" : htmlwidth * 40 / 640 + "px"
            });
            $(".wrap").css({
                "width": htmlwidth
            })
        }
    }
}SelfAdaption();
$(window).resize(function(){
    SelfAdaption()
});

var height=$(window).height();
$(".wrap").css("min-height",height+'px');


var code_url;
var order_id;
var userAgent=window.navigator.userAgent;
// 获取order_id
if(trade_state==1){
    var Request = GetRequest();
    var order_id = Request['order_id'];  //dataName就是url？号后跟的参数名
    console.log(order_id);
    checkOrder(order_id);
}

console.log(userAgent);
$(".fee").text(online_time_unit_price);
$(".p2-tips").text('完成打赏将享受'+online_time_unit+'小时高速上网');
$(".page-3 span").text('可享受'+online_time_unit+'小时高速上网');

//    portal页点击红包打赏上网
$(".btn-online").click(function () {
    onlineTempFn();  //临时放行
});


// 弹窗
$(".agreement").click(function () {

    $(".pop").css("display","block");
    var popH=$(".pop-cont").height();
    var popT=(height-popH)/2+'px';
    $(".pop").css("top",popT);
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
            "userAgent" : userAgent+"phicomm mobile"
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
                $(".btn-pay").attr("href",code_url).css("opacity","1");
                checkOrder(order_id);
            }else{
               console.log("返回url失败");
               makeOrder();//重新下单
            }
        }
    });
}

$(".btn-pay").click(function () {
   if($(this).attr("href")=="##"){
       alert("请稍等！");
   }
});



// 查询订单
function checkOrder(order_id) {
    var dataStr=JSON.stringify({
        "device_mac": device_mac,
        "order_id": order_id,
        "router_mac": router_mac
    });
    $.ajax({
        url: "/sharedwifi/v1/h5web/queryorder",
        contentType:"application/json;charset=UTF-8",
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
                default:
                    $(".page-5").removeClass("page-show");
                    $(".page-4").addClass("page-show");
                    console.log("支付失败");
                    break;
            }

        }
    });
}
var timer;
var n=0;
// 临时放行
function onlineTempFn() {
    // 临时放行
    var onlineUrlTemp="http://"+router_ip+":"+router_port+"/sharedwifi/extend_auth?device_mac="+device_mac+"&message=allow-guest&sign="+sign+"&timestamp="+timestamp;
    console.log(onlineUrlTemp);
    $.ajax({
        url: onlineUrlTemp,
        async: false,
        type: "get",
        dataType: "json",
        success : function(data){
            console.log(data.code,n);
            if(data.code==1){
                $(".page-1").removeClass("page-show");
                $(".page-2").addClass("page-show");
                makeOrder();//下单
            }else{
                alert("临时放行失败");
            }
        },
        error : function(data){
            console.log("error");
        }
    });
}


function onlineFn() {
// 永久放行
    var onlineUrl="http://"+router_ip+":"+router_port+"/sharedwifi/auth?device_ip="+device_ip+"&device_mac="+device_mac+"&order_id="+order_id+"&stage=login";
    console.log(onlineUrl);

    $.ajax({
        url: onlineUrl,
        async: false,
        type: "get",
        dataType: "json",
        success : function(data){
            console.log(data.auth,n);
            if(data.auth==1){
                $(".page-5").removeClass("page-show");
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
