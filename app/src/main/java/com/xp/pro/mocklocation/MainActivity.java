package com.xp.pro.mocklocation;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * LocationDialog: 模拟定位对话框交互控件
 * Author: xp
 * Date: 18/7/13 00:18
 * Email: xiexiepro@gmail.com
 * Blog: http://XieXiePro.github.io
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private static final int REQUEST_PERMISSION_LOCATION = 0x18;

    private double latitude;
    private double longitude;
    LocationWidget idLocationWigdet;
    private EditText setLatitude;
    private EditText setLongitude;
    private Button setLocation;
    private GPSService mService;
    private TextView mMockedLocation;
    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            GPSService.LocalBinder binder = (GPSService.LocalBinder) service;
            mService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_content_view);
        idLocationWigdet = (LocationWidget) findViewById(R.id.id_location_wigdet);
        setLatitude = (EditText) findViewById(R.id.et_latitude);
        setLongitude = (EditText) findViewById(R.id.et_longitude);
        setLocation = (Button) findViewById(R.id.btn_set_location);
        setLocation.setOnClickListener(this);
        findViewById(R.id.btn_mock).setOnClickListener(this);
        findViewById(R.id.btn_show_mock_location).setOnClickListener(this);
        mMockedLocation = (TextView) findViewById(R.id.tv_show_mock_location);
        if (Build.VERSION.SDK_INT >= 23) {
            applyPermission();
        } else {
            initLocation();
        }
    }

    private void initLocation() {
        idLocationWigdet.setMangerLocationData(0, 0);
        idLocationWigdet.startMockLocation();
        idLocationWigdet.refreshData();
        Intent intent = new Intent(MainActivity.this, GPSService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    @TargetApi(23)
    public void applyPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSION_LOCATION);

    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initLocation();
                } else {
                    Toast.makeText(MainActivity.this,
                            "I sorry,lack permission i cann't show image there~",
                            Toast.LENGTH_LONG).show();
                    this.finish();
                }

            }

            break;

            default:
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mock: {
                idLocationWigdet.stopMockLocation();
                break;
            }
            case R.id.btn_set_location: {
                try {
                    latitude = Double.parseDouble(setLatitude.getText().toString().trim());
                    longitude = Double.parseDouble(setLongitude.getText().toString().trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                idLocationWigdet.setMangerLocationData(latitude, longitude);
                break;
            }
            case R.id.btn_show_mock_location: {
                Location location = mService.getMockLocation();
                String info = "latitude:" + location.getLatitude() + "\n"
                        + "longitude:" + location.getLongitude() + "\n";
                mMockedLocation.setText(info);

            }
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}