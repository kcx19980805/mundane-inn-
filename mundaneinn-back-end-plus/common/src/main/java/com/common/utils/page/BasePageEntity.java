package com.common.utils.page;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * 分页类
 */
public class BasePageEntity {

    @ApiModelProperty("当前记录起始索引(从1开始)")
    private Integer page;

    @ApiModelProperty(value = "用于sql查询时使用", hidden = true)
    private Integer sqlPage;

    @ApiModelProperty("每页显示记录数(默认查询全部数据为空)")
    private Integer limit;

    @ApiModelProperty(value = "序号排序(默认为空 asc升序 desc降序)",hidden = true)
    private String order;


    public Integer getSqlPage() {
        return sqlPage;
    }

    public void setSqlPage(Integer sqlPage) {
        this.sqlPage = sqlPage;
    }


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        // 前端传值page从1开始 ,但sql是从0开始
        if (page == null) {
            this.page = 0;
            this.sqlPage = 0;
        } else {
            if (page >= 1) {
                page = page - 1;
            } else {
                page = 0;
            }
            this.page = page;
            this.sqlPage = page * limit;
        }
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit == null ? 10 : limit;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = StringUtils.isBlank(order) ? "asc" : order;
    }
}
