package com.njwb.ui.views;

import com.njwb.entity.Supplier;
import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.SupplierService;
import com.njwb.ui.controllers.AddOrUpdateSupplier;
import com.njwb.ui.models.FrameModel;
import com.njwb.ui.models.SupplierTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * @program: SupermarketBillsManager
 * @description: 供应商管理模块
 * @author: 张文轩
 * @create: 2019-10-26 19:36
 **/
public class SupplierManagerPanel extends JPanel {
    // 通过工厂构建对象
    private SupplierService sService = (SupplierService) ObjectFactory.objectMap.get("SupplierService");
    public JTable jt;
    /**
     * 构造函数
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public SupplierManagerPanel(int x, int y, int width, int height) {
        this.setBounds(x, y, width,height);
        initSupplierUI();
    }

    /**
     * 加载供应商管理UI
     */
    public void initSupplierUI() {
        this.setLayout(null);
        // 查询panel
        JPanel mutilSelectPanel = new JPanel();
        mutilSelectPanel.setLayout(new GridLayout(3, 1));
        mutilSelectPanel.setBounds(0, 0, this.getWidth(), (int) (this.getHeight()*(1.0/7.0)));
        mutilSelectPanel.setBackground(Color.CYAN);
        this.add(mutilSelectPanel);
        JPanel spacer1 = new JPanel();
        JPanel contentJp = new JPanel(new GridLayout(1,7));
        JPanel spacer2 = new JPanel();
        mutilSelectPanel.add(spacer1);
        mutilSelectPanel.add(contentJp);
        mutilSelectPanel.add(spacer2);
        spacer1.setOpaque(false);
        contentJp.setOpaque(false);
        spacer2.setOpaque(false);
        // 供应商名称
        JLabel goodsName = new JLabel("供应商名称：", JLabel.CENTER);
        JTextField goodsTf = new JTextField(20);
        goodsTf.setSize((int) (this.getWidth()*(1.0/5.0)), (int) (mutilSelectPanel.getHeight()*(1.0/2.0)));
        contentJp.add(goodsName);
        contentJp.add(goodsTf);
        JPanel spacer3 = new JPanel();
        spacer3.setOpaque(false);
        contentJp.add(spacer3);
        // 查询按钮
        JButton mutilBtn = new JButton("查询");
        contentJp.add(mutilBtn);
        JPanel spacer4 = new JPanel();
        spacer4.setOpaque(false);
        contentJp.add(spacer4);

        // 供应商操作panel
        JPanel operationP = new JPanel(new GridLayout(1, 5));
        operationP.setBounds(0, mutilSelectPanel.getHeight(), this.getWidth(), (int) (mutilSelectPanel.getHeight()*(3.0/5.0)));
        this.add(operationP);
        // 供应商管理
        JLabel operationJl = new JLabel("供应商管理>>");
        Font fnt=new Font("粗体",Font.BOLD,20);
        operationJl.setFont(fnt);
        operationP.add(operationJl);
        // 导入按钮
        JButton importJb = new JButton("导入");
        importJb.setBackground(Color.YELLOW);
        operationP.add(importJb);
        // 添加供应商按钮
        JButton insertBtn = new JButton("添加供应商");
        insertBtn.setBackground(Color.PINK);
        operationP.add(insertBtn);
        // 修改供应商按钮
        JButton updateBtn = new JButton("修改供应商");
        updateBtn.setBackground(Color.PINK);
        operationP.add(updateBtn);
        // 删除供应商按钮
        JButton deleteBtn = new JButton("删除供应商");
        deleteBtn.setBackground(Color.PINK);
        operationP.add(deleteBtn);

        //表格
        //创建空的表格
        jt = new JTable();
        JScrollPane jsp = new JScrollPane(jt);
        try {
            jt.setModel(new SupplierTableModel(null));
        } catch (MyException ex) {
            JOptionPane.showMessageDialog(jt, ex.getMessage());
        }
        jsp.setBounds(0, (operationP.getY()+operationP.getHeight()), this.getWidth(), (int) (this.getHeight()*0.5));
        this.add(jsp);

        // 查询按钮功能实现
        mutilBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jt.setModel(new SupplierTableModel(goodsTf.getText()));
                } catch (MyException ex) {
                    JOptionPane.showMessageDialog(jt, ex.getMessage());
                }
            }
        });

        // 导入按钮功能实现
        importJb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("导入");
                if (JFileChooser.APPROVE_OPTION == jfc.showOpenDialog(mutilSelectPanel)) {
                    File f = jfc.getSelectedFile();
                    BufferedReader bf = null;
                    try {
                        bf = new BufferedReader(new FileReader(f));
                        String line = null;
                        while ((line = bf.readLine()) != null) {
                            String[] supplier = line.split("\\|");
                            Supplier s = new Supplier();
                            s.setS_name(supplier[0]);
                            s.setS_info(supplier[1]);
                            s.setS_linkman(supplier[2]);
                            s.setS_phone(supplier[3]);
                            s.setS_faxes(supplier[4]);
                            s.setS_address(supplier[5]);
                            sService.importOperation(s);
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    jt.setModel(new SupplierTableModel(null));
                } catch (MyException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 弹出框定位
        FrameModel frame = new FrameModel(500, 100, 500, 700);
        // 添加供应商按钮功能实现
        insertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddOrUpdateSupplier("添加", -1, jt, frame);
            }
        });

        // 修改按钮功能实现
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jt.getSelectedRows().length<=0){
                    JOptionPane.showMessageDialog(jt, "必须选择一行进行修改！");
                    return ;
                }

                if(jt.getSelectedRows().length>1){
                    JOptionPane.showMessageDialog(jt, "不能多行修改！");
                    return ;
                }

                int row = jt.getSelectedRow();
                int id = Integer.parseInt((String) jt.getValueAt(row, 0));

                Supplier s = null;
                try {
                    s = sService.queryByIdSupplier(id);
                    //调用service层将选中的信息带入到修改界面
                    new AddOrUpdateSupplier("修改", id, jt, frame);
                } catch (MyException ex) {
                    JOptionPane.showMessageDialog(jt, ex.getMessage());
                }
            }
        });

        // 删除按钮功能实现
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 判断是否选中
                if(jt.getSelectedRows().length<=0){
                    JOptionPane.showMessageDialog(jt, "至少选择一行进行删除");
                    return ;
                }
                /**
                 * 删除多行
                 */
                // 删除提醒
                if (jt.getSelectedRows().length >= 1) {
                    if (JOptionPane.showConfirmDialog(null, "删除选中供应商会对现有商品造成影响，是否继续？", "删除", JOptionPane.YES_NO_OPTION) == 0) {}
                    else return;
                }
                int[] rows = jt.getSelectedRows();
                int[] ids = new int[rows.length];
                for(int i=0;i<rows.length;i++){
                    int rowId = Integer.parseInt((String) jt.getValueAt(rows[i], 0));
                    ids[i] = rowId;
                }
                try {
                    sService.deleteByIdSupplieer(ids);
                    // 删除完成之后重新加载表格
                    jt.setModel(new SupplierTableModel(null));
                    JOptionPane.showMessageDialog(jt, "删除成功！");
                } catch (MyException ex) {
                    JOptionPane.showMessageDialog(jt, ex.getMessage());
                }
            }
        });
    }
}
