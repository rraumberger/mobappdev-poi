package at.fhj.mappdev.poiapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static at.fhj.mappdev.poiapp.DetailPoiActivity.POI_KEY;

public class AllPoiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_poi);

        showStoredPois();
    }

    @Override
    protected void onResume() {
        super.onResume();

        showStoredPois();
    }

    private void showStoredPois() {
        final ListView poiListView = (ListView) findViewById(R.id.liPoi);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);

        poiListView.setAdapter(adapter);

        final SharedPreferences sharedPreferences = PreferencesHelper.openPreferences(this);

        final List<Map.Entry<String, ?>> coords = new ArrayList<>();
        coords.addAll(sharedPreferences.getAll().entrySet());

        Collections.sort(coords, new Comparator<Map.Entry<String, ?>>() {
            @Override
            public int compare(Map.Entry<String, ?> o1, Map.Entry<String, ?> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        for (final Map.Entry<String, ?> entry : coords) {
            adapter.add(getShownText(entry));
        }

        Log.i("POI", "successfully loaded!");

        poiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent i = new Intent(AllPoiActivity.this, DetailPoiActivity.class);

                i.putExtra(POI_KEY, coords.get(position).getKey());

                startActivity(i);
            }
        });
    }

    @NonNull
    private String getShownText(Map.Entry<String, ?> entry) {
        if (entry.getValue() != null) {
            return entry.getValue().toString();
        }
        return entry.getKey();
    }
}
