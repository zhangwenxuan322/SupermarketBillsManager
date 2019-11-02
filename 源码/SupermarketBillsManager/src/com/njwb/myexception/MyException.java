package com.njwb.myexception;

/**
 * @program: SupermarketBillsManager
 * @description: 自定义异常类
 * @author: 张文轩
 * @create: 2019-10-25 15:11
 **/
public class MyException extends Exception{
    public MyException() {
    }

    public MyException(String e) {
        super(e);
    }
}
