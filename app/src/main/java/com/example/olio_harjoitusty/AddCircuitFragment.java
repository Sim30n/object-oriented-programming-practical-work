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

public class AddCircuitFragment extends Fragment {

    ListView circuitList;
    ArrayAdapter<String> circuitAdapter;
    ArrayList<String> viewArr = new ArrayList<String>();
    FirebaseFunctions firebaseFunctions = new FirebaseFunctions();
    Button addNew;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addcircuit, container, false);

        // Clear lists to avoid crashes.
        viewArr.clear();

        circuitList = (ListView) v.findViewById(R.id.add_circuits_list);
        addNew = (Button) v.findViewById(R.id.os_add_new);
        circuitAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, viewArr);
        circuitList.setAdapter(circuitAdapter);
        // Callback to make list of existing circuits.
        firebaseFunctions.getAllCircuits(new FirebaseFunctions.MyCallbackCircuits() {
            @Override
            public void onCallback(final ArrayList<Circuit> circuits) {
                // Add circuits to listview.
                for(int i = 0; i<circuits.size(); i++){
                    viewArr.add(circuits.get(i).getName());
                }
                circuitAdapter.notifyDataSetChanged(); // refresh the list view
                // Button will start existing circuit editing view.
                circuitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Fragment tulos = new EditCircuitFragment(viewArr.get(position), circuits.get(position).getI_d());
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, tulos ); // give your fragment container and  first parameter
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                // button will create new circuit
                addNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uniqueID = UUID.randomUUID().toString();
                        ArrayList<String> tyhja = new ArrayList<String>();
                        firebaseFunctions.addCircuit(false, "", "uusi kilpailu", tyhja ,
                                "", uniqueID, tyhja);
                        Fragment tulos = new EditCircuitFragment("uusi osakilpailu", uniqueID);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, tulos ); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                    }
                });
            }
        });
        return v;
    }
}
