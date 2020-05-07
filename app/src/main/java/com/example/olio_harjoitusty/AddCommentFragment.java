package com.example.olio_harjoitusty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCommentFragment extends Fragment {

    String circuitID;
    EditText comment;
    EditText nick;
    Button submit;
    FirebaseFunctions firebaseFunctions = new FirebaseFunctions();

    public AddCommentFragment(String circuitID) {
        this.circuitID = circuitID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_comment, container, false);
        comment = v.findViewById(R.id.edit_comment);
        nick = v.findViewById(R.id.edit_comment_name);
        submit = v.findViewById(R.id.submit_comment);
        // Button will add comment to given circuit, can be anonymous.
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String strDate = dateFormat.format(date);
                String com = comment.getText().toString();
                String nic = nick.getText().toString();
                String toDatabase = com + " -" + nic + ", " + strDate;
                firebaseFunctions.addComment(circuitID, toDatabase); // add comment to database
                Fragment newFrag = new CircuitInfoFragment(circuitID);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFrag );
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        return v;
    }
}
