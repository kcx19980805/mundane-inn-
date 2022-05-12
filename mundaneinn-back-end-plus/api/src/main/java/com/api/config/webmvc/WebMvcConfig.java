package com.api.config.webmvc;

import com.api.config.file.FileConfig;
import com.api.config.jwt.JWTInterceptor;
import com.common.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

/**
 * MVC配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JWTInterceptor jwtInterceptor;

    @Autowired
    private FileConfig fileConfig;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 所有接口都需token验证,排除不需要的
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**").excludePathPatterns(outUrls());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler(Constants.RESOURCE_UPLOAD + "/**").addResourceLocations("file:" + fileConfig.getUploadPath());
        registry.addResourceHandler(Constants.RESOURCE_DOWNLOAD + "/**").addResourceLocations("file:" + fileConfig.getDownloadPath());
    }

    /**
     * 设置跨域访问
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                //跨域允许时间
                .maxAge(7200);
    }

    /**
     * 所有排除不需要token的urls
     * @return
     */
    public static ArrayList outUrls() {
        ArrayList arrayList = new ArrayList();

        arrayList.add(Constants.RESOURCE_UPLOAD + "/**");
        arrayList.add(Constants.RESOURCE_DOWNLOAD + "/**");
        arrayList.add("/v2/api-docs");
        arrayList.add("/statics/**");
        arrayList.add("/swagger-ui.html");
        arrayList.add("/doc.html");
        arrayList.add("/swagger-resources/**");
        arrayList.add("/webjars/**");
        arrayList.add("/**.html");
        arrayList.add("/**.js");
        arrayList.add("/**.css");

        //登录
        arrayList.add("/api/login");
        //发送短信验证码
        arrayList.add("/api/sendSms");
        //城市列表
        arrayList.add("/api/sysAreaList");
        //地区分类列表
        arrayList.add("/api/clientAreaTypeList");
        //用户常用地区列表
        arrayList.add("/api/clientAreaPopularList");
        //房源配套类型列表
        arrayList.add("/api/clientTypeMatchList");
        //房源房型类型列表
        arrayList.add("/api/clientTypeResidenceList");
/*        //房源列表
        arrayList.add("/api/clientHouseList");
        //房源单个信息
        arrayList.add("/api/clientHouseInfo");
        //评论列表
        arrayList.add("/api/houseCommentList");*/
        //支付宝支付结果回调
        arrayList.add("/api/alipayCallBack");

        return arrayList;
    }
}
