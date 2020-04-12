package com.example.olio_harjoitusty;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;


@SuppressWarnings("unchecked")
public class FirebaseGetDriver {

    FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();

    String kaupunki;
    String valinta;

    public FirebaseGetDriver(String kaupunki, String valinta){
        this.kaupunki = kaupunki;
        this.valinta = valinta;
    }

    public interface MyCallback {
        void onCallback(ArrayList<Kilpailija> kuljettajat);
    }

    public void getDrivers(final MyCallback myCallback){
        mDocRef.collection("/kilpailut/"+kaupunki+"/"+valinta)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<Kilpailija> kuljettajat = new ArrayList<Kilpailija>();
                    for (QueryDocumentSnapshot document : task.getResult()){
                        System.out.println(document.getId()+ " "+ document.get("position"));
                        long l = document.getLong("position");
                        int position = (int) l;
                        System.out.println(document.get("kierrosajat"));
                        ArrayList<Double> kierrosajat_sorted = new ArrayList<Double>();
                        ArrayList<Double> kierrosajat = new ArrayList<Double>();
                        kierrosajat = (ArrayList<Double>) document.get("kierrosajat");
                        kierrosajat_sorted = (ArrayList<Double>) document.get("kierrosajat");
                        Collections.sort(kierrosajat_sorted);
                        kuljettajat.add(new Kilpailija(position, document.getId(), kierrosajat_sorted.get(0), kierrosajat));
                    }
                    myCallback.onCallback(kuljettajat);
                } else {
                    System.out.println("Error getting documents: "+ task.getException());
                }
            }
        });
    }

}
