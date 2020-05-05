package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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

        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.circuit_info_layout);
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
        // Add textview 2
        TextView textView2 = new TextView(this.getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //layoutParams.gravity = Gravity.RIGHT;
        layoutParams.setMargins(10, 10, 10, 10); // (left, top, right, bottom)
        textView2.setLayoutParams(layoutParams);
        textView2.setText("Kommmentti tulee tähän");
        //textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        //textView2.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
        linearLayout.addView(textView2);
        return v;
    }
}
