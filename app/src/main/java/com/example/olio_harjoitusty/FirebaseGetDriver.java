package com.example.olio_harjoitusty;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class FirebaseGetDriver {

    FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();

    String osakilpailu;
    String valinta;
    String nimi;

    public FirebaseGetDriver(String osakilpailu, String valinta, String nimi){
        this.osakilpailu = osakilpailu;
        this.valinta = valinta;
        this.nimi = nimi;
    }

    public interface MyCallback {
        void onCallback(ArrayList<Kilpailija> kuljettajat);
    }


    public void getDriversByRace(final MyCallback myCallback){
        mDocRef.collection("/kausi2020/")
                .whereEqualTo("osakilpailu", osakilpailu)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Kilpailija kilpailija = null;
                if(task.isSuccessful()){
                    ArrayList<Kilpailija> kuljettajat = new ArrayList<Kilpailija>();
                    for (QueryDocumentSnapshot document : task.getResult()){
                        kilpailija = document.toObject(Kilpailija.class);
                        kuljettajat.add(kilpailija);
                    }
                    myCallback.onCallback(kuljettajat);
                } else {
                    System.out.println("Error getting documents: "+ task.getException());
                }
            }
        });
    }

    public void getDriversByName(final MyCallback myCallback){
        mDocRef.collection("/kausi2020/")
                .whereEqualTo("nimi", nimi)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Kilpailija kilpailija = null;
                        if(task.isSuccessful()){
                            ArrayList<Kilpailija> kuljettajat = new ArrayList<Kilpailija>();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                kilpailija = document.toObject(Kilpailija.class);
                                kuljettajat.add(kilpailija);
                            }
                            myCallback.onCallback(kuljettajat);
                        } else {
                            System.out.println("Error getting documents: "+ task.getException());
                        }
                    }
                });
    }

    public void getAllDrivers(final MyCallback myCallback){
        mDocRef.collection("/kausi2020/")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Kilpailija kilpailija = null;
                        if(task.isSuccessful()){
                            ArrayList<Kilpailija> kuljettajat = new ArrayList<Kilpailija>();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                kilpailija = document.toObject(Kilpailija.class);
                                kuljettajat.add(kilpailija);
                            }
                            myCallback.onCallback(kuljettajat);
                        } else {
                            System.out.println("Error getting documents: "+ task.getException());
                        }
                    }
                });
    }

    public void addData(){
        CollectionReference kilpailija = mDocRef.collection("kausi2020");
        Map<String, Object> data1 = new HashMap<>();
        data1.put("nimi", "petteri");
        data1.put("positio_aika", 5);
        data1.put("positio_kisa", 4);
        data1.put("pisteet", 12);
        data1.put("osakilpailu", "imatra");
        data1.put("kierrosajat_aika", Arrays.asList(53.1, 51.86, 55.89, 52.02, 52.26, 51.75, 51.73, 51.85, 52.01, 52.03, 51.63, 51.6));
        data1.put("kierrosajat_kisa", Arrays.asList(60.54, 52.48, 52.24, 53.96, 51.14, 51.13, 51.05, 50.76, 50.96, 56.79, 51.57, 51.58, 51.05, 50.77, 51.25, 51.62, 50.94, 50.67, 51.31, 50.97));
        data1.put("pvm", "14.9.2019");
        kilpailija.document("petteri-imatra").set(data1);
    }
}
