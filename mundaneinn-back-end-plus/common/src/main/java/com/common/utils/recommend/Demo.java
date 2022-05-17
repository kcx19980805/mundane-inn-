package com.common.utils.recommend;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 计算推荐
 * @date 2021/3/18 14:07
 * 测试
 */
public class Demo {
    public static void main(String[] args) {
        DataModel move1=new DataModel( 1L);
        DataModel move2=new DataModel( 3L);
        DataModel move3=new DataModel(5L);
        DataModel move4=new DataModel( 30L);
        DataModel move5=new DataModel( 31L);
        DataModel move6=new DataModel( 45L);
        DataModel move7=new DataModel( 50L);
        DataModel move8=new DataModel(66L);
        DataModel move9=new DataModel( 70L);
        DataModel move10=new DataModel( 80L);
        DataModel move11=new DataModel( 100L);
        DataModel move12=new DataModel( 104L);
        DataModel move13=new DataModel(110L);
        DataModel move14=new DataModel( 115L);
        DataModel move15=new DataModel( 120L);
        DataModel move16=new DataModel(151L);

        List<UserCF> others = new ArrayList<>();

        List<DataModel> list1 = new ArrayList<>();
        list1.add(move1);
        list1.add(move2);
        list1.add(move3);
        list1.add(move4);
        list1.add(move5);
        list1.add(move6);
        list1.add(move7);
        UserCF userCF1 = new UserCF(1001L,list1);

        //5
        List<DataModel> list2 = new ArrayList<>();
        list2.add(move1);
        list2.add(move2);
        list2.add(move3);
        list2.add(move4);
        list2.add(move5);
        list2.add(move8);
        list2.add(move9);
        UserCF userCF2 = new UserCF(1002L,list2);
        others.add(userCF2);

        //4
        List<DataModel> list3 = new ArrayList<>();
        list3.add(move1);
        list3.add(move2);
        list3.add(move3);
        list3.add(move4);
        list3.add(move8);
        list3.add(move10);
        UserCF userCF3 = new UserCF(1003L,list3);
        others.add(userCF3);

        //3
        List<DataModel> list4 = new ArrayList<>();
        list4.add(move1);
        list4.add(move3);
        list4.add(move5);
        list4.add(move8);
        list4.add(move11);
        UserCF userCF4 = new UserCF(1004L,list4);
        others.add(userCF4);


        //1
        List<DataModel> list5 = new ArrayList<>();
        list5.add(move1);
        list5.add(move8);
        list5.add(move9);
        list5.add(move10);
        list5.add(move11);
        UserCF userCF5 = new UserCF(1005L,list5);
        others.add(userCF5);

        //3
        List<DataModel> list6 = new ArrayList<>();
        list6.add(move3);
        list6.add(move4);
        list6.add(move5);
        list6.add(move9);
        list6.add(move10);
        list6.add(move11);
        list6.add(move12);
        list6.add(move13);
        list6.add(move14);
        list6.add(move15);
        list6.add(move16);
        UserCF userCF6 = new UserCF(1006L,list6);
        others.add(userCF6);

        //0
        List<DataModel> list7 = new ArrayList<>();
        list7.add(move16);
        UserCF userCF7 = new UserCF(1007L,list7);
        others.add(userCF7);

        Recommend recommend = new Recommend();
        List<DataModel> res = recommend.results(userCF1, others);
        System.out.println("----------推荐结果如下-------------");
        for (DataModel dataModel : res) {
            System.out.println("数据id："+ dataModel.dataId);
        }
    }
}

