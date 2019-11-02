package com.njwb.ui.models;

import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.AccountService;

import javax.swing.table.AbstractTableModel;

/**
 * @program: SupermarketBillsManager
 * @description: 商品分组表格模型
 * @author: 张文轩
 * @create: 2019-10-29 16:54
 **/
public class AccountGroupTableModel extends AbstractTableModel {
    //创建service对象
    private AccountService as = (AccountService) ObjectFactory.objectMap.get("AccountService");

    //初始化表格列和行
    String[] colums = {"商品名称", "交易数量", "交易金额", "供应商名称"};
    Object[][] rows = null;

    /**
     * 通过构造方法来实现查询数据，传递到表示层的表格模型中
     */
    public AccountGroupTableModel() throws MyException {
        rows = as.selectByGroup();
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
