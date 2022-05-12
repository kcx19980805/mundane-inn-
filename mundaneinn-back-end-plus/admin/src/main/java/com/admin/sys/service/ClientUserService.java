package com.admin.sys.service;

import com.admin.sys.entity.ClientUserEntity;
import com.admin.sys.entity.ClientUserImportEntity;
import com.admin.sys.requestEntity.RequestClientUserAddEntity;
import com.admin.sys.requestEntity.RequestClientUserDeleteEntity;
import com.admin.sys.requestEntity.RequestClientUserListEntity;
import com.admin.sys.requestEntity.RequestClientUserUpdateEntity;
import com.admin.sys.responseEntity.ResponseClientUserInfoEntity;
import com.admin.sys.responseEntity.ResponseClientUserListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.page.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户管理
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:12
 */
public interface ClientUserService extends IService<ClientUserEntity> {

    /**
     * 用户管理-listener执行的excel导入
     * @param list
     * @return
     */
    int saveClientUserExcelImport(List<ClientUserImportEntity> list);

    /**
     * 用户管理-导入
     * @param multipartFile
     * @return
     */
    int saveClientUserList(MultipartFile multipartFile);


    /**
     * 用户管理-列表
     *
     * @param requestClientUserListEntity
     * @return
     */
    PageData<ResponseClientUserListEntity> userList(RequestClientUserListEntity requestClientUserListEntity);

    /**
     * 用户管理-单个信息
     * @param userId
     * @return
     */
    ResponseClientUserInfoEntity userInfo(String userId);

    /**
     * 用户管理-添加
     * @param requestClientUserAddEntity
     * @return
     */
    Integer addUser(RequestClientUserAddEntity requestClientUserAddEntity);

    /**
     * 用户管理-修改
     * @param requestClientUserUpdateEntity
     * @return
     */
    Integer updateUser(RequestClientUserUpdateEntity requestClientUserUpdateEntity);

    /**
     * 用户管理-假删除
     * @param requestClientUserDeleteEntity
     * @return
     */
    Integer deleteUser(RequestClientUserDeleteEntity requestClientUserDeleteEntity);

    /**
     * 账号管理-禁用
     *
     * @param userId
     * @return
     */
    Integer disableUser(String userId);

    /**
     * 账号管理-启用
     *
     * @param userId
     * @return
     */
    Integer restoreUser(String userId);

}

