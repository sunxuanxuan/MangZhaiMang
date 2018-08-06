package com.example.mangzhaimang.bookit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.view.View;
import android.widget.TextView;

import com.example.mangzhaimang.assistance.ScreenUtils;
import com.example.mangzhaimang.assistance.Type;

import java.io.File;

public class MPopupWindow extends PopupWindow implements View.OnClickListener {

    public Context mContext;
    private com.example.mangzhaimang.assistance.Type type;
    public Activity mActivity;
    private File file;
    private Uri ImgUri;
    private TextView btn_camera,btn_choose,btn_cancel;

    public MPopupWindow(Context context,Activity mActivity){
        initView(context);
        this.mActivity = mActivity;
    }

    private void initView(final Context mContext){
        this.mContext = mContext;
        View view = LayoutInflater.from(mContext).inflate(R.layout.popwindow_layout,null);
        setContentView(view);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f,mContext);
            }
        });

        btn_camera = (TextView)view.findViewById(R.id.pop_take_photo);
        btn_camera.setBackgroundResource(R.drawable.corner_view);
        btn_choose = (TextView)view.findViewById(R.id.pop_choose_phone);
        btn_choose.setBackgroundResource(R.drawable.corner_view);
        btn_cancel = (TextView)view.findViewById(R.id.pop_cancel);
        btn_cancel.setBackgroundResource(R.drawable.corner_view);

        btn_cancel.setOnClickListener(this);
        btn_choose.setOnClickListener(this);
        btn_camera.setOnClickListener(this);

        this.setWidth((int)(ScreenUtils.getScreenWidth(mContext)-50));
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        this.update();
        this.setAnimationStyle(R.style.take_photo_anim);
    }

    public void showPopupWindow(View parent){
        if(!this.isShowing()){
            setBackgroundAlpha(0.5f,mContext);
            this.showAtLocation(parent, Gravity.BOTTOM,0,0);
        }else {
            this.dismiss();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pop_take_photo:
                /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".jpg");
                ImgUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,ImgUri);
                mActivity.startActivityForResult(intent,1);
                type = com.example.mangzhaimang.bookit.Type.CAMERA;
                if(listener!=null){
                    listener.getType(type);
                    listener.getImgUri(ImgUri,file);
                }*/
                this.dismiss();
                break;
            case R.id.pop_choose_phone:
                Intent intent1 = new Intent("android.intent.action.PICK");
                intent1.setType("image/*");
                mActivity.startActivityForResult(intent1,2);
                type = com.example.mangzhaimang.assistance.Type.PHONE;
                if(listener!=null){
                    listener.getType(type);
                }
                this.dismiss();
                break;
            case R.id.pop_cancel:
                this.dismiss();
                break;
                default:break;
        }
    }

    public void onPhoto(Uri uri,int outputX,int outputY){
        Intent intent = null;

        intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");

        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        mActivity.startActivityForResult(intent, 3);


    }

    private void setBackgroundAlpha(float sAlpha,Context mContext){
        WindowManager.LayoutParams lp =((Activity)mContext).getWindow().getAttributes();
        lp.alpha = sAlpha;
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public interface onGetTypeClickListener{
        void getType(Type type);
        void getImgUri(Uri ImgUri, File file);
    }
    private onGetTypeClickListener listener;
    public void setOnGetTypeClickListener(onGetTypeClickListener listener){
        this.listener = listener;
    }
}
