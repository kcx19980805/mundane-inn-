package com.common.utils.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 手动分页类
 * @author kcx
 */
public class PageUtils {
    /**
     * 得到分页数据
     * @param data
     * @param limit 每页数据条数
     * @param pageNo 页码从0开始
     * @param <T>
     * @return
     */
    public static <T> List<T> getPageData(List<T> data, int limit, int pageNo){
        //起始截取数据位置
        int startNum = (pageNo)* limit+1 ;
        //参数不合法
        if(pageNo < 0 || limit<=0){
            return new ArrayList<>();
        }
        List<T> res = new ArrayList<>();
        int rum = data.size() - startNum;
        //正好是最后一个
        if(rum == 0){
            int index = data.size() -1;
            res.add(data.get(index));
        }else if(rum > 0){
            int end=0;
            //剩下的数据够1页，返回整页的数据
            if(rum / limit >= 1){
                end=startNum + limit;
            }//不够一页，返回剩下数据
            else if(rum / limit == 0){
                end=data.size();
            }
            for(int i=startNum;i<end;i++){
                res.add(data.get(i-1));
            }
        }
        return res;
    }
}
