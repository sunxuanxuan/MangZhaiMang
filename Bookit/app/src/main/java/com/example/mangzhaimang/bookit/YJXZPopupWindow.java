package com.example.mangzhaimang.bookit;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mangzhaimang.assistance.ScreenUtils;

public class YJXZPopupWindow extends PopupWindow implements View.OnClickListener{

    private Context mContext;
    private View mView;
    private HomeFragment papa;
    private TextView item1;
    private TextView item2;
    private TextView item3;

    public YJXZPopupWindow(Context context,HomeFragment papa){
        this.mContext = context;
        this.papa = papa;
        initView(context);
    }

    public void initView(Context context){
        View view = LayoutInflater.from(mContext).inflate(R.layout.yjxz_layout,null);
        this.mView = view;
        setContentView(view);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f,mContext);
            }
        });

        item1 = (TextView)view.findViewById(R.id.tip_item1);
        item1.setOnClickListener(this);
        item2 = (TextView)view.findViewById(R.id.tip_item2);
        item2.setOnClickListener(this);
        item3 = (TextView)view.findViewById(R.id.tip_item3);
        item3.setOnClickListener(this);

        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        this.setWidth((int)(ScreenUtils.getScreenWidth(mContext)*0.8));
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        this.update();
        this.setAnimationStyle(R.style.take_photo_anim);
    }

    public void showPopupWindow(View parent){
        if(!this.isShowing()){
            setBackgroundAlpha(0.5f,mContext);
            this.showAtLocation(parent, Gravity.CENTER,0,0);
        }else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tip_item1:
                dismiss();
                this.papa.stochastic();
                break;
            case R.id.tip_item2:
                dismiss();
                break;
            case R.id.tip_item3:
                dismiss();
                break;
                default:;break;
        }
    }

    private void setBackgroundAlpha(float sAlpha,Context mContext){
        WindowManager.LayoutParams lp =((Activity)mContext).getWindow().getAttributes();
        lp.alpha = sAlpha;
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
}
