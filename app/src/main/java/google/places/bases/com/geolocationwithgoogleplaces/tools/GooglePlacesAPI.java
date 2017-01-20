package google.places.bases.com.geolocationwithgoogleplaces.tools;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import google.places.bases.com.geolocationwithgoogleplaces.model.MyLocation;

/**
 * Created by Rafael mont'Alvão Seixas de Siqueira
 * email: rms.siqueira@gmail.com
 * .
 */

public class GooglePlacesAPI implements GoogleApiClient.OnConnectionFailedListener{

    private String TAG = "GOOGLE_PLACES_API";

    // GoogleApiClient object instance
    private GoogleApiClient mGoogleApiClient;

    // Reference of activity context
    private Context mContext;

    // constructor of GooglePlacesAPI class
    public GooglePlacesAPI(Context mContext){
        this.mContext = mContext;

        // Create a new instance of GoogleApiClient object
        mGoogleApiClient = new GoogleApiClient
                .Builder(mContext)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                //.addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    // Start if existing a conection error
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // Public method to start connection
    public void connect() {
        mGoogleApiClient.connect();
    }

    // Public methos to stop connection
    public void disconnect() {
        mGoogleApiClient.disconnect();
    }

    // Public function to get a list of  of MyLocation from API
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public ArrayList<MyLocation> getPlaces (){

        // Create the list of MyLocations
        final ArrayList<MyLocation> lPlaces = new ArrayList<MyLocation>();

        // Test if exist a permition to get location
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // Get result of API service
            PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);

            // Set the result callback of the api client function
            result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer likelyPlaces) {

                    // loop at the result to populate the list of MyLocations
                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {

                        // Create a new instance of location and add to the list
                        MyLocation myLocation = new MyLocation();
                        myLocation.setName(placeLikelihood.getPlace().getName().toString());
                        myLocation.setPercentual(placeLikelihood.getLikelihood());
                        lPlaces.add(myLocation);

                        // Debug line
                        Log.i(TAG, String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));

                    }

                    // Erase the result information
                    likelyPlaces.release();
                }
            });

        }

        // Return the list of MyPlaces
        return lPlaces;

    }
}
