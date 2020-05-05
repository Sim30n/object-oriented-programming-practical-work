package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
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
        final View v = inflater.inflate(R.layout.fragment_circuitinfo, container, false);


        //os = v.findViewById(R.id.info_list);
        //osAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, osViewArr);
        //os.setAdapter(osAdapter);
        head = v.findViewById(R.id.info_head);
        info = v.findViewById(R.id.info_info);
        pvm = v.findViewById(R.id.info_pvm);

        /*Dynamic TextView setup*/
        final LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.circuit_info_layout);

        firebaseGetDriver.setCircuitID(circuitID);
        firebaseGetDriver.getCircuitByID(new FirebaseGetDriver.MyCallbackCircuitByID() {
            @Override
            public void onCallback(Circuit circuit) {
                head.setText(circuit.getName());
                head.setAllCaps(true);
                info.setText(circuit.getInfo());
                pvm.setText(circuit.getPvm());

                for(int i = 0; i<circuit.getPartisipants().size(); i++){
                    //osViewArr.add(circuit.getPartisipants().get(i));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(50, 10, 10, 10); // (left, top, right, bottom)
                    TextView textView2 = new TextView(getActivity());
                    textView2.setLayoutParams(layoutParams);
                    textView2.setTextColor(getResources().getColor(R.color.Black));
                    textView2.setText(circuit.getPartisipants().get(i));
                    linearLayout.addView(textView2);
                }

                //*******************
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 10, 10, 10); // (left, top, right, bottom)
                TextView textKom = new TextView(getActivity());
                textKom.setLayoutParams(params);
                textKom.setTextColor(getResources().getColor(R.color.Black));
                textKom.setText("Kommentit:");
                textKom.setPadding(10, 50, 0, 0);
                linearLayout.addView(textKom);
                //********************

                for(int i = 0; i<circuit.getKommentit().size(); i++){
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(50, 10, 10, 10); // (left, top, right, bottom)
                    TextView textView2 = new TextView(getActivity());
                    textView2.setLayoutParams(layoutParams);
                    textView2.setTextColor(getResources().getColor(R.color.Black));
                    textView2.setText(circuit.getKommentit().get(i));
                    linearLayout.addView(textView2);
                }
                /*EditText komEdit = new EditText(getActivity());
                komEdit.setText("Uusi kommentti");
                komEdit.setBackground(null);
                komEdit.setLayoutParams(params);
                komEdit.setPadding(10, 0, 0, 0);
                linearLayout.addView(komEdit);
                EditText nikEdit = new EditText(getActivity());
                nikEdit.setText("Nimimerkki");
                nikEdit.setBackground(null);
                nikEdit.setLayoutParams(params);
                nikEdit.setPadding(10, 0, 0, 0);
                linearLayout.addView(nikEdit);*/
                Button lisaaButton = new Button(getActivity());
                lisaaButton.setText("lisää kommentti");
                linearLayout.addView(lisaaButton);
                //osAdapter.notifyDataSetChanged();
            }
        });
        // Add textview 2

        return v;
    }
}
