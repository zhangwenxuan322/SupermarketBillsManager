package com.njwb.entity;

/**
 * @program: SupermarketBillsManager
 * @description: 供应商表
 * @author: 张文轩
 * @create: 2019-10-25 11:41
 **/
public class Supplier {
    // 供应商ID
    private int s_id;
    // 供应商名称
    private String s_name;
    // 供应商描述(选填)
    private String s_info;
    // 供应商联系人
    private String s_linkman;
    // 供应商电话
    private String s_phone;
    // 供应商地址
    private String s_address;
    // 供应商传真(选填)
    private String s_faxes;

    public Supplier() {
    }

    public Supplier(int s_id, String s_name, String s_info, String s_linkman, String s_phone, String s_address, String s_faxes) {
        this.s_id = s_id;
        this.s_name = s_name;
        this.s_info = s_info;
        this.s_linkman = s_linkman;
        this.s_phone = s_phone;
        this.s_address = s_address;
        this.s_faxes = s_faxes;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_info() {
        return s_info;
    }

    public void setS_info(String s_info) {
        this.s_info = s_info;
    }

    public String getS_linkman() {
        return s_linkman;
    }

    public void setS_linkman(String s_linkman) {
        this.s_linkman = s_linkman;
    }

    public String getS_phone() {
        return s_phone;
    }

    public void setS_phone(String s_phone) {
        this.s_phone = s_phone;
    }

    public String getS_address() {
        return s_address;
    }

    public void setS_address(String s_address) {
        this.s_address = s_address;
    }

    public String getS_faxes() {
        return s_faxes;
    }

    public void setS_faxes(String s_faxes) {
        this.s_faxes = s_faxes;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "s_id=" + s_id +
                ", s_name='" + s_name + '\'' +
                ", s_info='" + s_info + '\'' +
                ", s_linkman='" + s_linkman + '\'' +
                ", s_phone='" + s_phone + '\'' +
                ", s_address='" + s_address + '\'' +
                ", s_faxes='" + s_faxes + '\'' +
                '}';
    }
}
