package cis470.attendanceseeker.entities;

public class DeviceInfo {

    private String mName;
    private String mMacAddress;
    private int mRSSI;

    public DeviceInfo(String name, String macAddress, int RSSI) {
        mName = name;
        mMacAddress = macAddress;
        mRSSI = RSSI;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getMacAddress() {
        return mMacAddress;
    }

    public void setMacAddress(String macAddress) {
        this.mMacAddress = macAddress;
    }

    public int getRSSI() {
        return mRSSI;
    }

    public void setRSSI(int RSSI) {
        this.mRSSI = RSSI;
    }

}
