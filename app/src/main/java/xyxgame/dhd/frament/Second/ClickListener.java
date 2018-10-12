package xyxgame.dhd.frament.Second;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import xyxgame.dhd.R;
import xyxgame.dhd.SQL.SQLBuilder;
import xyxgame.dhd.SQLSave.SQLSaveBuilder;
import xyxgame.dhd.SQLSave.ShopSave;

public class ClickListener implements BaseQuickAdapter.OnItemChildClickListener {
    Activity activity;
    List<ShopSave> shopSaves;

    public ClickListener(Activity activity, List<ShopSave> shopSaves) {
        this.activity = activity;
        this.shopSaves = shopSaves;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {

            case R.id.delet:

                SQLSaveBuilder.getInstance(activity).deleteAPI(shopSaves.get(position).getName());
                SQLBuilder.getInstance(activity).updateFromName(true, shopSaves.get(position).getName());
                shopSaves.remove(position);
                adapter.notifyDataSetChanged();
                break;
            case R.id.bt1:
                shopSaves.get(position).setNums(shopSaves.get(position).getNums() == 1 ? 1 : shopSaves.get(position).getNums() - 1);
                adapter.notifyDataSetChanged();
                SQLSaveBuilder.getInstance(activity).updateFromName(shopSaves.get(position).getNums(), shopSaves.get(position).getName());
                break;
            case R.id.bt2:
                shopSaves.get(position).setNums(shopSaves.get(position).getNums() + 1);
                adapter.notifyDataSetChanged();
                SQLSaveBuilder.getInstance(activity).updateFromName(shopSaves.get(position).getNums(), shopSaves.get(position).getName());

                break;
        }
    }
}