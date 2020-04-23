package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class TuloksetFragment extends Fragment {
    Integer tarkistus;

    ListView list;
    ArrayList<Pisteet> pisteetArr = new ArrayList<Pisteet>();
    ArrayList<String> viewArr = new ArrayList<String>();

    ArrayList<String> drivers = new ArrayList<String>();
    FirebaseGetDriver firebaseGetDriver = new FirebaseGetDriver("kaikki", "kaikki", "kaikki");
    ArrayAdapter<String> pisteAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tulokset, container, false);
        list = (ListView) v.findViewById(R.id.piste_lista);
        pisteAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, viewArr);
        list.setAdapter(pisteAdapter);
        addPoints();
        return v;
    }

    public void addPoints(){
        firebaseGetDriver.getAllDrivers(new FirebaseGetDriver.MyCallback() {
            @Override
            public void onCallback(ArrayList<Kilpailija> kuljettajat) {
                for (int i = 0; i<kuljettajat.size(); i++){
                    if(drivers.contains(kuljettajat.get(i).getNimi())){
                        continue;
                    } else {
                        drivers.add(kuljettajat.get(i).getNimi());
                    }
                }
                Integer points_total = 0;
                for (int i = 0; i<drivers.size(); i++){
                    for (int j = 0; j<kuljettajat.size(); j++){
                        if(drivers.get(i).equals(kuljettajat.get(j).getNimi())){
                            long l = kuljettajat.get(i).getPisteet();
                            int point = (int) l;
                            points_total = points_total + point;
                            pisteetArr.add(new Pisteet(drivers.get(i), points_total));
                        }
                    }
                    points_total = 0;
                }
                Collections.sort(pisteetArr);
                for(int i = 0; i<pisteetArr.size(); i++){
                    String name = pisteetArr.get(i).getNimi();
                    String cap_name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    viewArr.add((i+1)+". "+cap_name + ": " +pisteetArr.get(i).getTotal());
                }
                pisteAdapter.notifyDataSetChanged();
            }
        });
    }
}
