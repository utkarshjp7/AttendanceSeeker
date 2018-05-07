package cis470.attendanceseeker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private final int REQUEST_ACCESS_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        mToolBar = findViewById(R.id.toolbar);

        ActivityCompat.requestPermissions(this,
                new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_ACCESS_COARSE_LOCATION);

        if(mToolBar != null) {
            mToolBar.setTitle("");
            setSupportActionBar(mToolBar);
        }
    }

    protected abstract int getLayoutResource();

    @Override
    public void setTitle(CharSequence title) {
        if(mToolBar != null) {
            mToolBar.setTitle(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        if(mToolBar != null) {
            mToolBar.setTitle(titleId);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode
            , String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACCESS_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
