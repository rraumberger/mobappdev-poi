package at.fhj.mappdev.poiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button addPoi = (Button) findViewById(R.id.btnAddPoi);
        addPoi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent(NewPoiActivity.class);
            }
        });

        final Button myPoi = (Button) findViewById(R.id.btnAllPoi);
        myPoi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent(AllPoiActivity.class);
            }
        });
    }

    private void intent(Class<?> newIntent) {
        final Intent intent = new Intent(this, newIntent);
        this.startActivity(intent);
    }


}
