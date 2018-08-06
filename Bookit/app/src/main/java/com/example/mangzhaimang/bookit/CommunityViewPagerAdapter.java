package com.example.mangzhaimang.bookit;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

public class CommunityViewPagerAdapter extends PagerAdapter {

    public static final int MAX_SCROLL_VALUE = 10000;

    private List<ImageView> mItems;
    private Context mContext;
    private LayoutInflater mInflater;

    public CommunityViewPagerAdapter(List<ImageView> items,Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @Override
    public Object instantiateItem(ViewGroup container,int position){
        View ret = null;
        position %=mItems.size();
        Log.d("Adapter", "instantiateItem: position: " + position);
        ret = mItems.get(position);
        ViewParent viewParent = ret.getParent();
        if (viewParent != null) {
            ViewGroup parent = (ViewGroup) viewParent;
            parent.removeView(ret);
        }
        container.addView(ret);

        return ret;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public int getCount() {
        int ret = 0;
        if (mItems.size() > 0) {
            ret = MAX_SCROLL_VALUE;
        }
        return ret;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }
}
