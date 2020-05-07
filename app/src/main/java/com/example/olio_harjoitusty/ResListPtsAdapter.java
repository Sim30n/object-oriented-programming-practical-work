package com.example.olio_harjoitusty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/* Custom adapter for list view in points view.*/
public class ResListPtsAdapter extends ArrayAdapter<ResViewPts> {

    private static final String TAG = "ResListPtsAdapter";

    private Context mContext;
    int mResource;

    public ResListPtsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ResViewPts> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nimi = getItem(position).getNimi();
        String pts = getItem(position).getPts();

        ResViewPts resView = new ResViewPts(nimi, pts);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.resP_nimi);
        TextView tvPts = (TextView) convertView.findViewById(R.id.resP_pts);

        tvName.setText(nimi);
        tvPts.setText(pts);

        return convertView;
    }
}