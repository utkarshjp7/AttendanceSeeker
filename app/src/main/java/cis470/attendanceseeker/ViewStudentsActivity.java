package cis470.attendanceseeker;


import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;

public class ViewStudentsActivity extends ListActivity {

    DBAdapter db = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_students);
        setTitle("Student List");

        listAllStudents();
    }

    private void listAllStudents() {
        db.open();
        Cursor cursor = db.getAllRecords();
        startManagingCursor(cursor);

        String[] from = new String[] { DBAdapter.COL_STUDENT_ID };
        int[] to = new int[]{R.id.student_list_row };
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.view_students_row, cursor, from, to, 0);

        this.setListAdapter(cursorAdapter);
        db.close();
    }
}
