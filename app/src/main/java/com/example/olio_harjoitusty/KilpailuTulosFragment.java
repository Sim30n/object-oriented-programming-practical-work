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
import java.util.Collections;


@SuppressWarnings("unchecked")
public class KilpailuTulosFragment extends Fragment {

    private String kaupunki;
    private String valinta;

    public KilpailuTulosFragment(String kaupunki, String valinta){
        this.kaupunki = kaupunki;
        this.valinta = valinta;
    }

    ListView list;
    ArrayList<String> jarjestys = new ArrayList<String>();
    ArrayList<String> kierrosaika = new ArrayList<String>();
    ArrayAdapter<String> jarjestysAdapter;
    FirebaseGetDriver firebaseGetDriver;
    TextView head;

    ArrayList<ResView> resultView = new ArrayList<ResView>();
    ResListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        firebaseGetDriver = new FirebaseGetDriver();
        firebaseGetDriver.setOsakilpailu(kaupunki);
        View v = inflater.inflate(R.layout.fragment_kilpailutulos, container, false);
        head = v.findViewById(R.id.nameCircuit);
        String cap_kaupunki = kaupunki.substring(0, 1).toUpperCase() + kaupunki.substring(1);
        head.setText(cap_kaupunki);

        list = (ListView) v.findViewById(R.id.lista);
        adapter = new ResListAdapter(this.getActivity(), R.layout.adapter_result_layout, resultView);
        list.setAdapter(adapter);


        //****************************
        /*list = (ListView) v.findViewById(R.id.lista);
        jarjestysAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, jarjestys){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(jarjestys.get(position));
                text2.setText(kierrosaika.get(position));
                return view;
            }
        };
        list.setAdapter(jarjestysAdapter);*/
        //******************************
        addPositiot();
        return v;
    }

    public void addPositiot(){
        firebaseGetDriver.getDriversByRace(new FirebaseGetDriver.MyCallback() {
            @Override
            public void onCallback(ArrayList<Kilpailija> kuljettajat) {
                Collections.sort(kuljettajat, new Sortbypodium());
                //kierrosaika.add("**************************");
                //jarjestys.add("***** AIKA-AJOT *****");
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
                    //kierrosaika.add("      "+aika.toString());
                    //jarjestys.add(positio + ".  " + cap_nimi);
                    resultView.add(new ResView(viewNimi, aika_str, ""));
                }
                Collections.sort(kuljettajat, new Sortbyrace());
                //kierrosaika.add("************************");
                //jarjestys.add("***** KILPAILU *****");
                resultView.add(new ResView("", "KILPAILU:", ""));
                for(int i=0; i<kuljettajat.size(); i++){
                    Integer positio = kuljettajat.get(i).getPositio_kisa();
                    String nimi = kuljettajat.get(i).getNimi();
                    String cap_nimi = nimi.substring(0, 1).toUpperCase() + nimi.substring(1);
                    Double aika = kuljettajat.get(i).getBestRacetime();
                    String aika_str = "    " + Double.toString(aika);
                    Long pts = kuljettajat.get(i).getPisteet();
                    String pts_str = Long.toString(pts) + " pts.";
                    String viewNimi = positio+". " +cap_nimi;
                    //kierrosaika.add("      "+aika.toString());
                    //jarjestys.add(positio + ".  " + cap_nimi);
                    resultView.add(new ResView(viewNimi, aika_str, pts_str));
                }
                //jarjestysAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();

            }
        });
    }

}
