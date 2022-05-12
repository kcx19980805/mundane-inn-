

package com.admin.config.webmvc;

import com.admin.config.file.FileConfig;
import com.common.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Autowired
    private FileConfig fileConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置一个静态文件的路径 否则css和js无法使用，虽然默认的静态资源是放在static下，但是没有配置里面的文件夹
        //registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //拦截文件上传下载路径并解析为配置路径
        registry.addResourceHandler(Constants.RESOURCE_UPLOAD + "/**").addResourceLocations("file:" + fileConfig.getUploadPath());
        registry.addResourceHandler(Constants.RESOURCE_DOWNLOAD + "/**").addResourceLocations("file:" + fileConfig.getDownloadPath());
    }
}