package com.example.mangzhaimang.bookit;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mangzhaimang.assistance.ScreenUtils;
import com.example.mangzhaimang.assistance.Seat;

import java.util.List;

public class HomeTipPopupWindow extends PopupWindow implements View.OnClickListener{

    private Context mContext;
    private TextView item1;
    private TextView item2;
    private OrderOutOfTime outofTime;
    private View parent;
    private int seatId;
    private List<Seat> seats;
    private HomeFragment papa;
    private View mView;

    public HomeTipPopupWindow(Context context,HomeFragment pp){
        this.seats = pp.seats;
        this.papa = pp;
        this.mContext = context;
        initView(context);
    }

    public void initView(Context context){
        View view = LayoutInflater.from(mContext).inflate(R.layout.tipwindow_layout,null);
        this.mView = view;
        setContentView(view);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f,mContext);
            }
        });

        item1 = (TextView)view.findViewById(R.id.tip_item1);
        item2 = (TextView)view.findViewById(R.id.tip_item2);


        item1.setOnClickListener(this);
        item2.setOnClickListener(this);

        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        this.setWidth((int)(ScreenUtils.getScreenWidth(mContext)*0.8));
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        this.update();
        this.setAnimationStyle(R.style.take_photo_anim);
    }

    public void showPopupWindow(View parent,int seatId){
        this.seatId = seatId;
        this.parent = parent;
        if(!this.isShowing()){
            setBackgroundAlpha(0.5f,mContext);
            this.showAtLocation(parent, Gravity.CENTER,0,0);
        }else {
            this.dismiss();
        }
        TextView tv = (TextView) this.mView.findViewById(R.id.seat_pos);
        String seat = String.format("%02d",seatId);
        String str = "小鹰帮您锁定了"+seat+"号座位";
        tv.setText(str);
    }

    private void setBackgroundAlpha(float sAlpha,Context mContext){
        WindowManager.LayoutParams lp =((Activity)mContext).getWindow().getAttributes();
        lp.alpha = sAlpha;
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tip_item1:
                fill(this.seatId);
                dismiss();
                this.papa.startThread();
                outofTime = new OrderOutOfTime(mContext,this.papa);
                outofTime.showPopupWindow(this.parent,seatId);
                break;
            case R.id.tip_item2:dismiss();break;
            default:;break;
        }
    }
    public void fill(int seatId){
        Seat seat = seats.get(seatId);
        ImageView imageView = this.parent.findViewById(seatId);
        if(seat.getCate()==Seat.Cate.BASE){
            imageView.setImageBitmap(this.papa.addCode(R.drawable.my_seat,seatId));
            seat.setStatus(Seat.Status.MINE);
        }else if(seat.getCate()==Seat.Cate.COM){
            imageView.setImageBitmap(this.papa.addCode(R.drawable.my_com,seatId));
            seat.setStatus(Seat.Status.MINE);
        }else if(seat.getCate()==Seat.Cate.POWER){
            imageView.setImageBitmap(this.papa.addCode(R.drawable.my_power,seatId));
            seat.setStatus(Seat.Status.MINE);
        }
    }
}
