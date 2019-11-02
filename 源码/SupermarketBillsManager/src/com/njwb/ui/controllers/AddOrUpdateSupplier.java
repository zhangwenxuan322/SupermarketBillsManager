package com.njwb.ui.controllers;

import com.njwb.entity.Supplier;
import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.SupplierService;
import com.njwb.ui.models.FrameModel;
import com.njwb.ui.models.SupplierTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @program: SupermarketBillsManager
 * @description: 添加或修改供应商信息
 * @author: 张文轩
 * @create: 2019-10-28 14:15
 **/
public class AddOrUpdateSupplier {
    // 通过对象工厂获取
    private SupplierService sService = (SupplierService) ObjectFactory.objectMap.get("SupplierService");
    // 当前操作
    private String operation;
    // 供应商id
    private int id;
    // 待刷新的table
    private JTable jt;
    // 新建的供应商
    private Supplier s = new Supplier();
    // textfield数组
    private JTextField[] jts = new JTextField[5];
    // 当前下标
    private int index = 0;
    // 原供应商名
    String originName;
    /**
     * 构造函数
     * @param operation 添加或修改操作
     * @param id 供应商id，-1为修改
     * @param jt 待刷新的table
     * @param frame 定位
     */
    public AddOrUpdateSupplier(String operation, int id, JTable jt, FrameModel frame) {
        this.operation = operation;
        this.jt = jt;
        this.id = id;
        initAddUserUI(frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight());
    }

    /**
     * 构建UI
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void initAddUserUI(int x, int y, int width, int height) {
        JFrame jf = new JFrame(operation + "供应商");
        JPanel backPanel = new JPanel(new GridLayout(7,1));
        JPanel nameP = textBasePanel("供应商名称：", true);
        JPanel textAreaP = new JPanel();
        JLabel infonName = new JLabel("供应商描述：");
        // 多行输入框
        JTextArea jta = new JTextArea(3, 20);
        JScrollPane scroll = new JScrollPane(jta);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jta.setLineWrap(true);
        textAreaP.add(infonName);
        textAreaP.add(jta);
        JPanel linkmanP = textBasePanel("供应商联系人：", true);
        JPanel phoneP = textBasePanel("供应商电话：", true);
        JPanel faxesP = textBasePanel("供应商传真：", false);
        JPanel addressP = textBasePanel("供应商地址：", true);
        JPanel btnP = new JPanel();
        JButton addBtn = new JButton(operation);
        JButton closeBtn = new JButton("关闭");
        btnP.add(addBtn);
        btnP.add(closeBtn);
        backPanel.add(nameP);
        backPanel.add(textAreaP);
        backPanel.add(linkmanP);
        backPanel.add(phoneP);
        backPanel.add(faxesP);
        backPanel.add(addressP);
        backPanel.add(btnP);
        jf.add(backPanel);
        jf.setVisible(true);
        jf.setBounds(x, y, width, height);

        if (id != -1) {
            try {
                s = sService.queryByIdSupplier(id);
                originName = s.getS_name();
            } catch (MyException e) {
                e.printStackTrace();
            }
            jts[0].setText(s.getS_name());
            jta.setText(s.getS_info());
            jts[1].setText(s.getS_linkman());
            jts[2].setText(s.getS_phone());
            jts[3].setText(s.getS_faxes());
            jts[4].setText(s.getS_address());
        }

        // 添加或修改按钮功能实现
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s.setS_name(jts[0].getText());
                s.setS_info(jta.getText());
                s.setS_linkman(jts[1].getText());
                s.setS_phone(jts[2].getText());
                s.setS_faxes(jts[3].getText());
                s.setS_address(jts[4].getText());
                if (operation.equals("添加")) {
                    try {
                        sService.addSupplier(s);
                        JOptionPane.showMessageDialog(jf, "添加成功");
                        jf.dispose();
                        jt.setModel(new SupplierTableModel(null));
                    } catch (MyException ex) {
                        JOptionPane.showMessageDialog(jf, ex.getMessage());
                    }
                }else {
                    try {
                        sService.updateByIdSupplier(s, originName);
                        jf.dispose();
                        jt.setModel(new SupplierTableModel(null));
                    } catch (MyException ex) {
                        JOptionPane.showMessageDialog(jf, ex.getMessage());
                    }
                }
            }
        });

        // 关闭按钮功能实现
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
    }

    /**
     * 构建输入框panel
     * @param name
     * @param hasRedstar
     * @return
     */
    public JPanel textBasePanel(String name, Boolean hasRedstar) {
        JPanel jp = new JPanel();
        // 红星
        JLabel redStar = new JLabel("*");
        redStar.setForeground(Color.RED);
        // 行名
        JLabel jlName = new JLabel(name);
        // 输入框
        JTextField jt = new JTextField(20);
        jts[index] = jt;
        index++;
        if (hasRedstar) {
            jp.add(redStar);
        }
        jp.add(jlName);
        jp.add(jt);
        return jp;
    }
}
