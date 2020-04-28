package com.example.olio_harjoitusty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;
    Button button;
    BottomNavigationView bottomnav;
    EditText userName;
    EditText passWord;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomnav = findViewById(R.id.bottom_navigation);
        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        bottomnav.setOnNavigationItemSelectedListener(navlistener);
        bottomnav.setVisibility(View.GONE);
        button = findViewById(R.id.login);

        //FirebaseAuth.getInstance().signOut();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(userName.getText().toString(), passWord.getText().toString());
            }
        });


        //----------------------------------------------------------------
        // [START create_user_with_email]
        /*mAuth.createUserWithEmailAndPassword("user@user.com", "12goija032pjäreghjk034")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            System.out.println("SUCCESS");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            *//*Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();*//*
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]

        // Check if user is signed in (non-null) and update UI accordingly.
        */
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        //----------------------------------------------------------------





        /*
        FirebaseGetDriver firebaseGetDriver = new FirebaseGetDriver("kaikki", "kaikki", "kaikki");
        firebaseGetDriver.addData();

         */
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_osakilpailut:
                            selectedFragment = new OsakilpailutFragment();
                            break;
                        case R.id.nav_kuljettajat:
                            selectedFragment = new KuljettajatFragment();
                            break;
                        case R.id.nav_tulokset:
                            selectedFragment = new TuloksetFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new OsakilpailutFragment()).commit();
            bottomnav.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
            userName.setVisibility(View.GONE);
            passWord.setVisibility(View.GONE);
            System.out.println("LOGIN");
        } else {
            /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new LoginFragment()).commit();*/

            System.out.println("FAIL");
        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            System.out.println("Sisäään");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

}
