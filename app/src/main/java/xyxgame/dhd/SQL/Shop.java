package xyxgame.dhd.SQL;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    public  Integer num;
    public  list  list;

    public Shop(Integer num, xyxgame.dhd.SQL.list list) {
        this.num = num;
        this.list = list;
    }

    @Override
    public String toString() {
        return "\nShop{" +
                "num=" + num +
                ", list=" + list +
                '}';
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public xyxgame.dhd.SQL.list getList() {
        return list;
    }

    public void setList(xyxgame.dhd.SQL.list list) {
        this.list = list;
    }
}
