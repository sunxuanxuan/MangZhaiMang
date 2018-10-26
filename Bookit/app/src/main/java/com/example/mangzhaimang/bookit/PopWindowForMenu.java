package com.example.mangzhaimang.bookit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class PopWindowForMenu extends PopupWindow implements View.OnClickListener{

    private Context mContext;
    private LinearLayout gzsm,jdjb;
    private Fragment parentF;

    public PopWindowForMenu(Context context,Fragment pp){
        this.mContext = context;
        initView(context);
        this.parentF = pp;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_gzsm:
                Intent intent = new Intent(mContext,GzsmActivity.class);
                this.parentF.startActivity(intent);
                dismiss();
                break;
            case R.id.home_jdjb:
                Intent intent1 = new Intent(mContext,JDJBActivity.class);
                this.parentF.startActivity(intent1);
                dismiss();
                break;
                default:;break;
        }
    }

    public void initView(Context context){
        View view = LayoutInflater.from(mContext).inflate(R.layout.popwindowformenu,null);
        setContentView(view);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f,mContext);
            }
        });

        gzsm = (LinearLayout)view.findViewById(R.id.home_gzsm);
        jdjb = (LinearLayout)view.findViewById(R.id.home_jdjb);
        gzsm.setOnClickListener(this);
        jdjb.setOnClickListener(this);

        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        this.update();
    }

    public void showPopupWindow(View parent){
        if(!this.isShowing()){
            setBackgroundAlpha(0.5f,mContext);
            this.showAsDropDown(parent);
        }else {
            this.dismiss();
        }
    }

    private void setBackgroundAlpha(float sAlpha,Context mContext){
        WindowManager.LayoutParams lp =((Activity)mContext).getWindow().getAttributes();
        lp.alpha = sAlpha;
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
}
