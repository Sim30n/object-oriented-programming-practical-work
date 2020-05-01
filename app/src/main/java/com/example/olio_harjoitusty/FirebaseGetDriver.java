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

    public interface MyCallbackCircuits {
        void onCallback(ArrayList<Circuit> circuits);
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

    public void getAllCircuits(final MyCallbackCircuits myCallbackCircuits){
        mDocRef.collection("/osakilpailut2020/")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Circuit circuit = null;
                        if(task.isSuccessful()){
                            ArrayList<Circuit> circuits = new ArrayList<Circuit>();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                circuit = document.toObject(Circuit.class);
                                circuits.add(circuit);
                            }
                            myCallbackCircuits.onCallback(circuits);
                        } else {
                            System.out.println("Error getting documents: "+ task.getException());
                        }
                    }
                });
    }

    public void addData(String nimi, Long pos_a, Long pos_k, Long points, String race,
                        ArrayList<Double> times_a, ArrayList<Double> times_k, String pvm, String docname){
        /*//*********************************************************
        ArrayList<Double> kisa = new ArrayList<>();
        String[] kisa_ajat = {"43.199"};
        for (int i = 0; i<kisa_ajat.length; i++ ){
            kisa.add(Double.parseDouble(kisa_ajat[i]));
        }
        ArrayList<Double> aika = new ArrayList<>();
        String[] aika_ajat = {"46.367",
                "45.679",
                "45.205",
                "45.173",
                "44.714",
                "45.132",
                "45.134",
                "44.166",
                "44.580",
                "43.889"};
        for (int i = 0; i<aika_ajat.length; i++ ){
            aika.add(Double.parseDouble(aika_ajat[i]));
        }
        System.out.println("Kisa: " + kisa);
        System.out.println("Aika: " + aika);
        //**************************************************
        CollectionReference kilpailija = mDocRef.collection("kausi2020");
        Map<String, Object> data1 = new HashMap<>();
        data1.put("nimi", "petteri");
        data1.put("positio_aika", 2);
        data1.put("positio_kisa", 3);
        data1.put("pisteet", 15);
        data1.put("osakilpailu", "vantaa");
        data1.put("kierrosajat_aika", aika);
        data1.put("kierrosajat_kisa", kisa);
        data1.put("pvm", "12.10.2019");
        kilpailija.document("petteri-vantaa").set(data1);*/
        System.out.println(nimi);
        System.out.println(pos_a);
        System.out.println(pos_k);
        System.out.println(points);
        System.out.println(race);
        System.out.println(times_a);
        System.out.println(times_k);
        System.out.println(pvm);
        System.out.println(docname);
        CollectionReference kilpailija = mDocRef.collection("kausi2020");
        Map<String, Object> data1 = new HashMap<>();
        data1.put("nimi", nimi);
        data1.put("positio_aika", pos_a);
        data1.put("positio_kisa", pos_k);
        data1.put("pisteet", points);
        data1.put("osakilpailu", race);
        data1.put("kierrosajat_aika", times_a);
        data1.put("kierrosajat_kisa", times_k);
        data1.put("pvm", pvm);
        kilpailija.document(docname).set(data1);
    }
}
