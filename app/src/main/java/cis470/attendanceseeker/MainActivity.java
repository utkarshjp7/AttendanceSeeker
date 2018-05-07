package cis470.attendanceseeker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends BaseActivity {

    private ListView mClassList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Attendance Seeker");

        mClassList = findViewById(R.id.class_list);
        mClassList.setClickable(true);
        mClassList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String className = (String) mClassList.getItemAtPosition(position);
                Intent intent = new Intent("cis470.attendanceseeker.Class");
                intent.putExtra("className", className);
                startActivity(intent);
            }
        });
    }

    protected int getLayoutResource() {
        return R.layout.activity_main;
    }
}
