package at.fhj.mappdev.poiapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NewPoiActivity extends AppCompatActivity {

    public static final int GPS_LOCATION_REQUEST_CODE = 1;
    public static final int ADDRESS_GEOCODE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_poi);

        final Button getGps = (Button) findViewById(R.id.fetchCoordinates);

        getGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillLocation();
            }
        });

        final Button getAddress = (Button) findViewById(R.id.getAddress);

        getAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillAddress();
            }
        });

        final Button save = (Button) findViewById(R.id.btnSavePoi);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePoi();
                finish();
            }
        });
    }

    private void savePoi() {
        final String gps = ((TextView) findViewById(R.id.tfCoordinates)).getText().toString();
        final CharSequence address = ((TextView) findViewById(R.id.tfAddress)).getText();

        PreferencesHelper.savePoi(this, gps, address);
    }

    private void fillAddress() {
        requestLocation(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                final Geocoder geocoder = new Geocoder(NewPoiActivity.this, Locale.getDefault());
                try {
                    if (location != null) {
                        ((TextView) findViewById(R.id.tfCoordinates)).setText(String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()));
                        final List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        final TextView addressField = (TextView) findViewById(R.id.tfAddress);
                        final Address address = addresses.get(0);
                        addressField.setText(address.getCountryCode() + "," + address.getPostalCode() + "," + address.getAddressLine(0));
                    }
                } catch (IOException e) {
                    Log.e("GEOCODER", e.getMessage(), e);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }, ADDRESS_GEOCODE_REQUEST_CODE);

    }

    private void fillLocation() {
        requestLocation(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    ((TextView) findViewById(R.id.tfCoordinates)).setText(String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()));
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }, GPS_LOCATION_REQUEST_CODE);
    }

    private void requestLocation(final LocationListener listener, final int requestCode) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
            return;
        }
        final LocationManager locationService = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationService.requestSingleUpdate(LocationManager.GPS_PROVIDER, listener, Looper.myLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == GPS_LOCATION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fillLocation();
        } else if (requestCode == ADDRESS_GEOCODE_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fillLocation();
        }
    }
}
