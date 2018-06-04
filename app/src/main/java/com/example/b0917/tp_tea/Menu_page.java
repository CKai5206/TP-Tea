package com.example.b0917.tp_tea;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b0917.tp_tea.models.menu_category;
import com.example.b0917.tp_tea.models.menu_drinks;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;


public class Menu_page extends Fragment{
    View inflatedView;
    ArrayList<menu_category> menu_categories;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflatedView = inflater.inflate(R.layout.menu_page,container,false);
        LinearLayout layout = (LinearLayout)this.inflatedView.findViewById(R.id.LinearLayout);
        try{
            String json = null;
            InputStream is = getContext().getAssets().open("menu_Event.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
            Log.i(json,"json");
        }catch (IOException ex){
            ex.printStackTrace();
        }
//        Animation slide_in = AnimationUtils.loadAnimation(getActivity(),R.anim.slide);
//        for(int i = 0;i < 10;i++){
//            Button btn = new Button(getActivity());
//            btn.setId(i);
//            final int btn_ = btn.getId();
//            btn.setText("btn" + btn_);
////            btn.startAnimation(slide_in);
//            layout.addView(btn);

//        }
        return inflatedView;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
