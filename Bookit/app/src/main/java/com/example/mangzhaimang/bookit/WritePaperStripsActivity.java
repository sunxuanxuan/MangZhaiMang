package com.example.mangzhaimang.bookit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mangzhaimang.assistance.Type;

import java.io.File;

public class WritePaperStripsActivity extends AppCompatActivity {

    private ImageView pre;
    private ImageView add_photo;
    private MPopupWindow puWindow;

    private File file;
    private Uri ImgUri;

    private Type type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        setContentView(R.layout.paper_strips);

        pre = (ImageView)findViewById(R.id.wps_pre);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add_photo = (ImageView)findViewById(R.id.add_photo);

        add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                puWindow = new MPopupWindow(WritePaperStripsActivity.this,WritePaperStripsActivity.this);
                puWindow.showPopupWindow(findViewById(R.id.paper_strips));
                puWindow.setOnGetTypeClickListener(new MPopupWindow.onGetTypeClickListener() {
                    @Override
                    public void getType(Type type) {
                        WritePaperStripsActivity.this.type = type;
                    }

                    @Override
                    public void getImgUri(Uri ImgUri, File file) {
                        WritePaperStripsActivity.this.ImgUri = ImgUri;
                        WritePaperStripsActivity.this.file = file;
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Log.e("requestCode",type+"");
        if(requestCode == 1){
            if(ImgUri!=null){
                puWindow.onPhoto(ImgUri,70,70);
            }else if(requestCode == 2){
                if(data!=null){
                    Uri uri = data.getData();
                    puWindow.onPhoto(uri,70,70);
                }
            }else if(requestCode==3){
                if(type == Type.PHONE){
                    if(data != null){
                        Bundle extras = data.getExtras();
                        Bitmap bitmap = (Bitmap) extras.get("data");
                    }
                }else if(type == Type.CAMERA){
                }
            }
        }
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
        if(v != null &&  (v instanceof EditText)){
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
