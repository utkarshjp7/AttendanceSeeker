package cis470.attendanceseeker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import cis470.attendanceseeker.entities.DeviceInfo;

public class RegisterStudentActivity extends BaseActivity {

    private String mClassName;
    private BluetoothAdapter mBTAdapter;
    private BluetoothReceiver mBTReceiver;
    private ArrayList<DeviceInfo> mDeviceList;
    private DeviceRecyclerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = (String) getIntent().getSerializableExtra("className");
        setTitle(mClassName + " Register cis470.attendanceseeker.entities.Student");

        mDeviceList = new ArrayList<>();
        mAdapter = new DeviceRecyclerAdapter(mDeviceList);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        mBTReceiver = new BluetoothReceiver(new BluetoothReceiver.BluetoothListener() {
            @Override
            public void onDetectDevice(DeviceInfo deviceInfo) {
                mDeviceList.add(deviceInfo);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFinishDiscovery() {
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mBTReceiver, filter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView deviceRecyclerView = findViewById(R.id.device_list);
        deviceRecyclerView.setLayoutManager(mLayoutManager);
        deviceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        deviceRecyclerView.setAdapter(mAdapter);

        deviceRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, deviceRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent("cis470.attendanceseeker.StudentDetail");
                intent.putExtra("className", mClassName);
                intent.putExtra("macAddress", mDeviceList.get(position).getMacAddress());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBTAdapter != null) {
            mBTAdapter.cancelDiscovery();
        }
        this.unregisterReceiver(mBTReceiver);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.register_student;
    }

    public void onClickRegisterManually(View view) {
        Intent intent = new Intent("cis470.attendanceseeker.StudentDetail");
        intent.putExtra("className", mClassName);
        startActivity(intent);
    }

    public void onClickShowNearbyDevices(View view) {
        if (mBTAdapter.isDiscovering()) {
            mBTAdapter.cancelDiscovery();
        }
        mBTAdapter.startDiscovery();
        Toast.makeText(getApplicationContext(), "Finding Nearby Devices", Toast.LENGTH_LONG).show();
    }



}
