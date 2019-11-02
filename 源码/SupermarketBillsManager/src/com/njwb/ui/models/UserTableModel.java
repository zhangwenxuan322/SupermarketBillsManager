package com.njwb.ui.models;

import com.njwb.entity.User;
import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.UserService;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @program: SupermarketBillsManager
 * @description: 用户表格模型
 * @author: 张文轩
 * @create: 2019-10-27 15:32
 **/
public class UserTableModel extends AbstractTableModel {
    //创建service对象
    private UserService us = (UserService) ObjectFactory.objectMap.get("UserService");

    //初始化表格列和行
    String[] colums = {"编号", "姓名", "性别", "年龄", "电话", "地址", "权限"};
    Object[][] rows = null;

    /**
     * 通过构造方法来实现查询数据，传递到表示层的表格模型中
     */
    public UserTableModel(String name) throws MyException {
        rows = us.userSelector(name);
    }
    @Override
    public String getColumnName(int column) {
        return colums[column];
    }

    @Override
    public int getRowCount() {
        return rows.length;
    }

    @Override
    public int getColumnCount() {
        return colums.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rows[rowIndex][columnIndex];
    }
}
