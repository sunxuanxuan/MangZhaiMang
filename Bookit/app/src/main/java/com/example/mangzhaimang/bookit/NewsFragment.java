package com.example.mangzhaimang.bookit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangzhaimang.assistance.News;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private List<News> list = new ArrayList<>();

    public static NewsFragment newInstance(String name){
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.news_fragment_layout,container,false);

        initNews();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.news_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(list);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void initNews(){
        list.clear();
        String name = this.getArguments().getString("name");
        if(name.equals("意见反馈")){
            News news1 = new News("摘芒6号",1,"图书馆二楼插座坏了！",R.drawable.h1,4,R.drawable.like);
            list.add(news1);
            News news2 = new News("摘芒7号",2,"空调开太大了！",R.drawable.h2,3,R.drawable.like2);
            list.add(news2);
            News news3 = new News("摘芒3号",2,"能不能制裁一下秀恩爱的！",R.drawable.h3,4,R.drawable.like);
            list.add(news3);
            News news4 = new News("摘芒4号",1,"为什么《白夜行》显示在馆但一直找不到呢?",R.drawable.h4,7,R.drawable.like2);
            list.add(news4);

        } else if(name.equals("借还物品")) {
            News news1 = new News("摘芒1号",1,"图书馆三楼，求借一支笔!",R.drawable.h1,4,R.drawable.like);
            list.add(news1);
            News news2 = new News("摘芒2号",2,"有人带iPhone充电线了吗!",R.drawable.h2,3,R.drawable.like2);
            list.add(news2);
            News news3 = new News("摘芒3号",2,"好吧...",R.drawable.h3,4,R.drawable.like);
            list.add(news3);
        }else if(name.equals("全部动态")){
            News news1 = new News("摘芒1号",1,"图书馆三楼，求借一支笔!",R.drawable.h1,4,R.drawable.like);
            list.add(news1);
            News news2 = new News("摘芒2号",2,"有人带iPhone充电线了吗!",R.drawable.h2,3,R.drawable.like2);
            list.add(news2);
            News news3 = new News("摘芒3号",2,"好吧...",R.drawable.h3,4,R.drawable.like);
            list.add(news3);
            News news4 = new News("摘芒6号",1,"图书馆二楼插座坏了！",R.drawable.h1,4,R.drawable.like);
            list.add(news1);
            News news5 = new News("摘芒7号",2,"空调开太大了！",R.drawable.h2,3,R.drawable.like2);
            list.add(news5);
            News news6 = new News("摘芒3号",2,"能不能制裁一下秀恩爱的！",R.drawable.h3,4,R.drawable.like);
            list.add(news6);
            News news7 = new News("摘芒4号",1,"无语",R.drawable.h4,7,R.drawable.like2);
            list.add(news7);
            News news8 = new News("摘芒4号",1,"彭于晏来学校宣传电影了！！",R.drawable.h4,7,R.drawable.like2);
            list.add(news8);
        }else {
            News news8 = new News("摘芒4号",1,"彭于晏来学校宣传电影了！！",R.drawable.h4,7,R.drawable.like2);
            list.add(news8);
        }
    }
}
