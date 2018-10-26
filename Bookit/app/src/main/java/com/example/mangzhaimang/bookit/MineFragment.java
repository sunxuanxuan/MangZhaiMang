package com.example.mangzhaimang.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MineFragment extends Fragment {
    private TextView mine_name;
    private ImageView mine_next;
    private CardView setting;

    private SlidingUpPanelLayout time_slidingup;
    private CardView time_exhibit;
    private CardView message_record;
    private CardView myseats;
    private CardView violation_record;

    public static MineFragment newInstance(){
        Bundle args = new Bundle();
        args.putString("name","我的");
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.mine_layout, container, false);
        RelativeLayout relativeLayout = view.findViewById(R.id.mine_personal);

        mine_name = (TextView)view.findViewById(R.id.mine_cur_name);
        mine_next = (ImageView)view.findViewById(R.id.mine_next);
        setting = (CardView)view.findViewById(R.id.mine_setting_cv);
        time_slidingup = (SlidingUpPanelLayout)view.findViewById(R.id.time_slidingup);
        time_exhibit = (CardView)view.findViewById(R.id.time_exhibit);
        message_record = (CardView)view.findViewById(R.id.mine_message_record);
        myseats = (CardView)view.findViewById(R.id.mine_myseats);
        violation_record = (CardView)view.findViewById(R.id.mine_violation_record);

        mine_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PersonalMessageActivity.class);
                intent.putExtra("old_name",mine_name.getText().toString());
                startActivity(intent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SettingActivity.class);
                startActivity(intent);
            }
        });

        time_exhibit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_slidingup.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });

        time_slidingup.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    mine_next.setClickable(false);
                }else if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    mine_next.setClickable(true);
                }
            }
        });

        message_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MessageRecord.class);
                startActivity(intent);
            }
        });

        myseats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MySeatsActivity.class);
                startActivity(intent);
            }
        });

        violation_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ViolationRecordActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
