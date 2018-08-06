package com.example.mangzhaimang.bookit;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mangzhaimang.assistance.ScreenUtils;
import com.example.mangzhaimang.assistance.Seat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class OrderOutOfTime extends PopupWindow implements View.OnClickListener{

    private Context mContext;
    private TextView item1;
    private TextView item2;
    private TextView mTimer;
    private HomeFragment gpapa;
    private int seatId;

    public OrderOutOfTime(Context context, HomeFragment gpapa){
        this.mContext = context;
        this.gpapa = gpapa;
        initView(context);
    }

    public void initView(Context context){
        View view = LayoutInflater.from(mContext).inflate(R.layout.orderoutoftime_layout,null);
        setContentView(view);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f,mContext);
            }
        });

        item1 = (TextView)view.findViewById(R.id.tip_item1);
        item2 = (TextView)view.findViewById(R.id.tip_item2);
        mTimer = (TextView)view.findViewById(R.id.timer);
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);

        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        this.setWidth((int)(ScreenUtils.getScreenWidth(mContext)*0.8));
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        this.update();
        this.setAnimationStyle(R.style.take_photo_anim);

        this.gpapa.setmTimer(mTimer);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tip_item1:
                dismiss();
                break;
            case R.id.tip_item2:
                dismiss();
                cancelOreder();
                break;
        }
    }

    public void showPopupWindow(View parent,int seatId){
        this.seatId = seatId;
        if(!this.isShowing()){
            setBackgroundAlpha(0.5f,mContext);
            this.showAtLocation(parent, Gravity.CENTER,0,0);
        }else {
            this.dismiss();
        }
    }

    public void cancelOreder(){
        Seat seat = this.gpapa.seats.get(seatId);
        ImageView imageView = this.gpapa.getView().findViewById(seatId);
        if(seat.getCate()==Seat.Cate.POWER){
            imageView.setImageBitmap(this.gpapa.addCode(R.drawable.power_seat,seatId));
            seat.setStatus(Seat.Status.EMPTY);
        }else if(seat.getCate()== Seat.Cate.COM){
            imageView.setImageBitmap(this.gpapa.addCode(R.drawable.com_seat,seatId));
            seat.setStatus(Seat.Status.EMPTY);
        }else if(seat.getCate()==Seat.Cate.BASE){
            imageView.setImageBitmap(this.gpapa.addCode(R.drawable.em_seat,seatId));
            seat.setStatus(Seat.Status.EMPTY);
        }
    }

    private void setBackgroundAlpha(float sAlpha,Context mContext){
        WindowManager.LayoutParams lp =((Activity)mContext).getWindow().getAttributes();
        lp.alpha = sAlpha;
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
}
