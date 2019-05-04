package com.andy.consumer.pojo;

public class MainResult {
    private Integer code;
    private String msg;
    private Object data;

    public MainResult(Object data) {
        this.code = BizConstant.RespCode.SUCCESS;
        this.msg = BizConstant.RespMsg.SUCCESS;
        this.data = data;
    }

    public MainResult() {
        this.code = BizConstant.RespCode.SUCCESS;
        this.msg = BizConstant.RespMsg.SUCCESS;
    }

    public MainResult(String msg) {
        this.code = BizConstant.RespCode.FAIL;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
