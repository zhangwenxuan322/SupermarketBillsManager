package com.njwb.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: SupermarketBillsManager
 * @description: 对象工厂类
 * @author: 张文轩
 * @create: 2019-10-25 15:39
 **/
public class ObjectFactory {
    // 声明map集合保存object配置文件中对象信息
    public static Map<String, Object> objectMap = new HashMap<String, Object>();

    static {
        BufferedReader br = null;
        br = new BufferedReader(
                new InputStreamReader(ObjectFactory.class.getClassLoader().getResourceAsStream("object")));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                String[] srr = line.split("=");
                //得到object配置中的对象名称
                String key = srr[0];
                //根据object配置中的类路径，反射创建对象
                Object value = Class.forName(srr[1]).newInstance();
//                System.out.println(key+"-->"+value);
                //添加到map集合中
                objectMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
