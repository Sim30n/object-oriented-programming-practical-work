package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TuloksetFragment extends Fragment {
    Integer tarkistus;

    ListView list;
    ArrayList<String> pisteetArr = new ArrayList<String>();
    FirebaseGetPoints firebaseGetPoints = new FirebaseGetPoints();
    ArrayAdapter<String> pisteAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tulokset, container, false);
        list = (ListView) v.findViewById(R.id.piste_lista);
        pisteAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, pisteetArr);
        list.setAdapter(pisteAdapter);
        addPoints();
        return v;
    }

    public void addPoints(){
        /*firebaseGetPoints.getPoints(new FirebaseGetPoints.PointsCallback() {
            @Override
            public void onCallback(ArrayList<Pisteet> pisteet) {
                System.out.println(pisteet.get(0).summa());
                for (int i = 0; i < pisteet.size(); i++) {
                    System.out.println("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤");
                    String nimi = pisteet.get(i).getNimi();
                    Integer summa = pisteet.get(i).summa();
                    pisteetArr.add(nimi + " " + summa);
                }
                pisteAdapter.notifyDataSetChanged();
            }
        });*/

    }
}
