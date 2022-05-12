package com.admin.sys.controller;

import com.admin.sys.requestEntity.RequestClientUserListEntity;
import com.admin.sys.requestEntity.RequestClientUserUpdateEntity;
import com.admin.sys.requestEntity.RequestHouseListEntity;
import com.admin.sys.requestEntity.RequestHouseUpdateEntity;
import com.admin.sys.responseEntity.ResponseClientUserListEntity;
import com.admin.sys.responseEntity.ResponseHouseListEntity;
import com.admin.sys.service.ClientHouseService;
import com.common.utils.Result;
import com.common.utils.page.PageData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 房源管理
 * @author Administrator
 */
@RestController
@RequestMapping("sys/clientHouse")
public class ClientHouseController {

    @Autowired
    private ClientHouseService clientHouseService;

    /**
     * 房源管理-列表-查询
     */
    @GetMapping("/list")
    @RequiresPermissions(value = "sys:userhouse:list")
    public Result<PageData<ResponseHouseListEntity>> clientHouseList(@Validated RequestHouseListEntity req) {
        System.out.println(req);
        return Result.ok(clientHouseService.clientHouseList(req));
    }

    /**
     * 房源管理-审核
     * @param req
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions(value = "sys:userhouse:update")
    public Result updateClientHouse(@Validated @RequestBody RequestHouseUpdateEntity req){
        return  Result.toRow(clientHouseService.updateClientHouse(req));
    }

}
