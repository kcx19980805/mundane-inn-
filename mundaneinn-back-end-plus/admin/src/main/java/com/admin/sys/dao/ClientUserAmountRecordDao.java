package com.admin.sys.dao;

import com.admin.sys.entity.ClientUserAmountRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 用户余额变更记录表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Mapper
public interface ClientUserAmountRecordDao extends BaseMapper<ClientUserAmountRecordEntity> {

}
