package com.example.mangzhaimang.assistance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class News {

    private String name;
    private int level;
    private String content;
    private int imageId;
    private String time;
    private int like_count;
    private int like_id;
    private String completeContent;

    public News(String name, int level, String content, int imageId, int like_count, int like_id){
        this.name = name;
        this.level = level;
        this.content = content;
        this.imageId = imageId;
        this.like_count = like_count;
        this.like_id = like_id;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Date curDate = new Date(System.currentTimeMillis());
        String str = format.format(curDate);
        this.time = str.substring(str.indexOf(" "));
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getContent() {
        return content;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTime(){
        return time;
    }

    public int getLike_count() {
        return like_count;
    }

    public int getLike_id() {
        return like_id;
    }
}
