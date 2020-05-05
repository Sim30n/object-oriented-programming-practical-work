package com.example.olio_harjoitusty;

import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    private static final String TAG = "DocSnippets";

    String osakilpailu;
    String valinta;
    String nimi;
    String circuitID;

    public FirebaseGetDriver(){
        osakilpailu = "";
        valinta = "";
        nimi = "";
        circuitID = "";
    }

    public interface MyCallback {
        void onCallback(ArrayList<Kilpailija> kuljettajat);
    }

    public interface MyCallbackCircuits {
        void onCallback(ArrayList<Circuit> circuits);
    }

    public interface MyCallbackCircuitByName {
        void onCallback(Circuit circuit);
    }

    public interface MyCallbackCircuitByID {
        void onCallback(Circuit circuit);
    }

    public void setCircuitID(String circuitID) {
        this.circuitID = circuitID;
    }

    public void setOsakilpailu(String osakilpailu) {
        this.osakilpailu = osakilpailu;
    }

    public void setValinta(String valinta) {
        this.valinta = valinta;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
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
                                System.out.println(document.getId()+"#############%%%%%%%%%%%%%%%");
                                circuit.setI_d(document.getId());
                                circuits.add(circuit);
                            }
                            myCallbackCircuits.onCallback(circuits);
                        } else {
                            System.out.println("Error getting documents: "+ task.getException());
                        }
                    }
                });
    }

    public void getCircuitByName(final MyCallbackCircuitByName myCallbackCircuitByName){
        mDocRef.collection("/osakilpailut2020/")
                .whereEqualTo("nimi", osakilpailu)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Circuit circuit = null;
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                System.out.println("##########################"+document.getId());
                                circuit = document.toObject(Circuit.class);
                                circuit.setI_d(document.getId());

                            }
                            myCallbackCircuitByName.onCallback(circuit);
                        } else {
                            System.out.println("Error getting documents: "+ task.getException());
                        }
                    }
                });
    }

    public void getCircuitByID(final MyCallbackCircuitByID myCallbackCircuitByID){
        DocumentReference docRef = mDocRef.collection("osakilpailut2020").document(circuitID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Circuit circuit = null;
                    if (document.exists()) {
                        circuit = document.toObject(Circuit.class);
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                    myCallbackCircuitByID.onCallback(circuit);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void deleteCircuit(String circuitID){
        mDocRef.collection("osakilpailut2020").document(circuitID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
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

    public void addCircuit(boolean isDriven, String info, String name,
                           ArrayList<String> partisipants, String pvm, String circuitID){
        CollectionReference circuit = mDocRef.collection("osakilpailut2020");
        Map<String, Object> data1 = new HashMap<>();
        data1.put("ajettu", isDriven);
        data1.put("info", info);
        data1.put("nimi", name);
        data1.put("osallistujat", partisipants);
        data1.put("pvm", pvm);
        circuit.document(circuitID).set(data1);
    }
}
