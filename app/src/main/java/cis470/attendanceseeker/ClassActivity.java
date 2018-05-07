package cis470.attendanceseeker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class ClassActivity extends BaseActivity {

    private String mClassName;
    private TextView mRSSIText;
    private BluetoothAdapter mBTAdapter;
    private BroadcastReceiver mReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = (String) getIntent().getSerializableExtra("className");
        setTitle(mClassName);

        mRSSIText = findViewById(R.id.textView);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        mReceiver = new BluetoothReceiver();
        this.registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.class_main;
    }

    public void onClickRegisterNewStudent(View view) {
        Intent intent = new Intent("cis470.attendanceseeker.StudentDetail");
        startActivity(intent);
    }

    public void onClickAllStudents(View view) {
        Intent intent = new Intent("cis470.attendanceseeker.ViewStudents");
        intent.putExtra("className", mClassName);
        startActivity(intent);
    }

    public void onClickAttendance(View view) {
        mRSSIText.setText("");
        if (mBTAdapter.isDiscovering()) {
            mBTAdapter.cancelDiscovery();
        }
        mBTAdapter.startDiscovery();
        Toast.makeText(getApplicationContext(), "Discovery started", Toast.LENGTH_LONG).show();
    }

    public class BluetoothReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                String macAddress = device.getAddress();
                if(name != null) {
                    String newDevice = String.format(Locale.US, "Name: %s Address: %s RSSI %d", name, macAddress, rssi);
                    mRSSIText.append(newDevice);
                }
            }
        }
    }
}
