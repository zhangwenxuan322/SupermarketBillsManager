package com.njwb.ui.views;

import com.njwb.entity.Account;
import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.AccountService;
import com.njwb.ui.controllers.AddOrUpdateAccount;
import com.njwb.ui.models.AccountTableModel;
import com.njwb.ui.models.FrameModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * @program: SupermarketBillsManager
 * @description: 账单管理模块
 * @author: 张文轩
 * @create: 2019-10-26 16:25
 **/
public class BillManagerPanel extends JPanel {
    // 通过工厂构建对象
    private AccountService aService = (AccountService) ObjectFactory.objectMap.get("AccountService");
    public JTable jt;
    /**
     * 构造函数
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public BillManagerPanel(int x, int y, int width, int height) {
        this.setBounds(x, y, width,height);
        initBillUI();
    }

    /**
     * 加载账单管理UI
     */
    public void initBillUI() {
        this.setLayout(null);
        // 组合查询panel
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
        // 商品名称
        JLabel goodsName = new JLabel("商品名称：", JLabel.CENTER);
        JTextField goodsTf = new JTextField(20);
        goodsTf.setSize((int) (this.getWidth()*(1.0/5.0)), (int) (mutilSelectPanel.getHeight()*(1.0/2.0)));
        contentJp.add(goodsName);
        contentJp.add(goodsTf);
        // 是否付款
        JLabel ispayedLb = new JLabel("是否付款：", JLabel.CENTER);
        String[] payedCondition = {"请选择", "是", "否"};
        JComboBox<String> payCb = new JComboBox<String>(payedCondition);
        contentJp.add(ispayedLb);
        contentJp.add(payCb);
        JPanel spacer3 = new JPanel();
        spacer3.setOpaque(false);
        contentJp.add(spacer3);
        // 组合查询按钮
        JButton mutilBtn = new JButton("组合查询");
        contentJp.add(mutilBtn);
        JPanel spacer4 = new JPanel();
        spacer4.setOpaque(false);
        contentJp.add(spacer4);

        // 账单操作panel
        JPanel operationP = new JPanel(new GridLayout(1, 5));
        operationP.setBounds(0, mutilSelectPanel.getHeight(), this.getWidth(), (int) (mutilSelectPanel.getHeight()*(3.0/5.0)));
        this.add(operationP);
        // 账单管理
        JLabel operationJl = new JLabel("账单管理>>");
        Font fnt=new Font("粗体",Font.BOLD,25);
        operationJl.setFont(fnt);
        operationP.add(operationJl);
        // 导出按钮
        JButton exportJb = new JButton("导出");
        exportJb.setBackground(Color.YELLOW);
        operationP.add(exportJb);
        // 添加账单按钮
        JButton insertBtn = new JButton("添加账单");
        insertBtn.setBackground(Color.PINK);
        operationP.add(insertBtn);
        // 修改账单按钮
        JButton updateBtn = new JButton("修改账单");
        updateBtn.setBackground(Color.PINK);
        operationP.add(updateBtn);
        // 删除账单按钮
        JButton deleteBtn = new JButton("删除账单");
        deleteBtn.setBackground(Color.PINK);
        operationP.add(deleteBtn);

        //表格
        //创建空的表格
        jt = new JTable();
        JScrollPane jsp = new JScrollPane(jt);
        try {
            jt.setModel(new AccountTableModel(null, -1));
        } catch (MyException ex) {
            JOptionPane.showMessageDialog(jt, ex.getMessage());
        }
        jsp.setBounds(0, (operationP.getY()+operationP.getHeight()), this.getWidth(), (int) (this.getHeight()*0.5));
        this.add(jsp);

        // 组合查询按钮功能实现
        mutilBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jt.setModel(new AccountTableModel(goodsTf.getText(), payCb.getSelectedIndex()-1));
                } catch (MyException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 导出按钮功能实现
        exportJb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.showSaveDialog(mutilSelectPanel);
                File file = jfc.getSelectedFile();
                BufferedWriter bw = null;
                try {
                    file.createNewFile();
                    String title = "账单编号|"+"商品名称|"+"商品数量|"+"交易金额|"+"是否付款|"+"供应商名称|"+"商品描述|"+"账单时间\n";
                    bw = new BufferedWriter(new FileWriter(file));
                    if(file != null) {
                        int[] rows = jt.getSelectedRows();
                        if(rows.length == 0) {
                            String[][] accounts = aService.accountSelector(null, -1);
                            bw.write(title);
                            bw.flush();
                            for (int i = 0;i < accounts.length;i++) {
                                bw.write(accounts[i][0]+"|"
                                        +accounts[i][1]+"|"
                                        +accounts[i][2]+"|"
                                        +accounts[i][3]+"|"
                                        +accounts[i][4]+"|"
                                        +accounts[i][5]+"|"
                                        +accounts[i][6]+"|"
                                        +accounts[i][7]+"\n");
                                bw.flush();
                            }
                        }else {
                            bw.write(title);
                            bw.flush();
                            for (int i : rows) {
                                String id = String.valueOf(jt.getValueAt(i, 0));
                                String name = String.valueOf(jt.getValueAt(i, 1));
                                String nums = String.valueOf(jt.getValueAt(i, 2));
                                String amount = String.valueOf(jt.getValueAt(i, 3));
                                String ispayed = String.valueOf(jt.getValueAt(i, 4));
                                String s_name = String.valueOf(jt.getValueAt(i, 5));
                                String s_info = String.valueOf(jt.getValueAt(i, 6));
                                String date = String.valueOf(jt.getValueAt(i, 7));
                                bw.write(id+"|"
                                        +name+"|"
                                        +nums+"|"
                                        +amount+"|"
                                        +ispayed+"|"
                                        +s_name+"|"
                                        +s_info+"|"
                                        +date+"\n");
                                bw.flush();
                            }
                        }
                        JOptionPane.showMessageDialog(contentJp, "导出成功！");
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                } catch (MyException e1) {
                    e1.printStackTrace();
                }finally {
                    try {
                        bw.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        // 弹出框定位
        FrameModel frame = new FrameModel(500, 100, 500, 700);
        // 添加按钮功能实现
        insertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddOrUpdateAccount("添加", -1, jt, frame);
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

                Account account = null;
                try {
                    account = aService.queryByIdAccount(id);
                    //调用service层将选中的信息带入到修改界面
                    new AddOrUpdateAccount("修改", id, jt, frame);
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
                    if (JOptionPane.showConfirmDialog(null, "是否确定要删除所选中行？", "删除", JOptionPane.YES_NO_OPTION) == 0) {}
                    else return;
                }
                int[] rows = jt.getSelectedRows();
                int[] ids = new int[rows.length];
                for(int i=0;i<rows.length;i++){
                    int rowId = Integer.parseInt((String) jt.getValueAt(rows[i], 0));
                    ids[i] = rowId;
                }
                try {
                    aService.deleteByIdAccount(ids);
                    // 删除完成之后重新加载表格
                    jt.setModel(new AccountTableModel(null, -1));
                    JOptionPane.showMessageDialog(jt, "删除成功！");
                } catch (MyException ex) {
                    JOptionPane.showMessageDialog(jt, ex.getMessage());
                }
            }
        });
    }
}
