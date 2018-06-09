package com.example.b0917.tp_tea;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.google.gson.reflect.TypeToken;

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
        loadmenufile(layout);
        return inflatedView;
    }


    public void loadmenufile(LinearLayout layout){
        try{
            String json = null;
            InputStream is = getContext().getAssets().open("menu_Event.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
            Gson gson = new Gson();
            menu_categories = gson.fromJson(json, new TypeToken<ArrayList<menu_category>>() {}.getType());
            for(int indexofcategory = 0;indexofcategory < menu_categories.size();indexofcategory++){
                Button btn = new Button(getActivity());
                btn.setId(indexofcategory);
                btn.setText(menu_categories.get(indexofcategory).category);
                layout.addView(btn);
                Bundle bundle = new Bundle();
                bundle.putSerializable("menu_categories",menu_categories);
                bundle.putInt("onclick_category",btn.getId());
                btn.setOnClickListener(buttonListener);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private Button.OnClickListener buttonListener = new Button.OnClickListener(){
        Fragment fragment = null;
        @Override
        public void onClick(View view) {
            fragment = new category_detail_page();
            FragmentTransaction fragTrans = getFragmentManager().beginTransaction();

            fragTrans.replace(R.id.content_framelayout,fragment);
            fragTrans.addToBackStack(null);
            fragTrans.commit();
        }
    };
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
