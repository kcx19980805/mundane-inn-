package com.common.utils.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author leibosong
 */

@Data
public class PageData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("当前列表数据数组")
    private T list;

    @ApiModelProperty("列表数据总数")
    private int total;

    /**
     * 表格数据对象
     */
    public PageData() {

    }

    /**
     * 分页
     *
     * @param t     列表数据对象
     * @param total 总记录数
     */
    public PageData(T t, int total) {
        this.list = t;
        this.total = total;
    }

    public static PageData ok(List<?> list, Integer total) {
        PageData rspData = new PageData();
        rspData.setList(list == null || list.size() == 0 ? new ArrayList<>() : list);
        rspData.setTotal(total);
        return rspData;
    }
}
