package com.ponking.model.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回对象
 * @author Peng
 * @date 2020/6/25--12:49
 **/
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private long code;
    private T data;
    private String message;

    private Result(){

    }

    public static <T> Result<T> success(T data){
        return restResult(data, ResultState.SUCCESS.getCode(),ResultState.SUCCESS.getMsg());
    }
    public static <T> Result<T> success(){
        return restResult(null, ResultState.SUCCESS.getCode(),ResultState.SUCCESS.getMsg());
    }

    public static <T> Result<T> failed(T data){
        return restResult(data, ResultState.FAILED);
    }

    public static <T> Result<T> failed(){
        return restResult(null, ResultState.FAILED);
    }

    private static <T> Result<T> restResult(T data, long code, String message) {
        Result apiResult = new Result();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMessage(message);
        return apiResult;
    }
    private static <T> Result<T> restResult(T data, ResultState state) {
        Result apiResult = new Result();
        apiResult.setCode(state.getCode());
        apiResult.setData(data);
        apiResult.setMessage(state.getMsg());
        return apiResult;
    }

    public Result<T> data(T data){
        this.setData(data);
        return this;
    }

    public Result<T> message(String message){
        this.setMessage(message);
        return this;
    }

    public Result<T> code(long message){
        this.setCode(message);
        return this;
    }

    private void setCode(long code) {
        this.code = code;
    }

    private void setData(T data) {
        this.data = data;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
