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
import android.widget.LinearLayout;

import com.example.b0917.tp_tea.models.menu_category;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


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
                btn.setOnClickListener(new Button.OnClickListener(){
                    Fragment fragment = null;
                    @Override
                    public void onClick(View view) {
                        fragment = new MenuDrinks();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("list",menu_categories.get(view.getId()).item);
                        bundle.putInt("onclick_category",view.getId());
                        fragment.setArguments(bundle);
                        FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
                        fragTrans.replace(R.id.content_framelayout,fragment);
                        fragTrans.addToBackStack(null);
                        fragTrans.commit();
                    }
                });
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
