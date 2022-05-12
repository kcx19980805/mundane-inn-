package com.admin.sys.service.impl;

import com.admin.config.excel.ClientUserExcelImportListener;
import com.admin.sys.dao.ClientUserDao;
import com.admin.sys.entity.ClientUserEntity;
import com.admin.sys.entity.ClientUserImportEntity;
import com.admin.sys.requestEntity.RequestClientUserAddEntity;
import com.admin.sys.requestEntity.RequestClientUserDeleteEntity;
import com.admin.sys.requestEntity.RequestClientUserListEntity;
import com.admin.sys.requestEntity.RequestClientUserUpdateEntity;
import com.admin.sys.responseEntity.ResponseClientUserInfoEntity;
import com.admin.sys.responseEntity.ResponseClientUserListEntity;
import com.admin.sys.service.ClientUserService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.exception.CustomException;
import com.common.utils.encryption.Base64Utils;
import com.common.utils.encryption.Shiro2Utils;
import com.common.utils.page.PageData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 用户管理
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:12
 */
@Service("clientUserService")
public class ClientUserServiceImpl extends ServiceImpl<ClientUserDao, ClientUserEntity> implements ClientUserService {
    /**
     * 用户管理-listener执行的excel导入
     *
     * @param list
     * @return
     */
    @Override
    public int saveClientUserExcelImport(List<ClientUserImportEntity> list) {
        for (ClientUserImportEntity entity : list) {
            String account = entity.getAccount();
            if (!StringUtils.isNotBlank(entity.getEmail())) {
                throw new CustomException("邮箱不能为空");
            }
            if (!StringUtils.isNotBlank(account)) {
                throw new CustomException("账号不能为空");
            }
            if (baseMapper.selectOne(new QueryWrapper<ClientUserEntity>().eq("email", entity.getEmail())) != null) {
                throw new CustomException("邮箱" + entity.getEmail() + "已被注册");
            }
            ClientUserEntity clientUserEntity = new ClientUserEntity();
            clientUserEntity.setAccount(account);
            String salt = Shiro2Utils.getGenerateSalt();
            clientUserEntity.setSalt(salt);
            clientUserEntity.setPassword(Shiro2Utils.sha256(Base64Utils.base64Encoder("123456"), salt));
            clientUserEntity.setNickName(account);
            clientUserEntity.setPhone(entity.getPhone());
            clientUserEntity.setName(entity.getName());
            clientUserEntity.setSex(entity.getSex());
            clientUserEntity.setEmail(entity.getEmail());
            if (baseMapper.insert(clientUserEntity) <= 0) {
                throw new CustomException("导入失败!");
            }
        }
        return 1;
    }

