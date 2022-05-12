package com.api.sys.controller;

import com.api.config.file.FileConfig;
import com.common.utils.Result;
import com.common.utils.file.FileUploadUtils;
import com.common.utils.file.FileUtils;
import com.common.utils.file.ResponseUploadFileEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用请求处理
 */
@Api(tags = "下载/上传")
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private FileConfig fileConfig;

    /**
     * 上传后的文件-下载请求
     *
     * @param fileName 文件名称
     */
    @ApiOperation("上传后的文件下载请求")
    @GetMapping("/download")
    public void fileDownload(String fileName,HttpServletResponse response, HttpServletRequest request) {
        try {
            //String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = fileConfig.getUploadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 本地资源通用下载
     */
    @ApiOperation("本地资源(静态资源)通用下载")
    @GetMapping("/download/resource")
    public void resourceDownload(String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            // 本地资源路径  downloadPath配置的文件路径
            String filePath = fileConfig.getDownloadPath() + fileName;
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通用上传请求
     * 支持多文件上传
     */
    @ApiOperation(value = "通用上传请求", response = ResponseUploadFileEntity.class)
    @PostMapping("/upload")
    public Result uploadFile(HttpServletRequest request) throws Exception {
        try {
            String uploadPath = fileConfig.getUploadPath();
            System.out.println("uploadPath==" + uploadPath);
            List<ResponseUploadFileEntity> list = new ArrayList<>();
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            for (MultipartFile file : files) {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(uploadPath, file);
                System.out.println("fileName==" + fileName);
                StringBuffer requestURL = request.getRequestURL();
                System.out.println("requestURL==" + requestURL);
                String contextPath = request.getServletContext().getContextPath();
                System.out.println("contextPath==" + contextPath);
                String path = requestURL.delete(requestURL.length() - request.getRequestURI().length(), requestURL.length()).append(contextPath).toString();
                System.out.println("path==" + path);
                String url = path + fileName;
                System.out.println(url);
                ResponseUploadFileEntity responseUploadFileEntity = new ResponseUploadFileEntity();
                responseUploadFileEntity.setFileName(fileName);
                responseUploadFileEntity.setUrl(url);
                list.add(responseUploadFileEntity);
            }
            return Result.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("文件上传失败", e.getMessage());
        }
    }


}
