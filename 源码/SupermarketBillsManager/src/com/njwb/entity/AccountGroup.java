package com.njwb.entity;

/**
 * @program: SupermarketBillsManager
 * @description: 商品分组查询
 * @author: 张文轩
 * @create: 2019-10-29 16:57
 **/
public class AccountGroup {
    private String name;
    private int nums;
    private int amount;
    private String supName;

    public AccountGroup(String name, int nums, int amount, String supName) {
        this.name = name;
        this.nums = nums;
        this.amount = amount;
        this.supName = supName;
    }

    public AccountGroup() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }
}
