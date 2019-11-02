package com.njwb.entity;

/**
 * @program: SupermarketBillsManager
 * @description: 用户表
 * @author: 张文轩
 * @create: 2019-10-25 11:40
 **/
public class User {
    // 用户ID
    private int u_id;
    // 用户名称
    private String u_name;
    // 用户密码
    private String u_password;
    // 用户性别
    private String u_gender;
    // 用户年龄
    private int u_age;
    // 用户电话
    private String u_phone;
    // 用户地址(选填)
    private String u_address;
    // 用户权限
    private String u_auth;

    public User() {
    }

    public User(int u_id, String u_name, String u_password, String u_gender, int u_age, String u_phone, String u_address, String u_auth) {
        this.u_id = u_id;
        this.u_name = u_name;
        this.u_password = u_password;
        this.u_gender = u_gender;
        this.u_age = u_age;
        this.u_phone = u_phone;
        this.u_address = u_address;
        this.u_auth = u_auth;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public String getU_gender() {
        return u_gender;
    }

    public void setU_gender(String u_gender) {
        this.u_gender = u_gender;
    }

    public int getU_age() {
        return u_age;
    }

    public void setU_age(int u_age) {
        this.u_age = u_age;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getU_address() {
        return u_address;
    }

    public void setU_address(String u_address) {
        this.u_address = u_address;
    }

    public String getU_auth() {
        return u_auth;
    }

    public void setU_auth(String u_auth) {
        this.u_auth = u_auth;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_id=" + u_id +
                ", u_name='" + u_name + '\'' +
                ", u_password='" + u_password + '\'' +
                ", u_gender='" + u_gender + '\'' +
                ", u_age=" + u_age +
                ", u_phone='" + u_phone + '\'' +
                ", u_address='" + u_address + '\'' +
                ", u_auth='" + u_auth + '\'' +
                '}';
    }
}
