package com.example.southplatform.network.exception;

import androidx.annotation.Nullable;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.text.ParseException;

import retrofit2.HttpException;

public class ExceptionHandle {
    //http返回码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseException handleException(Throwable e) {
        ResponseException rex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            /**
             * 传入状态码，根据状态码判定错误信息
             */
            rex = new ResponseException(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    rex.setMessage("未验证");
                    break;
                case FORBIDDEN:
                    rex.setMessage("服务禁止访问");
                    break;
                case NOT_FOUND:
                    rex.setMessage("服务不存在");
                    break;
                case REQUEST_TIMEOUT:
                    rex.setMessage("请求超时");
                    break;
                case GATEWAY_TIMEOUT:
                    rex.setMessage("网关超时");
                    break;
                case INTERNAL_SERVER_ERROR:
                    rex.setMessage("服务器内部错误");
                    break;
                case BAD_GATEWAY:

                    break;
                case SERVICE_UNAVAILABLE:
                    break;
                default:
                    rex.setMessage("网络错误");
                    break;
            }
            return rex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            rex = new ResponseException(e, ERROR.PARSE_ERROR);
            rex.setMessage("解析错误");
            return rex;
        } else if (e instanceof ConnectException) {
            rex = new ResponseException(e, ERROR.NETWORD_ERROR);
            rex.setMessage("连接失败");
            return rex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            rex = new ResponseException(e, ERROR.SSL_ERROR);
            rex.setMessage("证书验证失败");
            return rex;
        } else {
            rex = new ResponseException(e, ERROR.UNKNOWN);
            rex.setMessage("未知错误");
            return rex;
        }
    }

    public class ERROR {
        /**
         * 自定义异常
         */
        private static final int UNAUTHORIZED = 401;//请求用户进行身份验证
        private static final int UNREQUEST = 403;//服务器理解请求客户端的请求，但是拒绝执行此请求
        private static final int UNFINDSOURCE = 404;//服务器无法根据客户端的请求找到资源
        private static final int SEVERERROR = 500;//服务器内部错误，无法完成请求。
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;
        //系统级错误代码
        private static final int a1 = 10001;//System error	                                                                                                  系统错误
        private static final int a2 = 10002;//Needtoken	                                                                                                      缺少Token
        private static final int a3 = 10003;//Token expired	                                                                                                  Token过期
        private static final int a4 = 10004;//Needtimestamp	                                                                                                  缺少timestamp字段
        private static final int a5 = 10005;//Errortimestampformat	                                                                                          Timestamp格式错误
        private static final int a6 = 10006;//Timestampexpired	                                                                                              Timestamp过期
        private static final int a7 = 10007;//Param error, see doc for more info	                                                                          参数错误，请参考API文档
        private static final int a8 = 10008;//Miss required parameter (%s) , see doc for more info	                                                          缺失必选参数 (%s)，请参考API文档
        private static final int a9 = 10009;//Parameter (%s)'s value invalid, expect (%s) , but get (%s) , see doc for more info	                          参数值非法，需为 (%s)，实际为 (%s)，请参考API文档
        private static final int a10 = 10010;//IP requests out of rate limit	                                                                              IP请求频次超过上限
        private static final int a11 = 10011;//User requests out of rate limit	                                                                              用户请求频次超过上限
        //  服务级错误代码
        private static final int b1 = 20001;//	Device does not exists	                                                                                      设备不存在
        private static final int b2 = 20002;//Device already exists	                                                                                          设备已存在
        private static final int b3 = 20003;//Device authentication error,you can not add	                                                                  设备验证出错，不能添加
        private static final int b4 = 20004;//Email's value invalid	Email                                                                                     不合法
        private static final int b5 = 20005;//	Username's value invalid	                                                                                  用户名不合法
        private static final int b6 = 20006;//	User already exists	                                                                                          用户已经存在
        private static final int b7 = 20007;//User password is error	                                                                                      用户密码错误
        private static final int b8 = 20008;//User type is invalid	                                                                                          用户类型不合法
        private static final int b9 = 20009;//Email already exists	                                                                                          Email已经存在
        private static final int b10 = 20010;//Email not change	Email                                                                                         没有改变
        private static final int b11 = 20011;//The SMS verification code error,Please re-obtain the SMS verification code                                     短信验证码错误，请重新获取短信验证码
        private static final int b12 = 20012;//Mobile already exists	                                                                                      手机号码已经存在
        private static final int b13 = 20013;//Mobile is invalid	                                                                                          手机号码错误
        private static final int b14 = 20014;//User not exists	                                                                                              用户不存在
        private static final int b15 = 20015;//Image can not empty	                                                                                          图片不能为空
        private static final int b16 = 20016;//Send SMS interval is too small	                                                                              发送短信间隔太小
        private static final int b17 = 20017;//QQ already bind	                                                                                              QQ已经绑定
        private static final int b18 = 20018;//QQ not bind	                                                                                                  QQ没有绑定
        private static final int b19 = 20019;//Device not share	                                                                                              设备无分享
        private static final int b20 = 20020;//Device share invalid	                                                                                          设备分享无效
        private static final int b21 = 20021;//Send sms count error	                                                                                          短信数据达到最大限制
        private static final int b22 = 20022;//Target user not exists	                                                                                      目标用户不存在
        private static final int b23 = 20023;//user no permission share device	                                                                              用户无权限添加分享设备
        private static final int b24 = 20024;//user no permission delete  share device                                     	                                  用户无权限删除分享设备
        private static final int b27 = 20027;//Area code not exists	                                                                                          国际地区代码不存在
        private static final int c26 = 30026;//app_key is not null	                                                                                          app_key不能为空
        private static final int c27 = 30027;//parameter length no math	                                                                                      参数长度不符合
        private static final int c28 = 30028;//parameter length large	                                                                                      参数超过最大长度
        private static final int c29 = 30029;//parameter no math reg	                                                                                      参数检验不合法
        private static final int c30 = 30030;//app key not null	App key                                                                                       不能为空
        private static final int c31 = 30031;//sign invalid	                                                                                                  签名无效
        private static final int c32 = 30032;//sign is not null	                                                                                              签名不能为空
        private static final int c33 = 30033;//company_id is not null	                                                                                      厂商ID不能为空
        private static final int c34 = 30034;//timestamp is not null	timestamp                                                                             不能为空
        private static final int c35 = 30035;//imei is not null	imei                                                                                          不能为空
        private static final int c36 = 30036;//device id is not null	                                                                                      设备编码不能为空
        private static final int c37 = 30037;//device type is not null	                                                                                      设备类型不能为空
        private static final int c38 = 30038;//target userid is not null	                                                                                  目标用户ID不能为空
        private static final int c39 = 30039;//sensor is is not null	                                                                                      传感器编号不能为空
        private static final int c40 = 30040;//sensor type is not null	                                                                                      传感器类型不能为空
        private static final int c41 = 30041;//sensor type is not legitimate	                                                                              传感器类型格式不合法
        private static final int c42 = 30042;//Data type is not null	                                                                                      请求的数据类型不能为空
        private static final int c43 = 30043;//Data type is not legitimate	                                                                                  请求数据类型格式不合法
        private static final int c44 = 30044;//Page parameter is not null	                                                                                  页数不能为空
        private static final int c45 = 30045;//Page parameter is not legitimate	                                                                              页数检验格式不合法
        private static final int c46 = 30046;//Status parameter is not null	                                                                                  传感器状态不能为空
        private static final int c47 = 30047;//Log data parameter is not null	                                                                              功率数据不能为空
        private static final int c48 = 30048;//Date format parameter is not null	                                                                          日期格式不能为空
        private static final int c49 = 30049;//Date format is not legitimate	                                                                              日期格式不合法，如：2015-11-19
        private static final int c50 = 30050;//	Month format is not legitimate	                                                                              月份格式不合法，如：2015-11
        private static final int c51 = 30051;//	Year format is not legitimate	                                                                              年份格式不合法，如：2015
        private static final int c52 = 30052;//User shared device	                                                                                          该设备已分享
        private static final int c53 = 30053;//User share device is success	                                                                                  设备分享成功
        private static final int c54 = 30054;//Control data is not null	                                                                                      控制数据不能为空
        private static final int c55 = 30055;//Sensor name parameter is not null	                                                                          传感器名称不能为空
        private static final int c56 = 30056;//Mobile parameter is not null	                                                                                  手机号码不能为空
        private static final int c57 = 30057;//Device id parameter is not legitimate                                     	                                  设备编码参数不合法
        private static final int c58 = 30058;//Device ring.xml parameter is not null 	                                                                          设备标题不能为空
        private static final int c59 = 30059;//Device module parameter is invalid	                                                                          设备模式无效
        private static final int c60 = 30060;//Device type parameter is invalid	                                                                              设备类型无效
        private static final int c61 = 30061;//Area code parameter is not null	                                                                              地区编号不能为空
        private static final int c62 = 30062;//Area code parameter is null	                                                                                  地区编号为空
        private static final int c63 = 30063;//User info is null	                                                                                          用户信息为空
        private static final int c64 = 30064;//User nickname is invalid	                                                                                      用户昵称无效
        private static final int c65 = 30065;//Password is not null	                                                                                          密码不能为空
        private static final int c66 = 30066;//Email format is not legitimate	                                                                              Email格式不合法
        private static final int c67 = 30067;//Area code is not exists	                                                                                      地区编码不存在
        private static final int c68 = 30068;//Validcode is not null	                                                                                      短信验证码不能为空
        private static final int c69 = 30069;//Valid ticket is not null	                                                                                      短信验证ticket不能为空
        private static final int c70 = 30070;//Old Password is not null	                                                                                      旧密码不能为空
        private static final int c71 = 30071;//Message valid ticket error	                                                                                  短信验证ticket错误
        private static final int c72 = 30072;//Message valid code error	                                                                                      短信验证码错误，请重新获取短信验证码
        private static final int c73 = 30073;//Qq opened is not null	Qq openid                                                                             不能为空
        private static final int c74 = 30074;//User old password is error	                                                                                  用户旧密码错误
        private static final int c75 = 30075;//Name is not null	                                                                                              用户名称不可为空
        private static final int c76 = 30076;//Name modify error	                                                                                          用户名称修改失败
        private static final int c77 = 30077;//Sex is not null	                                                                                              用户性别不能为空
        private static final int c78 = 30078;//Sex format error	                                                                                              用户性别格式错误
        private static final int c79 = 30079;//Head set error	                                                                                              头像设置失败
        private static final int c80 = 30080;//User address is not null	                                                                                      用户地址不能为空
        private static final int c81 = 30081;//Address set error	                                                                                          地址设置失败
        private static final int c82 = 30082;//User base info set error	                                                                                      用户基本信息修改失败
        private static final int c83 = 30083;//Email is not null	e-mail                                                                                    地址不能为空
        private static final int c84 = 30084;//Email set error	                                                                                              邮箱修改失败
        private static final int c85 = 30085;//I token not null	                                                                                              苹果i_token不可为空
        private static final int c86 = 30086;//User Type is not null	                                                                                      用户类型不能为空
        private static final int c87 = 30087;//Time is not null	                                                                                              日期不能为空
        private static final int c89 = 30089;//No.key is not null	                                                                                          主键编号不能为空
        private static final int c95 = 30095;//Version code is not null	                                                                                      版本编号不能为空
        private static final int c96 = 30096;//Os is not null	                                                                                              操作系统类型不能为空
        private static final int c97 = 30097;//Company id is not null 	                                                                                      APP版本升级时所对应的厂商编号不能为空
        private static final int c98 = 30098;//Data type is not null	                                                                                      数据类型不能为空
        private static final int c99 = 30099;//Action Content is not null	                                                                                  执行内容不能为空
        private static final int c102 = 30102;//Remarks is not null	                                                                                          评论内容不能为空
        private static final int c109 = 30109;//User nickname is not null	                                                                                  用户昵称不能为空
        private static final int c110 = 30110;//language param is not null	                                                                                  国际化语言参数不能为空
        private static final int c111 = 30111;//Email can not be active	                                                                                      用户邮箱没有激活
        private static final int c112 = 30112;//date format is wrong	                                                                                      日期格式错误
        private static final int c113 = 30113;//no latest version	                                                                                          没有最新版本
        private static final int c114 = 30114;//mark url is null	                                                                                          客户端路径为空
        private static final int c122 = 30122;//moblie already bound                                                                                          手机号码已绑定
    }

    public static class ResponseException extends Exception {
        private int code;
        private String message;

        public ResponseException(Throwable cause, int code) {
            super(cause);
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Nullable
        @Override
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "ResponseException{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
