package com.example.mangzhaimang.bookit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.mangzhaimang.assistance.Type;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalMessageActivity extends AppCompatActivity {
    private ImageView pre;
    private CircleImageView headPic;
    private MPopupWindow puWindow;
    private RelativeLayout p_name,p_message;
    private String old_name;

    private File file;
    private Uri ImgUri;
    private Type type;

    private View p_mask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        setContentView(R.layout.personal_layout);

        Intent intent = getIntent();
        old_name = intent.getStringExtra("old_name");

        pre = (ImageView)findViewById(R.id.personal_pre);
        headPic = (CircleImageView)findViewById(R.id.personal_headpicture);
        p_name = (RelativeLayout)findViewById(R.id.personal_name);
        p_message = (RelativeLayout)findViewById(R.id.personal_message);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        p_mask = findViewById(R.id.p_mask);

        p_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                puWindow = new MPopupWindow(PersonalMessageActivity.this,PersonalMessageActivity.this);
                puWindow.showPopupWindow(findViewById(R.id.personal_layout));
                puWindow.setOnGetTypeClickListener(new MPopupWindow.onGetTypeClickListener() {
                    @Override
                    public void getType(Type type) {
                        PersonalMessageActivity.this.type = type;
                    }

                    @Override
                    public void getImgUri(Uri ImgUri, File file) {
                        PersonalMessageActivity.this.ImgUri = ImgUri;
                        PersonalMessageActivity.this.file = file;
                    }
                });
            }
        });

        p_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalMessageActivity.this,ChangeNameActivity.class);
                intent.putExtra("old_name",old_name);
                startActivity(intent);
            }
        });

        p_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalMessageActivity.this,BoundMessageActivity.class);
                startActivity(intent);
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
                        if(bitmap!=null){
                            headPic.setImageBitmap(bitmap);
                        }
                    }
                }else if(type == Type.CAMERA){
                    headPic.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
                }
            }
        }
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
