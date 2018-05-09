package com.example.b0917.tp_tea;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Home_page extends Fragment{
    Button menu_btn,branches_btn,event_btn,game_btn;
    View inflatedView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.home_page,container,false);
        getButtonView();
        setButtonEvent();
        return inflatedView;
    }

    public void getButtonView(){
        menu_btn = this.inflatedView.findViewById(R.id.menu_btn);
        branches_btn = this.inflatedView.findViewById(R.id.branches_btn);
        event_btn = this.inflatedView.findViewById(R.id.event_btn);
        game_btn = this.inflatedView.findViewById(R.id.game_btn);
    }

    public void setButtonEvent() {
        menu_btn.setOnClickListener(buttonListener);
        branches_btn.setOnClickListener(buttonListener);
        event_btn.setOnClickListener(buttonListener);
        game_btn.setOnClickListener(buttonListener);
    }

    private Button.OnClickListener buttonListener = new Button.OnClickListener(){
        Fragment fragment = null;
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.menu_btn:
                    fragment = new Menu_page();
                    break;
                case R.id.branches_btn:
                    fragment = new Branches_page();
                    break;
                case R.id.event_btn:
                    fragment = new Event_page();
                    break;
                case R.id.game_btn:
                    fragment = new Game_page();
                    break;
            }
            FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
            fragTrans.replace(R.id.home_page,fragment);
            fragTrans.commit();
        }
    };
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
