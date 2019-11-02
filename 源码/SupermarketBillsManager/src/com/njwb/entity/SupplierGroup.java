package com.njwb.entity;

/**
 * @program: SupermarketBillsManager
 * @description: 供应商分组查询
 * @author: 张文轩
 * @create: 2019-10-29 16:12
 **/
public class SupplierGroup {
    private int id;
    private String name;
    private int amount;
    private int kind;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "SupplierGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", kind=" + kind +
                ", count=" + count +
                '}';
    }
}
