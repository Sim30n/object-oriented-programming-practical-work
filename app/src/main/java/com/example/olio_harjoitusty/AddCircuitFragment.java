package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class AddCircuitFragment extends Fragment {

    ListView circuitList;
    ArrayAdapter<String> circuitAdapter;
    ArrayList<String> viewArr = new ArrayList<String>();
    FirebaseGetDriver firebaseGetDriver = new FirebaseGetDriver();
    ArrayList<Circuit> circuitsArr = new ArrayList<Circuit>();
    Button addNew;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addcircuit, container, false);
        circuitList = (ListView) v.findViewById(R.id.add_circuits_list);
        addNew = (Button) v.findViewById(R.id.os_add_new);
        circuitAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, viewArr);
        circuitList.setAdapter(circuitAdapter);
        firebaseGetDriver.getAllCircuits(new FirebaseGetDriver.MyCallbackCircuits() {
            @Override
            public void onCallback(final ArrayList<Circuit> circuits) {
                for(int i = 0; i<circuits.size(); i++){
                    viewArr.add(circuits.get(i).getName());
                }
                circuitAdapter.notifyDataSetChanged();
                circuitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //System.out.println(circuitsArr.get(position).getI_d());
                        Fragment tulos = new EditCircuitFragment(viewArr.get(position), circuits.get(position).getI_d());
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, tulos ); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                        //isDriven(viewArr.get(position));
                    }
                });
                addNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uniqueID = UUID.randomUUID().toString();
                        ArrayList<String> tyhja = new ArrayList<String>();
                        firebaseGetDriver.addCircuit(false, "", "uusi kilpailu", tyhja , "", uniqueID);
                        Fragment tulos = new EditCircuitFragment("uusi osakilpailu", uniqueID);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, tulos ); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                    }
                });
            }
        });
        addCircuits();




        /*button = v.findViewById(R.id.eka);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment tulos = new KilpailuTulosFragment("imatra", "aika-ajot");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, tulos ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        button2 = v.findViewById(R.id.toka);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment tulos = new KilpailuTulosFragment("vantaa", "aika-ajot");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, tulos ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });*/

        return v;
    }

    public void addCircuits(){

    }

    public void isDriven(final String os){
        firebaseGetDriver.getAllCircuits(new FirebaseGetDriver.MyCallbackCircuits() {
            @Override
            public void onCallback(ArrayList<Circuit> circuits) {
                for(int i = 0; i<circuits.size(); i++){
                    if(os.equals(circuits.get(i).getName())){
                        if(circuits.get(i).isAjettu()){
                            Fragment tulos = new KilpailuTulosFragment(circuits.get(i).getName(), "aika-ajot");
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, tulos ); // give your fragment container id in first parameter
                            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                            transaction.commit();
                        }
                        System.out.println(circuits.get(i).isAjettu());
                    }
                }
            }
        });
    }
}
