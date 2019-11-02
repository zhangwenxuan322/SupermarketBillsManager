package com.njwb.service;

import com.njwb.entity.User;
import com.njwb.myexception.MyException;

/**
 * @program: SupermarketBillsManager
 * @description: 用户业务层
 * @author: 张文轩
 * @create: 2019-10-25 15:08
 **/
public interface UserService {
    /**
     * 登录操作
     * @param user 用户信息
     * @throws MyException
     */
    void login(User user) throws MyException;

    /**
     * 根据id查询
     * @param id 选中的用户id
     * @param currentUser 当前操作的用户信息
     * @return 查询到的用户信息
     * @throws MyException
     */
    User queryByIdUser(int id, User currentUser, int operation) throws MyException;

    /**
     * 根据id更新
     * @param user 用户信息
     * @throws MyException
     */
    void updateByIdUser(User user, String confirmPwd, String originName) throws MyException;

    /**
     * 用户查询操作
     * @param name 用户姓名
     * @return 结果数组
     * @throws MyException
     */
    String[][] userSelector(String name) throws MyException;

    /**
     * 根据id删除用户
     * @param ids 待删除用户的id组
     * @param currentUser 当前操作的用户信息
     * @throws MyException
     */
    void deleteByIdUser(int[] ids, User currentUser) throws MyException;

    /**
     * 添加用户操作
     * @param user 用户信息
     * @throws MyException
     */
    void addUser(User user, String confirmPwd) throws MyException;
}
