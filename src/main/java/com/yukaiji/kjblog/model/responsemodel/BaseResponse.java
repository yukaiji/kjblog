package com.yukaiji.kjblog.model.responsemodel;

/**
 * @author kaijiyu
 */
public class BaseResponse {

    /** 是否成功 1是 0否**/
    private String success = "1";
    /** 错误消息 **/
    private String message;
    /** 错误码 **/
    private String code;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
