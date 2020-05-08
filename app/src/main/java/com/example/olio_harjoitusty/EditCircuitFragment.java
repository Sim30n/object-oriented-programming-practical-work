package com.example.olio_harjoitusty;

import android.os.Bundle;
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
import androidx.fragment.app.FragmentTransaction;

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
    Button delCircuit;
    ListView edList;
    boolean ajettu;
    ArrayList<String> viewArr = new ArrayList<String>();
    ArrayList<String> kommentit = new ArrayList<String>();

    FirebaseFunctions firebaseFunctions = new FirebaseFunctions();
    ArrayAdapter<String> circuitsAdapter;

    public EditCircuitFragment(String circuitName, String circuitID) {
        this.circuitID = circuitID;
        this.circuitName = circuitName;
    }

    public void setCircuitID(String circuitID) {
        this.circuitID = circuitID;
    }

    /*This is for editing circuits.*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_circuit, container, false);
        //Clear lists to avoid crashes
        viewArr.clear();
        kommentit.clear();
        edNimi = (EditText) v.findViewById(R.id.edit_name);
        edPvm = (EditText) v.findViewById(R.id.edit_pvm);
        edInfo = (EditText) v.findViewById(R.id.edit_info);
        edLisaa = (EditText) v.findViewById(R.id.lisaa_nimi);
        edList = (ListView) v.findViewById(R.id.edit_osallistujat);
        isDriven = (Switch) v.findViewById(R.id.switch2);
        addNimi = (Button) v.findViewById(R.id.lisaa_os);
        updateCircuit = (Button) v.findViewById(R.id.paivita_kisa);
        delCircuit = (Button) v.findViewById(R.id.poista_kisa);
        circuitsAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, viewArr);
        edList.setAdapter(circuitsAdapter);
        firebaseFunctions.setOsakilpailu(circuitName);
        firebaseFunctions.setCircuitID(circuitID);
        firebaseFunctions.getCircuitByID(new FirebaseFunctions.MyCallbackCircuitByID() {
            @Override
            public void onCallback(Circuit circuit) {
                if(circuit != null){
                    edNimi.setText(circuit.getName());
                    edPvm.setText(circuit.getPvm());
                    edInfo.setText(circuit.getInfo());
                    ArrayList<String> os = new ArrayList<String>();
                    os = circuit.getPartisipants();
                    kommentit = circuit.getKommentit();
                    for(int i = 0; i<os.size(); i++){
                        viewArr.add(os.get(i));
                    }
                    circuitsAdapter.notifyDataSetChanged();
                }

            }
        });
        edList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewArr.remove(position);
                circuitsAdapter.notifyDataSetChanged();
            }
        });
        isDriven.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ajettu = isChecked;
            }
        });
        addNimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewArr.add(edLisaa.getText().toString());
                circuitsAdapter.notifyDataSetChanged();
            }
        });

        // Add to firestore database.
        updateCircuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String osInfo = edInfo.getText().toString();
                String osName = edNimi.getText().toString();
                String osPvm = edPvm.getText().toString();
                firebaseFunctions.addCircuit(ajettu, osInfo, osName, viewArr, osPvm, circuitID, kommentit);
                Fragment newFrag = new AddCircuitFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFrag );
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        // Delete the circuit.
        delCircuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFunctions.deleteCircuit(circuitID);
                Fragment newFrag = new AddCircuitFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFrag ); //
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        return v;
    }
}
