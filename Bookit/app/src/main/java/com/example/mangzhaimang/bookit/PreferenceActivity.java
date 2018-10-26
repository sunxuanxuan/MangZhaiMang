package com.example.mangzhaimang.bookit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PreferenceActivity extends AppCompatActivity {

    private ImageView pre_next;
    private List<TextView> tags = new ArrayList<>();
    private int tagcount=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
        setContentView(R.layout.activity_preference);

        pre_next = (ImageView)findViewById(R.id.prefer_next);
        for(int i=0;i<8;i++){
            String tag = "tags_"+String.valueOf(i+1);
            final TextView tv = (TextView) findViewById(getResources().getIdentifier(tag,"id",getPackageName()));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tags.contains(tv)){
                        tagcount--;
                        tv.setTextColor(Color.GRAY);
                        tags.remove(tv);
                        tv.setBackgroundResource(R.drawable.tag_bg_unchecked);
                    }else if(tagcount==3){
                        return;
                    }else {
                        tagcount++;
                        tags.add(tv);
                        tv.setTextColor(getResources().getColor(R.color.orange));
                        tv.setBackgroundResource(R.drawable.tag_bg_checked);
                    }
                }
            });
        }

        pre_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreferenceActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Translucent navigation bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
}
