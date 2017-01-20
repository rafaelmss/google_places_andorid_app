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

/**
 * Created by Rafael mont'Alvão Seixas de Siqueira
 * email: rms.siqueira@gmail.com
 * .
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Object reference of mapped API Class
    private GooglePlacesAPI googlePlaces;

    // First funtion at activity lifecicle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout
        setContentView(R.layout.activity_main);

        // Create a new instance of mapped API Class
        googlePlaces = new GooglePlacesAPI(this);

        // Set lister of the button to list location
        findViewById(R.id.buttonGetLocation).setOnClickListener(this);
    }

    // Function to manage button actions
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.buttonGetLocation):
                listLocations();
                break;
        }
    }

    // Print the list of MyLocations
    private void listLocations() {

        // Map the ListView
        ListView list = (ListView) findViewById(R.id.locationsListView);

        // Test the permition to get location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,R.string.no_location_permition,Toast.LENGTH_LONG).show();
            return;
        }

        // Get the list of MyLocations
        ArrayList<MyLocation> all = googlePlaces.getPlaces();

        // Generate the adapter to set ListView list
        GeneralCustomList adapter = new GeneralCustomList( this , all );
        list.setAdapter( adapter );
    }

    // Activity lifecicle after create
    @Override
    public void onStart(){
        super.onStart();

        // Start the API connection
        googlePlaces.connect();
    }

    // Activity lifecicle before desctoy
    @Override
    public void onStop(){

        // Stop the API connection
        googlePlaces.disconnect();

        super.onStop();
    }
}
