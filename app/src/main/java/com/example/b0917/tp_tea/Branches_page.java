package com.example.b0917.tp_tea;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Branches_page extends Fragment{
    View inflatedView;
    String json = null;
    Spinner citySpinner;
    Spinner districtSpinner;
    List<BranchData> branches;
    List<String> cityDropListValue = new ArrayList<String>();
    List<String> districtDropListValue = new ArrayList<String>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.branches_page,container,false);

        citySpinner = inflatedView.findViewById(R.id.citySpinner);
        districtSpinner = inflatedView.findViewById(R.id.districtSpinner);
        Gson gson = new Gson();

        try {
            Log.i("start","start");

            //read branchesData.json to String
            InputStream inputStream = getContext().getAssets().open("branchesData.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String (buffer, "UTF-8");
            Log.i("read file","read");

            //json String to GSON
            branches = gson.fromJson(json, new TypeToken<List<BranchData>>(){}.getType());
            Log.i("branches list create",branches.toString());

            //get all cities to list
            for(int i = 0 ; i < branches.size() ; i++) {
                cityDropListValue.add(branches.get(i).getCity());
            }
            Log.i("cityDropList create",cityDropListValue.toString());

            //put city list to spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, cityDropListValue);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Log.i("cityAdapter should be",adapter.getItem(0).toString());
            citySpinner.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Log.i("cityAdapter really",citySpinner.getAdapter().getItem(0).toString());


            Log.i("end","END");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inflatedView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
