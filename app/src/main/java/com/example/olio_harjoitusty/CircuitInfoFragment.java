package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CircuitInfoFragment extends Fragment {

    FirebaseFunctions firebaseFunctions = new FirebaseFunctions();
    TextView head;
    TextView info;
    TextView pvm;
    String circuitID;
    String usrName;

    public CircuitInfoFragment(String circuitID) {
        this.circuitID = circuitID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_circuitinfo, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            usrName = user.getDisplayName();
        }
        head = v.findViewById(R.id.info_head);
        info = v.findViewById(R.id.info_info);
        pvm = v.findViewById(R.id.info_pvm);

        /*Dynamic TextView setup*/
        final LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.circuit_info_layout);

        firebaseFunctions.setCircuitID(circuitID);
        firebaseFunctions.getCircuitByID(new FirebaseFunctions.MyCallbackCircuitByID() {
            @Override
            public void onCallback(Circuit circuit) {
                head.setText(circuit.getName());
                head.setAllCaps(true);
                info.setText(circuit.getInfo());
                pvm.setText(circuit.getPvm());

                // Add all the participants dynamically.
                for(int i = 0; i<circuit.getPartisipants().size(); i++){
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(50, 10, 10, 10);
                    TextView textView2 = new TextView(getActivity());
                    textView2.setLayoutParams(layoutParams);
                    textView2.setTextColor(getResources().getColor(R.color.Black));
                    textView2.setText(circuit.getPartisipants().get(i));
                    linearLayout.addView(textView2);
                }
                //**************************************
                // Add all "Kommentit" text view dynamically.
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 10, 10, 10);
                TextView textKom = new TextView(getActivity());
                textKom.setLayoutParams(params);
                textKom.setTextColor(getResources().getColor(R.color.Black));
                textKom.setText("Kommentit:");
                textKom.setPadding(10, 50, 0, 0);
                linearLayout.addView(textKom);
                //****************************************
                // Add all the comments about the race dynamically.
                for(int i = 0; i<circuit.getKommentit().size(); i++){
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(50, 10, 10, 10);
                    TextView textView2 = new TextView(getActivity());
                    textView2.setLayoutParams(layoutParams);
                    textView2.setTextColor(getResources().getColor(R.color.Black));
                    textView2.setText(circuit.getKommentit().get(i));
                    linearLayout.addView(textView2);
                }
                //***********************************************
                // Add  "add comment" button dynamically.
                Button lisaaButton = new Button(getActivity());
                lisaaButton.setText("lisää kommentti");
                linearLayout.addView(lisaaButton);
                lisaaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment newFrag = new AddCommentFragment(circuitID);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, newFrag ); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                    }
                });
                //************************************************
                // Add "enroll to circuit button" dynamically.
                Button enrollInButton = new Button(getActivity());
                enrollInButton.setText("ilmoittaudu mukaan (sitova)");
                linearLayout.addView(enrollInButton);
                enrollInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firebaseFunctions.addPartisipant(circuitID, usrName);
                        Fragment newFrag = new CircuitInfoFragment(circuitID);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, newFrag ); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                    }
                });
            }
        });
        return v;
    }
}
