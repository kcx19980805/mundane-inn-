package com.admin.sys.service.impl;

import com.admin.sys.dao.SysMenuDao;
import com.admin.sys.entity.SysMenuEntity;
import com.admin.sys.responseEntity.ResponseMenuListEntity;
import com.admin.sys.responseEntity.ResponseMenuListEntity2;
import com.admin.sys.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 平台系统菜单管理
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:12
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

    /**
     * 查询管理员所有的菜单列表
     *
     * @param type 所属管理组(存sys_permission_group表id 默认0超级管理员)
     * @return
     */
    @Override
    public List<ResponseMenuListEntity> getAdminMenuList(String type) {
        List<ResponseMenuListEntity> list = baseMapper.getAdminMenuList(type);
        List<ResponseMenuListEntity> menuList = new ArrayList<>(16);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                List<ResponseMenuListEntity> list2 = new ArrayList<>(16);
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(i).getId().equals(list.get(j).getParentId())) {
                        list2.add(list.get(j));
                    }
                }
                //排序 order_num 升序
//                Collections.sort(list2, new Comparator<Map<String, Object>>() {
//                    @Override
//                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
//                        // 降序：o2.compareTo(o1)  升序：o1.compareTo(o2)
//                        return new BigDecimal(o1.get("order_num").toString()).compareTo(new BigDecimal(o2.get("order_num").toString()));
//                    }
//                });
                list.get(i).setList(list2);
                if ("0".equals(list.get(i).getParentId())) {
                    menuList.add(list.get(i));
                }
            }
        }
        return menuList;
    }

    /**
     * 查询管理员所有的操作权限(增删改查)
     *
     * @param type 所属管理组(存sys_permission_group表id 默认0超级管理员)
     * @return
     */
    @Override
    public List<String> getPermsList(String type) {
        return baseMapper.getPermsList(type);
    }

    /**
     * 平台端 新增/修改权限组时 查询所有菜单-按钮树形图
     *
     * @return
     */
    @Override
    public List<ResponseMenuListEntity2> getTreeMenuList() {
        List<ResponseMenuListEntity2> menuEntityList = baseMapper.getTreeMenuList();
        if (menuEntityList != null && menuEntityList.size() > 0) {
            for (ResponseMenuListEntity2 responseMenuListEntity2 : menuEntityList) {
                if ("0".equals(responseMenuListEntity2.getParentId())) {
                    responseMenuListEntity2.setParent(true);
                } else {
                    responseMenuListEntity2.setParent(!"2".equals(responseMenuListEntity2.getType()));
                }
            }
        }
        return menuEntityList;
    }

}
