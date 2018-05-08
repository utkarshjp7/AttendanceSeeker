package cis470.attendanceseeker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import cis470.attendanceseeker.entities.DeviceInfo;
import cis470.attendanceseeker.entities.Student;
import cis470.attendanceseeker.entities.StudentAttendance;

public class AttendanceListActivity extends BaseActivity {

    private String mClassName;
    private BluetoothAdapter mBTAdapter;
    private ArrayList<StudentAttendance> mAttendanceList;
    private AttendanceRecyclerAdapter mAdapter;
    private BluetoothReceiver mBTReceiver;
    private DBAdapter db = new DBAdapter(this);
    private final int mBTDiscoveryTimePeriod = 60; //in seconds
    private long mBTDiscoveryStartTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = (String) getIntent().getSerializableExtra("className");
        setTitle(mClassName + " Attendance");

        mAttendanceList = new ArrayList<>();
        initializeAttendaceList();

        mAdapter = new AttendanceRecyclerAdapter(mAttendanceList);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();

        mBTReceiver = new BluetoothReceiver(new BluetoothReceiver.BluetoothListener() {
            @Override
            public void onDetectDevice(DeviceInfo deviceInfo) {
                db.open();
                final Student student = db.getStudentByMacAddress(deviceInfo.getMacAddress());
                if(student != null) {
                    for(int i = 0; i < mAttendanceList.size(); i++) {
                        StudentAttendance item = mAttendanceList.get(i);
                        if(item.getStudentId().equals(student.getStudentId())) {
                            item.setPresent(true);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
                db.close();
            }

            @Override
            public void onFinishDiscovery() {
                long curTime = (new Date()).getTime();
                long secondsSinceFirstDiscovery = ((curTime - mBTDiscoveryStartTime) / 1000);
                if(secondsSinceFirstDiscovery < mBTDiscoveryTimePeriod) {
                    discoverDevices(false);
                    System.out.println("Discovering again");
                }
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mBTReceiver, filter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView attendanceRecyclerView = findViewById(R.id.attendance_list);
        attendanceRecyclerView.setLayoutManager(mLayoutManager);
        attendanceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        attendanceRecyclerView.setAdapter(mAdapter);
        discoverDevices(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBTAdapter != null) {
            mBTAdapter.cancelDiscovery();
        }
        System.out.println("Discovering");
        this.unregisterReceiver(mBTReceiver);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.attendance_list;
    }

    private void discoverDevices(boolean firstDiscovery) {
        if (mBTAdapter.isDiscovering()) {
            mBTAdapter.cancelDiscovery();
        }

        if(firstDiscovery) {
            mBTDiscoveryStartTime = (new Date()).getTime();
        }
        mBTAdapter.startDiscovery();
    }

    private void initializeAttendaceList() {
        db.open();
        Cursor cursor = db.getStudentsByClass(mClassName);
        try {
            while (cursor.moveToNext()) {
                int idxStudentName = cursor.getColumnIndex(DBAdapter.COL_STUDENT_NAME);
                int idxStudentId = cursor.getColumnIndex(DBAdapter.COL_STUDENT_ID);
                String studentName = cursor.getString(idxStudentName);
                String studentId = cursor.getString(idxStudentId);
                StudentAttendance studentAttendance = new StudentAttendance(studentId, studentName, false);
                mAttendanceList.add(studentAttendance);
            }
        } finally {
            cursor.close();
        }
    }

}
