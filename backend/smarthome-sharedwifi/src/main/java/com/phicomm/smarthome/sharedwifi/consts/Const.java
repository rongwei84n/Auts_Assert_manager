package com.phicomm.smarthome.sharedwifi.consts;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * 常量定义
 * @author wenhua.tang
 *
 */
@PropertySources(@PropertySource(value = "classpath:server.properties", ignoreResourceNotFound = true))
public interface Const {

	String DEFAULT_CHARSET = "utf-8";

	/**
	 * header头部
	 */
	String AUTHORIZATION = "Authorization";

    String ORDER_PRE = "sw_";
    String STRING_EMPTY = "";
    String STRING_SHARED_WIFI_CODE = "SW";

	public interface ThirdParty {
		String PHICOMM_CLOUD_URL = "https://account.phicomm.com/";
		String PHICOMM_CLOUD_URL_ACCOUNT_DETAIL = PHICOMM_CLOUD_URL + "v1/accountDetail";

		String PHICOMM_CLOUD_BIND_ACCOUNT = "https://phiclouds.phicomm.com/routerappservicev1/routerapp/device/bindaccount";
	}

	public interface ResponseStatus {
		int STAUS_OK = 0;
		int STAUS_ERROR = 1;

		// 错误码定义， 10000以上定义为错误码
		int STAUS_COMMON_FAILED = 10001;

		int STAUS_WITH_DRAW_TOO_OFEN = 10004;// 提现太频繁
		int STAUS_WITH_DRAW_NO_BALANCE = 10005;// 提现余额不足
		int STAUS_NO_PARA_IN_REQUEST = 10006;// 没有请求参数
		int STAUS_NO_TOKEN_IN_REQUEST = 10007;// 请求参数没有token
		int STAUS_NO_OPEN_ID_IN_REQUEST = 10008;// 请求参数没有open_id
		int STAUS_ALIPAY_ACCOUNT_IN_REQUEST = 10009;// 请求参数没有支付宝账号
		int STAUS_FAILED_GET_PHICOMM_ACCOUNT = 10010;// 没有获取到对应的 斐讯云账号

		// 支付类错误码
		int STAUS_ORDER_PAYOK = 0;
		int STAUS_ORDER_PAYERROR = 1;
		int STAUS_ORDER_REFUND = 10111;// 转入退款
		int STAUS_ORDER_NOTPAY = 10012;// 未支付
		int STAUS_ORDER_CLOSED = 10013;// 已关闭
		int STAUS_ORDER_REVOKED = 10014;// 已撤销（刷卡支付）
		int STAUS_ORDER_USERPAYING = 10015;// 用户支付中

		int STAUS_DATABASE_OPERATE_ERROR = 10016;//操作数据库错误，比如插入一条数据
		int STAUS_BIND_OPENID_UID_ALREADY = 10017;//open id和uid已经绑定过
		int STAUS_NO_ROUTER_MAC_IN_REQUEST = 10018;//请求参数中没有路由器硬件地址

		// 订单类错误码
		int STAUS_ORDER_FAILED = 1;// 下单失败
		int STAUS_ORDER_NOT_EXIST = 10100;// 订单不存在
		// 公共类错误码
		int STAUS_LESS_PARA = 11000;// 参数缺失
		
		/** 服务正常返回数据 */
        int STATUS_OK = 0;
        String STATUS_OK_STR    = "服务正常返回数据";
        String STATUS_OK_ENABLE = "服务正常，但功能未启用";
		
		/** 错误码定义， 10000以上定义为错误码 */
        int STATUS_COMMON_FAILED = 10001;
		
        String ORDER_PRE = "sw_";
        String STRING_EMPTY = "";
        String STRING_SHARED_WIFI_CODE = "SW";


        /** 服务无异常,但请求参数异常 */
        int STATUS_COMMON_NULL = 10003;
        String STATUS_COMMON_NULL_STR = "服务正常,但请求参数异常";

        /** 服务无异常，但数据插入失败 */
        int STATUS_COMMON_INSERT_FAILED = 10004;
        String STATUS_COMMON_INSERT_FAILED_STR = "服务正常,但数据插入(注册)失败";
    }

    /** SERVICE　BUSINESS INTERFACES DEFINITION ************************************************/

    /** 订单支付状态 */
    public interface OrderPayStatusInterfaces{

        /** 订单支付状态_未支付_Int_0 */
        int ORDER_PAY_STATUS_UNPAID = 0;

        /** 订单支付状态_已支付_Int_1 */
        int ORDER_PAY_STATUS_PAID = 1;

    }

    /** 订单整体状态 */
    public interface OrderStatusInterfaces{

        /** 订单状态_未支付_Int_1 */
        int ORDER_STATUS_UNPAID        = 1;

        /** 订单状态_消费中_Int_2 */
        int ORDER_STATUS_PAIDINCOMMON  = 2;

        /** 订单状态_已过期_Int_3 */
        int ORDER_STATUS_EXPIRED       = 3;
    }

    /** 路由器访问者授权结果 */
    public interface GuestAuthorityResultInterfaces{

        /** 授权结果_通过_Int_0 */
        int AUTHORITY_RESULT_OK        = 0;

        /** 授权结果_不通过_Int_1 */
        int AUTHORITY_RESULT_NOK       = 1;

    }

    /** 路由器Auth心跳Action动作KEY */
    public interface RouterAuthBeatActionKeys{

        /** 登陆态 - 单一Device授权验证 */
        String AUTH_ACTION_KEY_LOGIN    = "login";

        /** 遍历态 - 多条Device授权验证 */
        String AUTH_ACTION_KEY_QUREY    = "query";

        /** 登出态 - 待确定 */
        String AUTH_ACTION_KEY_LOGOUT   = "logout";

        /** 请求下载 - 路由器重新上电后向服务获取全量授权通过Device list */
        String AUTH_ACTION_KEY_DOWNLOAD = "download";

    }

    /** 数据库内单条目有效性状态 */
    public interface DataItemStatusInDbInterfaces{

        /** 条目状态正常，继续提供业务 */
        int ITEM_STATUS_OK          = 0;

        /** 条目状态为禁用/删除，条目不可用 */
        int ITEM_STATUS_NOK         = -1;
    }

}
