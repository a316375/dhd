package xyxgame.dhd.SQL;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
;

public class SQL extends SQLiteOpenHelper {

    public static String s1="num",s2="name",s3="price",s4="ishow";

    public static String Save_table="shopsave",s1_Save="num",s2_Save="name",s3_Save="nums";

    // 构造函数
    public SQL(Context context) {
        super(context, "ShoppingSQL.db", null, 1);
        // TODO 自动生成的构造函数存根
    }

    // 数据库创建时
    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建数据库第一横内容
        System.out.println("地址是：" + db.getPath().toString());
        System.out.println("地址是：...");
        //数据库表头num name price
        db.execSQL("create table shopping (_id integer primary key autoincrement,num integer(6) ,name char(20) unique,price double(3,1),ishow boolean(1))");
        //数据库表头num name price
        db.execSQL("create table shopsave (_id integer primary key autoincrement,num integer(6) ,name char(20) unique,nums int(5))");

    }

    // 数据库升级时
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 自动生成的方法存根

    }

}



