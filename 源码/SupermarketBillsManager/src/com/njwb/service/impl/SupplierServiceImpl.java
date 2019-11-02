package com.njwb.service.impl;

import com.njwb.dao.AccountDao;
import com.njwb.dao.SupplierDao;
import com.njwb.dao.impl.AccountDaoImpl;
import com.njwb.entity.Account;
import com.njwb.entity.Supplier;
import com.njwb.entity.SupplierGroup;
import com.njwb.factory.ObjectFactory;
import com.njwb.myexception.MyException;
import com.njwb.service.SupplierService;
import com.njwb.transaction.TransAction;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: SupermarketBillsManager
 * @description: 供应商业务逻辑层接口实现类
 * @author: 张文轩
 * @create: 2019-10-28 13:54
 **/
public class SupplierServiceImpl implements SupplierService {
    //通过对象工厂获取
    private SupplierDao sdao = (SupplierDao) ObjectFactory.objectMap.get("SupplierDao");
    private TransAction ta = (TransAction)ObjectFactory.objectMap.get("TransAction");
    private AccountDao adao = new AccountDaoImpl();
    /**
     * 供应商名称查询
     * @param name
     * @return
     * @throws MyException
     */
    @Override
    public String[][] supplierSelector(String name) throws MyException {
        String[][] results = null;
        if (name == null || name.equals("")) {// 如果传参为空，则全部查询
            try {
                List<Supplier> list = sdao.selectAllSuppliers();
                // 将接收到的list转化成二维数组
                results = listCovert(list);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {// 正常传参按姓名模糊查询
            try {
                List<Supplier> list = sdao.vagueSelectSupplierByName(name);
                if (list.size() == 0) {
                    String[][] supplierArr = new String[0][0];
                    results = supplierArr;
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
     *  添加供应商
     * @param supplier
     * @throws MyException
     */
    @Override
    public void addSupplier(Supplier supplier) throws MyException {
        if (supplier.getS_name() == null || supplier.getS_name().equals("")) throw new MyException("供应商名称未填写！");
        else if (supplier.getS_linkman() == null || supplier.getS_linkman().equals("")) throw new MyException("供应商联系人未填写！");
        else if (supplier.getS_phone() == null || supplier.getS_phone().equals("")) throw new MyException("供应商电话未填写！");
        else if (supplier.getS_address() == null || supplier.getS_address().equals("")) throw new MyException("供应商地址未填写！");
        try {
            // 验证供应商是否存在
            Supplier s = sdao.selectSupplierByName(supplier.getS_name());
            if (s != null) throw new MyException("供应商名称已存在！");
            // 事务开启
            ta.begin();
            // 插入数据
            sdao.insertSupplier(supplier);
            // 事务关闭
            ta.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // 事务回滚
            ta.rollback();
        }
    }

    /**
     * 根据id修改供应商信息
     * @param supplier
     * @throws MyException
     */
    @Override
    public void updateByIdSupplier(Supplier supplier, String originName) throws MyException {
        if (supplier.getS_name() == null || supplier.getS_name().equals("")) throw new MyException("供应商名称未填写！");
        else if (supplier.getS_linkman() == null || supplier.getS_linkman().equals("")) throw new MyException("供应商联系人未填写！");
        else if (supplier.getS_phone() == null || supplier.getS_phone().equals("")) throw new MyException("供应商电话未填写！");
        else if (supplier.getS_address() == null || supplier.getS_address().equals("")) throw new MyException("供应商地址未填写！");
        try {
            Supplier s = sdao.selectSupplierByName(supplier.getS_name());
            if (s != null) {
                if (s.getS_name().equals(originName));
                else throw new MyException("该供应商名已存在！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            // 事务开启
            ta.begin();
            // 修改数据
            sdao.updateByIdSupplier(supplier);
            // 事务关闭
            ta.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // 事务回滚
            ta.rollback();
        }
    }

    /**
     * 根据id查找供应商
     * @param id
     * @return
     * @throws MyException
     */
    @Override
    public Supplier queryByIdSupplier(int id) throws MyException {
        Supplier u = null;
        try {
            u = sdao.selectSupplierById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public int queryByNameSupplier(String name) throws MyException {
        Supplier u = null;
        try {
            u = sdao.selectSupplierByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u.getS_id();
    }

    /**
     * 根据id删除供应商
     * @param ids
     * @throws MyException
     */
    @Override
    public void deleteByIdSupplieer(int[] ids) throws MyException {
        try {
            // 事务开启
            ta.begin();
            for (int i = 0;i < ids.length;i++) {
                // 删除操作
                sdao.deletSupplierById(ids[i]);
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
     * 导入操作
     * @param supplier
     */
    @Override
    public void importOperation(Supplier supplier) {
        if (supplier.getS_name() == null || supplier.getS_name().equals("")) return;
        else if (supplier.getS_linkman() == null || supplier.getS_linkman().equals("")) return;
        else if (supplier.getS_phone() == null || supplier.getS_phone().equals("")) return;
        else if (supplier.getS_address() == null || supplier.getS_address().equals("")) return;
        try {
            // 验证供应商是否存在
            Supplier s = sdao.selectSupplierByName(supplier.getS_name());
            if (s != null) return;
            // 事务开启
            ta.begin();
            // 插入数据
            sdao.insertSupplier(supplier);
            // 事务关闭
            ta.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // 事务回滚
            ta.rollback();
        }
    }

    @Override
    public String[][] selectByGroup() throws MyException {
        String[][] results = null;
        try {
            List<SupplierGroup> list = sdao.selectByGroup();
            String[][] listToStr = new String[list.size()][5];
            for (int i = 0;i < listToStr.length;i++) {
                SupplierGroup supplierGroup = list.get(i);
                for (int j = 0; j < listToStr[i].length; j++) {
                    switch (j) {
                        case 0:
                            listToStr[i][j] = String.valueOf(supplierGroup.getId());
                            break;
                        case 1:
                            listToStr[i][j] = supplierGroup.getName();
                            break;
                        case 2:
                            listToStr[i][j] = String.valueOf(supplierGroup.getAmount());
                            break;
                        case 3:
                            listToStr[i][j] = String.valueOf(supplierGroup.getKind());
                            break;
                        case 4:
                            listToStr[i][j] = String.valueOf(supplierGroup.getCount());
                            break;
                    }
                }
                results = listToStr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 集合转换
     * @param list 集合
     */
    public String[][] listCovert(List<Supplier> list) {
        // 将接收到的list转化成二维数组
        String[][] listToStr = new String[list.size()][6];
        for (int i = 0;i < listToStr.length;i++) {
            Supplier supplier = list.get(i);
            for (int j = 0; j < listToStr[i].length; j++) {
                switch (j) {
                    case 0:
                        listToStr[i][j] = String.valueOf(supplier.getS_id());
                        break;
                    case 1:
                        listToStr[i][j] = supplier.getS_name();
                        break;
                    case 2:
                        listToStr[i][j] = supplier.getS_info();
                        break;
                    case 3:
                        listToStr[i][j] = supplier.getS_linkman();
                        break;
                    case 4:
                        listToStr[i][j] = supplier.getS_phone();
                        break;
                    case 5:
                        listToStr[i][j] = supplier.getS_address();
                        break;
                }
            }
        }
        return listToStr;
    }
}
