package com.admin.sys.service.impl;

import com.admin.sys.dao.SysUserDao;
import com.admin.sys.entity.SysUserEntity;
import com.admin.sys.requestEntity.*;
import com.admin.sys.responseEntity.ResponseUserInfoEntity;
import com.admin.sys.responseEntity.ResponseUserListEntity;
import com.admin.sys.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.exception.CustomException;
import com.common.utils.encryption.Base64Utils;
import com.common.utils.encryption.Shiro2Utils;
import com.common.utils.page.PageData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * 账号管理(平台)
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:12
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {


    /**
     * 账号管理-新增
     *
     * @param requestUserAddEntity
     * @return
     */
    @Override
    public int saveUser(RequestUserAddEntity requestUserAddEntity) {
        String account = new String(Base64.getDecoder().decode(requestUserAddEntity.getAccount()));
        //校验账号是否已存在
        if (checkAccountUnique("", account) != 0) {
            throw new CustomException("账号已存在");
        }
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setAccount(account);
        sysUserEntity.setType(Long.parseLong(requestUserAddEntity.getPermissionGroupId()));
        String salt = Shiro2Utils.getGenerateSalt();
        sysUserEntity.setSalt(salt);
        sysUserEntity.setPassword(Shiro2Utils.sha256(Base64Utils.base64Encoder("123456"), salt));
        return baseMapper.insert(sysUserEntity);
    }

    /**
     * 账号管理-修改
     *
     * @param requestUserUpdateEntity
     * @return
     */
    @Override
    public int updateUser(RequestUserUpdateEntity requestUserUpdateEntity) {
        String userId = requestUserUpdateEntity.getUserId();
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(Long.parseLong(userId));
        sysUserEntity.setType(Long.parseLong(requestUserUpdateEntity.getPermissionGroupId()));
        return baseMapper.updateById(sysUserEntity);
    }

    /**
     * 账号管理-假删除
     *
     * @param requestUserDeleteEntity
     * @return
     */
    @Override
    public int deleteUnRealUser(RequestUserDeleteEntity requestUserDeleteEntity) {
        List<String> userIdList = Arrays.asList(requestUserDeleteEntity.getUserId().split(","));
        if (userIdList.size() > 0) {
            SysUserEntity sysUserEntity = new SysUserEntity();
            sysUserEntity.setIsDel("1");
            return baseMapper.update(sysUserEntity, new QueryWrapper<SysUserEntity>().in("id", userIdList));
        } else {
            throw new CustomException("参数异常");
        }
    }

    /**
     * 账号管理-列表-查询
     *
     * @param requestUserListEntity
     * @return
     */
    @Override
    public PageData<ResponseUserListEntity> userList(RequestUserListEntity requestUserListEntity) {
        Integer total = baseMapper.userListTotal(requestUserListEntity);
        List<ResponseUserListEntity> listEntityList = new ArrayList<>();
        if (total > 0) {
            listEntityList = baseMapper.userList(requestUserListEntity);
            //每页大小
            int limit = requestUserListEntity.getLimit();
            if (listEntityList.size() > 0  && limit > 0) {
                //当前页码 从0开始
                int currPage = requestUserListEntity.getPage();

                //排序 asc升序 desc降序 转小写
                String order = requestUserListEntity.getOrder().toLowerCase();
                for (int i = 0; i < listEntityList.size(); i++) {
                    //自动生成序号 字段:iid
                    listEntityList.get(i).setIid("asc".equals(order) ? limit * currPage + i + 1 : total - (limit * currPage) - i);
                }
            }
        }
            return PageData.ok(listEntityList, total);
    }

    /**
     * 账号管理-查询单个详情
     *
     * @param userId 账号id
     * @return
     */
    @Override
    public ResponseUserInfoEntity userInfo(String userId) {
        return baseMapper.userInfo(userId);
    }


    /**
     * 修改密码
     *
     * @param requestUserUpdatePasswordEntity
     * @return
     */
    @Override
    public int updatePassword(RequestUserUpdatePasswordEntity requestUserUpdatePasswordEntity) {
        String oldPassword = requestUserUpdatePasswordEntity.getOldPassword();
        String newPassword = requestUserUpdatePasswordEntity.getNewPassword();
        String confirmPassword = requestUserUpdatePasswordEntity.getConfirmPassword();
        if (!newPassword.equals(confirmPassword)) {
            throw new CustomException("两次输入新密码不匹配");
        }
        //SysUserEntity sysUserEntity = ShiroUtils.getObject();
        //Long userId = sysUserEntity.getId();
        Long userId =Long.valueOf(requestUserUpdatePasswordEntity.getUserId());

        SysUserEntity sysUserEntity1 = baseMapper.selectOne(new QueryWrapper<SysUserEntity>().eq("id", userId).eq("is_del", 0));
        if (sysUserEntity1 == null) {
            throw new CustomException("当前账号异常");
        }
        System.out.println(sysUserEntity1);
        //Shiro2Utils.sha256("123456", salt)

        if (Shiro2Utils.checkPasswordSha256(sysUserEntity1.getPassword(), oldPassword, sysUserEntity1.getSalt())) {
            SysUserEntity sysUserEntity2 = new SysUserEntity();
            //新密码 加密
            String salt = Shiro2Utils.getGenerateSalt();
            sysUserEntity2.setSalt(salt);
            sysUserEntity2.setPassword(Shiro2Utils.sha256(newPassword, salt));
            sysUserEntity2.setId(userId);
            return baseMapper.updateById(sysUserEntity2);
        } else {
            throw new CustomException("旧密码错误");
        }
    }


    /**
     * 校验 账号是否重复
     *
     * @param userId  账号id
     * @param account 账号
     * @return
     */
    @Override
    public int checkAccountUnique(String userId, String account) {
        return baseMapper.checkAccountUnique(userId, account);
    }

    /**
     * 账号管理-禁用
     *
     * @param requestUserDeleteEntity
     * @return
     */
    @Override
    public int disableUser(RequestUserDeleteEntity requestUserDeleteEntity) {
        List<String> userIdList = Arrays.asList(requestUserDeleteEntity.getUserId().split(","));
        if (userIdList.size() > 0) {
            SysUserEntity sysUserEntity = new SysUserEntity();
            sysUserEntity.setStatus("1");
            return baseMapper.update(sysUserEntity, new QueryWrapper<SysUserEntity>().in("id", userIdList));
        } else {
            throw new CustomException("参数异常");
        }
    }

    /**
     * 账号管理-启用
     *
     * @param requestUserDeleteEntity
     * @return
     */
    @Override
    public int restoreUser(RequestUserDeleteEntity requestUserDeleteEntity) {
        List<String> userIdList = Arrays.asList(requestUserDeleteEntity.getUserId().split(","));
        if (userIdList.size() > 0) {
            SysUserEntity sysUserEntity = new SysUserEntity();
            sysUserEntity.setStatus("0");
            return baseMapper.update(sysUserEntity, new QueryWrapper<SysUserEntity>().in("id", userIdList));
        } else {
            throw new CustomException("参数异常");
        }
    }

    /**
     * 平台登录
     *
     * @param account 账号
     * @return
     */
    @Override
    public SysUserEntity login(String account) {
        return baseMapper.login(account);
    }


}

