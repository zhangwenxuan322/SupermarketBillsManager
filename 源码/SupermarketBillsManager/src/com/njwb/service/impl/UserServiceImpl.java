package com.njwb.service.impl;

import com.njwb.dao.UserDao;
import com.njwb.entity.User;
import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.UserService;
import com.njwb.transaction.TransAction;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: SupermarketBillsManager
 * @description: 用户业务层实现类
 * @author: 张文轩
 * @create: 2019-10-25 15:09
 **/
public class UserServiceImpl implements UserService {
    //通过对象工厂获取
    private UserDao udao = (UserDao) ObjectFactory.objectMap.get("UserDao");
    private TransAction ta = (TransAction)ObjectFactory.objectMap.get("TransAction");
    /**
     * 登录操作
     * @param user 用户名
     * @throws MyException
     */
    @Override
    public void login(User user) throws MyException {
        String name = user.getU_name();
        String pwd = user.getU_password();
        String auth = user.getU_auth();
        if ((name == null || name.equals("")) || ((pwd == null) || pwd.equals("")))
            throw new MyException("您输入的账号或密码为空，请重新输入！");
        try {
            User u = udao.selectUserByName(name);
            if (u == null) throw new MyException("您输入的用户不存在，请重新输入！");
            else {
                if (!u.getU_password().equals(pwd) || !u.getU_auth().equals(auth)) {
                    throw new MyException("您输入的密码或选择的身份有误，请重新输入或选择！");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据id查询
     * @param id
     * @return 用户信息
     * @throws MyException
     */
    @Override
    public User queryByIdUser(int id, User currentUser, int operation) throws MyException {
        User u = null;
        try {
            u = udao.selectUserById(id);
            if (currentUser != null) {
                if (u.getU_name().equals(currentUser.getU_name()) && operation == 1) throw new MyException("不能删除自己！");
                else if (u.getU_auth().equals(currentUser.getU_auth()) && !u.getU_name().equals(currentUser.getU_name())) throw new MyException("不能对同级操作！");
                else if (u.getU_auth().equals("超级管理员") && currentUser.getU_auth().equals("部门经理")) throw new MyException("不能对上级操作！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    /**
     * 根据id更新
     * @param user 用户信息
     * @throws MyException
     */
    @Override
    public void updateByIdUser(User user, String confirmPwd, String originName) throws MyException {
        if (user.getU_name() == null || user.getU_name().equals("")) throw new MyException("用户姓名未填写！");
        else if (user.getU_password() == null || user.getU_password().equals("")) throw new MyException("用户密码未填写！");
        else if (confirmPwd == null || confirmPwd.equals("")) throw new MyException("确认密码未填写！");
        else if (!user.getU_password().equals(confirmPwd)) throw new MyException("两次密码不一致！");
        else if (user.getU_age() == 0) throw new MyException("用户年龄未填写！");
        else if (user.getU_phone() == null || user.getU_phone().equals("")) throw new MyException("用户电话未填写！");
        try {
            User u = udao.selectUserByName(user.getU_name());
            if (u != null) {
                if (u.getU_name().equals(originName));
                else throw new MyException("该用户名已存在！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            // 事务开启
            ta.begin();
            // 修改数据
            udao.updateByIdUser(user);
            // 事务关闭
            ta.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // 事务回滚
            ta.rollback();
        }
    }

    /**
     * 用户查询操作
     * @param name 用户姓名
     * @return 结果数组
     * @throws MyException
     */
    @Override
    public String[][] userSelector(String name) throws MyException {
        String[][] results = null;
        if (name == null || name.equals("")) {// 如果传参为空，则全部查询
            try {
                List<User> list = udao.selectAllUsers();
                // 将接收到的list转化成二维数组
                results = listCovert(list);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {// 正常传参按姓名模糊查询
            try {
                List<User> list = udao.vagueSelectUserByName(name);
                if (list.size() == 0) {
                    String[][] userArr = new String[0][0];
                    results = userArr;
                } else {
                    // 收到的user放入二维数组
                    results = listCovert(list);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    /**
     * 根据id删除用户
     * @param ids 待删除用户的id组
     * @param currentUser 当前操作的用户信息
     * @throws MyException
     */
    @Override
    public void deleteByIdUser(int[] ids, User currentUser) throws MyException {
        try {
            // 事务开启
            ta.begin();
            for (int i = 0;i < ids.length;i++) {
                // 查询选中用户信息
                queryByIdUser(ids[i], currentUser, 1);
                // 删除操作
                udao.deletUserById(ids[i]);
            }
            // 事务提交
            ta.commit();
        } catch (SQLException e) {
            // 事务回滚
            ta.rollback();
            throw new MyException("删除失败！");
        }
    }

    /**
     * 添加用户操作
     * @param user 用户信息
     * @throws MyException
     */
    @Override
    public void addUser(User user, String confirmPwd) throws MyException {
        if (user.getU_name() == null || user.getU_name().equals("")) throw new MyException("用户姓名未填写！");
        else if (user.getU_password() == null || user.getU_password().equals("")) throw new MyException("用户密码未填写！");
        else if (confirmPwd == null || confirmPwd.equals("")) throw new MyException("确认密码未填写！");
        else if (!user.getU_password().equals(confirmPwd)) throw new MyException("两次密码不一致！");
        else if (user.getU_age() == 0) throw new MyException("用户年龄未填写！");
        else if (user.getU_phone() == null || user.getU_phone().equals("")) throw new MyException("用户电话未填写！");
        try {
            //验证表示层传入用户名是否重复
            User u = udao.selectUserByName(user.getU_name());
            if(u!=null){
                throw new MyException("用户名已经存在！！！");
            }
            // 事务开启
            ta.begin();
            // 插入数据
            udao.insertUser(user);
            // 事务关闭
            ta.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // 事务回滚
            ta.rollback();
        }
    }

    /**
     * 集合转换
     * @param list 集合
     */
    public String[][] listCovert(List<User> list) {
        // 将接收到的list转化成二维数组
        String[][] listToStr = new String[list.size()][7];
        for (int i = 0;i < listToStr.length;i++) {
            User user = list.get(i);
            for (int j = 0; j < listToStr[i].length; j++) {
                switch (j) {
                    case 0:
                        listToStr[i][j] = String.valueOf(user.getU_id());
                        break;
                    case 1:
                        listToStr[i][j] = user.getU_name();
                        break;
                    case 2:
                        listToStr[i][j] = user.getU_gender();
                        break;
                    case 3:
                        listToStr[i][j] = String.valueOf(user.getU_age());
                        break;
                    case 4:
                        listToStr[i][j] = user.getU_phone();
                        break;
                    case 5:
                        listToStr[i][j] = user.getU_address();
                        break;
                    case 6:
                        listToStr[i][j] = user.getU_auth();
                        break;
                }
            }
        }
        return listToStr;
    }
}
