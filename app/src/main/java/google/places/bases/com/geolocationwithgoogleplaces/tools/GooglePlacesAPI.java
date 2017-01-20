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
 * Created by rafael on 02/01/17.
 */

public class GooglePlacesAPI implements GoogleApiClient.OnConnectionFailedListener{

    private String TAG = "GOOGLE_PLACES_API";

    private GoogleApiClient mGoogleApiClient;
    private Context mContext;

    // constructor of GooglePlacesAPI class
    public GooglePlacesAPI(Context mContext){
        this.mContext = mContext;

        mGoogleApiClient = new GoogleApiClient
                .Builder(mContext)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                //.addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void connect() {
        mGoogleApiClient.connect();
    }

    public void disconnect() {
        mGoogleApiClient.disconnect();
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public ArrayList<MyLocation> getPlaces (){

        final ArrayList<MyLocation> lPlaces = new ArrayList<MyLocation>();

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);

            result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {

                        MyLocation myLocation = new MyLocation();
                        myLocation.setName(placeLikelihood.getPlace().getName().toString());
                        myLocation.setPercentual(placeLikelihood.getLikelihood());
                        lPlaces.add(myLocation);

                        Log.i(TAG, String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));

                    }
                    likelyPlaces.release();
                }
            });

        }

        return lPlaces;

    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public synchronized void getTestPlaces(final Context context){

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);

            result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer likelyPlaces) {

                    int count = 0;
                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {

                        String name = placeLikelihood.getPlace().getName().toString();
                        int percent = Math.round(100*placeLikelihood.getLikelihood());

                        if (percent == 0){
                            if (count == 0){
                                Toast.makeText(context,
                                        "Empty",
                                        Toast.LENGTH_LONG).show();
                            }
                            break;
                        }

                        Toast.makeText(context,
                                String.format("'%s'\n\n%d%%",name,percent),
                                Toast.LENGTH_LONG).show();

                        Log.i(TAG, String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));

                        count++;

                    }
                    likelyPlaces.release();
                }
            });

        }
    }


    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public synchronized void getTestPlace(){

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);

            result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer likelyPlaces) {

                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {

                        String name = placeLikelihood.getPlace().getName().toString();
                        int percent = Math.round(100*placeLikelihood.getLikelihood());

                        File pasta = new File(Environment.getExternalStorageDirectory() + File.separator + "SPO10");
                        if (!pasta.exists()) {
                            pasta.mkdirs();
                        }

                        try {

                            File arquivo = new File(Environment.getExternalStorageDirectory() + File.separator + "SPO10" + File.separator + "locals.txt");
                            FileOutputStream out = new FileOutputStream(arquivo);
                            OutputStreamWriter OSW = new OutputStreamWriter(out, "ISO-8859-1");
                            PrintWriter Print = new PrintWriter(OSW);

                            Print.println(name + " " + String.valueOf(percent)+"%");

                            Print.close();
                            OSW.close();
                            out.close();

                        } catch (FileNotFoundException e) {
                            //ERROR
                        } catch (IOException e) {
                            //ERROR
                        }


                        break;

                    }
                    likelyPlaces.release();
                }
            });

        }
    }
}
