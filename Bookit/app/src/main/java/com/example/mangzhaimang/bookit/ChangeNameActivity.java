package com.example.mangzhaimang.bookit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangzhaimang.assistance.ScreenUtils;

public class ChangeNameActivity extends AppCompatActivity {

    private ImageView pre;
    private EditText cn_edit;
    private Button cn_savebt;
    private TextView mine_name;
    private String old_name;
    private String new_name;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        setContentView(R.layout.changename_layout);

        pre = (ImageView) findViewById(R.id.cn_pre);
        cn_edit = (EditText)findViewById(R.id.cn_edit);
        cn_savebt = (Button)findViewById(R.id.cn_save_btn);
        cn_savebt.setBackgroundResource(R.drawable.button_corner);

        Intent intent = getIntent();
        old_name = intent.getStringExtra("old_name");
        cn_edit.setText(old_name);

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        LinearLayout.LayoutParams pl = (LinearLayout.LayoutParams)cn_edit.getLayoutParams();
        pl.width = (int)(ScreenUtils.getScreenWidth(ChangeNameActivity.this)*0.9);
        pl = (LinearLayout.LayoutParams)cn_savebt.getLayoutParams();
        pl.width = (int)(ScreenUtils.getScreenWidth(ChangeNameActivity.this)*0.9);

        cn_savebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_name = cn_edit.getText().toString();
                byte[] buff = new_name.getBytes();
                int text_width = buff.length;
                if (text_width > 15){
                    Toast.makeText(ChangeNameActivity.this,"昵称总长度不能超过15个字符",Toast.LENGTH_SHORT).show();
                }else {
                    finish();
                }
            }
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            View v = getCurrentFocus();
            if(isShouldHideInput(v,ev)){
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null){
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if(getWindow().superDispatchTouchEvent(ev)){
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v,MotionEvent e){
        if(v != null &&  (v instanceof  EditText)){
            int [] leftTop = {0,0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if(e.getX() > left && e.getX() < right && e.getY() > top && e.getY() < bottom){
                return false;
            }else {
                return true;
            }
        }
        return false;
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
