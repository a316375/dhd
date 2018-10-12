package xyxgame.dhd.SQL;

import java.util.Comparator;

public class listSort implements Comparator<list> {

    @Override
    public int compare(list obj0, list obj1) {
        list s0 = (list) obj0;
        list s1 = (list) obj1;


        //首先比较价格，如果价格相同，则比较名字
        int flag = Double.compare(s0.price, s1.price);
        if (flag == 0) {
            return s0.name.compareTo(s1.name);
        } else {
            return flag;
        }
    }


}


