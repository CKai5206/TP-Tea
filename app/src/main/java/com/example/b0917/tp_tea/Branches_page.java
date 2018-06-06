package com.example.b0917.tp_tea;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Branches_page extends Fragment{
    String json = null;

    View inflatedView;
    Spinner citySpinner;
    Spinner districtSpinner;
    ListView branchesListView;

    List<BranchData> branches;
    List<String> cityDropListValue = new ArrayList<String>();
    List<String> districtDropListValue = new ArrayList<String>();
    List<BranchData.Branch> branchesShowList = new ArrayList<BranchData.Branch>();

    ArrayAdapter<String> cityAdapter;
    ArrayAdapter<String> districtsAdapter;
    BranchesShowListAdapter branchesShowListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.branches_page,container,false);

        citySpinner = inflatedView.findViewById(R.id.citySpinner);
        districtSpinner = inflatedView.findViewById(R.id.districtSpinner);
        branchesListView = inflatedView.findViewById(R.id.branchesListView);
        Gson gson = new Gson();

        try {
            Log.i("onCreateView start","START");

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
            cityAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, cityDropListValue);
            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            citySpinner.setAdapter(cityAdapter);
            cityAdapter.notifyDataSetChanged();

            //create citySpinner listener
            citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    Log.i("you select city",citySpinner.getSelectedItem().toString());

                    //find index of chose city & get districts list
                    districtDropListValue = Arrays.asList(branches.get(findIndexByCity(branches,citySpinner.getSelectedItem().toString())).getDistricts());
                    Log.i("get districts list",districtDropListValue.toString());

                    //change districtSpinner value
                    districtsAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, districtDropListValue);
                    districtsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    districtSpinner.setAdapter(districtsAdapter);
                    districtsAdapter.notifyDataSetChanged();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //create districtSpinner listenedistrict
            districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.i("district selcet",districtSpinner.getSelectedItem().toString());

                    //change listView item
                    branchesShowList = findShowBranchesByDistrict(branches.get(findIndexByCity(branches,citySpinner.getSelectedItem().toString())),districtSpinner.getSelectedItem().toString());
                    branchesShowListAdapter = new BranchesShowListAdapter(getActivity().getApplicationContext(),branchesShowList);
                    branchesListView.setAdapter(branchesShowListAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            Log.i("onCreateView end","END");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inflatedView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public int findIndexByCity(List<BranchData> branches, String city){
        int i = 0;
        while (!city.equals(branches.get(i).getCity())){
            i++;
        }
        return i;
    }

    public List<BranchData.Branch> findShowBranchesByDistrict(BranchData branchData, String district){
        List<BranchData.Branch> branchList = new ArrayList<BranchData.Branch>();
        for (int i = 0; i < branchData.getBranches().size() ; i++){
            if (branchData.getBranches().get(i).getDistrict().equals(district)){
                branchList.add(branchData.getBranches().get(i));
            }
        }
        Log.i("branchList first item",branchList.get(0).getStoreName());
        return branchList;
    }
}
