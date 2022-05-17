package com.admin.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 系统页面视图
 */
@Controller
public class SysPageController {


    @GetMapping("sys/{module}/{url}.html")
    public String module(@PathVariable("module") String module, @PathVariable("url") String url) {
        return "sys/" + module + "/" + url;
    }

    @GetMapping("sys/{url}.html")
    public String module2(@PathVariable("url") String url) {
        return "sys/" + url;
    }

    @GetMapping(value = {"/", "index.html"})
    public String index() {
        return "index";
    }

    /**
     * 默认跳转为平台登录页
     *
     * @return
     */
    @GetMapping(value = {"/login", "/admin", "login.html"})
    public String adminLogin() {
        //根据域名 重定向登录页
        //String sn = request.getServerName();
        return "login";
    }

    @GetMapping("main.html")
    public String main() {
        return "main";
    }

    @GetMapping("404.html")
    public String notFound() {
        return "404";
    }

}
