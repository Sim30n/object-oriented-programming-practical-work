package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class EditCircuitFragment extends Fragment {
    String circuitID;

    EditText edNimi;
    EditText edPvm;
    EditText edInfo;
    EditText edLisaa;
    Button addNimi;
    Button updateCircuit;
    ListView edList;
    ArrayList<String> viewArr = new ArrayList<String>();
    FirebaseGetDriver firebaseGetDriver = new FirebaseGetDriver();
    ArrayAdapter<String> circuitsAdapter;

    public EditCircuitFragment(String circuitID) {
        this.circuitID = circuitID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_circuit, container, false);
        edNimi = (EditText) v.findViewById(R.id.edit_name);
        edPvm = (EditText) v.findViewById(R.id.edit_pvm);
        edInfo = (EditText) v.findViewById(R.id.edit_info);
        edLisaa = (EditText) v.findViewById(R.id.lisaa_nimi);
        edList = (ListView) v.findViewById(R.id.edit_osallistujat);
        circuitsAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, viewArr);
        edList.setAdapter(circuitsAdapter);
        viewArr.add("asd");
        firebaseGetDriver.setOsakilpailu(circuitID);
        firebaseGetDriver.getCircuitByName(new FirebaseGetDriver.MyCallbackCircuitByName() {
            @Override
            public void onCallback(Circuit circuit) {
                System.out.println(circuit.getName());
                edNimi.setText(circuit.getName());
                edPvm.setText(circuit.getPvm());
                edInfo.setText(circuit.getInfo());
                ArrayList<String> os = new ArrayList<String>();
                os = circuit.getPartisipants();
                for(int i = 0; i<os.size(); i++){
                    viewArr.add(os.get(i));
                }
                circuitsAdapter.notifyDataSetChanged();
            }

        });
        return v;
    }


}
