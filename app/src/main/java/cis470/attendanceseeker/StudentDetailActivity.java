package cis470.attendanceseeker;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cis470.attendanceseeker.entities.Student;

public class StudentDetailActivity extends BaseActivity {

    String mClassName;
    EditText mStudentIdEditText;
    EditText mStudentNameEditText;
    EditText mMacAddressEditText;

    DBAdapter db = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = (String) getIntent().getSerializableExtra("className");
        setTitle(mClassName + " cis470.attendanceseeker.entities.Student Detail");

        mStudentIdEditText = findViewById(R.id.editTextStudentId);
        mStudentNameEditText = findViewById(R.id.ediTextStudentName);
        mMacAddressEditText = findViewById(R.id.editTextMacAddress);

        String studentId = (String) getIntent().getSerializableExtra("studentId");
        String macAddress = (String) getIntent().getSerializableExtra("macAddress");

        if(studentId != null) {
            db.open();
            Student student = db.getStudentById(studentId);
            if(student != null) {
                mStudentIdEditText.setText(student.getStudentId());
                mStudentNameEditText.setText(student.getStudentName());
                mMacAddressEditText.setText(student.getMacAddress());
            }
        }

        if(macAddress != null) {
            mMacAddressEditText.setText(macAddress);
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
            Toast.makeText(this, "cis470.attendanceseeker.entities.Student id is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(studentName.isEmpty()) {
            Toast.makeText(this, "cis470.attendanceseeker.entities.Student name is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(macAddress.isEmpty()) {
            Toast.makeText(this, "Mac Address is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Student student = new Student(studentId, studentName, macAddress, mClassName);
        try {
            db.open();
            db.insertStudentDevice(student);
            db.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show();
        }
    }
}
