package com.api.config.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取文件上传/下载相关配置
 *
 * @author
 */
@Component
@ConfigurationProperties(prefix = "file")
@Data
public class FileConfig {

    /**
     * 文件上传路径
     */
    private String uploadPath;

    /**
     * 文件下载路径
     */
    private String downloadPath;

    /**
     * 单个文件上传大小
     */
    private long uploadMaxSize;



}
