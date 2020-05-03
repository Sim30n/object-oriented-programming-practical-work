package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class EditCircuitFragment extends Fragment {
    String circuitName;
    String circuitID;

    EditText edNimi;
    EditText edPvm;
    EditText edInfo;
    EditText edLisaa;
    Switch isDriven;
    Button addNimi;
    Button updateCircuit;
    ListView edList;
    boolean ajettu;
    ArrayList<String> viewArr = new ArrayList<String>();
    FirebaseGetDriver firebaseGetDriver = new FirebaseGetDriver();
    ArrayAdapter<String> circuitsAdapter;

    public EditCircuitFragment(String circuitName) {
        this.circuitName = circuitName;
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
        isDriven = (Switch) v.findViewById(R.id.switch2);
        addNimi = (Button) v.findViewById(R.id.lisaa_os);
        updateCircuit = (Button) v.findViewById(R.id.paivita_kisa);
        circuitsAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, viewArr);
        edList.setAdapter(circuitsAdapter);
        viewArr.add("asd");
        firebaseGetDriver.setOsakilpailu(circuitName);
        firebaseGetDriver.getCircuitByName(new FirebaseGetDriver.MyCallbackCircuitByName() {
            @Override
            public void onCallback(Circuit circuit) {
                circuitID = circuit.getI_d();
                //circuitID = "1111";
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
        isDriven.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ajettu = isChecked;
                System.out.println(ajettu);
            }
        });
        addNimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewArr.add(edLisaa.getText().toString());
                circuitsAdapter.notifyDataSetChanged();
            }
        });
        updateCircuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String osInfo = edInfo.getText().toString();
                String osName = edNimi.getText().toString();
                String osPvm = edPvm.getText().toString();
                firebaseGetDriver.addCircuit(ajettu, osInfo, osName, viewArr, osPvm, circuitID);
            }
        });
        return v;
    }
}
