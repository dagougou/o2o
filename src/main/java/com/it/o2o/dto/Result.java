package com.it.o2o.dto;

/**
 * @author wjh
 * @create 2019-06-02-18:02
 */
public class Result<T> {
    private T data;
    private boolean success;
    private String errorMsg;
    private int errorCode;

    public Result() {
    }

    //操作成功的构造器
    public Result(boolean success, T data) {
        this.data = data;
        this.success = success;
    }

    //操作错误的构造器

    public Result(boolean success, String errorMsg, int errorCode) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
