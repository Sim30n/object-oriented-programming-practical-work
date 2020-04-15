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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;


@SuppressWarnings("unchecked")
public class FirebaseGetDriver {

    FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();

    String osakilpailu;
    String valinta;
    Integer pisteet;
    ArrayList<String> testi = new ArrayList<String>();

    public FirebaseGetDriver(String osakilpailu, String valinta){
        this.osakilpailu = osakilpailu;
        this.valinta = valinta;
    }

    public interface MyCallback {
        void onCallback(ArrayList<Kilpailija> kuljettajat);
    }

    /*public void getDrivers(final MyCallback myCallback){
        mDocRef.collection("/2020/kilpailut/"+kaupunki+"/")
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
                        if(valinta.equals("aika-ajot")){
                            pisteet = 0;
                        } else {
                            long pisteet_l = document.getLong("pisteet");
                            pisteet = (int) pisteet_l;
                        }
                        //kuljettajat.add(new Kilpailija(position, document.getId(), kierrosajat_sorted.get(0), kierrosajat, pisteet, kaupunki));
                    }
                    myCallback.onCallback(kuljettajat);
                } else {
                    System.out.println("Error getting documents: "+ task.getException());
                }
            }
        });
    }*/

    public void getDrivers(final MyCallback myCallback){
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

    /*public void addData(){
        CollectionReference kilpailija = mDocRef.collection("kausi2020");
        Map<String, Object> data1 = new HashMap<>();
        data1.put("nimi", "petteri");
        data1.put("positio_aika", 5);
        data1.put("positio_kisa", 4);
        data1.put("pisteet", 12);
        data1.put("osakilpailu", "imatra");
        data1.put("kierrosajat_aika", Arrays.asList(53.1, 51.86));
        data1.put("kierrosajat_kisa", Arrays.asList(60.54, 52.48));
        data1.put("pvm", "14.9.2019");
        kilpailija.document("petteri-imatra").set(data1);

    }*/

}
