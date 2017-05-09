package at.fhj.mappdev.poiapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public final class PreferencesHelper {

    private PreferencesHelper() {

    }

    public static SharedPreferences openPreferences(Activity activity) {
        return activity.getSharedPreferences(activity.getString(R.string.poi_shared_prefs), Context.MODE_PRIVATE);
    }

    public static void savePoi(Activity activity, String coordinates, CharSequence address) {
        final SharedPreferences sharedPreferences = openPreferences(activity);
        final SharedPreferences.Editor prefEditor = sharedPreferences.edit();

        prefEditor.putString(coordinates, address != null ? address.toString().trim() : "");

        prefEditor.apply();
        Log.i("POI", "successfully saved!");
    }

    public static void deletePoi(Activity activity, String coordinates) {
        final SharedPreferences sharedPreferences = openPreferences(activity);
        final SharedPreferences.Editor prefEditor = sharedPreferences.edit();

        prefEditor.remove(coordinates);

        prefEditor.apply();
        Log.i("POI", "successfully removed!");
    }

    public static void updatePoi(Activity activity, String storedCoordinates, String coordinates, CharSequence address) {
        final SharedPreferences sharedPreferences = openPreferences(activity);
        final SharedPreferences.Editor prefEditor = sharedPreferences.edit();

        prefEditor.remove(storedCoordinates);
        prefEditor.putString(coordinates, address != null ? address.toString().trim() : "");

        prefEditor.apply();
        Log.i("POI", "successfully updated!");
    }
}
