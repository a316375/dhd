package xyxgame.dhd.Save;

import android.app.Activity;

import java.util.List;

import xyxgame.dhd.MainActivity;
import xyxgame.dhd.SQL.SQLBuilder;
import xyxgame.dhd.SQL.Shop;
import xyxgame.dhd.SQLSave.SQLSaveBuilder;
import xyxgame.dhd.SQLSave.ShopSave;

public class Find {

    public static List<Shop> getAll(Activity activity){
        return  SQLBuilder.getInstance(activity).findAll();//所有的列表
    };

    public static List<ShopSave> getSave(Activity activity) {
       return  SQLSaveBuilder.getInstance(activity).findAll();  //保存的列表
    }

//    //进行删除已有数据
//    public static  List<Shop> Result(Activity activity,List<Shop> shops, List<ShopSave> shopSaves){
//        //两个循环挑除已有订单
//        for (int i=0;i<shops.size();i++){
//            String name=shops.get(i).getName();
//            for (int j=0;j<shopSaves.size();j++){
//                String name2=shopSaves.get(j).getName();
//                SQLBuilder.getInstance(activity).updateFromName(false,MainActivity.list.get(j).getName());
//              }
//            }
//
//        return shops;
//    }

}
