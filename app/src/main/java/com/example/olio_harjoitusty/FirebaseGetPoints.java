package com.example.olio_harjoitusty;

import androidx.annotation.NonNull;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


@SuppressWarnings("unchecked")
public class FirebaseGetPoints {

    FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();

    String kaupunki;
    Integer tarkistus;
    Integer points;
    final ArrayList<Object> pisteetArrayList = new ArrayList<Object>();

    FirebaseGetCircuits firebaseGetCircuits = new FirebaseGetCircuits();

    public FirebaseGetPoints(){
    }

    public interface PointsCallback {
        void onCallback(ArrayList<Object> pisteet);
    }

    // Function to remove duplicates from an ArrayList
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)   {
        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();
        // Traverse through the first list
        for (T element : list) {
            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }

    /*public void getPoints(final PointsCallback pointsCallback){
        FirebaseGetCircuits firebaseGetCircuits = new FirebaseGetCircuits();
        tarkistus = 1;
        firebaseGetCircuits.getCircuits(new FirebaseGetCircuits.MyCallback() {
            @Override
            public void onCallback(final ArrayList<String> osakilpailut) {
                for(int i = 0; i<osakilpailut.size(); i++){
                    String kaupunki = osakilpailut.get(i);

                    FirebaseGetDriver firebaseGetDriver = new FirebaseGetDriver(kaupunki, "kilpailu");
                    firebaseGetDriver.getDrivers(new FirebaseGetDriver.MyCallback() {
                        @Override
                        public void onCallback(ArrayList<Kilpailija> kuljettajat) {
                            ArrayList<Object> kaikki = new ArrayList<Object>();
                            kaikki.add(kuljettajat);
                            kaikki.add(kuljettajat);
                            System.out.println(kaikki);
                            pointsCallback.onCallback(kaikki);
                        }
                    });
                    tarkistus++;
                }
            }
        });
    }*/



}
