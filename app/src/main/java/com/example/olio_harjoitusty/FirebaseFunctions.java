package com.example.olio_harjoitusty;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*This class is for firestore database*/

@SuppressWarnings("unchecked")
public class FirebaseFunctions {

    FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();
    private static final String TAG = "DocSnippets";

    String osakilpailu;
    String nimi;
    String circuitID;

    public FirebaseFunctions(){
        osakilpailu = "";
        nimi = "";
        circuitID = "";
    }

    /* Callbacks interfaces that can be used else where*/
    public interface MyCallback {
        void onCallback(ArrayList<Driver> kuljettajat);
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

    /*Setters for getting data from database*/
    public void setCircuitID(String circuitID) {
        this.circuitID = circuitID;
    }

    public void setOsakilpailu(String osakilpailu) {
        this.osakilpailu = osakilpailu;
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
                Driver driver = null;
                if(task.isSuccessful()){
                    ArrayList<Driver> kuljettajat = new ArrayList<Driver>();
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        driver = document.toObject(Driver.class);
                        kuljettajat.add(driver);
                    }
                    myCallback.onCallback(kuljettajat);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
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
                        Driver driver = null;
                        if(task.isSuccessful()){
                            ArrayList<Driver> kuljettajat = new ArrayList<Driver>();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                driver = document.toObject(Driver.class);
                                kuljettajat.add(driver);
                            }
                            myCallback.onCallback(kuljettajat);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                        Driver driver = null;
                        if(task.isSuccessful()){
                            ArrayList<Driver> kuljettajat = new ArrayList<Driver>();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                driver = document.toObject(Driver.class);
                                kuljettajat.add(driver);
                            }
                            myCallback.onCallback(kuljettajat);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                circuit = document.toObject(Circuit.class);
                                circuit.setI_d(document.getId());
                                circuits.add(circuit);
                            }
                            myCallbackCircuits.onCallback(circuits);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                circuit = document.toObject(Circuit.class);
                                circuit.setI_d(document.getId());

                            }
                            myCallbackCircuitByName.onCallback(circuit);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
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
        Log.d(TAG, "Document added.");

    }

    public void addCircuit(boolean isDriven, String info, String name, ArrayList<String> partisipants,
                           String pvm, String circuitID, ArrayList<String> kommentit){
        CollectionReference circuit = mDocRef.collection("osakilpailut2020");
        Map<String, Object> data1 = new HashMap<>();
        data1.put("ajettu", isDriven);
        data1.put("info", info);
        data1.put("nimi", name);
        data1.put("osallistujat", partisipants);
        data1.put("pvm", pvm);
        data1.put("kommentit", kommentit);
        circuit.document(circuitID).set(data1);
        Log.d(TAG, "Circuit added.");
    }

    public void addComment(String circuitId, String comment){
        DocumentReference kommentti = mDocRef.collection("osakilpailut2020")
                .document(circuitId);
        kommentti.update("kommentit", FieldValue.arrayUnion(comment));
        Log.d(TAG, "Comment added.");
    }

    public void addPartisipant(String circuitId, String partisipant){
        DocumentReference kommentti = mDocRef.collection("osakilpailut2020")
                .document(circuitId);
        kommentti.update("osallistujat", FieldValue.arrayUnion(partisipant));
        Log.d(TAG, "Participant added.");
    }

    public void deletePartisipant(String circuitID, String osa){
        DocumentReference kommentti = mDocRef.collection("osakilpailut2020")
                .document(circuitID);
        kommentti.update("osallistujat", FieldValue.arrayRemove(osa));
        Log.d(TAG, "Participant deleted.");
    }

    /*  This is for adding race results to database without UI in dev mode.
    public void addDataManual(){
        //*********************************************************
        ArrayList<Double> kisa = new ArrayList<>();
        String[] kisa_ajat = {
                "30.845",
                "30.614",
                "29.390",
                "29.901",
                "33.368",
                "29.087",
                "28.903",
                "28.681",
                "29.077",
                "28.586",
                "28.632",
                "28.757",
                "28.651",
                "29.203",
                "30.041",
                "28.924",
                "28.697",
                "28.794",
                "28.886"};
        for (int i = 0; i<kisa_ajat.length; i++ ){
            kisa.add(Double.parseDouble(kisa_ajat[i]));
        }
        ArrayList<Double> aika = new ArrayList<>();
        String[] aika_ajat = {
                "29.315",
                "29.724",
                "29.443",
                "29.293",
                "29.170",
                "31.525",
                "28.778",
                "29.108",
                "29.031",
                "28.689",
                "28.508",
                "28.782",
                "28.741",
                "28.925",
                "29.129",
                "28.924",
                "28.589",
                "29.401"};
        for (int i = 0; i<aika_ajat.length; i++ ){
            aika.add(Double.parseDouble(aika_ajat[i]));
        }
        //System.out.println("Kisa: " + kisa);
        //System.out.println("Aika: " + aika);
        //**************************************************
        CollectionReference kilpailija = mDocRef.collection("kausi2020");
        Map<String, Object> data1 = new HashMap<>();
        data1.put("nimi", "juha");
        data1.put("positio_aika", 8);
        data1.put("positio_kisa", 8);
        data1.put("pisteet", 4);
        data1.put("osakilpailu", "lahti");
        data1.put("kierrosajat_aika", aika);
        data1.put("kierrosajat_kisa", kisa);
        data1.put("pvm", "28.12.2019");
        kilpailija.document("juha-lahti").set(data1);
    }
     */
}
