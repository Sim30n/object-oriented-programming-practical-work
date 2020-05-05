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


public class CircuitInfoFragment extends Fragment {

    FirebaseGetDriver firebaseGetDriver = new FirebaseGetDriver();
    ArrayAdapter<String> osAdapter;
    ArrayList<String> osViewArr = new ArrayList<String>();

    TextView head;
    TextView info;
    TextView pvm;
    ListView os;
    String circuitID;

    public CircuitInfoFragment(String circuitID) {
        this.circuitID = circuitID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_circuitinfo, container, false);
        os = v.findViewById(R.id.info_list);
        osAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, osViewArr);
        os.setAdapter(osAdapter);
        head = v.findViewById(R.id.info_head);
        info = v.findViewById(R.id.info_info);
        pvm = v.findViewById(R.id.info_pvm);
        firebaseGetDriver.setCircuitID(circuitID);
        firebaseGetDriver.getCircuitByID(new FirebaseGetDriver.MyCallbackCircuitByID() {
            @Override
            public void onCallback(Circuit circuit) {
                head.setText(circuit.getName());
                info.setText(circuit.getInfo());
                pvm.setText(circuit.getPvm());
                for(int i = 0; i<circuit.getPartisipants().size(); i++){
                    osViewArr.add(circuit.getPartisipants().get(i));
                }
                osAdapter.notifyDataSetChanged();
            }
        });
        return v;
    }
}
