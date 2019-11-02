package com.njwb.ui.controllers;

import com.njwb.entity.Account;
import com.njwb.entity.Supplier;
import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.AccountService;
import com.njwb.service.SupplierService;
import com.njwb.ui.models.AccountTableModel;
import com.njwb.ui.models.FrameModel;
import com.njwb.ui.models.SupplierTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: SupermarketBillsManager
 * @description: 添加或修改商品信息
 * @author: 张文轩
 * @create: 2019-10-29 09:09
 **/
public class AddOrUpdateAccount {
    // 通过对象工厂获取
    private AccountService aService = (AccountService) ObjectFactory.objectMap.get("AccountService");
    private SupplierService sService = (SupplierService) ObjectFactory.objectMap.get("SupplierService");
    // 当前操作
    private String operation;
    // 商品id
    private int id;
    // 待刷新的table
    private JTable jt;
    // 新建的商品
    private Account a = new Account();
    // textfield数组
    private JTextField[] jts = new JTextField[5];
    // 付款情况combox
    private JComboBox<String> paymentCb;
    // 所属供应商combox
    private String[] supplierStrs;
    private JComboBox<String> supplierCb;
    // 当前下标
    private int index = 0;
    // 原供商品名
    String originName;
    // 原付款状态
    int opriginPay;

    public static void main(String[] args) {
        new AddOrUpdateAccount("添加", -1, null,new FrameModel(300,100,500,500));
    }
    /**
     * 构造函数
     * @param operation 添加或修改操作
     * @param id 供应商id，-1为修改
     * @param jt 待刷新的table
     * @param frame 定位
     */
    public AddOrUpdateAccount(String operation, int id, JTable jt, FrameModel frame) {
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
        JFrame jf = new JFrame(operation + "商品");
        JPanel backPanel = new JPanel(new GridLayout(9,1));
        JPanel nameP = textBasePanel("商品名称：", true);
        JPanel numsP = textBasePanel("交易数量：", true);
        JPanel unitP = textBasePanel("交易单位：", true);
        JPanel amountP = textBasePanel("交易金额：", true);
        JPanel paymentP = comboxBasePanel("是否付款：");
        JPanel supplierP = comboxBasePanel("所属供应商：");
        JLabel infonName = new JLabel("商品描述：");
        JPanel textAreaP = new JPanel();
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
        JPanel dateP = textBasePanel("交易时间：", true);
        JPanel btnP = new JPanel();
        JButton addBtn = new JButton(operation);
        JButton closeBtn = new JButton("关闭");
        btnP.add(addBtn);
        btnP.add(closeBtn);
        backPanel.add(nameP);
        backPanel.add(numsP);
        backPanel.add(unitP);
        backPanel.add(amountP);
        backPanel.add(paymentP);
        backPanel.add(supplierP);
        backPanel.add(textAreaP);
        backPanel.add(dateP);
        backPanel.add(btnP);
        jf.add(backPanel);
        jf.setVisible(true);
        jf.setBounds(x, y, width, height);

        if (id != -1) {
            try {
                a = aService.queryByIdAccount(id);
                originName = a.getA_name();
                opriginPay = a.getA_ispayed();
            } catch (MyException e) {
                e.printStackTrace();
            }
            jts[0].setText(a.getA_name());
            jts[1].setText(String.valueOf(a.getA_nums()));
            jts[2].setText(a.getA_unit());
            jts[3].setText(String.valueOf(a.getA_amount()));
            paymentCb.setSelectedIndex(a.getA_ispayed());
            int supplierId = a.getS_id();
            Supplier supplier = null;
            try {
                supplier = sService.queryByIdSupplier(supplierId);
                for (int i = 0;i < supplierStrs.length;i++) {
                    if (supplierStrs[i].equals(supplier.getS_name())) {
                        supplierCb.setSelectedIndex(i);
                        break;
                    }
                }
            } catch (MyException e) {
                e.printStackTrace();
            }
            jta.setText(a.getA_info());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            jts[4].setText(format.format(a.getA_date()));
        }

        // 添加或修改按钮功能实现
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = jts[0].getText();
                int nums = 0;
                if (!jts[1].getText().equals("")) nums = Integer.parseInt(jts[1].getText());
                String unit = jts[2].getText();
                double amount = 0;
                if (!jts[3].getText().equals("")) amount = Double.parseDouble(jts[3].getText());
                int ispayed = 0;
                if (paymentCb.getSelectedIndex() == 0) ispayed = 0;
                else if (paymentCb.getSelectedIndex() == 1) ispayed = 1;
                int supplierId = 0;
                try {
                    supplierId = sService.queryByNameSupplier(supplierCb.getItemAt(supplierCb.getSelectedIndex()));
                } catch (MyException ex) {
                    ex.printStackTrace();
                }
                String info = jta.getText();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    if (!jts[4].getText().equals("")) date = format.parse(jts[4].getText());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                a.setA_name(name);
                a.setA_nums(nums);
                a.setA_unit(unit);
                a.setA_amount(amount);
                a.setA_ispayed(ispayed);
                a.setS_id(supplierId);
                a.setA_info(info);
                a.setA_date(date);
                if (operation.equals("添加")) {
                    try {
                        aService.addAccount(a);
                        JOptionPane.showMessageDialog(jf, "添加成功");
                        jf.dispose();
                        jt.setModel(new AccountTableModel(null, -1));
                    } catch (MyException ex) {
                        JOptionPane.showMessageDialog(jf, ex.getMessage());
                    }
                }else {
                    try {
                        aService.updateByIdAccount(a, originName, opriginPay);
                        jf.dispose();
                        jt.setModel(new AccountTableModel(null, -1));
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

    public JPanel comboxBasePanel(String name) {
        JPanel jp = new JPanel();
        // 红星
        JLabel redStar = new JLabel("*");
        redStar.setForeground(Color.RED);
        // 行名
        JLabel jlName = new JLabel(name);
        JComboBox<String> jcb = null;
        String[] strs = null;
        if (name.equals("是否付款：")) {
            strs = new String[]{"是", "否"};
            jcb = new JComboBox<String>(strs);
            paymentCb = jcb;
        }else if (name.equals("所属供应商：")) {
            try {
                String[][] suppliers = sService.supplierSelector(null);
                strs = new String[suppliers.length];
                for (int i = 0;i < suppliers.length;i++) {
                    strs[i] = suppliers[i][1];
                }
                supplierStrs = strs;
                jcb = new JComboBox<String>(strs);
                supplierCb = jcb;
            } catch (MyException e) {
                e.printStackTrace();
            }
        }
        jp.add(redStar);
        jp.add(jlName);
        jp.add(jcb);
        return jp;
    }
}
