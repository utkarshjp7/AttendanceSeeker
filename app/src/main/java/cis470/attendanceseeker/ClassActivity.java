package cis470.attendanceseeker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ClassActivity extends BaseActivity {

    private String mClassName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = (String) getIntent().getSerializableExtra("className");
        setTitle(mClassName);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.class_main;
    }

    public void onClickRegisterNewStudent(View view) {
        Intent intent = new Intent("cis470.attendanceseeker.RegisterStudentActivity");
        intent.putExtra("className", mClassName);
        startActivity(intent);
    }

    public void onClickAllStudents(View view) {
        Intent intent = new Intent("cis470.attendanceseeker.ViewStudents");
        intent.putExtra("className", mClassName);
        startActivity(intent);
    }

    public void onClickTakeAttendance(View view) {
        Intent intent = new Intent("cis470.attendanceseeker.AttendanceList");
        intent.putExtra("className", mClassName);
        startActivity(intent);
    }

}
