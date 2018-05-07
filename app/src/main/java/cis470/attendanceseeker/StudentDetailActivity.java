package cis470.attendanceseeker;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StudentDetailActivity extends BaseActivity {

    EditText mStudentIdEditText;
    EditText mStudentNameEditText;
    EditText mMacAddressEditText;

    DBAdapter db = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Student Detail");

        mStudentIdEditText = findViewById(R.id.editTextStudentId);
        mStudentNameEditText = findViewById(R.id.ediTextStudentName);
        mMacAddressEditText = findViewById(R.id.editTextMacAddress);

        String studentId = (String) getIntent().getSerializableExtra("studentId");

        if(studentId != null) {
            db.open();
            StudentDevice studentDevice = db.getRecord(studentId);
            if(studentDevice != null) {
                mStudentIdEditText.setText(studentDevice.getStudentId());
                mStudentNameEditText.setText(studentDevice.getStudentName());
                mMacAddressEditText.setText(studentDevice.getMacAddress());
            }
        }
    }

    protected int getLayoutResource() {
        return R.layout.student_detail;
    }

    public void OnClickCancel(View view) {
        finish();
    }

    public void OnClickSave(View view) {
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
        try {
            db.open();
            db.insertStudentDevice(studentDevice);
            db.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show();
        }
    }
}
