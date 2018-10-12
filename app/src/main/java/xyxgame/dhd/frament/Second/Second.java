package xyxgame.dhd.frament.Second;


import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mob.MobSDK;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;
import xyxgame.dhd.DisplayUtil;
import xyxgame.dhd.MainActivity;
import xyxgame.dhd.R;
import xyxgame.dhd.SQL.SQLBuilder;
import xyxgame.dhd.SQL.Shop;
import xyxgame.dhd.SQLSave.SQLSaveBuilder;
import xyxgame.dhd.SQLSave.ShopSave;
import xyxgame.dhd.Save.Find;

public class Second extends Fragment {

    private String send[] = {"批发部", "烟草部"};
    List<ShopSave> shopSaves;//所有的shop

    ClickListener listener;

    List<ShopSave> rightShop;//烟草部集合
    List<ShopSave> leftShop;//批发部集合
    private ReadyAdatper adatper;
    private RecyclerView recyclerView;
    private ScrollIndicatorView scrollIndicatorView;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(getContext(), "分享失败", Toast.LENGTH_LONG).show();
                    ;
                    break;
                case 1:
                    Toast.makeText(getContext(), "分享取消了", Toast.LENGTH_LONG).show();
                    ;
                    break;
                case 2:
                    Toast.makeText(getContext(), "分享成功", Toast.LENGTH_LONG).show();
                    ;
                    break;
            }

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getContext(), R.layout.fragment_second, null);


        shopSaves = Find.getSave(getActivity());
        rightShop = new ArrayList<>();
        leftShop = new ArrayList<>();
        for (int i = 0; i < shopSaves.size(); i++) {
            if (shopSaves.get(i).getNum() == 2) {
                rightShop.add(shopSaves.get(i));//得到烟草的集合
            }
        }
        shopSaves.removeAll(rightShop);//除去烟草部
        leftShop = shopSaves;

        //头部菜单
        scrollIndicatorView = (ScrollIndicatorView) inflate.findViewById(R.id.singleTab_scrollIndicatorView);
        set(scrollIndicatorView, send.length);

        //recyclerView菜单
        recyclerView = (RecyclerView) inflate.findViewById(R.id.ready_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adatper = new ReadyAdatper(R.layout.item_ready, scrollIndicatorView.getCurrentItem() == 0 ? leftShop : rightShop);//默认加载排除后的数据
        recyclerView.setAdapter(adatper);

        listener = new ClickListener(getActivity(), leftShop);
        adatper.setOnItemChildClickListener(listener);


        inflate.findViewById(R.id.shop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Indtop = scrollIndicatorView.getCurrentItem();
                List<ShopSave> shopSaves = new ArrayList<>();
                shopSaves = Indtop == 0 ? leftShop : rightShop;
                String s = Indtop == 0 ? "件" : "条";
                String string = new String();
                for (int i = 0; i < shopSaves.size(); i++) {
                    string += (i + 1) + "-" + shopSaves.get(i).getName() + " X " + shopSaves.get(i).getNums() + s + "\n";
                }
//                for (int i = 0; i < 1000; i++) {
//                    string += "测试" + i + "个\n";
//                }
//                Log.d("--string.length--", "onClick: " + string.getBytes().length);
                ;

                Show(string);

            }
        });
        inflate.findViewById(R.id.shopclear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return inflate;
    }


    //-------------------

    private void set(final Indicator indicator, int count) {

        indicator.setAdapter(new Second.MyAdapter(count));

        float unSelectSize = 16;
        float selectSize = unSelectSize * 1.2f;
        int selectColor = Color.RED;
        int unSelectColor = getResources().getColor(R.color.tab_top_text_1);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        indicator.setScrollBar(new ColorBar(getContext(), Color.RED, 10));
        indicator.setCurrentItem(0, false);


        indicator.setOnItemSelectListener(new Indicator.OnItemSelectedListener() {

            @Override
            public void onItemSelected(View selectItemView, int select, int preSelect) {


                if (select == 0) {
                    adatper = new ReadyAdatper(R.layout.item_ready, leftShop);
                    adatper.notifyDataSetChanged();
                    recyclerView.setAdapter(adatper);
                    listener = new ClickListener(getActivity(), leftShop);
                } else {
                    adatper = new ReadyAdatper(R.layout.item_ready, rightShop);
                    adatper.notifyDataSetChanged();
                    recyclerView.setAdapter(adatper);
                    listener = new ClickListener(getActivity(), rightShop);
                }
                adatper.setOnItemChildClickListener(listener);


            }
        });


    }


    private class MyAdapter extends Indicator.IndicatorAdapter {
        private final int count;

        public MyAdapter(int count) {
            super();
            this.count = count;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.tab_top, parent, false);
            }
            TextView textView = (TextView) convertView;
            //用了固定宽度可以避免TextView文字大小变化，tab宽度变化导致tab抖动现象
            textView.setWidth(DisplayUtil.dipToPix(getContext(), 50));
            textView.setText(send[position]);

            return convertView;
        }

    }

    private void Show(String string) {
        //https://blog.csdn.net/ruanjianyujian/article/details/21379625
        Wechat.ShareParams wxsp = new Wechat.ShareParams();
        wxsp.setText(string);  //分享的内容
        wxsp.setShareType(Platform.SHARE_TEXT);   //分享的类别
        //sp.imagePath = “/mnt/sdcard/测试分享的图片.jpg”;  //要分享的图片路径
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                handler.sendEmptyMessage(1);
            }
        }); // 设置分享事件回调
        // 执行图文分享
        wechat.share(wxsp);


    }


}

