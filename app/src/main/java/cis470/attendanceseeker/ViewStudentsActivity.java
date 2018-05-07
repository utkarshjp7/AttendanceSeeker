package cis470.attendanceseeker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ViewStudentsActivity extends BaseActivity {

    DBAdapter db = new DBAdapter(this);
    private String mClassName;
    private ListView mStudentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = (String) getIntent().getSerializableExtra("className");
        setTitle(mClassName + " Students");

        mStudentList = findViewById(R.id.student_list);

        mStudentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView < ? > adapter, View view,int position, long arg){
                TextView textViewStudentId = view.findViewById(R.id.student_id);
                Intent intent = new Intent("cis470.attendanceseeker.StudentDetail");
                intent.putExtra("studentId", textViewStudentId.getText());
                startActivity(intent);
            }
        });

        listAllStudents();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.view_students;
    }

    private void listAllStudents() {
        db.open();
        Cursor cursor = db.getAllRecords();
        startManagingCursor(cursor);

        String[] from = new String[] { DBAdapter.COL_STUDENT_ID, DBAdapter.COL_STUDENT_NAME };
        int[] to = new int[]{ R.id.student_id, R.id.student_name };
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.view_students_row, cursor, from, to, 0);

        mStudentList.setAdapter(cursorAdapter);
        db.close();
    }
}
