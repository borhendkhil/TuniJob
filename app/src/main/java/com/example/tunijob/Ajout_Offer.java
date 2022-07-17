package com.example.tunijob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tunijob.adapter.model.EntrepriseModal;
import com.example.tunijob.adapter.model.Offres;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Ajout_Offer extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseStorage mStorage;
    private Button btn ;
    private ProgressBar PB;
    private TextInputEditText Titre,Description, Experience ,Postes;
    ImageView logo;
    String l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_offer);
        Intent intent =getIntent();
        String ide = intent.getStringExtra("ide");


        Titre=findViewById(R.id.edTitre);
        Description=findViewById(R.id.eddiscription);
        Experience=findViewById(R.id.edexperience);

        Postes=findViewById(R.id.edpost);
        PB=findViewById(R.id.prgresscnx);
        btn=findViewById(R.id.Enregistre);
        logo=findViewById(R.id.logoEntreprise);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Entreprise").child(ide);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                l = snapshot.child("logourl").getValue().toString();
                Picasso.get().load(l).placeholder(R.drawable.logo_entreprise).into(logo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PB.setVisibility(View.VISIBLE);
                String titre= Titre.getText().toString();
                String des= Description.getText().toString();
                String Exp= Experience.getText().toString();
                String pos = Postes.getText().toString();
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Offres").push();




                if( TextUtils.isEmpty(titre) || TextUtils.isEmpty(des) || TextUtils.isEmpty(Exp) || TextUtils.isEmpty(pos) ) {
                    PB.setVisibility(View.GONE);
                    Toast.makeText(Ajout_Offer.this, "Remplire tout les case s'il vous plaît", Toast.LENGTH_SHORT).show();
                }else {

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            PB.setVisibility(View.GONE);

                            Offres offres=new Offres(des,Exp,l,pos,titre,ide,snapshot.getKey());


                            databaseReference.setValue(offres);
                            Toast.makeText(Ajout_Offer.this, "Succès du Enregistrer de offre", Toast.LENGTH_SHORT).show();

                            finish();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            PB.setVisibility(View.GONE);
                            Toast.makeText(Ajout_Offer.this, "error"+error.toString(), Toast.LENGTH_SHORT).show();


                        }
                    });
                }

            }
        });










    }
}