package google.places.bases.com.geolocationwithgoogleplaces.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import google.places.bases.com.geolocationwithgoogleplaces.R;
import google.places.bases.com.geolocationwithgoogleplaces.model.MyLocation;

public class GeneralCustomList extends BaseAdapter {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    MyLocation tempValues=null;
    int i=0;

    // Constructor of the Adapter
    public GeneralCustomList(Activity a, ArrayList d) {

        // Set the main activity
        activity = a;

        // Set the list
        data=d;

        // Create the instance of the associator layout
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Return the size of list
    public int getCount() {
        if(data.size()<=0) {
            return 1;
        } else {
            return data.size();
        }
    }

    // Return the position (BaseAdapter)
    public Object getItem(int position) {
        return position;
    }

    // Return the position (BaseAdapter)
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Associate the view to XML
        View vi = inflater.inflate(R.layout.item_list_general, null);

        // Map the componets
        TextView title = (TextView) vi.findViewById(R.id.title_location);
        TextView subtitle = (TextView) vi.findViewById(R.id.subtext_location);

        // Test the size of list
        if(data.size()<=0) {

            // Associate if no have itens
            title.setText("No Data");
            subtitle.setText("No information to display");

        } else {

            // Get the item of list
            tempValues = ( MyLocation ) data.get( position );

            // Set the information
            title.setText( tempValues.getName() );
            subtitle.setText( tempValues.getPercentual() );

        }

        // Retunr the View
        return vi;
    }
}