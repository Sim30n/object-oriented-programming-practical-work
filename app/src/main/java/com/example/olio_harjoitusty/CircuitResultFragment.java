package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;


@SuppressWarnings("unchecked")
public class CircuitResultFragment extends Fragment {

    // String kaupunki is same as the circuit name
    private String kaupunki;
    public CircuitResultFragment(String kaupunki){
        this.kaupunki = kaupunki;
    }

    ListView list;
    FirebaseFunctions firebaseFunctions;
    TextView head;
    TextView raceDate;

    ArrayList<ResView> resultView = new ArrayList<ResView>();
    ResListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        firebaseFunctions = new FirebaseFunctions();
        firebaseFunctions.setOsakilpailu(kaupunki);
        View v = inflater.inflate(R.layout.fragment_kilpailutulos, container, false);
        resultView.clear();
        head = v.findViewById(R.id.nameCircuit);
        raceDate = v.findViewById(R.id.result_pvm);
        String cap_kaupunki = kaupunki.substring(0, 1).toUpperCase() + kaupunki.substring(1);
        head.setText(cap_kaupunki);
        list = (ListView) v.findViewById(R.id.lista);
        adapter = new ResListAdapter(this.getActivity(), R.layout.adapter_result_layout, resultView);
        list.setAdapter(adapter);
        addPositiot();
        return v;
    }

    // Add positions to the list view.
    public void addPositiot(){
        firebaseFunctions.getDriversByRace(new FirebaseFunctions.MyCallback() {
            @Override
            public void onCallback(ArrayList<Driver> kuljettajat) {
                raceDate.setText(kuljettajat.get(0).getPvm());
                Collections.sort(kuljettajat, new Sortbypodium()); // Sort  drivers by podium standing.
                resultView.add(new ResView("", "AIKA-AJOT:", ""));
                for(int i=0; i<kuljettajat.size(); i++){
                    Integer positio = kuljettajat.get(i).getPositio_aika();
                    String nimi = kuljettajat.get(i).getNimi();
                    String cap_nimi = nimi.substring(0, 1).toUpperCase() + nimi.substring(1);
                    String viewNimi = positio+". " +cap_nimi;
                    Double aika = kuljettajat.get(i).getParasKierrosaika();
                    String aika_str = "    " + Double.toString(aika);
                    Long pts = kuljettajat.get(i).getPisteet();
                    String pts_str = Long.toString(pts);
                    resultView.add(new ResView(viewNimi, aika_str, ""));
                }
                Collections.sort(kuljettajat, new Sortbyrace()); // Sort drivers by race standing.
                resultView.add(new ResView("", "KILPAILU:", ""));
                for(int i=0; i<kuljettajat.size(); i++){
                    Integer positio = kuljettajat.get(i).getPositio_kisa();
                    String nimi = kuljettajat.get(i).getNimi();
                    String cap_nimi = nimi.substring(0, 1).toUpperCase() + nimi.substring(1);
                    Double aika = kuljettajat.get(i).getBestRacetime();
                    String aika_str = "    " + Double.toString(aika);
                    Long pts = kuljettajat.get(i).getPisteet();
                    String pts_str = Long.toString(pts) + " pts";
                    String viewNimi = positio+". " +cap_nimi;
                    resultView.add(new ResView(viewNimi, aika_str, pts_str));
                }
                adapter.notifyDataSetChanged(); // Refresh the list.
            }
        });
    }

}
