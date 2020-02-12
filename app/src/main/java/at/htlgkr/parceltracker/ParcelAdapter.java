package at.htlgkr.parceltracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ParcelAdapter extends BaseAdapter {
    private List<Parcel> parcels = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflater;

    // you are able to alter this constructor parameters
    public ParcelAdapter(Context ctx, int layoutId, List<Parcel> parcels) {
        this.parcels = parcels;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
    	//implement this
        return parcels.size();
    }

    @Override
    public Object getItem(int position) {
    	//implement this
        return parcels.get(position);
    }

    @Override
    public long getItemId(int position) {
    	//implement this
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        Parcel parcel = parcels.get(position);
        View listItem = (view == null) ? inflater.inflate(this.layoutId, null) : view;
        ((TextView) listItem.findViewById(R.id.tv_title)).setText(parcel.getTitle());
        ((TextView) listItem.findViewById(R.id.tv_category)).setText(parcel.getCategory().getName());
        ((TextView) listItem.findViewById(R.id.tv_start)).setText(formatter.format(parcel.getStart()));
        ((TextView) listItem.findViewById(R.id.tv_end)).setText(formatter.format(parcel.getEnd()));
        return listItem;
    }
}

