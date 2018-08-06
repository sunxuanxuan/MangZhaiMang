package com.example.mangzhaimang.bookit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mangzhaimang.assistance.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> mNewsList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView headPic;
        TextView name;
        TextView content;
        TextView time;
        TextView like_count;
        ImageView like;
        TextView level;
        private View context;
        private int like_id;

        public ViewHolder(View view){
            super(view);
            this.context = view;
            headPic = (ImageView)view.findViewById(R.id.news_headPic);
            name = (TextView)view.findViewById(R.id.news_name);
            content = (TextView)view.findViewById(R.id.news_content);
            time = (TextView)view.findViewById(R.id.news_time);
            like_count = (TextView)view.findViewById(R.id.news_like_count);
            like = (ImageView) view.findViewById(R.id.news_like);
            level = (TextView)view.findViewById(R.id.news_level);
        }
    }

    public NewsAdapter(List<News> newsList){
        mNewsList = newsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        News news = mNewsList.get(position);
        holder.headPic.setImageResource(news.getImageId());
        holder.name.setText(news.getName());
        holder.content.setText(news.getContent());
        holder.time.setText(news.getTime());
        holder.like_count.setText("("+String.valueOf(news.getLike_count())+")");
        holder.like.setImageResource(news.getLike_id());
        holder.level.setText("Lv "+String.valueOf(news.getLevel()));
        holder.like_id = news.getLike_id();

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.like_id == R.drawable.like){
                        holder.like.setImageResource(R.drawable.like2);
                        holder.like_id = R.drawable.like2;
                        String str = holder.like_count.getText().toString();
                        String count = str.substring(1,str.indexOf(")"));
                        int num = Integer.parseInt(count);
                        holder.like_count.setText("("+String.valueOf(++num)+")");
                }else {
                    holder.like_id = R.drawable.like;
                    holder.like.setImageResource(R.drawable.like);
                    String str = holder.like_count.getText().toString();
                    String count = str.substring(1,str.indexOf(")"));
                    int num = Integer.parseInt(count);
                    holder.like_count.setText("("+String.valueOf(--num)+")");
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return mNewsList.size();
    }
}
