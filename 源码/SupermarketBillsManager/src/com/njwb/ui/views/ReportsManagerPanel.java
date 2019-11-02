package com.njwb.ui.views;

import com.njwb.myexception.MyException;
import com.njwb.ui.models.AccountGroupTableModel;
import com.njwb.ui.models.SupplierGroupTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @program: SupermarketBillsManager
 * @description: 报表管理模块
 * @author: 张文轩
 * @create: 2019-10-26 20:01
 **/
public class ReportsManagerPanel extends JPanel {
    /**
     * 构造函数
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public ReportsManagerPanel(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        initReportUI();
    }

    /**
     * 加载报表管理UI
     */
    public void initReportUI() {
        this.setLayout(null);
        // 分组查询
        JPanel groupP = new JPanel(new GridLayout(3, 1));
        groupP.setBounds(0, 0, this.getWidth(), (int) (this.getHeight()*0.3));
        JPanel selectP = new JPanel();
        // 单选
        ButtonGroup bg = new ButtonGroup();
        JRadioButton goods = new JRadioButton("商品分组查询");
        JRadioButton supplier = new JRadioButton("供应商分组查询", true);
        goods.setHorizontalAlignment(JRadioButton.CENTER);
        supplier.setHorizontalAlignment(JRadioButton.CENTER);
        bg.add(goods);
        bg.add(supplier);
        selectP.add(goods);
        selectP.add(supplier);
        JPanel spacer1 = new JPanel();
        JPanel spacer2 = new JPanel();
        groupP.add(spacer1);
        groupP.add(selectP);
        groupP.add(spacer2);
        this.add(groupP);

        //表格
        //创建空的表格
        JTable jt = new JTable();
        try {
            jt.setModel(new SupplierGroupTableModel());
        } catch (MyException e) {
            e.printStackTrace();
        }
        JScrollPane jsp = new JScrollPane(jt);
        jsp.setBounds(0, (groupP.getY()+groupP.getHeight()), groupP.getWidth(), (int) (this.getHeight()*0.5));
        this.add(jsp);

        // 商品分组查询按钮功能实现
        goods.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jt.setModel(new AccountGroupTableModel());
                } catch (MyException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 供应商分组查询按钮功能实现
        supplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jt.setModel(new SupplierGroupTableModel());
                } catch (MyException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
