package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;


public class AddPointsFragment extends Fragment {
    EditText name;
    EditText pos_time;
    EditText pos_race;
    EditText points;
    EditText race;
    EditText laptimesA;
    EditText laptimesR;
    EditText pvm;
    Button button;

    // This will add race result to database.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addpoints, container, false);
        name = (EditText) v.findViewById(R.id.points_name);
        pos_time = (EditText) v.findViewById(R.id.pos_a);
        pos_race = (EditText) v.findViewById(R.id.pos_k);
        points = (EditText) v.findViewById(R.id.points);
        race = (EditText) v.findViewById(R.id.os_name);
        laptimesA = (EditText) v.findViewById(R.id.lap_times_a);
        laptimesR = (EditText) v.findViewById(R.id.lap_times_k);
        pvm = (EditText) v.findViewById(R.id.pvm);
        final FirebaseFunctions firebaseFunctions = new FirebaseFunctions();
        button = v.findViewById(R.id.addPoints);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String driverName = name.getText().toString();
                Long position_time = Long.parseLong(pos_time.getText().toString());
                Long position_race = Long.parseLong(pos_race.getText().toString());
                Long race_points = Long.parseLong(points.getText().toString());
                String race_name = race.getText().toString();
                String lapA = laptimesA.getText().toString();
                String[] lapA_array = lapA.split(" "); //split given laptimes to strings
                ArrayList<Double> timesA = new ArrayList<Double>();
                for(String i : lapA_array){
                    Double time = Double.parseDouble(i); // parse to double for the database
                    timesA.add(time);
                }
                String lapR = laptimesR.getText().toString();
                String[] lapR_array = lapR.split(" ");
                ArrayList<Double> timesR = new ArrayList<Double>();
                for(String i : lapR_array){
                    Double time = Double.parseDouble(i);
                    timesR.add(time);
                }
                String race_date = pvm.getText().toString();
                String docname = driverName + "-" + race_name;
                // Add to firestore.
                firebaseFunctions.addData(driverName, position_time, position_race, race_points,
                        race_name, timesA, timesR, race_date, docname);
                showToast();
            }
        });
        return v;
    }

    public void showToast(){
        Toast.makeText(this.getActivity(), "Tulokset lisätty tietokantaan!",
                Toast.LENGTH_LONG).show();
    }


}
