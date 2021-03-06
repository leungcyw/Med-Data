package com.example.vax.data;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vax.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Document;

import java.util.HashMap;

public class AddMeds extends AppCompatActivity {
    FirebaseFirestore db;
    private String currentUserId;



    public void backToMain(View view) {

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_meds);

        db = FirebaseFirestore.getInstance();

        final EditText addMedText = findViewById(R.id.addMedsText);
        final Button addButton = findViewById(R.id.addMedsButton);

        Intent intent = getIntent();
        currentUserId = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        DocumentReference docref = db.collection("users").document(currentUserId);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> temp = new HashMap<>();
                temp.put(addMedText.getText().toString(), "placeholder");
                db.collection("users").document(currentUserId)
                        .set(temp, SetOptions.merge());
                System.out.println("added to db");
                MainActivity.createAgain = true;
                MyMeds.arrayAdapter.add(addMedText.getText().toString());
                //NavUtils.navigateUpFromSameTask(AddMeds.this);
                showMedMessage();

                finish();
            }
        });
    }

    public void showMedMessage() {
        displayToast(getString(R.string.add_med_message));
    }


    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}


