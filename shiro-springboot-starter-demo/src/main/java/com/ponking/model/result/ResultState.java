package com.ponking.model.result;

/**
 * @author Peng
 * @date 2020/6/25--12:52
 **/
public enum ResultState {
    SUCCESS("成功",20000),
    FAILED("失败",20001);

    private final String msg;
    private final long code;

    ResultState(String msg, long code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public long getCode() {
        return code;
    }
}