    /**
     * 用户管理-导入
     *
     * @param multipartFile
     * @return
     */
    @Override
    public int saveClientUserList(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        if (StringUtils.isNotBlank(fileName)) {
            if (!fileName.endsWith(ExcelTypeEnum.XLS.getValue()) && !fileName.endsWith(ExcelTypeEnum.XLSX.getValue())) {
                throw new CustomException("文件格式错误,请重新选择 xlsx 或 xls 格式文件");
            }
        }
        try {
            EasyExcel.read(multipartFile.getInputStream(), ClientUserImportEntity.class, new ClientUserExcelImportListener(this)).sheet().doRead();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * 用户管理-列表
     *
     * @param requestClientUserListEntity
     * @return
     */
    @Override
    public PageData<ResponseClientUserListEntity> userList(RequestClientUserListEntity requestClientUserListEntity) {
        Integer total = baseMapper.userListTotal(requestClientUserListEntity);
        List<ResponseClientUserListEntity> listEntityList = new ArrayList<>();
        if (total > 0) {
            listEntityList = baseMapper.userList(requestClientUserListEntity);
            //每页大小
            int limit = requestClientUserListEntity.getLimit();
            if (listEntityList.size() > 0 && limit > 0) {
                //当前页码 从0开始
                int currPage = requestClientUserListEntity.getPage();
                //排序 asc升序 desc降序 转小写
                String order = requestClientUserListEntity.getOrder().toLowerCase();
                for (int i = 0; i < listEntityList.size(); i++) {
                    //自动生成序号 字段:iid
                    listEntityList.get(i).setIid("asc".equals(order) ? limit * currPage + i + 1 : total - (limit * currPage) - i);
                }
            }
        }
        return PageData.ok(listEntityList, total);
    }

    /**
     * 用户管理-单个信息
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseClientUserInfoEntity userInfo(String userId) {
        return baseMapper.userInfo(userId);
    }

    /**
     * 用户管理-添加
     *
     * @param requestClientUserAddEntity
     * @return
     */
    @Override
    public Integer addUser(RequestClientUserAddEntity requestClientUserAddEntity) {
        String account = new String(Base64.getDecoder().decode(requestClientUserAddEntity.getAccount()));
        if (baseMapper.selectOne(new QueryWrapper<ClientUserEntity>().eq("email", requestClientUserAddEntity.getEmail())
                .eq("is_del",0)) != null) {
            throw new CustomException("邮箱已被注册");
        }
        ClientUserEntity clientUserEntity = new ClientUserEntity();
        clientUserEntity.setAccount(account);
        String salt = Shiro2Utils.getGenerateSalt();
        clientUserEntity.setSalt(salt);
        clientUserEntity.setPassword(Shiro2Utils.sha256(Base64Utils.base64Encoder("123456"), salt));
        clientUserEntity.setNickName(requestClientUserAddEntity.getNickName());
        clientUserEntity.setPhone(requestClientUserAddEntity.getPhone());
        clientUserEntity.setName(requestClientUserAddEntity.getName());
        clientUserEntity.setSex(requestClientUserAddEntity.getSex());
        clientUserEntity.setEmail(requestClientUserAddEntity.getEmail());
        return baseMapper.insert(clientUserEntity);
    }

    /**
     * 用户管理-修改
     *
     * @param requestClientUserUpdateEntity
     * @return
     */
    @Override
    public Integer updateUser(RequestClientUserUpdateEntity requestClientUserUpdateEntity) {
        String account =  Base64Utils.base64Decoder(requestClientUserUpdateEntity.getAccount());
        ClientUserEntity clientUserEntity = new ClientUserEntity();
        clientUserEntity.setId(Long.valueOf(requestClientUserUpdateEntity.getUserId()));
        clientUserEntity.setAccount(account);
        clientUserEntity.setNickName(requestClientUserUpdateEntity.getNickName());
        clientUserEntity.setPhone(requestClientUserUpdateEntity.getPhone());
        clientUserEntity.setName(requestClientUserUpdateEntity.getName());
        clientUserEntity.setSex(requestClientUserUpdateEntity.getSex());
        clientUserEntity.setEmail(requestClientUserUpdateEntity.getEmail());
        return baseMapper.updateById(clientUserEntity);
    }

    /**
     * 用户管理-假删除
     *
     * @param requestClientUserDeleteEntity
     * @return
     */
    @Override
    public Integer deleteUser(RequestClientUserDeleteEntity requestClientUserDeleteEntity) {
        ClientUserEntity clientUserEntity = new ClientUserEntity();
        clientUserEntity.setId(Long.valueOf(requestClientUserDeleteEntity.getUserId()));
        clientUserEntity.setIsDel("1");
        return baseMapper.updateById(clientUserEntity);
    }

    /**
     * 账号管理-禁用
     *
     * @param userId
     * @return
     */
    @Override
    public Integer disableUser(String userId) {
        ClientUserEntity clientUserEntity = new ClientUserEntity();
        clientUserEntity.setId(Long.valueOf(userId));
        clientUserEntity.setStatus("1");
        return baseMapper.updateById(clientUserEntity);
    }

    /**
     * 账号管理-启用
     *
     * @param userId
     * @return
     */
    @Override
    public Integer restoreUser(String userId) {
        ClientUserEntity clientUserEntity = new ClientUserEntity();
        clientUserEntity.setId(Long.valueOf(userId));
        clientUserEntity.setStatus("0");
        return baseMapper.updateById(clientUserEntity);
    }

}

