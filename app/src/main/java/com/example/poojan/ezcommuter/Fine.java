package com.example.poojan.ezcommuter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Fine extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText user;
    android.support.v7.widget.Toolbar toolbar;
    private TextView amt;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fine);
        toolbar = findViewById(R.id.finne);
        toolbar.setTitle("Fine");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth =FirebaseAuth.getInstance();
        final Spinner spinner = (Spinner) findViewById(R.id.finetype);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.fine_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        amt =  findViewById(R.id.amt);
        user =  findViewById(R.id.email);
        btnLogin =  findViewById(R.id.fine);
        Log.d("spinner",spinner.getSelectedItem().toString());
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("FineType").child(spinner.getSelectedItem().toString());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("fine",spinner.getSelectedItem().toString());
                amt.setText(dataSnapshot.getValue(Long.class).toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.d("fine",mAuth.getCurrentUser().getEmail());
        Log.d("fineAmt",amt.getText().toString());
        Log.d("fineUser",user.getText().toString());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                        .child("FineType").child(spinner.getSelectedItem().toString());
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("fine",spinner.getSelectedItem().toString());
                        amt.setText(dataSnapshot.getValue(Long.class).toString());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final fineclass fc = new fineclass(mAuth.getCurrentUser().getEmail(),
                        user.getText().toString(), spinner.getSelectedItem().toString(), amt.getText().toString());
                Log.d("fine","Hello");
                ref.child("Fine").push().setValue(fc);
                Intent intent = new Intent(Fine.this, Home.class);
                startActivity(intent);
                finish();
            }


        });



    }
}
