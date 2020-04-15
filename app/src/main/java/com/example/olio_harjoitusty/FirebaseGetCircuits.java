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
public class FirebaseGetCircuits {

    FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();

    public FirebaseGetCircuits(){

    }

    public interface MyCallback {
        void onCallback(ArrayList<String> osakilpailut);
    }

    public void getCircuits(final MyCallback myCallback){
        mDocRef.collection("/kilpailut/")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<String> osakilpailut = new ArrayList<String>();
                    for (QueryDocumentSnapshot document : task.getResult()){
                        //System.out.println(document.getId());
                        osakilpailut.add(document.getId());
                    }
                    myCallback.onCallback(osakilpailut);
                } else {
                    System.out.println("Error getting documents: "+ task.getException());
                }
            }
        });
    }

}
