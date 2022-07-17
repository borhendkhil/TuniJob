package com.example.tunijob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tunijob.adapter.model.CandidatModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class new_codidat extends AppCompatActivity {
    String[] value ={"Ariana","Béja","Ben Arous","Bizerte","Gabès","Gafsa","Jendouba","Kairouan","Kébili","Kasserine","Le Kef","Mahdia","La Manouba","Médenine","Monastir","Nabeul","Sfax","Sidi Bouzid","Siliana","Sousse","Tataouine","Tozeur","Tunis","Zaghouan"};
    AutoCompleteTextView localisation;
    ArrayAdapter<String> adapterItems;
    Button button;
    TextInputEditText idC,emailC,passC,confermeC,nomC,telC,etatC;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressBar PB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_condidat);
        idC=findViewById(R.id.edCIN);

        emailC= findViewById(R.id.edemailC);
        passC=findViewById(R.id.edpassC);
        confermeC=findViewById(R.id.edconfermpassC);

        nomC=findViewById(R.id.ednameC);
        telC=findViewById(R.id.edtelC);
        etatC=findViewById(R.id.edetat);
        PB=findViewById(R.id.prgresscnx);


        localisation=findViewById(R.id.edlocC);
        adapterItems = new ArrayAdapter<String>(this,R.layout.item_localisation,value);
        localisation.setAdapter(adapterItems);
        localisation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Candidat");


        button=findViewById(R.id.Enregistre);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id= Objects.requireNonNull(idC.getText()).toString();
                String email= emailC.getText().toString();
                String pass= passC.getText().toString();
                String nom= nomC.getText().toString();
                String tel= telC.getText().toString();
                String adr = localisation.getText().toString();

                String cpass=confermeC.getText().toString();
                String etat=etatC.getText().toString();

                CandidatModel candidatModel =new CandidatModel(id,email,pass,nom,tel,adr,etat);
                
                


                if( TextUtils.isEmpty(id) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(nom) || TextUtils.isEmpty(tel) || TextUtils.isEmpty(etat)) {
                    PB.setVisibility(View.GONE);
                    Toast.makeText(new_codidat.this, "Remplire tout les case s'il vous plaît", Toast.LENGTH_SHORT).show();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(new_codidat.this, "vérifier votre email", Toast.LENGTH_SHORT).show();




                }else if(!pass.equals(cpass)){
                    PB.setVisibility(View.GONE);

                    Toast.makeText(new_codidat.this, "Vérifier la confermation de mot de passs", Toast.LENGTH_SHORT).show();
                }else {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            PB.setVisibility(View.GONE);
                            databaseReference.child(id).setValue(candidatModel);
                            Toast.makeText(new_codidat.this, "Succès du Enregistrer du compte", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            PB.setVisibility(View.GONE);
                            Toast.makeText(new_codidat.this, "error"+error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}