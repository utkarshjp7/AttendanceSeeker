package cis470.attendanceseeker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends BaseActivity {

    private TextView RSSIText;
    private BluetoothAdapter BTAdapter;
    private BroadcastReceiver Receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Attendance Seeker");

        RSSIText = findViewById(R.id.textView);
        BTAdapter = BluetoothAdapter.getDefaultAdapter();
        Receiver = new BroadcastReceiver() {

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
                        RSSIText.append(newDevice);
                    }
                }
            }
        };

        this.registerReceiver(Receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                RSSIText.setText("");
                if (BTAdapter.isDiscovering()) {
                    BTAdapter.cancelDiscovery();
                }
                BTAdapter.startDiscovery();
                Toast.makeText(getApplicationContext(), "Discovery started", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void OnClickRegisterNewStudent(View view) {
        Intent i = new Intent("cis470.attendanceseeker.RegisterStudent");
        startActivity(i);
    }

    public void OnClickAllStudents(View view) {
        Intent i = new Intent("cis470.attendanceseeker.ViewStudents");
        startActivity(i);
    }

    protected int getLayoutResource() {
        return R.layout.activity_main;
    }
}
