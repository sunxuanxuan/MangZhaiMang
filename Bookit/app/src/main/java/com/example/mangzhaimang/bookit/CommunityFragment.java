package com.example.mangzhaimang.bookit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment implements ViewPager.OnPageChangeListener{
    private RefreshLayout refreshLayout;
    private ViewPager viewPager;
    private CommunityViewPagerAdapter mAdapter;
    private List<ImageView> mItems;
    private LinearLayout mIndicator;
    private ImageView [] mIimages;

    private String [] titles = {"全部动态","借还物品","话题讨论","意见反馈"};
    private ViewPager newsViewPager;
    private TabLayout tabLayout;

    private FloatingActionButton faBtn;

    public static CommunityFragment newInstance(){
        Bundle args = new Bundle();
        args.putString("name","社区");
        CommunityFragment fragment = new CommunityFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.community_layout, container, false);
        refreshLayout = view.findViewById(R.id.community_refresh_layout);
        viewPager = (ViewPager) view.findViewById(R.id.community_viewpager);
        mIndicator = (LinearLayout)view.findViewById(R.id.community_indictor);
        faBtn = (FloatingActionButton)view.findViewById(R.id.float_btn);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                delay();
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setEnableRefresh(true);
        refreshLayout.finishRefresh();

        mItems = new ArrayList<>();
        addImageView();
        mAdapter = new CommunityViewPagerAdapter(mItems,getActivity());
        viewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mIimages = new ImageView[mItems.size()];
        for(int i=0;i<mIimages.length;i++){
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20,20);
            lp.setMargins(15,0,15,0);
            imageView.setLayoutParams(lp);
            if(i==0){
                imageView.setImageResource(R.drawable.indictor_select);
            }else{
                imageView.setImageResource(R.drawable.indicator_no_select);
            }
            mIimages[i] = imageView;
            mIndicator.addView(imageView);
        }
        viewPager.addOnPageChangeListener(this);

        faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu pop = new PopupMenu(getActivity(),faBtn);
                pop.getMenuInflater().inflate(R.menu.popupmenu,pop.getMenu());
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId()==R.id.pop_item2){
                            Intent intent = new Intent(getActivity(),WritePaperStripsActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    }
                });

                setBackgroundAlpha(0.5f,getActivity());

                pop.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu popupMenu) {
                        setBackgroundAlpha(1.0f,getActivity());
                    }
                });
                pop.show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View viw, @Nullable Bundle savedInstanceState){
        super.onViewCreated(viw,savedInstanceState);

        tabLayout = (TabLayout) viw.findViewById(R.id.news_tab);
        newsViewPager = (ViewPager)viw.findViewById(R.id.news_viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        List<Fragment> list = new ArrayList<>();
        list.add(NewsFragment.newInstance("全部动态"));
        list.add(NewsFragment.newInstance("借还物品"));
        list.add(NewsFragment.newInstance("话题讨论"));
        list.add(NewsFragment.newInstance("意见反馈"));
        adapter.setList(list);

        newsViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(newsViewPager);
        for(int i=0;i<4;i++){
            tabLayout.getTabAt(i).setText(titles[i]);
        }

    }

    private void addImageView(){
        ImageView img0 = new ImageView(getActivity());
        img0.setImageResource(R.drawable.bg1);
        ImageView img1 = new ImageView(getActivity());
        img1.setImageResource(R.drawable.bg1);
        ImageView img2 = new ImageView(getActivity());
        img2.setImageResource(R.drawable.bg1);

        img0.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img2.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mItems.add(img0);
        mItems.add(img1);
        mItems.add(img2);

    }

    @Override
    public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels){

    }

    @Override
    public void onPageSelected(int position) {

        int currentViewPagerItem = position;
        if (mItems != null) {
            position %= mIimages.length;
            int total = mIimages.length;

            for (int i = 0; i < total; i++) {
                if (i == position) {
                    mIimages[i].setImageResource(R.drawable.indictor_select);
                } else {
                    mIimages[i].setImageResource(R.drawable.indicator_no_select);
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void delay(){
        double time = 500;
        while (time>0){time--;};
    }

    private void setBackgroundAlpha(float sAlpha,Context mContext){
        WindowManager.LayoutParams lp =((Activity)mContext).getWindow().getAttributes();
        lp.alpha = sAlpha;
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
}
