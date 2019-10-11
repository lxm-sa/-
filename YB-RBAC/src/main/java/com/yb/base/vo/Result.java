package com.yb.base.vo;

/**
 * 功能描述：返回结果类
 * Created by Administrator on 2019/7/18.
 */
public class Result {

    /**
     * 操作结果
     */
    protected Boolean success = true ;

    /**
     * 返回消息
     */
    protected String message;

    /**
     * 状态码
     */

    protected int statusCode;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
