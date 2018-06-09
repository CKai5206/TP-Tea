package com.example.b0917.tp_tea;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.b0917.tp_tea.models.menu_category;

import java.util.ArrayList;

public class category_detail_page extends Fragment{
    View inflatedView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.category_detail_page,container,false);
//        ConstraintLayout layout = (ConstraintLayout)this.inflatedView.findViewById(R.id.ConstrainLayout);
//        jsonfileReceiver(layout);
        return inflatedView;
    }
//    public void jsonfileReceiver(ConstraintLayout layout){
//        ArrayList<menu_category> menu_categories = (ArrayList<menu_category>)getArguments().getSerializable("menu_categories");
//        int onclick_category = (int)getArguments().get("onclick_category");
//        for(int indexofcategory = 0;indexofcategory < menu_categories.size();indexofcategory++){
//            Button btn = new Button(getActivity());
//            btn.setId(indexofcategory);
//            btn.setText(menu_categories.get(indexofcategory).category);
//            layout.addView(btn);
//        }
//    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
