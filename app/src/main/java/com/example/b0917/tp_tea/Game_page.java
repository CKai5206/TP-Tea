package com.example.b0917.tp_tea;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import com.example.b0917.tp_tea.LuckyPanView;
import com.example.b0917.tp_tea.Circleview;

import static com.example.b0917.tp_tea.R.id.ottery;


public class Game_page extends Fragment{

    Circleview claert;
    private LuckyPanView mLuckyPanView;
    private ImageView mStartBtn;
    int test;
    private TextView textView;
    View inflatedView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.game_page,container,false);
        FrameLayout layout = inflatedView.findViewById(R.id.ottery);
        int screnWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        final Random random = new Random();
        claert = new Circleview(getContext(), screnWidth);
        layout.addView(claert, new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.FILL_PARENT, DensityUtil.dip2px(getContext(), 300)));
        inflatedView.findViewById(R.id.begin_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int place = 4;
                Log.e("当前的位置", place + "");
                claert.setStopPlace(place);
                claert.setStopRoter(false);
            }
        });
        inflatedView.findViewById(R.id.end_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                claert.setStopRoter(true);
            }
        });
        mLuckyPanView = (LuckyPanView) inflatedView.findViewById(R.id.id_luckypan);
        mStartBtn = (ImageView) inflatedView.findViewById(R.id.id_start_btn);
        textView  = (TextView) inflatedView.findViewById(R.id.ZZ);
        final Handler handler = new Handler();
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mLuckyPanView.isStart()) {
                    test = random.nextInt(6);
                    textView.setText("未抽獎");
                    mLuckyPanView.luckyStart(test);
                } else {
                    if (!mLuckyPanView.isShouldEnd())
                    {
                        mLuckyPanView.luckyEnd();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(mLuckyPanView.setLucky(test));
                            }
                        },2400);

                    }
                }
            }
        });
        return inflatedView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
