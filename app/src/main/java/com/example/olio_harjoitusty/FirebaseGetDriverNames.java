package com.example.olio_harjoitusty;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class FirebaseGetDriverNames {
    FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();
    FirebaseGetCircuits firebaseGetCircuits = new FirebaseGetCircuits();
    ArrayList<String> nimet = new ArrayList<String>();
    ArrayList<String> uusi_nimet = new ArrayList<String>();


    public FirebaseGetDriverNames(){

    }

    public interface NamesCallback {
        void onCallback(ArrayList<String> nimet);
    }



    public void getNames(final NamesCallback namesCallback){
        firebaseGetCircuits.getCircuits(new FirebaseGetCircuits.MyCallback() {
            @Override
            public void onCallback(final ArrayList<String> osakilpailut) {
                for (int i = 0; i<osakilpailut.size(); i++){
                    mDocRef.collection("kilpailut/"+ osakilpailut.get(i) +"/kilpailu/")
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                //nimet = new ArrayList<String>();
                                for (QueryDocumentSnapshot document : task.getResult()){
                                    //System.out.println(document.getId());
                                    nimet.add(document.getId());
                                }
                                namesCallback.onCallback(nimet);

                            } else {
                                System.out.println("Error getting documents: "+ task.getException());
                            }
                            //uusi_nimet = removeDuplicates(nimet);
                        }
                    });
                }

            }
        });
    }
}
