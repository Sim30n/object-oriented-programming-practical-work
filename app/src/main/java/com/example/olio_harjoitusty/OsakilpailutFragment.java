package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class OsakilpailutFragment extends Fragment {
    ListView list;
    //Firebase firebase = new Firebase();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_osakilpailut, container, false);
        list = (ListView) v.findViewById(R.id.lista);
        ArrayList<String> names = new ArrayList<>();
        /*for(int i = 0; i<firebase.ajat.size(); i++){
            System.out.println(i);
        }*/
        names.add("1. Petteri                                                   0.43.21");
        names.add("2. fdfdsfds                                                  0.43.21");
        names.add("3. Petteri                                                   0.43.21");
        names.add("4. Petteri                                                   0.43.21");
        names.add("5. Petteri                                                   0.43.21");
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, names);
        list.setAdapter(namesAdapter);
        return v;
    }
}
