package cis470.attendanceseeker;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterStudentActivity extends BaseActivity {

    EditText mStudentIdEditText;
    EditText mStudentNameEditText;
    EditText mMacAddressEditText;

    DBAdapter db = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Register new student");

        mStudentIdEditText = findViewById(R.id.studentId);
        mStudentNameEditText = findViewById(R.id.studentName);
        mMacAddressEditText = findViewById(R.id.macAddress);
    }

    protected int getLayoutResource() {
        return R.layout.register_student;
    }

    protected void OnClickCancel(View view) {
        finish();
    }

    protected void OnClickSave(View view) {
        String studentId = mStudentIdEditText.getText().toString();
        String studentName = mStudentNameEditText.getText().toString();
        String macAddress = mMacAddressEditText.getText().toString();

        if(studentId.isEmpty()) {
            Toast.makeText(this, "Student id is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(studentName.isEmpty()) {
            Toast.makeText(this, "Student name is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(macAddress.isEmpty()) {
            Toast.makeText(this, "Mac Address is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        StudentDevice studentDevice = new StudentDevice(studentId, studentName, macAddress);
        db.open();
        db.insertStudentDevice(studentDevice);
        db.close();
    }
}
