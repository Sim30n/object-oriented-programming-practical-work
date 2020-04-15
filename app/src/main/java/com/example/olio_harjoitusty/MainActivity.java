package com.example.olio_harjoitusty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Integer tarkistus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseGetDriver firebaseGetDriver = new FirebaseGetDriver("imatra", "aika-ajot");
        /*FirebaseGetCircuits firebaseGetCircuits = new FirebaseGetCircuits();
        tarkistus = 0;
        firebaseGetCircuits.getCircuits(new FirebaseGetCircuits.MyCallback() {
            @Override
            public void onCallback(final ArrayList<String> osakilpailut) {
                FirebaseGetDriverNames firebaseGetDriverNames = new FirebaseGetDriverNames();
                firebaseGetDriverNames.getNames(new FirebaseGetDriverNames.NamesCallback() {
                    @Override
                    public void onCallback(ArrayList<String> nimet) {
                        tarkistus++;
                        *//*if(tarkistus == osakilpailut.size()){
                            System.out.println(nimet);
                        }*//*
                        System.out.println(nimet);

                        for(int i = 0; i<nimet.size(); i++){

                        }
                    }
                });
            }
        });*/

        firebaseGetDriver.getDrivers(new FirebaseGetDriver.MyCallback() {
            @Override
            public void onCallback(ArrayList<Kilpailija> kuljettajat) {
                System.out.println("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤");

            }
        });

        //firebaseGetDriver.addData();


        /*FirebaseGetPoints firebaseGetPoints = new FirebaseGetPoints();
        firebaseGetPoints.getPoints(new FirebaseGetPoints.PointsCallback() {
            @Override
            public void onCallback(ArrayList<Object> pisteet) {
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAA");
            }
        });*/



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navlistener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new OsakilpailutFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_osakilpailut:
                            selectedFragment = new OsakilpailutFragment();
                            break;
                        case R.id.nav_kuljettajat:
                            selectedFragment = new KuljettajatFragment();
                            break;
                        case R.id.nav_tulokset:
                            selectedFragment = new TuloksetFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}
