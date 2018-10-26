package com.example.mangzhaimang.bookit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.mangzhaimang.assistance.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JDJBActivity extends AppCompatActivity {

    private Spinner jdjb_spinner1,jdjb_spinner2;
    private List<String> seatsArr = new ArrayList<>();
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
        setContentView(R.layout.jdjb_layout);

        jdjb_spinner1 = (Spinner)findViewById(R.id.jdjb_spinner1);
        jdjb_spinner2 = (Spinner)findViewById(R.id.jdjb_spinner2);
        add_photo = (ImageView)findViewById(R.id.add_photo);
        pre = (ImageView)findViewById(R.id.jdjb_pre);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.xzt_spinner_layout,getResources().getStringArray(R.array.home_spinner_titles));
        jdjb_spinner1.setAdapter(adapter);
        for(int i=1;i<50;i++){
            seatsArr.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.xzt_spinner_layout,seatsArr);
        jdjb_spinner2.setAdapter(adapter1);

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                puWindow = new MPopupWindow(JDJBActivity.this,JDJBActivity.this);
                puWindow.showPopupWindow(findViewById(R.id.paper_strips));
                puWindow.setOnGetTypeClickListener(new MPopupWindow.onGetTypeClickListener() {
                    @Override
                    public void getType(Type type) {
                        JDJBActivity.this.type = type;
                    }

                    @Override
                    public void getImgUri(Uri ImgUri, File file) {
                        JDJBActivity.this.ImgUri = ImgUri;
                        JDJBActivity.this.file = file;
                    }
                });
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
