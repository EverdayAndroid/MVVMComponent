package com.everday.module_login.entity;
/**
 * @author Everday
 * @emil wangtaohandsome@gmail.com
 * create at 2019/3/29
 * description: 用户信息
 */
public class LoginInfoBean {
    private Integer status;
    private String msg;
    private LoginBean data;

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public LoginBean getData() {
        return data;
    }
}
