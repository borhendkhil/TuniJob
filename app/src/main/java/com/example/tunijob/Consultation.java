package com.example.tunijob;


import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tunijob.adapter.model.OfferSubmitted;
import com.example.tunijob.adapter.model.cunsltadapter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class Consultation extends AppCompatActivity  {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    cunsltadapter cunsltadapter;
    Animation botAnim;
    DatabaseReference reference;
    DatabaseReference databaseReference;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);
        Intent intent =getIntent();
        String ide = intent.getStringExtra("ide");
        botAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        recyclerView = findViewById(R.id.recycle10);
        recyclerView.setAnimation(botAnim);
        reference = FirebaseDatabase.getInstance().getReference("OfferSubmitted");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageView=findViewById(R.id.logoEntreprise);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Entreprise").child(ide);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String l = snapshot.child("logourl").getValue().toString();
                Picasso.get().load(l).placeholder(R.drawable.logo_entreprise).into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<OfferSubmitted> option =
                new FirebaseRecyclerOptions.Builder<OfferSubmitted>()
                        .setQuery(reference.orderByChild("identreprise").equalTo(ide), OfferSubmitted.class)
                        .build();
        cunsltadapter = new cunsltadapter(option);
        recyclerView.setAdapter(cunsltadapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        cunsltadapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        cunsltadapter.stopListening();
    }
}