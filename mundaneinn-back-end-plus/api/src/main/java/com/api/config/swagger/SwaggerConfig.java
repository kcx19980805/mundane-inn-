package com.api.config.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Lists;
import com.common.constant.Constants;
import com.common.constant.HttpStatus;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    /**
     * Swagger实例Bean是Docket，所以通过配置Docket实例来配置Swaggger,如果想设置多个分组就多个Docket
     * @param environment
     * @return
     */
    @Bean
    public Docket defaultDocket(Environment environment) {
        //设置要显示的Swagger环境
        Profiles profiles = Profiles.of("dev","test","prod");
        //通过environment.acceptsProfiles判断是否处在自己设定的环境当中
        boolean flag = environment.acceptsProfiles(profiles);
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(HttpStatus.SUCCESS).message("成功").responseModel(new ModelRef("ApiSuccess")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(HttpStatus.UNAUTHORIZED).message("授权过期(登录过期)").responseModel(new ModelRef("ApiError")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(HttpStatus.ERROR).message("通用错误提示").responseModel(new ModelRef("ApiError")).build());
        return new Docket(DocumentationType.SWAGGER_2)
                //关联配置文档信息
                .apiInfo(groupApiInfo())
                .groupName("默认分组")
                //enable是否开启文档
                .enable(flag)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                //扫描指定包中的swagger注解，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //扫描指定包中的swagger注解
                //.apis(RequestHandlerSelectors.basePackage("com.api.sys.controller"))
                // 配置如何通过path过滤 PathSelectors.any()任何路径都满足这个条件，即不过滤，PathSelectors.ant("/kcx/**")扫描指定路径的注解
                .paths(PathSelectors.any())
                .build()
                /* 设置安全模式，swagger可以设置访问token */
                .securitySchemes(security())
                .securityContexts(Lists.newArrayList(
                        securityContext()
                ));
    }

    /**
     * 配置文档信息
     * @return
     */
    private ApiInfo groupApiInfo() {
        return new ApiInfoBuilder()
                .title("APP接口文档")
                .description("api文档")
                .termsOfServiceUrl("http//localhost:8921/doc.html")
                .version("1.0")
                .build();
    }

    /**
     * 安全模式，这里指定 请求头token传递
     */
    private List<ApiKey> security() {
        return newArrayList(
                new ApiKey(Constants.TOKEN, Constants.TOKEN, "header")
        );
    }

    /**
     * 安全模式上下文
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    /**
     * 安全模式的默认认证
     */
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference(Constants.TOKEN, authorizationScopes));
    }

}
