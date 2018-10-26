package com.example.mangzhaimang.bookit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangzhaimang.assistance.ScreenUtils;
import com.example.mangzhaimang.assistance.Type;

import java.io.File;

public class LoginActivity extends AppCompatActivity {

    private EditText login_password;
    private EditText login_account;
    private EditText signup_password;
    private EditText signup_confirm;

    private EditText forget_password;
    private EditText forget_confirm;


    private RelativeLayout login_confirm;
    private ImageView login_check;
    private TextView lfp;

    private ImageView login_login;
    private ImageView signup_bt;
    private ImageView forget_bt;

    private TextView login_text_login;
    private TextView login_text_signup;
    private LinearLayout login_container;
    private LinearLayout signup_container;
    private LinearLayout forgetpass_container;

    private TextView forget_text;
    private LinearLayout login_signup;

    private LinearLayout signup_upload_pic;
    private LinearLayout forget_upload_pic;

    private MPopupWindow puWindow;

    private File file;
    private Uri ImgUri;

    private Type type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
        setContentView(R.layout.activity_login);

        login_password = (EditText)findViewById(R.id.login_password);
        login_account = (EditText)findViewById(R.id.login_account);

        signup_password = (EditText)findViewById(R.id.sign_password);
        signup_confirm = (EditText)findViewById(R.id.sign_confirm);

        login_confirm = (RelativeLayout)findViewById(R.id.login_confirm);
        login_check = (ImageView)findViewById(R.id.login_check);
        lfp = (TextView)findViewById(R.id.login_forget_password);
        login_login = (ImageView)findViewById(R.id.login_bt_login);
        login_text_login = (TextView)findViewById(R.id.login_text_login);
        login_text_signup = (TextView)findViewById(R.id.login_text_signup);
        login_container = (LinearLayout)findViewById(R.id.login_container);
        signup_container = (LinearLayout)findViewById(R.id.signup_container);
        forgetpass_container = (LinearLayout)findViewById(R.id.forgetpass_container);

        login_login = (ImageView)findViewById(R.id.login_bt_login);
        signup_bt = (ImageView)findViewById(R.id.signup_bt);
        forget_bt = (ImageView)findViewById(R.id.forget_bt);


        forget_text = (TextView)findViewById(R.id.login_text_forget);
        login_signup = (LinearLayout)findViewById(R.id.login_singup);

        login_signup = (LinearLayout)findViewById(R.id.login_singup);
        forget_text = (TextView)findViewById(R.id.login_text_forget);

        forget_password = (EditText)findViewById(R.id.forget_password);
        forget_confirm = (EditText)findViewById(R.id.forget_confirm);

        signup_upload_pic = (LinearLayout)findViewById(R.id.signup_upload_pic);
        forget_upload_pic = (LinearLayout)findViewById(R.id.forget_upload_pic);

        login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        signup_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        signup_confirm.setTransformationMethod(PasswordTransformationMethod.getInstance());

        forget_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        forget_confirm.setTransformationMethod(PasswordTransformationMethod.getInstance());

        lfp.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        login_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login_check.getVisibility() == View.VISIBLE){
                    login_check.setVisibility(View.GONE);
                }else if(login_check.getVisibility() == View.GONE){
                    login_check.setVisibility(View.VISIBLE);
                }
            }
        });

        login_text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_container.setVisibility(View.VISIBLE);
                signup_container.setVisibility(View.GONE);
                login_text_login.setTextColor(Color.WHITE);
                login_text_signup.setTextColor(Color.GRAY);
            }
        });

        login_text_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_container.setVisibility(View.GONE);
                signup_container.setVisibility(View.VISIBLE);
                login_text_signup.setTextColor(Color.WHITE);
                login_text_login.setTextColor(Color.GRAY);
            }
        });

        login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login_account.getText().toString().equals("")||login_password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"请将信息填写完整~",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signup_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,PreferenceActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_container.setVisibility(View.GONE);
                forgetpass_container.setVisibility(View.VISIBLE);
                login_signup.setVisibility(View.GONE);
                forget_text.setVisibility(View.VISIBLE);
            }
        });

        forget_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetpass_container.setVisibility(View.GONE);
                login_container.setVisibility(View.VISIBLE);
                login_signup.setVisibility(View.VISIBLE);
                forget_text.setVisibility(View.GONE);
            }
        });

        signup_upload_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPic();
            }
        });

        forget_upload_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPic();
            }
        });
    }

    private void uploadPic(){
        puWindow = new MPopupWindow(LoginActivity.this,LoginActivity.this);
        puWindow.showPopupWindow(findViewById(R.id.Login));
        puWindow.setOnGetTypeClickListener(new MPopupWindow.onGetTypeClickListener() {
            @Override
            public void getType(Type type) {
                LoginActivity.this.type = type;
            }

            @Override
            public void getImgUri(Uri ImgUri, File file) {
                LoginActivity.this.ImgUri = ImgUri;
                LoginActivity.this.file = file;
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
