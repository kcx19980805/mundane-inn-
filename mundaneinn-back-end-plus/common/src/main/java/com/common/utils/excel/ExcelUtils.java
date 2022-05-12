package com.common.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.common.utils.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author: leibosong
 * @data: 2020/12/11 22:00
 */
public class ExcelUtils {

    /**
     * 导出 通过实体
     *
     * @param fileName   文件名
     * @param entityList 导出的数据
     * @return
     */
    public static void exportByEntity(String fileName, Object object, List<?> entityList, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流 .autoCloseStream(Boolean.FALSE)
            EasyExcel.write(
                    response.getOutputStream(), object.getClass())
                    .head(object.getClass())
                    .registerWriteHandler(new CustomCellWriteHandler()).sheet("sheet1")
                    .doWrite(entityList);
        } catch (Exception e) {
            e.printStackTrace();
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(Result.error("excel文件导出失败"));
        }
    }
}
