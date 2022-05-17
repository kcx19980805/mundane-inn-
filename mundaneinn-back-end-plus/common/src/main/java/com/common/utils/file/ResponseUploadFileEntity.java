package com.common.utils.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用 上传文件 返回对象
 */

@Data
public class ResponseUploadFileEntity {

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件路径")
    private String url;

}
