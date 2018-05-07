package cis470.attendanceseeker;

import android.content.ContentValues;
import android.database.SQLException;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

    public static final String COL_ID = "_id";
    public static final String COL_STUDENT_ID = "StudentId";
    public static final String COL_STUDENT_NAME = "StudentName";
    public static final String COL_MAC_ADDRESS = "MacAddress";
    public static final String COL_CLASS_NAME = "Class";
    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "AttendanceSeeker";
    private static final String TABLE_DEVICE = "devices";
    private static final String TABLE_CLASS = "classes";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_DEVICES = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL);",
            TABLE_DEVICE, COL_ID, COL_STUDENT_ID, COL_STUDENT_NAME, COL_MAC_ADDRESS);
    private static final String CREATE_TABLE_CLASSES = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KET AUTOINCREMENT, %s TEXT UNIQUE NOT NULL);",
            TABLE_CLASS, COL_ID, COL_CLASS_NAME);

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        DBHelper = new DatabaseHelper(ctx);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(CREATE_TABLE_DEVICES);
                db.execSQL(CREATE_TABLE_CLASSES);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_DEVICE));
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    public long insertStudentDevice(StudentDevice studentDevice)
    {
        ContentValues values = new ContentValues();
        values.put(COL_STUDENT_ID, studentDevice.getStudentId());
        values.put(COL_STUDENT_NAME, studentDevice.getStudentName());
        values.put(COL_MAC_ADDRESS, studentDevice.getMacAddress());
        return db.insert(TABLE_DEVICE, null, values);
    }

    public boolean deleteStudentDevice(String studentId)
    {
        return db.delete(TABLE_DEVICE, COL_STUDENT_ID + "=" + studentId, null) > 0;
    }

    public Cursor getAllRecords()
    {
        return db.query(TABLE_DEVICE, new String[] { COL_ID, COL_STUDENT_ID, COL_STUDENT_NAME },
                null, null, null, null, null);
    }

    public StudentDevice getRecord(String studentId) {
        Cursor cursor = db.query(TABLE_DEVICE, new String[] { COL_ID, COL_STUDENT_ID, COL_STUDENT_NAME, COL_MAC_ADDRESS },
                COL_STUDENT_ID + "=" + studentId, null, null, null, null, null);

        StudentDevice studentDevice = null;
        if(cursor.moveToFirst()) {
            int idxStudentName = cursor.getColumnIndex(DBAdapter.COL_STUDENT_NAME);
            int idxMacAddress = cursor.getColumnIndex(DBAdapter.COL_MAC_ADDRESS);
            String studentName = cursor.getString(idxStudentName);
            String macAddress = cursor.getString(idxMacAddress);
            studentDevice = new StudentDevice(studentId, studentName, macAddress);
        }

        cursor.close();
        return studentDevice;
    }

    public Cursor getMacAddress(String studentId) throws SQLException
    {
        Cursor mCursor = db.query(true, TABLE_DEVICE, new String[] {COL_MAC_ADDRESS},
                COL_STUDENT_ID + "=" + studentId, null, null, null, null, null);
        if (mCursor != null) { mCursor.moveToFirst(); }
        return mCursor;
    }

    public boolean updateStudentDevice(StudentDevice studentDevice)
    {
        ContentValues args = new ContentValues();
        args.put(COL_STUDENT_NAME, studentDevice.getStudentName());
        args.put(COL_MAC_ADDRESS, studentDevice.getMacAddress());
        return db.update(TABLE_DEVICE, args, COL_STUDENT_ID + "=" + studentDevice.getStudentId(), null) > 0;
    }
}