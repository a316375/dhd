package xyxgame.dhd;


import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mob.MobSDK;

import java.util.List;

import xyxgame.dhd.SQL.list;
import xyxgame.dhd.frament.First.First;
import xyxgame.dhd.frament.NotifyDate;
import xyxgame.dhd.frament.Second.CheckService;
import xyxgame.dhd.frament.Second.MyService;
import xyxgame.dhd.frament.Second.Second;
import xyxgame.dhd.frament.Three.Three;


public class MainActivity extends AppCompatActivity implements NotifyDate {

    public static int selectPoint = 0, topPoint = 0;//First默认显示第一页
    public static int selectPoints[] = {0, 0, 0, 0, 0};//切换时候保存被选中的选项,默认五个选项五个0
    public static int i = 0;//切换时候保存被选中的选项,默认五个选项五个0

//    public static List<Shop> lists;//所有列表

//    public static ServiceConnection serviceConnection=new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//        }
//    };

    //底部按钮监听事件
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fg, new First()).commit();
                    ;//r.id代表activity_main布局里的控件id，是一个layout控件，使用a这个类替换它；
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fg, new Second()).commit();
                    ;//r.id代表activity_main布局里的控件id，是一个layout控件，使用a这个类替换它；
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fg, new Three()).commit();
                    ;//r.id代表activity_main布局里的控件id，是一个layout控件，使用a这个类替换它；
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobSDK.init(this);//mob分享初始化

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//这2行是设置返回按钮的
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //点击清单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item:


                        break;
                }
                return false;
            }
        });


        //中间frament
        FragmentManager fm = getSupportFragmentManager();// 拿到管理器
        // 开启事务；
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.fg, new First(), "ONE");//r.id代表activity_main布局里的控件id，是一个layout控件，使用a这个类替换它；
        transaction.commit();
        Fragment tag1 = fm.findFragmentByTag("ONE");


        //底部按钮
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    int index = 0;


    @Override
    protected void onRestart() {
        super.onRestart();
        boolean b = CheckService.isServiceExisted(getApplicationContext(), "xyxgame.dhd.frament.Second.MyService");

        Log.d("--", index % 2 + "onRestart: " + b);

        if (b) {
           // unbindService(serviceConnection);
            Log.d("----", index % 2 + "stopService: " + b);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar, menu);//加载menu布局
        return true;
    }


    //数据库变化时候刷新数据
    @Override
    public void Notify() {

        Toast.makeText(getApplicationContext(), "数据要刷新了", Toast.LENGTH_LONG).show();

    }

    @Override
    public void removeList(list list) {

    }
}
