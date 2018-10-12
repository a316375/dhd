package xyxgame.dhd.SQLSave;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyxgame.dhd.SQL.SQL;
import xyxgame.dhd.SQL.Shop;

public class SQLSaveBuilder {
    Activity activity;
    private static SQLSaveBuilder instance;

    private SQLSaveBuilder() {
    }

    public static SQLSaveBuilder getInstance(Activity activity) {
        if (instance == null) {
            instance = new SQLSaveBuilder();
        }
        instance.activity = activity;
        return instance;
    }


    //  * 自带api的真删改査，返回值为0代表失败；
//  **添加//*
    public int  insertAPI(ShopSave save) {
        SQL mysqldata = new SQL(activity);
        SQLiteDatabase writableDatabase = mysqldata.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQL.s1_Save, save.num);//类别
        values.put(SQL.s2_Save, save.name);//显示名字
        values.put(SQL.s3_Save, save.nums);//显示数量

        long i = writableDatabase.insert(SQL.Save_table, null, values);
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
        int i = writableDatabase.delete(SQL.Save_table, "name=?",
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
    public void updateFromName(int nums, String name) {
        SQL mysqldata = new SQL(activity);
        SQLiteDatabase writableDatabase = mysqldata.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nums", nums);//修改nums
        int j = writableDatabase.update(SQL.Save_table, values, "name=?", new String[]{name});//根据name
        writableDatabase.close();
    }


    /**
     * 查找数据库中所有的数据
     * * @return 返回persons的lsit
     */
    public List<ShopSave> findAll() {
        SQL mysqldata = new SQL(activity);
        List<ShopSave> persons = new ArrayList();
        SQLiteDatabase db = mysqldata.getReadableDatabase();
        //Cursor cursor = db.rawQuery("select *from person", null);
        Cursor cursor = db.query(SQL.Save_table, new String[]{SQL.s1_Save, SQL.s2_Save, SQL.s3_Save }, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(SQL.s1_Save));
            String name = cursor.getString(cursor.getColumnIndex(SQL.s2_Save));
            int number = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQL.s3_Save)));
            ShopSave person = new ShopSave(id, name, number);
             persons.add(person);
        }
        cursor.close();
        db.close();
        return persons;
    }


    /// *//**
//  * 初始化 事务,这里是移除所有
//  *//*
    public void InitDataTransaction() {


        SQL mysqldata = new SQL(activity);
        List<ShopSave> persons = findAll();
        SQLiteDatabase writableDatabase = mysqldata.getWritableDatabase();

        try {
            writableDatabase.beginTransaction();// 开启事务；

            //Cursor cursor = db.rawQuery("select *from person", null);
            Cursor cursor = writableDatabase.query(SQL.Save_table, new String[]{SQL.s1_Save, SQL.s2_Save, SQL.s3_Save}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                // int i=writableDatabase.delete("shopping", "name=?and _id=?", new
                // String[]{"可口可乐","5"});
                int i = writableDatabase.delete(SQL.Save_table, "name=?",
                        new String[]{persons.get(cursor.getPosition()).getName()});
                ;
            }
            cursor.close();

            // 设置事务执行成功
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();// 关闭事务，同时提交，如果执行了一半，另一半未完成，事务回归，不提交
        }
        writableDatabase.close();
    }

}

