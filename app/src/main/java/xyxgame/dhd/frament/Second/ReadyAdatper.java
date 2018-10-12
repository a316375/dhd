package xyxgame.dhd.frament.Second;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shizhefei.view.indicator.Indicator;

import java.util.List;

import xyxgame.dhd.R;
import xyxgame.dhd.SQL.list;
import xyxgame.dhd.SQLSave.ShopSave;

public class ReadyAdatper extends BaseQuickAdapter<ShopSave, BaseViewHolder> {
    public ReadyAdatper(int layoutResId, @Nullable List<ShopSave> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, ShopSave item) {
        helper.setText(R.id.text, item.getName())
                .setText(R.id.nums, "   " + item.getNums() + "   ")
                .addOnClickListener(R.id.bt1)
                .addOnClickListener(R.id.bt2)
                .addOnClickListener(R.id.delet)
        ;
    }


}
