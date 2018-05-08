package cis470.attendanceseeker.entities;

public class Student {

    private String mStudentId;
    private String mStudentName;
    private String mMacAddress;
    private String mClassName;

    public Student(String studentId, String studentName, String macAddress, String className) {
        this.mStudentId = studentId;
        this.mStudentName = studentName;
        this.mMacAddress = macAddress;
        this.mClassName = className;
    }

    public String getStudentId() {
        return this.mStudentId;
    }

    public String getStudentName() {
        return this.mStudentName;
    }

    public String getMacAddress() {
        return this.mMacAddress;
    }

    public String getClassName() { return this.mClassName; }

    public void setStudentId(String studentId) {
        this.mStudentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.mStudentName = studentName;
    }

    public void setMacAddress(String macAddress) {
        this.mMacAddress = macAddress;
    }

    public void setClassName(String className) { this.mClassName = className; }
}
