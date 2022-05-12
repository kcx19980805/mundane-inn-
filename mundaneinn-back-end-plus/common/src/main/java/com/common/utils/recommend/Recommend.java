package com.common.utils.recommend;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author kcx
 * @date 2021/3/18 13:42
 * 基于协同过滤算法计算推荐
 */
public class Recommend<T> {

    /**
     * 推荐数据量
     */
    public static final int RECOMMENDER_NUM  = 100;

    /**
     * 计算出协同推荐算法的结果
     * @param currentUser
     * @param otherUser
     * @return
     */
    public List<DataModel> results(UserCF currentUser, List<UserCF> otherUser) {
        List<DataModel> result=new ArrayList<>();
        //找到最近邻
        Map<Double, UserCF> distances = computeNearestNeighbor(currentUser, otherUser);
        //获取Map中的value集合
        Iterator<UserCF> iterator = distances.values().iterator();
        List<DataModel> currentDataModel=currentUser.dataModelList;
        List<DataModel> neighborDataModel;
        while(iterator.hasNext()){
            UserCF neighbor = iterator.next();
            System.out.println("最近邻用户id -> " + neighbor.getUserId());
            //找到最近邻有但当前用户没有的数据进行推荐
            neighborDataModel=neighbor.dataModelList;
            for (int i = 0; i < neighborDataModel.size(); i++) {
                DataModel dataModel = neighborDataModel.get(i);
                if(!currentDataModel.contains(dataModel)){
                    result.add(dataModel);
                }
                if(result.size()>=RECOMMENDER_NUM){
                    return result;
                }
            }
        }
        return result;
    }


    /**
     * 计算当前用户和其它用户的距离并排序
     * @param currentUser 当前用户
     * @param otherUser 其它用户
     * @return
     */
    private Map<Double, UserCF> computeNearestNeighbor(UserCF currentUser, List<UserCF> otherUser) {
        //TreeMap自动排序，不重复
        Map<Double, UserCF> distances = new TreeMap<>(Comparator.reverseOrder());
        //计算其它用户与当前用户的距离
        for (int i = 0; i < otherUser.size(); i++) {
            UserCF userCF = otherUser.get(i);
            double distance = calculatePearsonFormula(currentUser.dataModelList, userCF.dataModelList);
            System.out.println("用户 " + userCF.getUserId() + "距离" +distance);
            //去掉不相关的
            if(distance != Double.NaN && distance != Double.NEGATIVE_INFINITY && distance != Double.POSITIVE_INFINITY  && distance>0.0){
                distances.put(distance, userCF);
            }
        }
        return distances;
    }

    /**
     * 计算皮尔森(pearson)相关系数
     * @param X 向量1
     * @param Y 向量2
     * @return
     */
    private double calculatePearsonFormula(List<DataModel> X,List<DataModel> Y){
        //避免循环越界
        int n;
        if(X.size()>Y.size()){
            n=Y.size();
        }else{
            n=X.size();
        }
        //计算x,y的期望
        double Ex= X.stream().mapToDouble(x->x.dataId).sum();
        double Ey= Y.stream().mapToDouble(y->y.dataId).sum();
        //计算x平方、y平方的期望
        double Ex2=X.stream().mapToDouble(x->Math.pow(x.dataId,2)).sum();
        double Ey2=Y.stream().mapToDouble(y->Math.pow(y.dataId,2)).sum();
        //计算x*y的期望
        double Exy= IntStream.range(0,n).mapToDouble(i->X.get(i).dataId*Y.get(i).dataId).sum();
        //公式的分子
        double numerator=Exy-(Ex*Ey/n);
        //公式的分母
        double denominator=Math.sqrt((Ex2-Math.pow(Ex,2)/n)*(Ey2-Math.pow(Ey,2)/n));
        System.out.println("numerator="+numerator);
        System.out.println("denominator="+denominator);
        return numerator/denominator;
    }
}

