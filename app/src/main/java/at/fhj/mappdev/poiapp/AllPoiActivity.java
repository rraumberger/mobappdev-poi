package at.fhj.mappdev.poiapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Map;

public class AllPoiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_poi);

        final ListView poiListView = (ListView) findViewById(R.id.liPoi);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);

        poiListView.setAdapter(adapter);

        final SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.poi_shared_prefs), MODE_PRIVATE);

        for (final Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            adapter.add(getShownText(entry));
        }
        adapter.notifyDataSetChanged();
        Log.i("POI", "successfully loaded!");
    }

    @NonNull
    private String getShownText(Map.Entry<String, ?> entry) {
        if (entry.getValue() != null) {
            return entry.getValue().toString();
        }
        return entry.getKey();
    }
}
