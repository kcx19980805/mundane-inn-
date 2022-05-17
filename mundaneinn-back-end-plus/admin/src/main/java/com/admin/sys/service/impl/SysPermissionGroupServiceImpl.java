package com.admin.sys.service.impl;

import com.admin.sys.dao.SysPermissionGroupDao;
import com.admin.sys.entity.SysPermissionGroupEntity;
import com.admin.sys.entity.SysPermissionGroupMenuEntity;
import com.admin.sys.entity.SysUserEntity;
import com.admin.sys.requestEntity.RequestPermissionGroupAddEntity;
import com.admin.sys.requestEntity.RequestPermissionGroupDeleteEntity;
import com.admin.sys.requestEntity.RequestPermissionGroupListEntity;
import com.admin.sys.requestEntity.RequestPermissionGroupUpdateEntity;
import com.admin.sys.responseEntity.ResponsePermissionGroupInfoEntity;
import com.admin.sys.responseEntity.ResponsePermissionGroupListEntity;
import com.admin.sys.service.SysPermissionGroupMenuService;
import com.admin.sys.service.SysPermissionGroupService;
import com.admin.sys.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.exception.CustomException;
import com.common.utils.page.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 平台管理组信息表
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:13
 */
@Service("sysPermissionGroupService")
public class SysPermissionGroupServiceImpl extends ServiceImpl<SysPermissionGroupDao, SysPermissionGroupEntity> implements SysPermissionGroupService {

    @Autowired
    private SysPermissionGroupMenuService sysPermissionGroupMenuService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 管理组-新增
     *
     * @param requestPermissionGroupAddEntity
     * @return
     */
    @Override
    public int savePermissionGroup(RequestPermissionGroupAddEntity requestPermissionGroupAddEntity) {
        //管理组名
        String name = requestPermissionGroupAddEntity.getName();
        //校验管理组名是否已存在
        if (checkPermissionGroupNameUnique("", name) != 0) {
            throw new CustomException("管理组名已存在");
        }
        SysPermissionGroupEntity sysPermissionGroupEntity = new SysPermissionGroupEntity();
        sysPermissionGroupEntity.setName(name);
        int i = baseMapper.insert(sysPermissionGroupEntity);

        List<String> menuIdList = Arrays.asList(requestPermissionGroupAddEntity.getMenuId().split(","));
        if (menuIdList.size() > 0) {
            i = baseMapper.savePermissionGroupMenu(sysPermissionGroupEntity.getId().toString(), menuIdList);
        }
        return i;
    }

    /**
     * 管理组-修改
     *
     * @param requestPermissionGroupUpdateEntity
     * @return
     */
    @Override
    public int updatePermissionGroup(RequestPermissionGroupUpdateEntity requestPermissionGroupUpdateEntity) {
        //管理组名
        String name = requestPermissionGroupUpdateEntity.getName();
        //管理组id
        String permissionGroupId = requestPermissionGroupUpdateEntity.getPermissionGroupId();
        //校验管理组名是否已存在
        if (checkPermissionGroupNameUnique(permissionGroupId, name) > 0) {
            throw new CustomException("管理组名已存在");
        }
        SysPermissionGroupEntity sysPermissionGroupEntity = new SysPermissionGroupEntity();
        sysPermissionGroupEntity.setName(name);
        sysPermissionGroupEntity.setId(Long.parseLong(permissionGroupId));
        int i = baseMapper.updateById(sysPermissionGroupEntity);
        //删除原管理组-菜单数据
        sysPermissionGroupMenuService.remove(new QueryWrapper<SysPermissionGroupMenuEntity>().eq("permission_group_id", permissionGroupId));
        List<String> menuIdList = Arrays.asList(requestPermissionGroupUpdateEntity.getMenuId().split(","));
        if (menuIdList.size() > 0) {
            //重新新增 管理组-菜单数据
            i = baseMapper.savePermissionGroupMenu(permissionGroupId, menuIdList);
        }
        return i;
    }

    /**
     * 管理组-真删除
     *
     * @param requestPermissionGroupDeleteEntity
     * @return
     */
    @Override
    public int deletePermissionGroup(RequestPermissionGroupDeleteEntity requestPermissionGroupDeleteEntity) {
        List<String> groupIdList = Arrays.asList(requestPermissionGroupDeleteEntity.getPermissionGroupId().split(","));//得到管理组id
        if (groupIdList.size() > 0) {
            //校验 管理组是否已被使用
            int i = sysUserService.count(new QueryWrapper<SysUserEntity>().eq("is_del", 0).in("type", groupIdList));
            if (i > 0) {
                throw new CustomException("管理组已关联账号,不可删除");
            }
            return baseMapper.deletePermissionGroup(requestPermissionGroupDeleteEntity.getPermissionGroupId());
        }
        throw new CustomException("参数异常");
    }

    /**
     * 管理组列表-查询
     *
     * @param requestPermissionGroupListEntity
     * @return
     */
    @Override
    public PageData<ResponsePermissionGroupListEntity> permissionGroupList(RequestPermissionGroupListEntity requestPermissionGroupListEntity) {
        Integer total = baseMapper.permissionGroupListTotal(requestPermissionGroupListEntity);
        List<ResponsePermissionGroupListEntity> listEntityList = new ArrayList<>();
        if (total > 0) {
            listEntityList = baseMapper.permissionGroupList(requestPermissionGroupListEntity);
            //每页大小
            int limit = requestPermissionGroupListEntity.getLimit();
            if (listEntityList.size() > 0 && limit > 0) {
                //排序 asc升序 desc降序 转小写
                String order = requestPermissionGroupListEntity.getOrder().toLowerCase();
                //当前页码 从0开始
                int currPage = requestPermissionGroupListEntity.getPage();
                for (int i = 0; i < listEntityList.size(); i++) {
                    //自动生成序号 字段:iid
                    listEntityList.get(i).setIid("asc".equals(order) ? limit * currPage + i + 1 : total - (limit * currPage) - i);
                }
            }
        }
        return PageData.ok(listEntityList, total);
    }

    /**
     * 管理组信息-查询
     *
     * @param permissionGroupId 管理组id
     * @return
     */
    @Override
    public ResponsePermissionGroupInfoEntity permissionGroupInfo(String permissionGroupId) {
        return baseMapper.permissionGroupInfo(permissionGroupId);
    }


    /**
     * 校验 管理组 组名是否重复
     *
     * @param permissionGroupId 管理组id
     * @param groupName         管理组名
     * @return
     */
    @Override
    public int checkPermissionGroupNameUnique(String permissionGroupId, String groupName) {
        return baseMapper.checkPermissionGroupNameUnique(permissionGroupId, groupName);
    }

}
