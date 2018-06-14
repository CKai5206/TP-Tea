package com.example.b0917.tp_tea;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.b0917.tp_tea.models.menu_drinks;

import java.util.ArrayList;

public class MenuDrinks extends Fragment{
    View inflatedView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.menu_drinks,container,false);
        LinearLayout layout = (LinearLayout) this.inflatedView.findViewById(R.id.LinearLayout);
        jsonfileReceiver(layout);
        return inflatedView;
    }
    private void jsonfileReceiver(LinearLayout layout){
        Bundle bundle = getArguments();
        ArrayList<menu_drinks> menu_drinks = (ArrayList<menu_drinks>) bundle.getSerializable("list");
        for(int indexofdinks = 0 ;indexofdinks < menu_drinks.size();indexofdinks++){
            Button btn = new Button(getActivity());
            btn.setId(indexofdinks);
            btn.setText(menu_drinks.get(indexofdinks).name);
            layout.addView(btn);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
