package com.api.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.api.sys.entity.ClientTypeResidenceEntity;
import java.util.List;
import java.util.Map;


/**
 * 房源房型类型表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
public interface ClientTypeResidenceService extends IService<ClientTypeResidenceEntity> {
    /**
    * 房源房型类型表-列表
    * @return
    */
    List<ClientTypeResidenceEntity> clientTypeResidenceList();

}

