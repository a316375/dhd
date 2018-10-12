package xyxgame.dhd.frament.First;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import xyxgame.dhd.MainActivity;
import xyxgame.dhd.R;
import xyxgame.dhd.SQL.SQLBuilder;
import xyxgame.dhd.SQL.Shop;
import xyxgame.dhd.SQL.list;
import xyxgame.dhd.SQL.listSort;
import xyxgame.dhd.SQLSave.SQLSaveBuilder;
import xyxgame.dhd.SQLSave.ShopSave;
import xyxgame.dhd.Save.Find;

/**
 * Created by LuckyJayce on 2016/8/11.
 */
public class PriceFragment extends LazyFragment {

    public static final String INTENT_INT_POSITION = "intent_int_position";

    List<Shop> shoplists;//获取数据
    public List<list> lists;
    private List<list> save;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        shoplists = Find.getAll(getActivity());

        Log.d("pppp", "onCreateViewLazy  " + this + " " + savedInstanceState);

        int position = getArguments().getInt(INTENT_INT_POSITION);
        MainActivity.selectPoint = position;//设置被选中的item

        setContentView(R.layout.fragment_tabmain_item);


        final TextView textView = (TextView) findViewById(R.id.fragment_mainTab_item_textView);
        textView.setText(MainActivity.topPoint + " " + position + " 界面加载完毕\n");
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.fragment_mainTab_item_progressBar);


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        this.lists = LoadAllData(); //默认加载数据库为ture的列表
        save = FindSame();//找到相同数据

        //去除交集
        this.lists.removeAll(save);
        //以下进行排序
        listSort listSort = new listSort();
        Collections.sort(this.lists, listSort);//从低到高

        //默认所有页面的第一页position==0 控制的页面有 饮料-全部;酒-全部;.....中的全部页面,不需要操作
        if (MainActivity.topPoint == 0) {
            switch (position) {
                case 1:
                    this.lists.retainAll(getRetainAll(0, 3));
                    break;
                case 2:
                    this.lists.retainAll(getRetainAll(3, 6));
                    break;
                case 3:
                    this.lists.retainAll(getRetainAll(6, 10));
                    break;
                case 4:
                    this.lists.retainAll(getRetainAll(10, 99999999));
                    break;
            }
        }

        if (MainActivity.topPoint == 1) {
            switch (position) {
                case 1:
                    this.lists.retainAll(getRetainAll(0, 10));
                    break;
                case 2:
                    this.lists.retainAll(getRetainAll(10, 15));
                    break;
                case 3:
                    this.lists.retainAll(getRetainAll(15, 9999999));
                    break;
            }
        }
        if (MainActivity.topPoint == 2) {
            switch (position) {
                case 1:
                    this.lists.retainAll(getRetainAll(0, 6));
                    break;
                case 2:
                    this.lists.retainAll(getRetainAll(6, 10));
                    break;
                case 3:
                    this.lists.retainAll(getRetainAll(10, 15));
                    break;
                case 4:
                    this.lists.retainAll(getRetainAll(15, 20));
                    break;
                case 5:
                    this.lists.retainAll(getRetainAll(20, 25));
                    break;
                case 6:
                    this.lists.retainAll(getRetainAll(25, 30));
                    break;
                case 7:
                    this.lists.retainAll(getRetainAll(30, 35));
                    break;
                case 8:
                    this.lists.retainAll(getRetainAll(35, 40));
                    break;
                case 9:
                    this.lists.retainAll(getRetainAll(40, 9999999));
                    break;
            }
        }
        if (MainActivity.topPoint == 3) {
            switch (position) {
                case 1:
                    this.lists.retainAll(getRetainAll(0, 5));
                    break;
                case 2:
                    this.lists.retainAll(getRetainAll(5, 10));
                    break;
                case 3:
                    this.lists.retainAll(getRetainAll(10, 15));
                    break;
            }
        }


        textView.setText(MainActivity.topPoint + " " + position + " 界面加载完毕\n" + "\n\n\n" + this.lists.toString());
        final RViewAdatper rViewAdatper = new RViewAdatper(R.layout.item_fragment, this.lists);



        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //textView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(rViewAdatper);
            }
        }, 400);


        rViewAdatper.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.card1:

                        break;
                    case R.id.card2:
                        ShopSave shopSave = new ShopSave(MainActivity.topPoint, rViewAdatper.getItem(position).name, 1);
                        SQLSaveBuilder.getInstance(getActivity()).insertAPI(shopSave);
                        break;

                }
                SQLBuilder.getInstance(getActivity()).updateFromName(false, rViewAdatper.getItem(position).getName());
                //Toast.makeText(getContext(),"数据库修改false了"+rViewAdatper.getItem(position).name,Toast.LENGTH_LONG).show();
                list list=lists.get(position);
                rViewAdatper.remove(position);
                textView.setText("\n第"+position+"\n"+list+"\n"+MainActivity.topPoint + " " + position + " 界面加载完毕\n" + "\n\n\n" + lists.toString());





            }
        });


    }

    @NonNull
    private ArrayList<list> getRetainAll(int price1, int price2) {
        ArrayList<list> list = new ArrayList<>();

        for (int i = 0; i < this.lists.size(); i++) {
            if (this.lists.get(i).price > price1 && this.lists.get(i).price <= price2) {
                list.add(lists.get(i));
            }
        }
        return list;
    }


    private List<list> LoadAllData() {
        lists = new ArrayList<>();
        final List<Shop> shops = shoplists;
        for (int i = 0; i < shops.size(); i++) {
            if (MainActivity.topPoint == shops.get(i).getNum()) {
                lists.add(shops.get(i).list);
            }
        }
        return lists;
    }

    private List<list> FindSame() {
        List<ShopSave> savelists = Find.getSave(getActivity());
        List<list> save_shops = new ArrayList<>();

        for (int i = 0; i < lists.size(); i++) {
            for (int i1 = 0; i1 < savelists.size(); i1++) {
                if (lists.get(i).name.equals(savelists.get(i1).getName())) {
                    save_shops.add(lists.get(i));
                }
            }
        }
        return save_shops;
    }
}
