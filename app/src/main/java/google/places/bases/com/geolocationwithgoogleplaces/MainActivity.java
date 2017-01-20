package google.places.bases.com.geolocationwithgoogleplaces;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import google.places.bases.com.geolocationwithgoogleplaces.adapters.GeneralCustomList;
import google.places.bases.com.geolocationwithgoogleplaces.model.MyLocation;
import google.places.bases.com.geolocationwithgoogleplaces.tools.GooglePlacesAPI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GooglePlacesAPI googlePlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googlePlaces = new GooglePlacesAPI(this);

        findViewById(R.id.buttonGetLocation).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.buttonGetLocation):
                listLocations();
                break;
        }
    }

    private void listLocations() {
        ListView list = (ListView) findViewById(R.id.locationsListView);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,R.string.no_location_permition,Toast.LENGTH_LONG).show();
            return;
        }
        ArrayList<MyLocation> all = googlePlaces.getPlaces();
        GeneralCustomList adapter = new GeneralCustomList( this , all );
        list.setAdapter( adapter );
    }

    @Override
    public void onStart(){
        super.onStart();
        googlePlaces.connect();
    }

    @Override
    public void onStop(){
        googlePlaces.disconnect();
        super.onStop();
    }
}
