package com.njwb.ui.models;

import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.SupplierService;

import javax.swing.table.AbstractTableModel;

/**
 * @program: SupermarketBillsManager
 * @description: 供应商分组查询表格模板
 * @author: 张文轩
 * @create: 2019-10-29 16:17
 **/
public class SupplierGroupTableModel extends AbstractTableModel {
    //创建service对象
    private SupplierService ss = (SupplierService) ObjectFactory.objectMap.get("SupplierService");

    //初始化表格列和行
    String[] colums = {"供应商编号", "供应商名称", "总交易金额", "商品种类", "总商品数量"};
    Object[][] rows = null;

    /**
     * 通过构造方法来实现查询数据，传递到表示层的表格模型中
     */
    public SupplierGroupTableModel() throws MyException {
        rows = ss.selectByGroup();
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
