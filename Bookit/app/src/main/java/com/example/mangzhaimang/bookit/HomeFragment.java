package com.example.mangzhaimang.bookit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangzhaimang.assistance.ScreenUtils;
import com.example.mangzhaimang.assistance.Seat;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private RefreshLayout refreshLayout;
    private PopWindowForMenu popWindowForMenu;
    private HomeTipPopupWindow homeTipPopupWindow;
    private YJXZPopupWindow yjxzPopupWindow;
    private OrderOutOfTime outofTime;
    private Spinner spinner;
    private ImageView more;
    private RelativeLayout relativeLayout;
    private int choice;
    List<Seat> seats = new ArrayList<>();
    private int recLen = 900;
    private TextView mTimer;
    public MyThread myThread;
    private View mView;

    private TextView yjxz;

    public static HomeFragment newInstance(){
        Bundle args = new Bundle();
        args.putString("name","首页");
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_layout, container, false);
        this.mView = view;
        refreshLayout = view.findViewById(R.id.home_refresh_layout);
        more = (ImageView)view.findViewById(R.id.home_more);
        spinner = (Spinner) view.findViewById(R.id.home_title_spinner);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relative);
        yjxz = (TextView)view.findViewById(R.id.yjxz);

        addSeats();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                delay();
                refreshlayout.finishRefresh();
            }
        });
        refreshLayout.setEnableRefresh(true);
        refreshLayout.finishRefresh();

        yjxz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yjxzPopupWindow = new YJXZPopupWindow(getActivity(),HomeFragment.this);
                yjxzPopupWindow.showPopupWindow(getView());
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.title_spinner,getResources().getStringArray(R.array.home_spinner_titles));
        spinner.setAdapter(adapter);
        ViewGroup.LayoutParams lp = spinner.getLayoutParams();
        lp.width = (int)(ScreenUtils.getScreenWidth(getActivity())*0.7);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindowForMenu = new PopWindowForMenu(getActivity(),HomeFragment.this);
                popWindowForMenu.showPopupWindow(getActivity().findViewById(R.id.home_more));
            }
        });
    }

    private void delay(){
        double time = 500;
        while (time>0){time--;};
    }

    private void setBackgroundAlpha(float sAlpha,Context mContext){
        WindowManager.LayoutParams lp =((Activity)mContext).getWindow().getAttributes();
        lp.alpha = sAlpha;
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public Bitmap addCode(int resourceId,int code){
        String str = String.format("%02d",code);
        Bitmap old_map = BitmapFactory.decodeResource(getResources(),resourceId);
        Bitmap new_map = Bitmap.createBitmap(old_map.getWidth(),old_map.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(new_map);
        Paint paint = new Paint();
        canvas.drawBitmap(old_map,0,0,null);
        paint.setColor(Color.BLACK);
        if(code>=14&&code<=19||code>=34&&code<=42||code>=47&&code<=55){
            paint.setTextSize(170);
            Rect rect = new Rect();
            paint.getTextBounds(str,0,str.length(),rect);
            canvas.drawText(str,old_map.getWidth()/2-rect.width()/2,old_map.getHeight()/2,paint);
        }else {
            paint.setTextSize(70);
            Rect rect = new Rect();
            paint.getTextBounds(str,0,str.length(),rect);
            canvas.drawText(str,old_map.getWidth()/2-rect.width()/2,old_map.getHeight()/2+rect.height()/2,paint);
        }
        return new_map;
    }

    public void addSeats(){
        int count = 0;
        int width = (int)(ScreenUtils.getScreenWidth(getActivity())/12);

        //左上
        for(int i=0;i<5;i++){
            for(int j=0;j<3;j++){
                if(i == 4){
                    if(j==2){
                        continue;
                    }
                    ImageView imageView = new ImageView(getActivity());
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,width);
                    lp.setMargins(40+width/2+j*(width+10),40+i*(width+10),0,0);
                    imageView.setLayoutParams(lp);
                    imageView.setId(count++);
                    imageView.setImageBitmap(addCode(R.drawable.em_seat,count-1));
                    imageView.setOnClickListener(this);
                    relativeLayout.addView(imageView);

                    Seat seat = new Seat(count-1, Seat.Status.EMPTY, Seat.Cate.BASE);
                    seats.add(seat);

                    continue;
                }
                ImageView imageView = new ImageView(getActivity());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,width);
                lp.setMargins(40+j*(width+10),40+i*(width+10),0,0);
                if(j==0){
                    lp.leftMargin = 40;
                }
                if(i==0){
                    lp.topMargin = 40;
                }
                imageView.setLayoutParams(lp);
                imageView.setId(count++);
                imageView.setImageBitmap(addCode(R.drawable.em_seat,count-1));

                Seat seat = new Seat(count-1, Seat.Status.EMPTY, Seat.Cate.BASE);
                seats.add(seat);

                imageView.setOnClickListener(this);
                relativeLayout.addView(imageView);
            }
        }
        float half = ScreenUtils.getScreenWidth(getActivity())/2;

        ImageView im = new ImageView(getActivity());
        RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(width+10,width+10);
        llp.setMargins((int)half-(width+10)/2,20,0,0);
        im.setLayoutParams(llp);
        im.setImageResource(R.drawable.door);
        relativeLayout.addView(im);

        //中上
        for(int i=0;i<3;i++){
            for(int j=0;j<2;j++){
                ImageView imageView = new ImageView(getActivity());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width+30,width+30);
                lp.setMargins(0,i*(width+30)+width*2,0,0);
                if(j==0){
                    lp.leftMargin = (int)half - width - 50;
                }else{
                    lp.leftMargin = (int)half + 20;
                }
                imageView.setLayoutParams(lp);
                imageView.setId(count++);
                imageView.setImageBitmap(addCode(R.drawable.com_seat,count-1));

                Seat seat = new Seat(count-1, Seat.Status.EMPTY, Seat.Cate.COM);
                seats.add(seat);

                imageView.setOnClickListener(this);
                relativeLayout.addView(imageView);
            }
        }

        //右上
        int start = (int)(2 * half - (40+3*(width+10)));
        for(int i=0;i<5;i++){
            for(int j=0;j<3;j++){
                if(i == 4){
                    if(j==2){
                        continue;
                    }
                    ImageView imageView = new ImageView(getActivity());
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,width);
                    lp.setMargins(width/2+start+j*(width+10),40+i*(width+10),0,0);
                    imageView.setLayoutParams(lp);
                    imageView.setId(count++);
                    imageView.setImageBitmap(addCode(R.drawable.em_seat,count-1));

                    Seat seat = new Seat(count-1, Seat.Status.EMPTY, Seat.Cate.BASE);
                    seats.add(seat);

                    imageView.setOnClickListener(this);
                    relativeLayout.addView(imageView);
                    continue;
                }
                ImageView imageView = new ImageView(getActivity());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,width);
                lp.setMargins(start+j*(width+10),40+i*(width+10),0,0);
                if(j==0){
                    lp.leftMargin = start;
                }
                if(i==0){
                    lp.topMargin = 40;
                }
                imageView.setLayoutParams(lp);
                imageView.setId(count++);
                imageView.setImageBitmap(addCode(R.drawable.em_seat,count-1));

                Seat seat = new Seat(count-1, Seat.Status.EMPTY, Seat.Cate.BASE);
                seats.add(seat);

                imageView.setOnClickListener(this);
                relativeLayout.addView(imageView);
            }
        }
        //左下
        int startL = 40+5*(width+10)+width*2;
        width+=10;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                ImageView imageView = new ImageView(getActivity());
                RelativeLayout.LayoutParams pl = new RelativeLayout.LayoutParams(width,width);
                pl.setMargins(40+j*(width+10),startL+i*(width+10),0,0);
                if(i==0){
                    pl.topMargin=startL;
                }
                if(j==0){
                    pl.leftMargin = 40;
                }
                imageView.setLayoutParams(pl);
                imageView.setId(count++);
                imageView.setImageBitmap(addCode(R.drawable.power_seat,count-1));

                Seat seat = new Seat(count-1, Seat.Status.EMPTY, Seat.Cate.POWER);
                seats.add(seat);

                imageView.setOnClickListener(this);
                relativeLayout.addView(imageView);
            }
        }
        //中下
        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                ImageView imageView = new ImageView(getActivity());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width-10,width-10);
                lp.setMargins(0,startL+i*(width)+width/2,0,0);
                if(j==0){
                    lp.leftMargin = (int)half - width - 10;
                }else{
                    lp.leftMargin = (int)half + 20;
                }
                imageView.setLayoutParams(lp);
                imageView.setId(count++);
                imageView.setImageBitmap(addCode(R.drawable.em_seat,count-1));

                Seat seat = new Seat(count-1, Seat.Status.EMPTY, Seat.Cate.BASE);
                seats.add(seat);

                imageView.setOnClickListener(this);
                relativeLayout.addView(imageView);
            }
        }
        //右下
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                ImageView imageView = new ImageView(getActivity());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,width);
                lp.setMargins(start+j*(width+10),startL+i*(width+10),0,0);
                if(j==0){
                    lp.leftMargin = start;
                }
                if(i==0){
                    lp.topMargin = startL;
                }
                imageView.setLayoutParams(lp);
                imageView.setId(count++);
                imageView.setImageBitmap(addCode(R.drawable.power_seat,count-1));

                Seat seat = new Seat(count-1, Seat.Status.EMPTY, Seat.Cate.POWER);
                seats.add(seat);

                imageView.setOnClickListener(this);
                relativeLayout.addView(imageView);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Seat seat = seats.get(id);
        if(seat.getStatus()==Seat.Status.EMPTY){
            if(hasAseat()){
                Toast.makeText(getActivity(),"您已经预定了一个座位！",Toast.LENGTH_SHORT).show();
                return;
            }
            homeTipPopupWindow = new HomeTipPopupWindow(getActivity(),HomeFragment.this);
            homeTipPopupWindow.showPopupWindow(getView(),id);
        }else if(seat.getStatus()==Seat.Status.MINE){
            outofTime = new OrderOutOfTime(getActivity(),HomeFragment.this);
            outofTime.showPopupWindow(getView(),id);
        }else{
            Toast.makeText(getActivity(),"该座位已经有人了~",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean hasAseat(){
        for(int i=0;i<seats.size();i++){
            Seat seat = seats.get(i);
            if(seat.getStatus()==Seat.Status.MINE){
                return true;
            }
        }
        return false;
    }

    final  android.os.Handler handler = new android.os.Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    recLen--;
                    if(mTimer!=null){
                        int m = recLen/60;
                        int s = recLen%60;
                        mTimer.setText(String.format("%02d",m)+":"+String.format("%02d",s));
                    }
            }
            super.handleMessage(msg);
        }
    };

    public class MyThread implements Runnable{
        @Override
        public void run(){
            while (recLen>0){
                try {
                    Thread.sleep(1000);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }catch (Exception e){

                }
            }
        }
    }
    public void startThread(){
        if (myThread == null){
            myThread = new MyThread();
            new Thread(myThread).start();
        }else {
            recLen = 900;
        }
    }
    public void setmTimer(TextView t){
        this.mTimer = t;
    }
    public void stochastic(){
        int code = (int)(Math.random()*seats.size());
        while (seats.get(code).getStatus()!=Seat.Status.EMPTY){
            code = (int)(1 + Math.random()*seats.size());
        }
        ImageView im = this.mView.findViewById(code);
        im.callOnClick();
    }
}
