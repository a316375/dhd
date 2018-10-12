package xyxgame.dhd.frament.First;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import xyxgame.dhd.R;
import xyxgame.dhd.SQL.Shop;
import xyxgame.dhd.SQL.list;



public class RViewAdatper extends BaseQuickAdapter<list, BaseViewHolder> {


    public RViewAdatper(int layoutResId, @Nullable List<list> data) {
        super(layoutResId, data);
    }




    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, list item) {
        helper.setText(R.id.name, item.getName())
                .setText(R.id.price, item.getPrice() + "\n元")
                .addOnClickListener(R.id.card1)
                .addOnClickListener(R.id.card2);

    }

    public class BaseViewHolder {
        public CardView card1, card2;
        public TextView price, name;
    }
}
