package cis470.attendanceseeker;

/**
 * Created by uko on 5/2/18.
 */

public class StudentDevice {

    private String mStudentId;
    private String mStudentName;
    private String mMacAddress;

    public StudentDevice(String studentId, String studentName, String macAddress) {
        this.mStudentId = studentId;
        this.mStudentName = studentName;
        this.mMacAddress = macAddress;
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

    public void setStudentId(String studentId) {
        this.mStudentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.mStudentName = studentName;
    }

    public void setMacAddress(String macAddress) {
        this.mMacAddress = macAddress;
    }
}
