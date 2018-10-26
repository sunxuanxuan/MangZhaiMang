package com.example.mangzhaimang.bookit;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.IMediaControllerCallback;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangzhaimang.assistance.ScreenUtils;
import com.example.mangzhaimang.assistance.Seat;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private PopWindowForMenu popWindowForMenu;
    private Spinner spinner;
    private ImageView more;
    private RelativeLayout relativeLayout;
    List<Seat> seats = new ArrayList<>();
    private int recLen = 900;
    private int recLen2 = 0;
    private int timerState = 1;
    public MyThread myThread;
    private View mView;
    private TextView timerLabel;
    private TextView timerLabel2;

    private int choice = -1;

    private SlidingUpPanelLayout slidingup1;
    private LinearLayout sliupguide;
    private RelativeLayout prefer;
    private LinearLayout panel1;
    private LinearLayout panel2;
    private LinearLayout panel3;
    private LinearLayout panel4;

    private LinearLayout panel3_cancel,panel3_confirm;

    private LinearLayout sto_pos;
    private LinearLayout pre_pos;
    private LinearLayout old_pos;

    private TextView locked_pos;

    private int method_choice = 0;

    private ImageView choice_next;

    private LinearLayout panel2_next;
    private LinearLayout panel2_return;
    private LinearLayout panel4_quit;

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
        more = (ImageView)view.findViewById(R.id.home_more);
        spinner = (Spinner) view.findViewById(R.id.home_title_spinner);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relative);
        slidingup1 = (SlidingUpPanelLayout)view.findViewById(R.id.mine_slidingup1);
        sliupguide = (LinearLayout)view.findViewById(R.id.mine_slidingup_guide);
        prefer = (RelativeLayout)view.findViewById(R.id.mine_slidingup_prefer);
        sto_pos = (LinearLayout)view.findViewById(R.id.mine_sto_pos);
        pre_pos = (LinearLayout)view.findViewById(R.id.mine_pref_pos);
        old_pos = (LinearLayout)view.findViewById(R.id.mine_old_pos);
        choice_next = (ImageView) view.findViewById(R.id.mine_choice_next);
        panel1 = (LinearLayout)view.findViewById(R.id.home_panel1);
        panel2 = (LinearLayout)view.findViewById(R.id.home_panel2);
        panel3 = (LinearLayout) view.findViewById(R.id.home_panel3);
        panel4 = (LinearLayout)view.findViewById(R.id.home_panel4);
        panel2_return = (LinearLayout) view.findViewById(R.id.panel2_return);
        panel2_next = (LinearLayout)view.findViewById(R.id.panel2_next);
        locked_pos = (TextView) view.findViewById(R.id.home_locked_pos);
        timerLabel = (TextView)view.findViewById(R.id.mine_panel3_timer);
        timerLabel2 = (TextView)view.findViewById(R.id.mine_panel4_timer);
        panel3_cancel = (LinearLayout)view.findViewById(R.id.panel3_cancel) ;
        panel3_confirm = (LinearLayout)view.findViewById(R.id.panel3_confirm);
        panel4_quit = (LinearLayout)view.findViewById(R.id.home_panel4_quit);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.title_spinner,getResources().getStringArray(R.array.home_spinner_titles));
        spinner.setAdapter(adapter);
        ViewGroup.LayoutParams lp = spinner.getLayoutParams();
        lp.width = (int)(ScreenUtils.getScreenWidth(getActivity())*0.7);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/systematic.ttf");
        timerLabel.setTypeface(typeface);
        timerLabel2.setTypeface(typeface);

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

        slidingup1.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(previousState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    sliupguide.setVisibility(View.GONE);
                    prefer.setVisibility(View.VISIBLE);
                    clickable(false);
                }else if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    sliupguide.setVisibility(View.VISIBLE);
                    prefer.setVisibility(View.INVISIBLE);
                    clickable(true);
                }
            }
        });

        sto_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPosIcon();
                ImageView iv = (ImageView) sto_pos.getChildAt(0);
                iv.setImageResource(R.drawable.seat_y);
                method_choice = 1;
            }
        });

        pre_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPosIcon();
                ImageView iv = (ImageView)pre_pos.getChildAt(0);
                iv.setImageResource(R.drawable.seat_y);
                method_choice = 2;
            }
        });

        old_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPosIcon();
                ImageView iv = (ImageView)old_pos.getChildAt(0);
                iv.setImageResource(R.drawable.seat_y);
                method_choice = 3;
            }
        });

        choice_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (method_choice){
                    case 1:
                        stochastic();
                        break;
                        default:;break;
                }
            }
        });

        panel2_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panel1.setVisibility(View.VISIBLE);
                panel2.setVisibility(View.GONE);
                choice = -1;
            }
        });

        panel2_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panel2.setVisibility(View.GONE);
                panel3.setVisibility(View.VISIBLE);
                ImageView iv = (ImageView)relativeLayout.findViewById(choice);
                iv.setImageResource(R.drawable.my_seat);
                startThread();
            }
        });

        panel3_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panel3.setVisibility(View.GONE);
                panel1.setVisibility(View.VISIBLE);
                slidingup1.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                ImageView iv = (ImageView)relativeLayout.findViewById(choice);
                iv.setImageResource(R.drawable.em_seat);
                choice = -1;
            }
        });

        panel3_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panel3.setVisibility(View.GONE);
                panel4.setVisibility(View.VISIBLE);
                timerState = 2;
                timerLabel2.setText("00 : 00");
                recLen2 = 0;
            }
        });

        panel4_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panel4.setVisibility(View.GONE);
                panel1.setVisibility(View.VISIBLE);
                slidingup1.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                ImageView iv = (ImageView)relativeLayout.findViewById(choice);
                iv.setImageResource(R.drawable.em_seat);
                choice = -1;
                timerState = 1;
            }
        });
        panelListener();
        addSeats();
    }

    private void initPosIcon(){
        ImageView iv1 = (ImageView)sto_pos.getChildAt(0);
        ImageView iv2 = (ImageView)pre_pos.getChildAt(0);
        ImageView iv3 = (ImageView)old_pos.getChildAt(0);
        iv1.setImageResource(R.drawable.seat_b);
        iv2.setImageResource(R.drawable.seat_b);
        iv3.setImageResource(R.drawable.seat_b);
    }

    private void clickable(boolean state){
        int ccount = relativeLayout.getChildCount();
        for(int i=0;i<ccount;i++){
            ImageView iv =(ImageView)relativeLayout.getChildAt(i);
            iv.setClickable(state);
        }
    }

    public void addSeats(){
        int count = 0;
        int width = (int)(ScreenUtils.getScreenWidth(getActivity())/14);

        //左上
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){
                if(i==1&&j==0){
                    ImageView imageView = new ImageView(getActivity());
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,width);
                    lp.setMargins(40+(width+20)*j,40+(width+20)*i,0,0);
                    imageView.setLayoutParams(lp);
                    imageView.setImageResource(R.drawable.power);
                    relativeLayout.addView(imageView);
                    continue;
                }
                ImageView imageView = new ImageView(getActivity());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,width);
                lp.setMargins(40+(width+20)*j,40+(width+20)*i,0,0);
                imageView.setLayoutParams(lp);
                imageView.setId(count++);
                imageView.setImageResource(R.drawable.em_seat);
                imageView.setOnClickListener(this);
                relativeLayout.addView(imageView);

                Seat seat = new Seat(count - 1,Seat.Status.EMPTY,Seat.Cate.BASE);
                seats.add(seat);
            }
        }

        int start0 = (int)(0.5*width+50);
        for(int i=0;i<2;i++){
            ImageView imageView = new ImageView(getActivity());
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,width);
            lp.setMargins(start0+(width+20)*i,40+(width+20)*4 ,0,0);
            imageView.setLayoutParams(lp);
            imageView.setId(count++);
            imageView.setImageResource(R.drawable.em_seat);
            imageView.setOnClickListener(this);
            relativeLayout.addView(imageView);

            Seat seat = new Seat(count - 1,Seat.Status.EMPTY,Seat.Cate.BASE);
            seats.add(seat);
        }

        float half = ScreenUtils.getScreenWidth(getActivity())/2;

        ImageView im = new ImageView(getActivity());
        RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(width,width+10);
        llp.setMargins((int)half-(width+20)/2,20,0,0);
        im.setLayoutParams(llp);
        im.setImageResource(R.drawable.door);
        relativeLayout.addView(im);

        //中上
        for(int i=0;i<3;i++){
            for(int j=0;j<2;j++){
                if(i==2 && j==0){
                    ImageView imageView = new ImageView(getActivity());
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,width);
                    lp.setMargins(0,i*(width+20)+width*2,0,0);
                    lp.leftMargin = (int)half - width - 10;
                    imageView.setLayoutParams(lp);
                    imageView.setImageResource(R.drawable.power);
                    relativeLayout.addView(imageView);
                    continue;
                }
                ImageView imageView = new ImageView(getActivity());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,width);
                lp.setMargins(0,i*(width+20)+width*2,0,0);
                if(j==0){
                    lp.leftMargin = (int)half - width - 10;
                }else{
                    lp.leftMargin = (int)half + 10;
                }
                imageView.setLayoutParams(lp);
                imageView.setId(count++);
                imageView.setImageResource(R.drawable.em_seat);

                Seat seat = new Seat(count-1, Seat.Status.EMPTY, Seat.Cate.COM);
                seats.add(seat);

                imageView.setOnClickListener(this);
                relativeLayout.addView(imageView);
            }
        }

        //右上
        int start = (int)(2*half-80-3*width);
        for(int i=0;i<5;i++){
            for(int j=0;j<3;j++){
                if(i==4&&j==2){
                    ImageView imageView = new ImageView(getActivity());
                    RelativeLayout.LayoutParams pl = new RelativeLayout.LayoutParams(width,width);
                    pl.setMargins(start+(width+20)*j,40+(width+20)*i,0,0);
                    imageView.setLayoutParams(pl);
                    imageView.setImageResource(R.drawable.power);

                    relativeLayout.addView(imageView);
                    continue;
                }
                ImageView imageView = new ImageView(getActivity());
                RelativeLayout.LayoutParams pl = new RelativeLayout.LayoutParams(width,width);
                pl.setMargins(start+(width+20)*j,40+(width+20)*i,0,0);
                imageView.setLayoutParams(pl);
                imageView.setId(count++);
                imageView.setImageResource(R.drawable.em_seat);
                Seat seat = new Seat(count-1, Seat.Status.EMPTY, Seat.Cate.COM);
                seats.add(seat);

                imageView.setOnClickListener(this);
                relativeLayout.addView(imageView);
            }
        }
        //左下
        int startL = 40+5*(width+10)+width*2;

        ImageView iv1 = new ImageView(getActivity());
        RelativeLayout.LayoutParams pl1 = new RelativeLayout.LayoutParams(width*3,width*3);
        pl1.setMargins(40,startL,0,0);
        iv1.setLayoutParams(pl1);
        iv1.setImageResource(R.drawable.bookshelf);
        relativeLayout.addView(iv1);

        //中下
        int start2 = (int)(half-20-1.5*width);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(i==2&&j==2){
                    ImageView imageView = new ImageView(getActivity());
                    RelativeLayout.LayoutParams pl = new RelativeLayout.LayoutParams(width,width);
                    pl.setMargins(start2+(width+20)*j,startL+(width+20)*i,0,0);
                    imageView.setLayoutParams(pl);
                    imageView.setImageResource(R.drawable.power);
                    relativeLayout.addView(imageView);
                    continue;
                }
                ImageView imageView = new ImageView(getActivity());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,width);
                lp.setMargins(start2+(width+20)*j,startL+(width+20)*i,0,0);
                imageView.setLayoutParams(lp);
                imageView.setId(count++);
                imageView.setImageResource(R.drawable.em_seat);
                relativeLayout.addView(imageView);

                Seat seat = new Seat(count-1, Seat.Status.EMPTY, Seat.Cate.BASE);
                seats.add(seat);

                imageView.setOnClickListener(this);
            }
        }

        //右下
        ImageView iv2 = new ImageView(getActivity());
        RelativeLayout.LayoutParams pl2 = new RelativeLayout.LayoutParams(width*3,width*3);
        pl2.setMargins((int)(half)*2-40-width*3,startL,0,0);
        iv2.setLayoutParams(pl2);
        iv2.setImageResource(R.drawable.bookshelf);
        relativeLayout.addView(iv2);
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
            panel1.setVisibility(View.GONE);
            panel2.setVisibility(View.VISIBLE);
            String str = "小鹰已经帮你锁定了"+String.valueOf(id+1)+"号座位";
            locked_pos.setText(str);
            if(slidingup1.getPanelState()==SlidingUpPanelLayout.PanelState.COLLAPSED){
                slidingup1.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
            choice = id;
        }else{
            Toast.makeText(getActivity(),"该座位已经有人了~",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean hasAseat(){
        if(choice == -1){
            return false;
        }
        else return true;
    }

    final  android.os.Handler handler = new android.os.Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    if(timerState == 1){
                        recLen--;
                        if(recLen<=180){
                            timerLabel.setTextColor(Color.RED);
                        }
                        if(timerLabel!=null){
                            int m = recLen/60;
                            int s = recLen%60;
                            timerLabel.setText(String.format("%02d",m)+" : "+String.format("%02d",s));
                        }
                    }else if(timerState == 2){
                        recLen2++;
                        int m = recLen2/60;
                        int s = recLen2%60;
                        timerLabel2.setText(String.format("%02d",m)+" : "+String.format("%02d",s));
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
            timerLabel.setText("15 : 00");
            recLen = 900;
        }
    }
    public void stochastic(){
        int code = (int)(Math.random()*seats.size());
        while (seats.get(code).getStatus()!=Seat.Status.EMPTY){
            code = (int)(Math.random()*seats.size());
        }
        ImageView im = this.mView.findViewById(code);
        im.callOnClick();
    }

    private void panelListener(){
        panel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        panel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        panel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        panel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
