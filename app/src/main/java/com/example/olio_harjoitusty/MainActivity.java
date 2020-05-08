package com.example.olio_harjoitusty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

        /*FirebaseGetDriver firebaseGetDriver = new FirebaseGetDriver();
        firebaseGetDriver.addDataManual();*/
        //FirebaseAuth.getInstance().signOut();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(userName.getText().toString(), passWord.getText().toString());
            }
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_osakilpailut:
                            selectedFragment = new AllCircuitsFragment();
                            break;
                        case R.id.nav_kuljettajat:
                            selectedFragment = new UserProfileFragment();
                            break;
                        case R.id.nav_tulokset:
                            selectedFragment = new ResultsFragment();
                            break;
                        case R.id.nav_add_circuit:
                            selectedFragment = new AddCircuitFragment();
                            break;
                        case R.id.nav_add_points:
                            selectedFragment = new AddPointsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public void showToast(){
        Toast.makeText(this, "Sisäänkirjautuminen epäonnistui!",
                Toast.LENGTH_LONG).show();
    }

    // Update UI after login.
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String uid = user.getUid();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AllCircuitsFragment()).commit();
            if(uid.equals("ju8VUgiCEAXdtXieoDXDgyjnNnr2") != true){
                hideItem();
            }
            bottomnav.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
            userName.setVisibility(View.GONE);
            passWord.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "Login fail");
        }
    }

    // Sign in method.
    private void signIn(String email, String password) {
        if(email.equals("")!= true || password.equals("") != true) {
            Log.d(TAG, "signIn:" + email);
            // [START sign_in_with_email]
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                showToast();
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                updateUI(null);
                            }
                        }
                    });
            // [END sign_in_with_email]
        } else{
            showToast();
        }
    }

    // If not admin logged in, hide menu items
    private void hideItem() {
        Menu nav_Menu = bottomnav.getMenu();
        nav_Menu.findItem(R.id.nav_add_circuit).setVisible(false);
        nav_Menu.findItem(R.id.nav_add_points).setVisible(false);
    }

}
