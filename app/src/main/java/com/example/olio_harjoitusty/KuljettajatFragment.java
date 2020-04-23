package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class KuljettajatFragment extends Fragment {
    ListView list;
    ArrayAdapter<String> driversAdapter;
    ArrayList<String> viewArr = new ArrayList<String>();
    FirebaseGetDriver firebaseGetDriver = new FirebaseGetDriver("kaikki", "kaikki", "kaikki");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kuljettajat, container, false);
        list = (ListView) v.findViewById(R.id.drivers_list);
        driversAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, viewArr);
        list.setAdapter(driversAdapter);
        addDrivers();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = viewArr.get(position);
                String low_name = name.toLowerCase();
                Fragment driverProfile = new DriverProfileFragment(low_name);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, driverProfile ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        return v;
    }

    public void addDrivers(){
        firebaseGetDriver.getAllDrivers(new FirebaseGetDriver.MyCallback() {
            @Override
            public void onCallback(ArrayList<Kilpailija> kuljettajat) {
                for (int i = 0; i<kuljettajat.size(); i++){
                    String name = kuljettajat.get(i).getNimi();
                    String cap_name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    if(viewArr.contains(cap_name)){
                        continue;
                    } else {
                        viewArr.add(cap_name);
                    }
                }
                driversAdapter.notifyDataSetChanged();
            }
        });

    }
}
