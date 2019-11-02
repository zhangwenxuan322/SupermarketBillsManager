package com.njwb.ui.views;

import com.njwb.entity.User;
import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.UserService;
import com.njwb.ui.controllers.AddOrUpdateUser;
import com.njwb.ui.models.FrameModel;
import com.njwb.ui.models.UserTableModel;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @program: SupermarketBillsManager
 * @description: 用户管理模块
 * @author: 张文轩
 * @create: 2019-10-26 19:55
 **/
public class UserManagerPanel extends JPanel {
    // 通过工厂构建对象
    private UserService uService = (UserService) ObjectFactory.objectMap.get("UserService");
    public JTable jt;
    private JLabel nameLb;
    /**
     * 构造函数
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public UserManagerPanel(JFrame mainJf, JLabel nameLb, User user, int x, int y, int width, int height) {
        this.setBounds(x, y, width,height);
        this.nameLb = nameLb;
        initUserUI(user, mainJf);
    }

    /**
     * 加载账单管理UI
     */
    public void initUserUI(User user, JFrame mainJf) {
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
        // 用户名称
        JLabel userName = new JLabel("用户名称：", JLabel.CENTER);
        JTextField userTf = new JTextField(20);
        userTf.setSize((int) (this.getWidth()*(1.0/5.0)), (int) (mutilSelectPanel.getHeight()*(1.0/2.0)));
        contentJp.add(userName);
        contentJp.add(userTf);
        JPanel spacer3 = new JPanel();
        spacer3.setOpaque(false);
        contentJp.add(spacer3);
        // 查询按钮
        JButton selectBtn = new JButton("查询");
        contentJp.add(selectBtn);
        JPanel spacer4 = new JPanel();
        spacer4.setOpaque(false);
        contentJp.add(spacer4);

        // 用户操作panel
        JPanel operationP = new JPanel(new GridLayout(1, 5));
        operationP.setBounds(0, mutilSelectPanel.getHeight(), this.getWidth(), (int) (mutilSelectPanel.getHeight()*(3.0/5.0)));
        this.add(operationP);
        // 用户管理
        JLabel operationJl = new JLabel("用户管理>>");
        Font fnt=new Font("粗体",Font.BOLD,25);
        operationJl.setFont(fnt);
        operationP.add(operationJl);
        // 空白占位
        JPanel spacer = new JPanel();
        operationP.add(spacer);
        // 添加用户按钮
        JButton insertBtn = new JButton("添加用户");
        insertBtn.setBackground(Color.PINK);
        operationP.add(insertBtn);
        // 修改用户按钮
        JButton updateBtn = new JButton("修改用户");
        updateBtn.setBackground(Color.PINK);
        operationP.add(updateBtn);
        // 删除用户按钮
        JButton deleteBtn = new JButton("删除用户");
        deleteBtn.setBackground(Color.PINK);
        operationP.add(deleteBtn);

        // 表格
        // 初始化默认全部查询
        jt = new JTable();
        JScrollPane jsp = new JScrollPane(jt);
        try {
            jt.setModel(new UserTableModel(null));
        } catch (MyException e) {
            e.printStackTrace();
        }
        jsp.setBounds(0, (operationP.getY()+operationP.getHeight()), this.getWidth(), (int) (this.getHeight()*0.5));
        this.add(jsp);

        // 查询按钮功能实现
        selectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jt.setModel(new UserTableModel(userTf.getText()));
                } catch (MyException ex) {
                    JOptionPane.showMessageDialog(jt, ex.getMessage());
                }
            }
        });
        // 弹出框定位
        FrameModel frame = new FrameModel(500, 100, 500, 700);
        // 添加用户按钮功能实现
        insertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddOrUpdateUser(mainJf, nameLb, "添加", -1, jt, user, frame);
            }
        });

        // 修改用户按钮功能实现
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
                //调用service层将选中的信息带入到修改界面
                new AddOrUpdateUser(mainJf, nameLb, "修改", id, jt, user, frame);
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
                    uService.deleteByIdUser(ids, user);
                    // 删除完成之后重新加载表格
                    jt.setModel(new UserTableModel(null));
                    JOptionPane.showMessageDialog(jt, "删除成功！");
                } catch (MyException ex) {
                    JOptionPane.showMessageDialog(jt, ex.getMessage());
                }
            }
        });
    }
}
