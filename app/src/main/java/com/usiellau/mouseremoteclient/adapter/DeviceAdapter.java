package com.usiellau.mouseremoteclient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.usiellau.mouseremoteclient.R;
import com.usiellau.mouseremoteclient.entity.DeviceInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private ArrayList<DeviceInfo> mData;
    private OnItemClickListener onItemClickListener;

    public DeviceAdapter(ArrayList<DeviceInfo> mData){
        this.mData = mData;
    }

    public void updateData(ArrayList<DeviceInfo> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    public ArrayList<DeviceInfo> getData(){
        return mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeviceInfo deviceInfo = mData.get(position);
        holder.tvDeviceName.setText(deviceInfo.getHostname());
        holder.tvDeviceIp.setText(deviceInfo.getIp());
        holder.tvDevicePort.setText(String.valueOf(deviceInfo.getPort()));
        if(onItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_device_name) TextView tvDeviceName;
        @BindView(R.id.tv_device_ip) TextView tvDeviceIp;
        @BindView(R.id.tv_device_port) TextView tvDevicePort;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
