package com.zealtech.policephonebook2019.Model.response;

public class ResponseBase<T1> {
    private String code;
    private String message;
    private T1 data;

    public T1 getData() {
        return data;
    }

    public void  setData(T1 data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode() {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
