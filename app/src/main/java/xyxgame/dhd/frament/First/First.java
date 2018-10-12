package xyxgame.dhd.frament.First;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.RecyclerIndicatorView;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.slidebar.SpringBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.Arrays;
import java.util.List;

import xyxgame.dhd.DisplayUtil;
import xyxgame.dhd.MainActivity;
import xyxgame.dhd.R;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;


public class First extends Fragment {


    private IndicatorViewPager indicatorViewPager;
    public static String top[] = {"饮料", "酒", "烟", "百货", "其他"};
    String price_a[] = {"全部", "3元以下", "6元以下", "10元以下", "10元以上"};
    String price_b[] = {"全部", "10元以下", "15元以下", "15元以上"};
    String price_c[] = {"全部", "6元以下", "10元以下", "15元以下", "20元以下", "25以下", "30以下", "35以下", "40以下", "40以上"};
    String price_d[] = {"全部", "5元以下", "10元以下", "10元以上"};
    String price_e[] = {"全部"};
    List price_list;
    private PriceAdapter priceAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        price_list = Arrays.asList(price_a, price_b, price_c, price_d, price_e);

        View view = inflater.inflate(R.layout.activity_year, container, false);
        //一级菜单
        ScrollIndicatorView scrollIndicatorView = (ScrollIndicatorView) view.findViewById(R.id.singleTab_scrollIndicatorView);
        set(scrollIndicatorView, top.length);
        //二级菜单
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.year_viewPager);
        Indicator indicator2 = (RecyclerIndicatorView) view.findViewById(R.id.year_indicator);
        setPoints(viewPager);//保存标记给主页
        int selectColorId = Color.parseColor("#11aaff");
        int unSelectColorId = Color.parseColor("#010101");
        indicator2.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColorId, unSelectColorId));
        indicator2.setScrollBar(new ColorBar(getContext(), Color.RED, 5));
        viewPager.setOffscreenPageLimit(4);//预加载4个
        indicatorViewPager = new IndicatorViewPager(indicator2, viewPager);//绑定
        priceAdapter = new PriceAdapter((String[]) price_list.get(MainActivity.i));
        indicatorViewPager.setAdapter(priceAdapter);//设置数量适配数据
        indicatorViewPager.setCurrentItem(MainActivity.selectPoint, false);//默认选择

        indicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                indicatorViewPager.notifyDataSetChanged();
            }
        });

        return view;

    }

    //-------------------烟酒茶饮料其他一级滑动

    private void set(final Indicator indicator, int count) {

        indicator.setAdapter(new MyAdapter(count));
        indicator.setScrollBar(new ColorBar(getContext(), Color.RED, 5));

        float unSelectSize = 16;
        float selectSize = unSelectSize * 1.2f;
        int selectColor = getResources().getColor(R.color.tab_top_text_2);
        int unSelectColor = getResources().getColor(R.color.tab_top_text_1);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        indicator.setScrollBar(new SpringBar(getContext(), Color.GRAY));
        indicator.setCurrentItem(MainActivity.topPoint, true);

        indicator.setOnItemSelectListener(new Indicator.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View selectItemView, int select, int preSelect) {
                MainActivity.topPoint = select;
                MainActivity.selectPoint = select;
                MainActivity.i = select;//告诉主页这是第几个

                indicatorViewPager.setAdapter(new PriceAdapter((String[]) price_list.get(select)));//设置数量适配数据
                indicatorViewPager.setCurrentItem(MainActivity.selectPoints[MainActivity.i], false);//切换时候,设置第二级联动为第一个
                indicatorViewPager.notifyDataSetChanged();

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
            textView.setText(top[position]);

            return convertView;
        }

    }

    //-------二级滑动被选中并且显示时-----
    private void setPoints(final ViewPager viewPager) {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                MainActivity.selectPoints[MainActivity.i] = position;//标记第二滑动栏的数组中的第几个
                priceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    //-----------------------二级滑动
    private class PriceAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        String[] strings;

        public PriceAdapter(String[] strings) {
            super(getActivity().getSupportFragmentManager());
            this.strings = strings;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.tab_top, container, false);//头部字体大小
            }
            TextView textView = (TextView) convertView;
            int padding = DisplayUtil.dipToPix(getContext(), 12);//间距
            textView.setPadding(padding, 0, padding, 0);

            textView.setText(strings[position]);

            return convertView;
        }


        @Override
        public android.support.v4.app.Fragment getFragmentForPage(int position) {
            //设置轮播图每个页面的内容
            PriceFragment priceFragment = new PriceFragment();
            Bundle a = new Bundle();
            a.putInt(PriceFragment.INTENT_INT_POSITION, position);
            priceFragment.setArguments(a);
            return priceFragment;
        }

        @Override
        public int getCount() {
            return strings.length;
        }

        ////////////以下代码强制刷新ViewPager
        // 可以删除这段代码看看，数据源更新而viewpager不更新的情况
        private int mChildCount = 0;

        @Override
        public void notifyDataSetChanged() {
            // 重写这个方法，取到子Fragment的数量，用于下面的判断，以执行多少次刷新
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                // 这里利用判断执行若干次不缓存，刷新
                mChildCount--;
                // 返回这个是强制ViewPager不缓存，每次滑动都刷新视图
                return POSITION_NONE;
            }
            // 这个则是缓存不刷新视图
            return super.getItemPosition(object);
        }
        ////////////以上代码强制刷新ViewPager----END


    }
}
