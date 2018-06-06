package com.example.b0917.tp_tea;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class BranchesShowListAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<BranchData.Branch> branches;
    private Context context;

    public BranchesShowListAdapter(Context context, List<BranchData.Branch> branches){
        myInflater = LayoutInflater.from(context);
        this.branches = branches;
        this.context = context;
    }

    @Override
    public int getCount() {
        return branches.size();
    }

    @Override
    public Object getItem(int position) {
        return branches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return branches.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        //set holder
        if(convertView==null){
            convertView = myInflater.inflate(R.layout.branche_list_view, null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.storeName),
                    (TextView) convertView.findViewById(R.id.phone),
                    (ImageButton) convertView.findViewById(R.id.showMapButton)
            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //every 2 item show gray background
        if(position % 2 == 0){
            convertView.setBackgroundColor(Color.parseColor("#00000000"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#10303030"));
        }

        final BranchData.Branch branch = (BranchData.Branch) getItem(position);

        holder.storeNmae.setText(branch.getStoreName());
        holder.phone.setText(branch.getPhoneNum());
        holder.showMapButton.setImageResource(R.drawable.ic_maptag);

        //set showMapButton onClick
        holder.showMapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:" + branch.getCoordinate() + "?q=" + branch.getStoreName() + "+茶湯會&z=18");
                Log.i("Uri",gmmIntentUri.toString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView storeNmae;
        TextView phone;
        ImageButton showMapButton;

        public ViewHolder(TextView storeNmae, TextView phone, ImageButton showMapButton){
            this.storeNmae = storeNmae;
            this.phone = phone;
            this.showMapButton = showMapButton;
        }
    }
}
