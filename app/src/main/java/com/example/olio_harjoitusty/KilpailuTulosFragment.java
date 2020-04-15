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

import java.util.ArrayList;
import java.util.Collections;


@SuppressWarnings("unchecked")
public class KilpailuTulosFragment extends Fragment {

    private String kaupunki;
    private String valinta;

    public KilpailuTulosFragment(String kaupunki, String valinta){
        this.kaupunki = kaupunki;
        this.valinta = valinta;
    }

    ListView list;
    ArrayList<String> jarjestys = new ArrayList<String>();
    ArrayList<Double> kierrosaika = new ArrayList<Double>();
    ArrayAdapter<String> jarjestysAdapter;
    FirebaseGetDriver firebaseGetDriver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        firebaseGetDriver = new FirebaseGetDriver(kaupunki, valinta);
        View v = inflater.inflate(R.layout.fragment_kilpailutulos, container, false);
        list = (ListView) v.findViewById(R.id.lista);
        jarjestysAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, jarjestys){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(jarjestys.get(position));
                text2.setText("      "+kierrosaika.get(position).toString());

                return view;
            }
        };
        list.setAdapter(jarjestysAdapter);
        addPositiot();
        return v;
    }

    public void addPositiot(){
        firebaseGetDriver.getDrivers(new FirebaseGetDriver.MyCallback() {
            @Override
            public void onCallback(ArrayList<Kilpailija> kuljettajat) {
                Collections.sort(kuljettajat);
                for(int i=0; i<kuljettajat.size(); i++){
                    //jarjestys.add(kuljettajat.get(i));
                    Integer positio = kuljettajat.get(i).getPositio_aika();
                    String nimi = kuljettajat.get(i).getNimi();
                    String cap_nimi = nimi.substring(0, 1).toUpperCase() + nimi.substring(1);
                    Double aika = kuljettajat.get(i).getParasKierrosaika();
                    kierrosaika.add(aika);
                    jarjestys.add(positio + ".  " + cap_nimi);
                }
                jarjestysAdapter.notifyDataSetChanged();
            }
        });
    }

}
