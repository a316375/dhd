package xyxgame.dhd.SQL;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyxgame.dhd.SQLSave.ShopSave;
import xyxgame.dhd.Save.Find;
import xyxgame.dhd.frament.NotifyDate;

public class SQLBuilder {
    Activity activity;
    private static SQLBuilder instance;

    private SQLBuilder() {
    }

    public static SQLBuilder getInstance(Activity activity) {
        if (instance == null) {
            instance = new SQLBuilder();
        }
        instance.activity = activity;
        return instance;
    }


    //  * 自带api的真删改査，返回值为0代表失败；
//  *//*
    public int  insertAPI(int s1, String s2, double s3) {
        SQL mysqldata = new SQL(activity);
        SQLiteDatabase writableDatabase = mysqldata.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQL.s1, s1);//用来删除所有数据的id
        values.put(SQL.s2, s2);//显示名字
        values.put(SQL.s3, s3);//显示价格
        values.put(SQL.s4, true);//是否展示在列表

        long i = writableDatabase.insert("shopping", null, values);
        writableDatabase.close();

        return (int) i;
    }

    /**
     * 删除 输入name的数据
     **/
    public int deleteAPI(String name) {

        SQL mysqldata = new SQL(activity);
        SQLiteDatabase writableDatabase = mysqldata.getWritableDatabase();

        // int i=writableDatabase.delete("shopping", "name=?and _id=?", new
        // String[]{"可口可乐","5"});
        int i = writableDatabase.delete("shopping", "name=?",
                new String[]{name});
        System.out.println("删除了：行数" + i);
        writableDatabase.close();
        return i;
    }


    /**
     * 修改一条ishow数据
     * * @param b 修改ishow
     * * @param name 根据名字
     */
    public void updateFromName(Boolean b, String name) {
        SQL mysqldata = new SQL(activity);
        SQLiteDatabase writableDatabase = mysqldata.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ishow", b);
        int j = writableDatabase.update("shopping", values, "name=?", new String[]{name});
//        Toast.makeText(activity, j<0?"ishow修改失败":"ishow修改成功", Toast.LENGTH_LONG).show();
        writableDatabase.close();
    }


    /**
     * 查找数据库中所有的数据
     * * @return 返回persons的lsit
     */
    public List<Shop> findAll() {
        SQL mysqldata = new SQL(activity);
        List<Shop> persons = new ArrayList();

        SQLiteDatabase db = mysqldata.getReadableDatabase();
        //Cursor cursor = db.rawQuery("select *from person", null);
        Cursor cursor = db.query("shopping", new String[]{SQL.s1, SQL.s2, SQL.s3, SQL.s4}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(SQL.s1));
            String name = cursor.getString(cursor.getColumnIndex(SQL.s2));
            double number = Double.parseDouble(cursor.getString(cursor.getColumnIndex(SQL.s3)));
            boolean ishow = cursor.getInt(cursor.getColumnIndex(SQL.s4)) > 0;
            Shop person = new Shop(id,new  list(name,number,ishow));
            if (ishow) persons.add(person);
        }
        cursor.close();
        db.close();
        return persons;
    }


    /// *//**
//  * 初始化 事务
//  *//*
    public void InitDataTransaction(NotifyDate notifyDate) {

        SQL mysqldata = new SQL(activity);
        SQLiteDatabase writableDatabase = mysqldata.getWritableDatabase();


        try {
            writableDatabase.beginTransaction();// 开启事务；

            //Cursor cursor = db.rawQuery("select *from person", null);
            Cursor cursor = writableDatabase.query("shopping", new String[]{SQL.s1, SQL.s2, SQL.s3, SQL.s4}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(SQL.s2));
                boolean ishow = cursor.getInt(cursor.getColumnIndex(SQL.s4)) > 0;
                ContentValues values = new ContentValues();
                values.put("ishow", true);
                if (!ishow) {
                    int j = writableDatabase.update("shopping", values, "name=?", new String[]{name});
                }
                ;
            }
            cursor.close();

            // 设置事务执行成功
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();// 关闭事务，同时提交，如果执行了一半，另一半未完成，事务回归，不提交
            notifyDate.Notify();
        }
        writableDatabase.close();
    }

}

