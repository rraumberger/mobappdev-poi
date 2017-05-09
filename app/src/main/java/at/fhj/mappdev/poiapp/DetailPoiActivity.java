package at.fhj.mappdev.poiapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class DetailPoiActivity extends AppCompatActivity {

    public static final String POI_KEY = "poiKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_poi);

        final String poiKey = getIntent().getStringExtra(POI_KEY);
        final SharedPreferences sharedPreferences = PreferencesHelper.openPreferences(this);
        final String address = sharedPreferences.getString(poiKey, null);

        ((EditText) findViewById(R.id.tfAddress)).setText(address);
        ((EditText) findViewById(R.id.tfCoordinates)).setText(poiKey);

        findViewById(R.id.btnSavePoi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String coordinates = ((TextView) findViewById(R.id.tfCoordinates)).getText().toString();
                final CharSequence address = ((TextView) findViewById(R.id.tfAddress)).getText();

                PreferencesHelper.updatePoi(DetailPoiActivity.this, poiKey, coordinates, address);
                finish();
            }
        });

        findViewById(R.id.btnDeletePoi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesHelper.deletePoi(DetailPoiActivity.this, poiKey);
                finish();
            }
        });

        findViewById(R.id.btnShowPoi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:0,0?q=%s(%s)",
                        poiKey, address);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                DetailPoiActivity.this.startActivity(intent);
            }
        });
    }

}
