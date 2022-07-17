package com.example.tunijob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class home_Entreprise extends AppCompatActivity {

    ShapeableImageView bgapp;
    LinearLayout textsplash,texthome,menus;
    Animation formbottom;
    LinearLayout consultation, edit_compt,edit_offer,ajout_offer;
    DatabaseReference databaseReference;
    TextView textView;

    String ide;

    private static final String myid="secret";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_entreprise);



        bgapp = findViewById(R.id.bgapp);
        textsplash=findViewById(R.id.textsplash);
        texthome= findViewById(R.id.texthome);
        menus=findViewById(R.id.menus);
        textView=findViewById(R.id.titreEntreprise);

        SharedPreferences preferences = getSharedPreferences(myid,0);
        if (preferences.contains("msg")){
            ide = preferences.getString("msg","notfound");
        }

        formbottom=AnimationUtils.loadAnimation(this,R.anim.formbot);

        bgapp.animate().translationY(-1700).setDuration(800).setStartDelay(300);
        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(600);

        texthome.startAnimation(formbottom);

        menus.startAnimation(formbottom);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Entreprise");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(ide).child("nom").getValue(String.class);
                textView.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        consultation = findViewById(R.id.consultation);
        consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(home_Entreprise.this,Consultation.class);
                intent.putExtra("ide",ide);
                startActivity(intent);
            }
        });
        edit_compt=findViewById(R.id.edid_compte);
        edit_compt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(home_Entreprise.this, EditEntreprise.class);
                intent.putExtra("ide",ide);
                startActivity(intent);
            }
        });

        edit_offer=findViewById(R.id.edit_offer);
        edit_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(home_Entreprise.this,Edit_Offer.class);
                intent.putExtra("ide",ide);
                startActivity(intent);
            }
        });
        ajout_offer=findViewById(R.id.ajout_offer);
        ajout_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(home_Entreprise.this,Ajout_Offer.class);
                intent.putExtra("ide",ide);

                startActivity(intent);
            }
        });

    }
}