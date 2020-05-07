package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;

public class AllCircuitsFragment extends Fragment {

    ListView circuitList;
    ArrayAdapter<String> circuitAdapter;
    ArrayList<String> viewArr = new ArrayList<String>();
    FirebaseFunctions firebaseFunctions = new FirebaseFunctions();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_osakilpailut, container, false);

        // Clear lists to avoid crashes.
        viewArr.clear();

        circuitList = (ListView) v.findViewById(R.id.circuits_list);
        circuitAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, viewArr);
        circuitList.setAdapter(circuitAdapter);
        addCircuits();
        circuitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                firebaseFunctions.getAllCircuits(new FirebaseFunctions.MyCallbackCircuits() {
                    @Override
                    public void onCallback(ArrayList<Circuit> circuits) {
                        isDriven(circuits.get(position).getI_d());
                    }
                });
            }
        });
        return v;
    }

    // Add circuits to listview.
    public void addCircuits(){
        firebaseFunctions.getAllCircuits(new FirebaseFunctions.MyCallbackCircuits() {
            @Override
            public void onCallback(ArrayList<Circuit> circuits) {
                for(int i = 0; i<circuits.size(); i++){
                    String name = circuits.get(i).getName();
                    String cap_name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    viewArr.add(cap_name);
                }
                circuitAdapter.notifyDataSetChanged();
            }
        });
    }

    // Will check if the circuit is driven or not
    public void isDriven(final String osID){
        firebaseFunctions.getAllCircuits(new FirebaseFunctions.MyCallbackCircuits() {
            @Override
            public void onCallback(ArrayList<Circuit> circuits) {
                for(int i = 0; i<circuits.size(); i++){
                    if(osID.equals(circuits.get(i).getI_d())){
                        // If driven will start result fragment.
                        if(circuits.get(i).isAjettu()){
                            Fragment newFrag = new CircuitResultFragment(circuits.get(i).getName());
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, newFrag ); // give your fragment container id in first parameter
                            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                            transaction.commit();
                        } else { // If not driven, will start circuit info fragment.
                            Fragment newFrag = new CircuitInfoFragment(circuits.get(i).getI_d());
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, newFrag ); // give your fragment container id in first parameter
                            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                            transaction.commit();
                        }
                    }
                }
            }
        });
    }
}
