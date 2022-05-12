package com.common.utils.recommend;

/**
 * @author kcx
 * @date 2021/3/18 13:37
 * 数据模型
 */
public class DataModel implements Comparable<DataModel> {
    /**
     * 数据系数
     */
    public Long dataId;

    public DataModel(Long dataId) {
        this.dataId = dataId;
    }

    @Override
    public int compareTo(DataModel o) {
        return dataId > o.dataId ? -1 : 1;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "dataId=" + dataId +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof DataModel)){
            return false;
        }
        DataModel temp = (DataModel) obj;
        if(this.getDataId()==temp.getDataId()){
            return true;
        }
        return false;
    }

}

