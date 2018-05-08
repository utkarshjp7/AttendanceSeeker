package cis470.attendanceseeker.entities;

public class StudentAttendance {

    private String mStudentId;
    private String mStudentName;
    private boolean mIsPresent;

    public StudentAttendance(String studentId, String studentName, boolean isPresent) {
        this.mStudentId = studentId;
        this.mStudentName = studentName;
        this.mIsPresent = isPresent;
    }

    public String getStudentId() {
        return mStudentId;
    }

    public void setStudentId(String studentId) {
        this.mStudentId = studentId;
    }

    public String getStudentName() {
        return mStudentName;
    }

    public void setStudentName(String studentName) {
        this.mStudentName = studentName;
    }

    public boolean isPresent() {
        return mIsPresent;
    }

    public void setPresent(boolean present) {
        mIsPresent = present;
    }

}
