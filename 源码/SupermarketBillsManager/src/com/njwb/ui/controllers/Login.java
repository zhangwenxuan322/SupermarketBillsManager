package com.njwb.ui.controllers;

import com.njwb.entity.User;
import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @program: SupermarketBillsManager
 * @description: 登录界面
 * @author: 张文轩
 * @create: 2019-10-25 13:12
 **/
public class Login {
    //通过对象工厂获取
    private UserService uService = (UserService) ObjectFactory.objectMap.get("UserService");
    private User u = (User) ObjectFactory.objectMap.get("User");
    //两个输入框组成数组
    private final JTextField[] jts = new JTextField[2];
    //当前数组的下标
    private int index = 0;
    //下拉框
    private JComboBox<String> sjcb = new JComboBox<String>();
    //全局jframe
    private JFrame sjf = new JFrame();

    public static void main(String[] args) {
        new Login().initLoginUI();
    }

    /**
     * 加载UI
     */
    public void initLoginUI() {
        JFrame jf = new JFrame("登录");
        sjf = jf;
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(11, 1));
        // 构建四个Panel
        JPanel spacer1 = new JPanel();
        JPanel spacer2 = new JPanel();
        JPanel spacer3 = new JPanel();
        JPanel spacer4 = new JPanel();
        JPanel spacer5 = new JPanel();
        JPanel spacer6 = new JPanel();
        JPanel spacer7 = new JPanel();
        JPanel jpName = textBaseBlock("姓名：");
        JPanel jpPwd = pwdBaseBlock("密码：");
        JPanel jpAuth = comboxBaseBlock("身份：");
        JPanel jpBtns = buttonsBaseBlock();
        // 设置背景图
        // 将所有容器设置为透明
        jp.setOpaque(false);
        spacer1.setOpaque(false);
        spacer2.setOpaque(false);
        spacer3.setOpaque(false);
        spacer4.setOpaque(false);
        spacer5.setOpaque(false);
        spacer6.setOpaque(false);
        spacer7.setOpaque(false);
        jpName.setOpaque(false);
        jpPwd.setOpaque(false);
        jpAuth.setOpaque(false);
        jpBtns.setOpaque(false);
        // 将JFrame设置为透明
        ((JComponent) jf.getContentPane()).setOpaque(false);
        // 读取图片
        ImageIcon img = new ImageIcon("images/login_box.jpg");
        int width = img.getIconWidth();
        int height = img.getIconHeight();
        // 创建JLabel存放背景图
        JLabel jl = new JLabel(img);
        // 设置图片的坐标点
        jf.getLayeredPane().add(jl, new Integer(Integer.MIN_VALUE));
        // 设置背景图的位置
        jl.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        // 添加到底层Panel上
        jp.add(spacer1);
        jp.add(spacer2);
        jp.add(spacer3);
        jp.add(spacer5);
        jp.add(spacer7);
        jp.add(jpName);
        jp.add(jpPwd);
        jp.add(jpAuth);
        jp.add(jpBtns);
        jp.add(spacer4);
        jp.add(spacer6);
        jf.add(jp);
        jf.setVisible(true);
        // 定义工具包
        Toolkit kit = Toolkit.getDefaultToolkit();
        // 获取屏幕的尺寸
        Dimension screenSize = kit.getScreenSize();
        // 获取屏幕的宽
        int screenWidth = screenSize.width;
        // 获取屏幕的高
        int screenHeight = screenSize.height;
        // 窗口居中
        jf.setBounds(screenWidth/2-width/2, screenHeight/2-height/2, width, height);
        jf.setDefaultCloseOperation(3);
        jf.setDefaultCloseOperation(3);
    }

    /**
     * 以输入框为基础的组件
     * @param name 组件名
     * @return 组件
     */
    public JPanel textBaseBlock(String name) {
        JPanel jp = new JPanel();
        JLabel jlName = new JLabel(name);
        JTextField jt = new JTextField(20);
        jts[index] = jt;
        index++;
        jp.add(jlName);
        jp.add(jt);
        return jp;
    }

    /**
     * 以密码框为基础的组件
     * @param name 组件名
     * @return 组件
     */
    public JPanel pwdBaseBlock(String name) {
        JPanel jp = new JPanel();
        JLabel jlName = new JLabel(name);
        JPasswordField jt = new JPasswordField(20);
        jts[index] = jt;
        index++;
        jp.add(jlName);
        jp.add(jt);
        return jp;
    }

    /**
     * 以下拉框为基础的组件
     * @param name 组件名
     * @return 组件
     */
    public JPanel comboxBaseBlock(String name) {
        JPanel jp = new JPanel();
        JLabel jlName = new JLabel(name);
        String[] strs = {"超级管理员", "部门经理", "职员"};
        JComboBox<String> jcb = new JComboBox<String>(strs);
        sjcb = jcb;
        jp.add(jlName);
        jp.add(jcb);
        return jp;
    }

    /**
     * 最下层按钮组件
     * @return 组件
     */
    public JPanel buttonsBaseBlock() {
        JPanel jp = new JPanel();
        JButton loginBtn = new JButton("登录系统");
        JButton resetBtn = new JButton("重置");
        // 登录按钮实现
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = jts[0].getText();
                String pwd = jts[1].getText();
                String auth = sjcb.getItemAt(sjcb.getSelectedIndex());
                u.setU_name(name);
                u.setU_password(pwd);
                u.setU_auth(auth);
                try {
                    uService.login(u);
                    sjf.dispose();
                    new MainFrame(u).initMainFrame();
                } catch (MyException ex) {
                    JOptionPane.showMessageDialog(sjf, ex.getMessage());
                }
            }
        });
        // 重置按钮实现
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jts[0].setText("");
                jts[1].setText("");
                sjcb.setSelectedIndex(0);
            }
        });
        jp.add(loginBtn);
        jp.add(resetBtn);
        return jp;
    }
}
