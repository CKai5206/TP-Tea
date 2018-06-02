package com.example.b0917.tp_tea;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;

public class Branches_page extends Fragment{
    View inflatedView;
    String json = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.branches_page,container,false);
        Gson gson = new Gson();
        try {
            Log.i("start","start");
            InputStream inputStream = getContext().getAssets().open("branchesData.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String (buffer, "UTF-8");
            List<BranchData> branches = gson.fromJson(json, new TypeToken<List<BranchData>>(){}.getType());
            Log.i("its work",branches.get(0).getBranches().get(0).getAddress());
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
