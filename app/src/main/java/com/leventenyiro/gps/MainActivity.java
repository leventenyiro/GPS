package com.leventenyiro.gps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView textSzoveg;
    private Timer timer;
    private TimerTask timerTask;
    private float hosszusag, szelesseg;
    private Naplozas naplozas;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSzoveg = findViewById(R.id.textSzoveg);
        naplozas = new Naplozas();

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                szelesseg = (float) location.getLatitude();
                hosszusag = (float) location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }
        };
        timer.schedule(timerTask, 500,5000);
    }

    // időzített keresztfüggvény
    private void TimerMethod() {
        this.runOnUiThread(Timer_Tick);
    }

    public Runnable Timer_Tick = new Runnable() {
        @Override
        public void run() {
            textSzoveg.setText("La: " + Float.toString(hosszusag) + "\r\nLo: " + Float.toString(szelesseg));
            try {
                naplozas.kiiras(hosszusag, szelesseg);
                Toast.makeText(MainActivity.this, "Új koordináta feljegyezve!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
