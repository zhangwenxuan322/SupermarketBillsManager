package com.njwb.ui.controllers;

import com.njwb.entity.User;
import com.njwb.ui.views.BillManagerPanel;
import com.njwb.ui.views.ReportsManagerPanel;
import com.njwb.ui.views.SupplierManagerPanel;
import com.njwb.ui.views.UserManagerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: SupermarketBillsManager
 * @description: 主窗口
 * @author: 张文轩
 * @create: 2019-10-25 16:51
 **/
public class MainFrame {
    private User user = null;
    private JFrame f = new JFrame();
    private JPanel top = new JPanel();
    private JPanel left = new JPanel();
    private JPanel right = new JPanel();
    private JLabel nameLb = new JLabel();
    public MainFrame(User user) {
        this.user = user;
    }
    public static void main(String[] args) {
        User testU1 = new User();
        testU1.setU_name("张文轩");
        testU1.setU_password("123");
        testU1.setU_auth("超级管理员");
        User testU2 = new User();
        testU2.setU_name("sys");
        testU2.setU_password("123");
        testU2.setU_auth("部门经理");
        User testU3 = new User();
        testU3.setU_name("syz");
        testU3.setU_password("123");
        testU3.setU_auth("职员");
        new MainFrame(testU1).initMainFrame();
    }
    /**
     * 加载主菜单UI
     */
    public void initMainFrame() {
        JFrame jf = new JFrame();
        f = jf;
        JPanel jp = new JPanel();
        jp.setLayout(null);
        JPanel topPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        top = topPanel;
        left = leftPanel;
        right = rightPanel;
        topPanel.setOpaque(false);
        leftPanel.setOpaque(false);
        rightPanel.setOpaque(true);
        jp.setOpaque(false);
        // 将JFrame设置为透明
        ((JComponent) jf.getContentPane()).setOpaque(false);
        // 读取图片
        ImageIcon img = new ImageIcon("images/MainFrame.png");
        int width = img.getIconWidth();
        int height = img.getIconHeight();
        // 创建JLabel存放背景图
        JLabel jl = new JLabel(img);
        // 设置图片的坐标点
        jf.getLayeredPane().add(jl, new Integer(Integer.MIN_VALUE));
        // 设置背景图的位置
        jl.setBounds(0, 0, width, height);
        topPanel.setBounds(0,
                0,
                width,
                (int) (height * (1.0/6.0)));
//        topPanel.setBackground(Color.BLUE);
        leftPanel.setBounds(0,
                (int) (height * (1.0/6.0)),
                (int) (width * (1.0/5.3)),
                (int) (height * (5.0/6.0)));
//        leftPanel.setBackground(Color.red);
        rightPanel.setBounds((int) (width * (1.0/5.0)),
                (int) (height * (1.0/4.2)),
                (int) (width * (3.95/5.0)),
                (int) (height * (4.5/6.0)));
//        rightPanel.setBackground(Color.CYAN);
        // 加载顶部信息
        topPanelContent();
        // 加载左部信息
        leftPanelContent();
        // 加载右部信息
        rightPanel.setLayout(null);
        Font fnt=new Font("斜体",Font.ITALIC,25);
        JLabel rightWelcomeLb = new JLabel("欢迎来到超市管理系统！", JLabel.CENTER);
        rightWelcomeLb.setFont(fnt);
        rightWelcomeLb.setBounds(0, 0, rightPanel.getWidth(), rightPanel.getHeight());
        rightPanel.add(rightWelcomeLb);
        jp.add(topPanel);
        jp.add(leftPanel);
        jp.add(rightPanel);
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
    }

    /**
     * 顶部信息加载
     */
    public void topPanelContent() {
        top.setLayout(new GridLayout(4,1));
        JPanel spacer1 = new JPanel();
        JPanel welcomeJp = new JPanel();
        JPanel userJp = new JPanel();
        JPanel timeJp = new JPanel();
        spacer1.setOpaque(false);
        welcomeJp.setOpaque(false);
        userJp.setOpaque(false);
        timeJp.setOpaque(false);
        JLabel welcomeLb = new JLabel("欢迎您:");
        welcomeJp.add(welcomeLb);
        JLabel userLb = new JLabel("                              " + user.getU_name());
        nameLb = userLb;
        userJp.add(userLb);
        JLabel time =
                new JLabel("                                                                                                        ");
        Timer timeAction = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long timemillis = System.currentTimeMillis();
                // 转换日期显示格式
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                time.setText("                                                                                                        " + df.format(new Date(timemillis)));
            }
        });
        timeAction.start();
        timeJp.add(time);
        top.add(spacer1);
        top.add(welcomeJp);
        top.add(userJp);
        top.add(timeJp);
    }

    /**
     * 左部信息加载
     */
    public void leftPanelContent() {
        // 创建按钮
        JButton bill = createBtn("images/btn_bill.gif");
        JButton reports = createBtn("images/btn_reports.gif");
        JButton exit = createBtn("images/btn_exit.gif");
        left.setLayout(new GridLayout(7,1));
        // 根据权限加载按钮
        if (user.getU_auth().equals("超级管理员") || user.getU_auth().equals("部门经理")) {
            JButton suppliers = createBtn("images/btn_suppliers.gif");
            JButton users = createBtn("images/btn_users.gif");
            left.add(bill);
            left.add(suppliers);
            left.add(users);
            left.add(reports);
            left.add(exit);

            // 供应商管理按钮功能实现
            suppliers.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel sp = new SupplierManagerPanel(0, 0, right.getWidth(), right.getHeight());
                    refreshRight(sp);
                }
            });

            // 用户管理按钮功能实现
            users.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel up = new UserManagerPanel(f, nameLb, user, 0, 0, right.getWidth(), right.getHeight());
                    refreshRight(up);
                }
            });
        }else {
            left.add(bill);
            left.add(reports);
            left.add(exit);
        }

        //报表管理按钮功能实现
        reports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel rp = new ReportsManagerPanel(0, 0, right.getWidth(), right.getHeight());
                refreshRight(rp);
            }
        });

        // 账单管理按钮功能实现
        bill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel bp = new BillManagerPanel(0, 0, right.getWidth(), right.getHeight());
                refreshRight(bp);
            }
        });

        // 退出按钮功能实现
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "是否确定要退出系统？", "退出系统", JOptionPane.YES_NO_OPTION) == 0) {
                    f.dispose();
                    System.exit(0);
                }
            }
        });
    }

    /**
     * 根据对应按钮点击刷星页面
     * @param jp 对应界面
     */
    public void refreshRight(JPanel jp) {
        right.removeAll();
        // 加载对应界面
        right.add(jp);
        // 刷新右部视图
        right.updateUI();
        right.repaint();
    }

    /**
     * 创建左侧按钮
     * @param filename 背景图文件路径
     * @return 按钮
     */
    public JButton createBtn(String filename) {
        JButton jb = new JButton();
        // 读取图片
        ImageIcon img = new ImageIcon(filename);
        // 按钮设置图标
        jb.setIcon(img);
        return jb;
    }
}
