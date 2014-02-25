package de.repat.mosloc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
// for compass
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;  

/**
 * 
 * @author repat with a little help from Vogella Free Tutorials
 *         http://www.vogella.com/tutorials/AndroidLocationAPI/article.html
 * 
 */
public class MainActivity extends Activity implements LocationListener {

    private TextView latitudeField;
    private TextView longitudeField;
    private TextView providerField;

    private LocationManager locationManager;
    private String provider;

    @SuppressLint("DefaultLocale")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // portrait Mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // delete title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);

        latitudeField = (TextView) findViewById(R.id.LatitudeValueTextView);
        longitudeField = (TextView) findViewById(R.id.LongitudeValueTextView);
        providerField = (TextView) findViewById(R.id.ProviderValueTextView);

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Define the criteria how to select the location provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        providerField.setText(provider.toUpperCase());
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            onLocationChanged(location);
        } else {
            latitudeField.setText("Location not available");
            longitudeField.setText("Location not available");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitudeField.setText(String.valueOf(location.getLatitude()));
        longitudeField.setText(String.valueOf(location.getLongitude()));
        providerField.setText(provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        providerField.setText(provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
}