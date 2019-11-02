package com.njwb.ui.models;

import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.SupplierService;

import javax.swing.table.AbstractTableModel;

/**
 * @program: SupermarketBillsManager
 * @description: 供应商表格模型
 * @author: 张文轩
 * @create: 2019-10-28 14:08
 **/
public class SupplierTableModel extends AbstractTableModel {
    //创建service对象
    private SupplierService ss = (SupplierService) ObjectFactory.objectMap.get("SupplierService");

    //初始化表格列和行
    String[] colums = {"编号", "供应商名称", "供应商描述", "联系人", "电话", "地址"};
    Object[][] rows = null;

    /**
     * 通过构造方法来实现查询数据，传递到表示层的表格模型中
     */
    public SupplierTableModel(String name) throws MyException {
        rows = ss.supplierSelector(name);
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
