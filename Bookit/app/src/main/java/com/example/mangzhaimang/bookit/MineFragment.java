package com.example.mangzhaimang.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MineFragment extends Fragment {
    private TextView mine_name;
    private RelativeLayout setting;
    private ImageView mine_next;

    public static MineFragment newInstance(){
        Bundle args = new Bundle();
        args.putString("name","首页");
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
        setting = (RelativeLayout)view.findViewById(R.id.mine_setting_container);
        mine_next = (ImageView)view.findViewById(R.id.mine_next);

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

        return view;
    }
}
