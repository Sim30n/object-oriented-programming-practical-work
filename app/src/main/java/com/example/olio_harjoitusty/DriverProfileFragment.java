package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Map;

public class DriverProfileFragment extends Fragment {
    private String nimi;

    public DriverProfileFragment(String nimi){
        this.nimi = nimi;
    }

    ListView list;
    TextView kisa;
    TextView sija_aika;
    TextView nimiText;
    ArrayList<String> jarjestys = new ArrayList<String>();
    ArrayList<String> kierrosaika = new ArrayList<String>();
    ArrayAdapter<String> jarjestysAdapter;
    FirebaseGetDriver firebaseGetDriver;

    LineChart mChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        firebaseGetDriver = new FirebaseGetDriver("kaikki", "kaikki", nimi);
        View v = inflater.inflate(R.layout.fragment_driverprofile, container, false);
        kisa = (TextView) v.findViewById(R.id.kisa);
        sija_aika = (TextView) v.findViewById(R.id.aika_sija);
        nimiText = (TextView) v.findViewById(R.id.name);
        mChart = (LineChart) v.findViewById(R.id.line_chart);



        //mChart.setOnChartGestureListener((OnChartGestureListener) this.getActivity());
        // mChart.setOnChartValueSelectedListener((OnChartValueSelectedListener) getActivity());
        addPositiotRace();
        addChartData();
        return v;
    }

    public void addPositiotRace(){
        firebaseGetDriver.getDriversByName(new FirebaseGetDriver.MyCallback() {
            @Override
            public void onCallback(ArrayList<Kilpailija> kuljettajat) {
                Integer positio_kisa = 100;
                Integer positio_aika = 100;
                String race = "";
                String cap_race = "";
                String headName = kuljettajat.get(0).getNimi();
                headName = headName.substring(0, 1).toUpperCase() + headName.substring(1);
                nimiText.setText(headName);
                for(int i=0; i<kuljettajat.size(); i++){
                    if (positio_kisa > kuljettajat.get(i).getPositio_kisa()){
                        positio_kisa = kuljettajat.get(i).getPositio_kisa();
                        race = kuljettajat.get(i).getOsakilpailu();
                        cap_race = race.substring(0, 1).toUpperCase() + race.substring(1);
                    }
                    kisa.setText(cap_race + ", "+positio_kisa.toString());
                }
                for(int i=0; i<kuljettajat.size(); i++){
                    if (positio_aika > kuljettajat.get(i).getPositio_aika()){
                        positio_aika = kuljettajat.get(i).getPositio_aika();
                        race = kuljettajat.get(i).getOsakilpailu();
                        cap_race = race.substring(0, 1).toUpperCase() + race.substring(1);
                    }
                    sija_aika.setText(cap_race + ", "+positio_aika.toString());
                }
            }
        });
    }

    public void addChartData(){

        firebaseGetDriver.getDriversByName(new FirebaseGetDriver.MyCallback() {
            @Override
            public void onCallback(ArrayList<Kilpailija> kuljettajat) {
                ArrayList<Entry> yValues;
                mChart.setDragEnabled(true);
                mChart.setScaleEnabled(false);
                mChart.getAxisRight().setEnabled(false);
                mChart.getAxisLeft().setAxisMinimum(0f);
                mChart.getAxisLeft().setInverted(true);
                //mChart.getAxisLeft().setGranularity(1f);
                Description description = mChart.getDescription();
                description.setEnabled(true);
                // set the description text
                description.setText("Kilpailijan sijoitus osakailpailussa");
                yValues = new ArrayList<Entry>();
                for(int i = 0; i<kuljettajat.size(); i++){
                    yValues.add(new Entry(i, kuljettajat.get(i).getPositio_kisa()));
                }
                LineDataSet set1 = new LineDataSet(yValues, "Sijoitus");
                set1.setFillAlpha(110);
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);
                LineData data = new LineData(dataSets);
                mChart.setData(data);
                mChart.invalidate();
            }
        });
    }

}
