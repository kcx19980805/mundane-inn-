package com.admin.config.excel;

import com.admin.sys.entity.ClientUserImportEntity;
import com.admin.sys.service.ClientUserService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.common.exception.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理-excel导入监听器
 * 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 **/
public class ClientUserExcelImportListener extends AnalysisEventListener<ClientUserImportEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientUserImportEntity.class);

    /**
     * 行
     */
    int row = 1;

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;

    List<ClientUserImportEntity> list = new ArrayList<>();

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private ClientUserService clientUserService;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     * @param clientUserService
     */
    public ClientUserExcelImportListener(ClientUserService clientUserService) {
        this.clientUserService = clientUserService;
    }

    /**
     * 这个每一条数据解析都会来调用
     * @param clientUserImportEntity
     * @param analysisContext
     */
    @Override
    public void invoke(ClientUserImportEntity clientUserImportEntity, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(clientUserImportEntity));
        row++;
        if (!StringUtils.isNotBlank(clientUserImportEntity.getAccount())) {
            throw new CustomException("第" + row + "行账号不能为空");
        }
        if (!StringUtils.isNotBlank(clientUserImportEntity.getPhone())) {
            throw new CustomException("第" + row + "行手机号不能为空");
        }
        if (!StringUtils.isNotBlank(clientUserImportEntity.getName())) {
            throw new CustomException("第" + row + "行姓名不能为空");
        }
        if (!StringUtils.isNotBlank(clientUserImportEntity.getSex())) {
            throw new CustomException("第" + row + "行性别不能为空");
        }
        if (!StringUtils.isNotBlank(clientUserImportEntity.getEmail())) {
            throw new CustomException("第" + row + "行邮箱不能为空");
        }
        if (!clientUserImportEntity.getPhone().matches("((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)")) {
            throw new CustomException("第" + row + "行手机号格式不正确");
        }
        if (!clientUserImportEntity.getSex().matches("^[0-1]$")) {
            throw new CustomException("第" + row + "行性别格式不正确");
        }
        if (!clientUserImportEntity.getEmail().matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            throw new CustomException("第" + row + "行邮箱格式不正确");
        }
        list.add(clientUserImportEntity);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 会来调用
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        clientUserService.saveClientUserExcelImport(list);
        LOGGER.info("存储数据库成功！");
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     * @param exception
     * @param context
     * @throws Exception ExcelAnalysisException
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        LOGGER.info("解析失败:" + context + " 异常:" + exception.getMessage());
        super.onException(exception, context);

        //如果是某一个单元格的转换异常 能获取到具体行号
        //如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            throw new CustomException("第 " + excelDataConvertException.getRowIndex() + " 行， " + excelDataConvertException.getColumnIndex() + " 列解析异常");
        }
        //若不抛异常 则会继续解析下一行
        throw exception;
    }
}
