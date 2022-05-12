package com.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;


@Service
public class MapListUtils {

    /**
     * 循环遍历map中的list
     *
     * @param map
     * @param key 存放list的键
     * @return
     */
    public static List getlistValue(Map<String, Object> map, String key) {
        List list = new ArrayList();
        for (Map.Entry<String, Object> str : map.entrySet()) {
            if (key.equals(str.getKey())) {
                if (str.getValue() instanceof List) {
                    ArrayList rtnList = (ArrayList) str.getValue();
                    if (rtnList == null) {
                        return null;
                    }
                    for (Object o : rtnList) {
                        list.add(o);
                    }
                } else {
                    list.add(str.getValue());
                }
            }
        }
        return list;
    }

    /**
     * 实体转Map(TreeMap) 有序
     *
     * @param object
     * @return
     */
    public static Map<String, Object> entityToMap(Object object) {
        Map<String, Object> map = new TreeMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                boolean flag = field.isAccessible();
                //可访问private修饰的变量
                field.setAccessible(true);
                //判断是否为静态常量 忽略静态常量
                boolean isStatic = Modifier.isStatic(field.getModifiers());
                if (isStatic) {
                    continue;
                }
                Object o = field.get(object);
                map.put(field.getName(), o);
                field.setAccessible(flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * map2 追加到 listMap中
     *
     * @param listMap
     * @param map
     * @return 例:
     * listMap:{id=4, name=1}
     * map:{age=14, address = 成都}
     * 成功后: listMap:{id=4, name=1, age=14, address = 成都}
     */
    public static List<Map<String, Object>> mapAddToListMap(List<Map<String, Object>> listMap, Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            return listMap;
        }
        if (listMap.size() == 0) {
            listMap.add(map);
        } else {
            map.forEach((k, v) -> listMap.forEach(s -> {
                s.put(k, v);
            }));
        }
        return listMap;
    }

    /**
     * map1 追加到 map2中
     * key相同 value被覆盖
     *
     * @param map1
     * @param map2
     * @return 例:
     * Map1:{id=4, name=1}
     * map2:{age=14, address = 成都}
     * 成功后: Map:{id=4, name=1, age=14, address = 成都}
     */
    public static Map<String, Object> mapAddToMap(Map<String, Object> map1, Map<String, Object> map2) {
        if (map1 == null || map1.size() == 0) {
            return map2;
        }
        map1.forEach((k, v) -> {
            map2.put(k, v);
        });
        return map2;
    }

    /**
     * List<Map<String,Object>> 去重
     * 效率高 建议使用
     *
     * @param oldList: 需去重的数组
     * @param theKey:  关键字key  key对应的value相同则去重
     * @return
     */
    public static List<Map<String, Object>> listDuplicateRemoval2(List<Map<String, Object>> oldList, String theKey) {
        if (oldList == null || oldList.size() == 0) {
            return null;
        }
        //把list中的数据转换成msp,去掉同一theKey值多余数据，保留查找到第一个theKey值对应的数据
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Map<String, Object>> msp = new LinkedHashMap<>(16);
        for (int i = 0; i < oldList.size(); i++) {
            Map<String, Object> map = oldList.get(i);
            String id = map.get(theKey).toString();
            map.remove(theKey);
            msp.put(id, map);
        }
        //把msp再转换成list,就会得到根据某一字段去掉重复的数据的List<Map>
        Set<String> mspKey = msp.keySet();
        for (String key : mspKey) {
            Map<String, Object> newMap = msp.get(key);
            newMap.put(theKey, key);
            listMap.add(newMap);
        }
        return listMap;
    }

    /**
     * 将json串转化为map类型
     * json字符串转map
     * 采用fastjson
     * 转化方式1：Map map = (Map) JSON.parse(jsonStr);
     * 转化方式2：Map map = JSON.parseObject(jsonStr);
     * jsonStringToMap(String jsonString)为原生方法
     *
     * @param jsonString
     * @return
     */
    public static Map<String, Object> jsonStringToMap(String jsonString) {
        Map<String, Object> map = new HashMap<>(16);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        for (Object k : jsonObject.keySet()) {
            Object o = jsonObject.get(k);
            if (o instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<>();
                Iterator<Object> it = ((JSONArray) o).iterator();
                while (it.hasNext()) {
                    Object obj = it.next();
                    list.add(jsonStringToMap(obj.toString()));
                }
                map.put(k.toString(), list);
            } else if (o instanceof JSONObject) {
                // 如果内层是json对象的话，继续解析
                map.put(k.toString(), jsonStringToMap(o.toString()));
            } else {
                // 如果内层是普通对象的话，直接放入map中
                if (o instanceof String) {
                    map.put(k.toString(), o.toString().trim());
                } else {
                    map.put(k.toString(), o);
                }
            }
        }
        return map;
    }

    /**
     * 将Map<String, Object>转换为 key=value&key=value 形式的字符串
     *
     * @param map
     * @return      
     */
    public static String getNamValStr(Map<String, Object> map) {
        StringBuilder str = new StringBuilder();
        Set<String> set = map.keySet();
        for (String key : set) {
            str.append(key).append("=").append(map.get(key).toString()).append("&");
        }
        return str.toString().substring(0, str.lastIndexOf("&"));
    }
}
