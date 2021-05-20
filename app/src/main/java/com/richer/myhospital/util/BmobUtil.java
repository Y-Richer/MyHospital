package com.richer.myhospital.util;

import android.util.Log;

import com.richer.myhospital.home.model.City;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 2021/04/14 Richer
 *
 */
public class BmobUtil {

    /**
     * 条件查询
     *
     * @param listener 监听者
     * @param <T> 数据类型， 对应数据库表
     */
    public static <T extends BmobObject> void equalQuery(Map<String, String> condition, FindListener<T> listener) {
        BmobQuery<T> bmobQuery = new BmobQuery<>();
        Set<String> keySet = condition.keySet();
        for (String key:keySet) {
            String value = condition.get(key);
            bmobQuery.addWhereEqualTo(key, value);
        }
        bmobQuery.findObjects(listener);
    }

    /**
     * 插入一行
     * @param t 数据
     * @param listener 监听者
     * @param <T> 数据类型，对应数据库表
     */
    public static <T extends BmobObject> void insert(T t, SaveListener<String> listener) {
        t.save(listener);
    }

    public static <T extends BmobObject> void doSQLQuery(String sql, SQLQueryListener<T> listener){
        new BmobQuery<T>().doSQLQuery(sql, listener);
    }

    public static <T extends BmobObject> void remove() {

    }

    public static void test() {
        String sql = "select * from City";
        BmobQuery<City> query = new BmobQuery<>();
        query.setLimit(500);
        query.doSQLQuery(sql, new SQLQueryListener<City>() {
            @Override
            public void done(BmobQueryResult<City> result, BmobException e) {
                if (e == null) {
                    List<City> cities = result.getResults();
                    if (cities != null && cities.size() > 0) {
                        Log.d("TAG", "done: city"+cities);
                    }
                }else {
                    Log.d("TAG", "done: fail");
                }
            }
        });
    }

}
