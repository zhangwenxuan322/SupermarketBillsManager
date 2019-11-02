package com.njwb.entity;

import java.util.Date;

/**
 * @program: SupermarketBillsManager
 * @description: 账单表
 * @author: 张文轩
 * @create: 2019-10-25 11:42
 **/
public class Account {
    // 账单ID
    private int a_id;
    // 账单名称
    private String a_name;
    // 账单数量
    private int a_nums;
    // 交易金额
    private double a_amount;
    // 交易单位
    private String a_unit;
    // 是否付款
    private int a_ispayed;
    // 供应商ID
    private int s_id;
    // 商品描述(选填)
    private String a_info;
    // 交易时间
    private Date a_date;

    public Account() {
    }

    public Account(int a_id, String a_name, int a_nums, double a_amount, String a_unit, int a_ispayed, int s_id, String a_info, Date a_date) {
        this.a_id = a_id;
        this.a_name = a_name;
        this.a_nums = a_nums;
        this.a_amount = a_amount;
        this.a_unit = a_unit;
        this.a_ispayed = a_ispayed;
        this.s_id = s_id;
        this.a_info = a_info;
        this.a_date = a_date;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public int getA_nums() {
        return a_nums;
    }

    public void setA_nums(int a_nums) {
        this.a_nums = a_nums;
    }

    public double getA_amount() {
        return a_amount;
    }

    public void setA_amount(double a_amount) {
        this.a_amount = a_amount;
    }

    public String getA_unit() {
        return a_unit;
    }

    public void setA_unit(String a_unit) {
        this.a_unit = a_unit;
    }

    public int getA_ispayed() {
        return a_ispayed;
    }

    public void setA_ispayed(int a_ispayed) {
        this.a_ispayed = a_ispayed;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getA_info() {
        return a_info;
    }

    public void setA_info(String a_info) {
        this.a_info = a_info;
    }

    public Date getA_date() {
        return a_date;
    }

    public void setA_date(Date a_date) {
        this.a_date = a_date;
    }

    @Override
    public String toString() {
        return "Account{" +
                "a_id=" + a_id +
                ", a_name='" + a_name + '\'' +
                ", a_nums=" + a_nums +
                ", a_amount=" + a_amount +
                ", a_unit='" + a_unit + '\'' +
                ", a_ispayed=" + a_ispayed +
                ", s_id=" + s_id +
                ", a_info='" + a_info + '\'' +
                ", a_date=" + a_date +
                '}';
    }
}
