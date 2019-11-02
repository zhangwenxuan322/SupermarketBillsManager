package com.njwb.ui.controllers;

import com.njwb.entity.User;
import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.UserService;
import com.njwb.ui.models.FrameModel;
import com.njwb.ui.models.UserTableModel;
import com.njwb.ui.views.UserManagerPanel;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @program: SupermarketBillsManager
 * @description: 添加用户界面
 * @author: 张文轩
 * @create: 2019-10-27 17:08
 **/
public class AddOrUpdateUser {
    // 通过对象工厂获取
    private UserService uService = (UserService) ObjectFactory.objectMap.get("UserService");
    // 此处的user不能从工厂获取，工厂内的user是登录用户
    private User u = new User();
    // 当前操作的用户
    private User currentUser = new User();
    // 文本框数组
    private JTextField[] jts = new JTextField[5];
    // 当前文本框下标
    private int index = 0;
    // 性别下拉框
    private JComboBox<String> sexCb;
    // 多行文本框
    private JTextArea addressTa;
    // 权限下拉框
    private JComboBox<String> authCb;
    // 当前用户权限
    private String currentUserAuth;
    // 当前操作
    private String operation;
    // 要操作的用户id
    private int id;
    // 待刷新的table
    private JTable jt;
    // 主界面用户名label
    JLabel nameLb;
    // 原用户名
    String originName;

    /**
     * 构造函数
     * @param operation 当前操作名称
     * @param id 要操作的用户id，如果是添加不需要操作用户则传-1
     * @param jt 待刷新的table
     * @param user 用户信息
     * @param frame 窗口定位
     */
    public AddOrUpdateUser(JFrame mainJf, JLabel nameLb, String operation, int id, JTable jt, User user, FrameModel frame) {
        this.operation = operation;
        this.jt = jt;
        this.id = id;
        currentUser = user;
        currentUserAuth = user.getU_auth();
        this.nameLb = nameLb;
        if (id != -1) {
            try {
                u = uService.queryByIdUser(id, currentUser, 0);
                originName = u.getU_name();
            } catch (MyException e) {
                e.printStackTrace();
            }
        }
        initAddUserUI(mainJf, frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight());
    }

