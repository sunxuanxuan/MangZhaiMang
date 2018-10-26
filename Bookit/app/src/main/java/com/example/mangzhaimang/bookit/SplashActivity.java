package com.example.mangzhaimang.bookit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        initView();
    }

    private void initView(){
        ImageView background=(ImageView)findViewById(R.id.splash_background);
        background.postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToMain();
            }
        },1250);
    }

    private void jumpToMain(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
