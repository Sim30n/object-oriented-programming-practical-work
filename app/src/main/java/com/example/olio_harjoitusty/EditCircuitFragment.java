package com.example.olio_harjoitusty;

import android.os.Bundle;
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
    FirebaseGetDriver firebaseGetDriver = new FirebaseGetDriver("kaikki", "kaikki", "kaikki");
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
        edList = (ListView) v.findViewById(R.id.circuits_list);
        circuitsAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, viewArr);
        edList.setAdapter(circuitsAdapter);
        //addCircuits();

        edList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //isDriven(viewArr.get(position));
            }
        });
        return v;
    }


}
