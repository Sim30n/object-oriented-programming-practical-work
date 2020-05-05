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
import java.util.List;

public class ResListAdapter extends ArrayAdapter<ResView> {

    private static final String TAG = "ResListAdapter";

    private Context mContext;
    int mResource;

    public ResListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ResView> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nimi = getItem(position).getNimi();
        String aika = getItem(position).getAika();
        String pts = getItem(position).getPts();

        ResView resView = new ResView(nimi, aika, pts);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.res_nimi);
        TextView tvTime = (TextView) convertView.findViewById(R.id.res_aika);
        TextView tvPts = (TextView) convertView.findViewById(R.id.res_pts);

        tvName.setText(nimi);
        tvTime.setText(aika);
        tvPts.setText(pts);

        return convertView;
    }
}
