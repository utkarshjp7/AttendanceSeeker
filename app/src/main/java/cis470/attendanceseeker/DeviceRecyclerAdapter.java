package cis470.attendanceseeker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import cis470.attendanceseeker.entities.DeviceInfo;

public class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.MyViewHolder> {

    private List<DeviceInfo> mDeviceList;

    public DeviceRecyclerAdapter(List<DeviceInfo> deviceList) {
        this.mDeviceList = deviceList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, macAddress;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.device_list_name);
            macAddress = view.findViewById(R.id.device_list_macAddress);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DeviceInfo device = mDeviceList.get(position);
        holder.name.setText(device.getName());
        holder.macAddress.setText(device.getMacAddress());
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }
}
