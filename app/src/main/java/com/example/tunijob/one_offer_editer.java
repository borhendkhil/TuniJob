package com.example.tunijob;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tunijob.adapter.model.Offres;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class one_offer_editer extends AppCompatActivity {

    ImageView logo;
    FirebaseFirestore db;
    private Button btn ;
    private ProgressBar PB;
    private TextInputEditText Titre,Description, Experience ,Postes;
    DatabaseReference databaseReference;
    private static final String myid="secret";
    String ide;
    Bundle extras;
    String idoff;
    String titre;
    String des;
    String Exp;
    String pos;
    String l;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_offer_editer);

        Titre=findViewById(R.id.edTitre1);
        Description=findViewById(R.id.eddiscription1);
        Experience=findViewById(R.id.edexperience1);
        Postes=findViewById(R.id.edpost1);
        PB=findViewById(R.id.prgresscnx);
        btn=findViewById(R.id.Enregistre1);
        logo=findViewById(R.id.logoEntreprise);
        SharedPreferences preferences = getSharedPreferences(myid,0);
        if (preferences.contains("msg")){
            ide = preferences.getString("msg","notfound");
        }
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
        extras =getIntent().getExtras();
        if (extras != null) {
            titre=extras.getString("tit");
            Titre.setText(titre);
            des=extras.getString("de");
            Description.setText(des);
            Exp=extras.getString("ex");
            Experience.setText(Exp);
            pos=extras.getString("post");
            Postes.setText(pos);
            idoff = extras.getString("ido");
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titre=Titre.getText().toString();
                des=Description.getText().toString();
                Exp=Experience.getText().toString();
                pos=Postes.getText().toString();
                if( TextUtils.isEmpty(titre) || TextUtils.isEmpty(des) || TextUtils.isEmpty(Exp) || TextUtils.isEmpty(pos) ) {
                    PB.setVisibility(View.GONE);
                    Toast.makeText(one_offer_editer.this, "Remplire tout les case s'il vous plaît", Toast.LENGTH_SHORT).show();
                }else {
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("Offres").child(idoff);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            PB.setVisibility(View.GONE);
                            Offres offres=new Offres(des,Exp,l,pos,titre,ide,idoff);
                            databaseReference.setValue(offres);
                            Toast.makeText(one_offer_editer.this, "Succès du Modification de offre", Toast.LENGTH_SHORT).show();

                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            PB.setVisibility(View.GONE);
                            Toast.makeText(one_offer_editer.this, "error"+error.toString(), Toast.LENGTH_SHORT).show();


                        }
                    });

                }

            }
        });
    }
}
