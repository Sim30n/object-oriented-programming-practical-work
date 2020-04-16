package com.example.olio_harjoitusty;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class FirebaseGetDriver {

    FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();

    String osakilpailu;
    String valinta;

    public FirebaseGetDriver(String osakilpailu, String valinta){
        this.osakilpailu = osakilpailu;
        this.valinta = valinta;
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

    /*public void addData(){
        CollectionReference kilpailija = mDocRef.collection("kausi2020");
        Map<String, Object> data1 = new HashMap<>();
        data1.put("nimi", "lauri");
        data1.put("positio_aika", 3);
        data1.put("positio_kisa", 1);
        data1.put("pisteet", 25);
        data1.put("osakilpailu", "imatra");
        data1.put("kierrosajat_aika", Arrays.asList(52.21, 51.96));
        data1.put("kierrosajat_kisa", Arrays.asList(53.84, 52.46));
        data1.put("pvm", "14.9.2019");
        kilpailija.document("lauri-imatra").set(data1);

    }*/

}
