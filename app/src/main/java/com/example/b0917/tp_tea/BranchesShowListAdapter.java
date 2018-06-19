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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class BranchesShowListAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<BranchData.Branch> branches;
    private Context context;
    int currentItem = -1;
    float scale ;

    public BranchesShowListAdapter(Context context, List<BranchData.Branch> branches){
        myInflater = LayoutInflater.from(context);
        this.branches = branches;
        this.context = context;
        scale = context.getResources().getDisplayMetrics().density;
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
            convertView = myInflater.inflate(R.layout.branch_list_view, null);
            holder = new ViewHolder(
                    (RelativeLayout) convertView.findViewById(R.id.showArea),
                    (ImageView) convertView.findViewById(R.id.openORclose),
                    (TextView) convertView.findViewById(R.id.storeName),
                    (TextView) convertView.findViewById(R.id.phone),
                    (ImageButton) convertView.findViewById(R.id.showMapButton),
                    (RelativeLayout) convertView.findViewById(R.id.hideArea),
                    (TextView) convertView.findViewById(R.id.address)
            );
            convertView.setTag(holder);
        }else{
            Log.i("convertView != null","holder = (ViewHolder) convertView.getTag();");
            holder = (ViewHolder) convertView.getTag();
        }

        //every 2 item show gray background
        if(position % 2 == 0){
            convertView.setBackgroundColor(Color.parseColor("#00000000"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#10303030"));
        }

        holder.showArea.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //用 currentItem 记录点击位置
                Log.i("start click cell","START");
                int tag = (Integer) view.getTag();
                if (tag == currentItem) { //再次点击
                    currentItem = -1; //给 currentItem 一个无效值
                } else {
                    currentItem = tag;
                }
                //通知adapter数据改变需要重新加载
                notifyDataSetChanged(); //必须有的一步
                Log.i("end click cell","END");
            }
        });
        final BranchData.Branch branch = (BranchData.Branch) getItem(position);

        holder.showArea.setTag(position);
        holder.storeNmae.setText(branch.getStoreName());
        holder.phone.setText(branch.getPhoneNum());
        holder.showMapButton.setImageResource(R.drawable.ic_maptag);
        holder.address.setText(branch.getAddress());
        holder.openORcloce.setImageResource(R.drawable.ic_open);


        if (holder.address.getText().length() <= 13){
            holder.hideArea.getLayoutParams().height = (int) (23 * scale);
        }else{
            holder.hideArea.getLayoutParams().height = (int) (45 * scale);
        }

        if (currentItem == position) {
            holder.hideArea.setVisibility(View.VISIBLE);
            holder.openORcloce.setImageResource(R.drawable.ic_close);
        } else {
            holder.hideArea.setVisibility(View.GONE);
            holder.openORcloce.setImageResource(R.drawable.ic_open);
        }

        //set showMapButton onClick
        holder.showMapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("start click mapButton","START");
                Uri gmmIntentUri = Uri.parse("geo:" + branch.getCoordinate() + "?q=" + branch.getStoreName() + "+茶湯會&z=18");
                Log.i("Uri",gmmIntentUri.toString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
                Log.i("end click mapButton","END");
            }
        });
        return convertView;
    }

    private class ViewHolder {
        RelativeLayout showArea;
        ImageView openORcloce;
        TextView storeNmae;
        TextView phone;
        ImageButton showMapButton;

        RelativeLayout hideArea;
        TextView address;

        public ViewHolder(RelativeLayout showArea, ImageView openORcloce, TextView storeNmae, TextView phone, ImageButton showMapButton, RelativeLayout hideArea,  TextView address){
            this.showArea = showArea;
            this.openORcloce = openORcloce;
            this.storeNmae = storeNmae;
            this.phone = phone;
            this.showMapButton = showMapButton;
            this.hideArea = hideArea;
            this.address = address;
        }
    }
}
