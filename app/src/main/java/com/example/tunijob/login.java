package com.example.tunijob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class login extends AppCompatActivity {
    TextInputEditText emailE,passE;
    Button cnx;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ProgressBar PB;
    private SharedPreferences sharedPreferences;
    private static final String myid="secret";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailE =findViewById(R.id.edid);
        passE =findViewById(R.id.edpass);
        PB=findViewById(R.id.prgresscnx);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();


        cnx= findViewById(R.id.connection);
        cnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PB.setVisibility(View.VISIBLE);
                String tel=emailE.getText().toString();
                String pass=passE.getText().toString();
                if(TextUtils.isEmpty(tel) || TextUtils.isEmpty(pass)){
                    PB.setVisibility(View.GONE);
                    Toast.makeText(login.this, "id ou le mot de passe est vide", Toast.LENGTH_SHORT).show();

                }else {

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("Entreprise").hasChild(tel) && snapshot.child("Entreprise").child(tel).child("motdepasse").getValue(String.class).equals(pass)){


                                    PB.setVisibility(View.GONE);
                                    Toast.makeText(login.this,"succès de connection", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(login.this,home_Entreprise.class);
                                    intent.putExtra("ide",tel);
                                    sharedPreferences=getSharedPreferences(myid,0);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("msg",tel);
                                    editor.commit();
                                    finish();
                                    startActivity(intent);
                            }else {
                                databaseReference=firebaseDatabase.getReference();
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.child("Candidat").hasChild(tel) && snapshot.child("Candidat").child(tel).child("motdepasse").getValue(String.class).equals(pass)){

                                                PB.setVisibility(View.GONE);
//                                                Toast.makeText(login.this, "succès de connection", Toast.LENGTH_SHORT).show();

                                                Intent intent=new Intent(login.this,home.class);
                                                intent.putExtra("cin",tel);
                                                sharedPreferences=getSharedPreferences(myid,0);
                                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                                editor.putString("msg",tel);
                                                editor.commit();

                                                startActivity(intent);
                                                finish();



                                        }else {
                                            PB.setVisibility(View.GONE);
                                            Toast.makeText(login.this, "vérifier votre donne ", Toast.LENGTH_SHORT).show();

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        finish();

                                    }
                                });

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            finish();

                        }
                    });


                }
            }
        });
    }


}
