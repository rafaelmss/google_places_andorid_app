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

/**
 * Created by rafael on 27/12/16.
 */


public class GeneralCustomList extends BaseAdapter {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    MyLocation tempValues=null;
    int i=0;

    public GeneralCustomList(Activity a, ArrayList d) {
        activity = a;
        data=d;
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        if(data.size()<=0) {
            return 1;
        } else {
            return data.size();
        }
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        vi = inflater.inflate(R.layout.item_list_general, null);

        TextView title = (TextView) vi.findViewById(R.id.title_location);
        TextView subtitle = (TextView) vi.findViewById(R.id.subtext_location);

        if(data.size()<=0) {
            title.setText("No Data");
            subtitle.setText("No information to display");
        } else {

            tempValues = ( MyLocation ) data.get( position );

            title.setText( tempValues.getName() );
            subtitle.setText( tempValues.getPercentual() );

        }

        return vi;
    }
}