    /**
     * 加载UI
     */
    public void initAddUserUI(JFrame mainJf, int x, int y, int width, int height) {
        JFrame jf = new JFrame(operation + "用户");
        JPanel jp = new JPanel(new GridLayout(9, 1));
        JPanel userNameP = textBasePanel("用户名称：");
        JPanel userPwdP = pwdBasePanel("用户密码：");
        JPanel userConfirmP = pwdBasePanel("确认密码：");
        JPanel userSexP = comboxBasePanel("用户性别：");
        JPanel userAgeP = textBasePanel("用户年龄：");
        JPanel userPhoneP = textBasePanel("用户电话：");
        JPanel userAddressP = textAreaBasePanel("用户地址：");
        JPanel userAuthP = comboxBasePanel("用户权限：");
        JPanel btnsP = new JPanel();
        JButton addBtn = new JButton(operation);
        JButton closeBtn = new JButton("关闭");
        btnsP.add(addBtn);
        btnsP.add(closeBtn);

        // 如果是修改操作则须加载待修改用户信息
        if (id != -1) {
            jts[0].setText(u.getU_name());
            jts[1].setText(u.getU_password());
            jts[2].setText(u.getU_password());
            if (u.getU_gender().equals("男")) sexCb.setSelectedIndex(0);
            else if (u.getU_gender().equals("女")) sexCb.setSelectedIndex(1);
            jts[3].setText(String.valueOf(u.getU_age()));
            jts[4].setText(u.getU_phone());
            addressTa.setText(u.getU_address());
            // 权限限制
            if (currentUserAuth.equals("超级管理员")) {
                if (u.getU_auth().equals("部门经理")) authCb.setSelectedIndex(0);
                else if (u.getU_auth().equals("职员")) authCb.setSelectedIndex(1);
            }else if (currentUserAuth.equals("部门经理")) authCb.setSelectedIndex(0);
        }

        // 添加或修改按钮功能实现
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String formerName = null;
                String formerPwd = null;
                if (id != -1) {
                    if (!u.getU_name().equals("")) formerName = u.getU_name();
                    if (!u.getU_password().equals("")) formerPwd = u.getU_password();
                }
                u.setU_name(jts[0].getText());
                u.setU_password(jts[1].getText());
                u.setU_gender(sexCb.getItemAt(sexCb.getSelectedIndex()));
                if (!jts[3].getText().equals("")) u.setU_age(Integer.parseInt(jts[3].getText()));
                u.setU_phone(jts[4].getText());
                u.setU_address(addressTa.getText());
                u.setU_auth(authCb.getItemAt(authCb.getSelectedIndex()));
                if (operation.equals("添加")) {
                    try {
                        uService.addUser(u, jts[2].getText());
                        JOptionPane.showMessageDialog(jf, "添加成功");
                        jf.dispose();
                        jt.setModel(new UserTableModel(null));
                    } catch (MyException ex) {
                        JOptionPane.showMessageDialog(jf, ex.getMessage());
                    }
                }else {
                    try {
                        uService.updateByIdUser(u, jts[2].getText(), originName);
                        if (formerName.equals(currentUser.getU_name())) {
                            nameLb.setText("                              " + u.getU_name());
                            currentUser.setU_name(u.getU_name());
                            currentUser.setU_password(u.getU_password());
                            if ((!formerName.equals(currentUser.getU_name())) || (!formerPwd.equals(currentUser.getU_password()))) {
                                JOptionPane.showMessageDialog(jf, "当前用户姓名或密码发生了变更，请重新登录！");
                                mainJf.dispose();
                                new Login().initLoginUI();
                            }
                        }
                        jf.dispose();
                        jt.setModel(new UserTableModel(null));
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

        jp.add(userNameP);
        jp.add(userPwdP);
        jp.add(userConfirmP);
        jp.add(userSexP);
        jp.add(userAgeP);
        jp.add(userPhoneP);
        jp.add(userAddressP);
        jp.add(userAuthP);
        jp.add(btnsP);
        jf.add(jp);
        jf.setVisible(true);
        jf.setBounds(x, y, width, height);
    }

    /**
     * 输入框类型的行
     * @param name
     * @return
     */
    public JPanel textBasePanel(String name) {
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
        jp.add(redStar);
        jp.add(jlName);
        jp.add(jt);
        return jp;
    }

    /**
     * 密码框类型的行
     * @param name
     * @return
     */
    public JPanel pwdBasePanel(String name) {
        JPanel jp = new JPanel();
        // 红星
        JLabel redStar = new JLabel("*");
        redStar.setForeground(Color.RED);
        // 行名
        JLabel jlName = new JLabel(name);
        // 输入框
        JPasswordField jt = new JPasswordField(20);
        jts[index] = jt;
        index++;
        jp.add(redStar);
        jp.add(jlName);
        jp.add(jt);
        return jp;
    }

    /**
     * 下拉框类型的行
     * @param name
     * @return
     */
    public JPanel comboxBasePanel(String name) {
        JPanel jp = new JPanel();
        // 红星
        JLabel redStar = new JLabel("*");
        redStar.setForeground(Color.RED);
        // 行名
        JLabel jlName = new JLabel(name);
        // 下拉框
        String[] strs = null;
        JComboBox<String> jcb = null;
        if (name.equals("用户性别：")) {
            strs = new String[]{"男", "女"};
            jcb = new JComboBox<String>(strs);
            sexCb = jcb;
        } else if (name.equals("用户权限：")) {
            if (id != -1) {// 当前处于修改状态
                if (u.getU_name().equals(currentUser.getU_name())) {
                    if (currentUserAuth.equals("超级管理员")) strs = new String[]{"超级管理员"};
                    else if (currentUserAuth.equals("部门经理")) strs = new String[]{"部门经理"};
                }else {
                    if (currentUserAuth.equals("超级管理员")) strs = new String[]{"部门经理", "职员"};
                    else if (currentUserAuth.equals("部门经理")) strs = new String[]{"职员"};
                }
            }
            if (id == -1) {// 当前处于添加状态
                if (currentUserAuth.equals("超级管理员")) strs = new String[]{"部门经理", "职员"};
                else if (currentUserAuth.equals("部门经理")) strs = new String[]{"职员"};
            }
//            if (u.getU_id() == currentUser.getU_id()) {
//                if (currentUserAuth.equals("超级管理员")) strs = new String[]{"超级管理员"};
//                else if (currentUserAuth.equals("部门经理")) strs = new String[]{"部门经理"};
//            }else {
//                if (currentUserAuth.equals("超级管理员")) strs = new String[]{"部门经理", "职员"};
//                else if (currentUserAuth.equals("部门经理")) strs = new String[]{"职员"};
//            }
            jcb = new JComboBox<String>(strs);
            authCb = jcb;
        }
        jp.add(redStar);
        jp.add(jlName);
        jp.add(jcb);
        return jp;
    }

    /**
     * 多行输入框类型的行
     * @param name
     * @return
     */
    public JPanel textAreaBasePanel(String name) {
        JPanel jp = new JPanel();
        // 行名
        JLabel jlName = new JLabel(name);
        // 多行输入框
        JTextArea jta = new JTextArea(3, 20);
        addressTa = jta;
        JScrollPane scroll = new JScrollPane(jta);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jta.setLineWrap(true);
        jp.add(jlName);
        jp.add(scroll);
        return jp;
    }
}
