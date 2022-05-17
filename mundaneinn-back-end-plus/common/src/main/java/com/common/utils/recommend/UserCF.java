package com.common.utils.recommend;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kcx
 * @date 2021/3/18 13:39
 * 基于用户的CF
 */
public class UserCF {
    /**
     * 用户唯一id
     */
    public Long userId;
    /**
     * 用户所拥有的模型
     */
    public List<DataModel> dataModelList = new ArrayList<>();

    public UserCF() {

    }

    public UserCF(Long userId) {
        this.userId = userId;
    }

    public UserCF(Long userId,List<DataModel> dataModelList) {
        this.userId = userId;
        this.dataModelList=dataModelList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<DataModel> getDataModelList() {
        return dataModelList;
    }

    public void setDataModelList(List<DataModel> dataModelList) {
        this.dataModelList = dataModelList;
    }

    @Override
    public String toString() {
        return "UserCF{" +
                "userId=" + userId +
                ", dataModelList=" + dataModelList +
                '}';
    }
}

