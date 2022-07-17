package com.example.tunijob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.tunijob.adapter.model.OfferSubmitted;
import com.example.tunijob.adapter.model.Offres;
import com.example.tunijob.adapter.model.cunsltadapter;
import com.example.tunijob.adapter.model.offeradapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class Edit_Offer extends AppCompatActivity {
    FirebaseFirestore db;
    offeradapter offeradapter;
    RecyclerView recyclerView;
    Animation botAnim;
    DatabaseReference reference;
    DatabaseReference databaseReference;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_offer);
        Intent intent =getIntent();
        String ide = intent.getStringExtra("ide");
        botAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        recyclerView=findViewById(R.id.recycle1000);
        recyclerView.setAnimation(botAnim);
        reference = FirebaseDatabase.getInstance().getReference("Offres");
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Offres> option =
                new FirebaseRecyclerOptions.Builder<Offres>()
                        .setQuery(reference.orderByChild("identreprise").equalTo(ide), Offres.class)
                        .build();

        offeradapter = new offeradapter(option);
        recyclerView.setAdapter(offeradapter);

        logo=findViewById(R.id.logoEntreprise);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Entreprise").child(ide);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String l = snapshot.child("logourl").getValue().toString();
                Picasso.get().load(l).placeholder(R.drawable.logo_entreprise).into(logo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
    @Override
    public void onStart() {
        super.onStart();
        offeradapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        offeradapter.stopListening();
    }
}