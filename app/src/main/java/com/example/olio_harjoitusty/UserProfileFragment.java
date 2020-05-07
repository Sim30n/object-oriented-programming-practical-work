package com.example.olio_harjoitusty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class UserProfileFragment extends Fragment {
    EditText usrName;
    EditText usrEmail;
    EditText psw1;
    EditText psw2;
    Button updateInfo;
    Button logout;

    private static final String TAG = "Driver profile";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kuljettajat, container, false);
        usrName = (EditText) v.findViewById(R.id.user_name);
        usrEmail = (EditText) v.findViewById(R.id.user_email);
        psw1 = (EditText) v.findViewById(R.id.user_password1);
        psw2 = (EditText) v.findViewById(R.id.user_password2);
        updateInfo = (Button) v.findViewById(R.id.update_info);
        logout = (Button) v.findViewById(R.id.logout);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address,
            String name = user.getDisplayName();
            String email = user.getEmail();
            usrName.setText(name);
            usrEmail.setText(email);
        }
        // Update userinfo.
        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(usrName.getText().toString())
                        .build();
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    System.out.println("Updated");
                                    /*Log.d(TAG, "User profile updated.");*/
                                }
                            }
                        });
                String password1 = psw1.getText().toString();
                String password2 = psw1.getText().toString();
                // Check if the passwords are equal.
                if(password1.equals("")!=true && password2.equals("")!=true && password1.equals(password2)){
                    user.updatePassword(password1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User password updated.");
                                    }
                                }
                            });
                }
            }
        });

        //Logout with firestore.
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

}
