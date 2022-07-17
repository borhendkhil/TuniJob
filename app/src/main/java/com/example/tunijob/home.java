package com.example.tunijob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tunijob.adapter.model.Offres;
import com.example.tunijob.adapter.model.RecycleViewOnItemClik;
import com.example.tunijob.adapter.model.adapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class home extends AppCompatActivity implements RecycleViewOnItemClik {
    SwipeRefreshLayout swipeRefreshLayout ;
    RecyclerView recyclerView;
    DatabaseReference reference;
    adapter adapter;
    Animation botAnim;
    EditText searchView;
    TextView textView;
    ImageView logoout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        searchView = findViewById(R.id.searchInput);
        String s=searchView.getText().toString();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FirebaseRecyclerOptions <Offres> options =
                        new FirebaseRecyclerOptions.Builder<Offres>()
                                .setQuery(reference.orderByChild("titre").startAt(s).endAt(s+"\uf8ff"), Offres.class)
                                .build();


                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        logoout=findViewById(R.id.logoout);
        logoout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        botAnim = AnimationUtils.loadAnimation(this, R.anim.lefttoright);
        recyclerView = findViewById(R.id.recyclerviewMarker);
        recyclerView.setAnimation(botAnim);
        textView = findViewById(R.id.welcomeText);
        reference = FirebaseDatabase.getInstance().getReference("Offres");
        
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions <Offres> options =
                new FirebaseRecyclerOptions.Builder<Offres>()
                        .setQuery(reference.orderByChild("description").startAt(s).endAt(s+"\uf8ff"), Offres.class)
                        .build();

        adapter = new adapter(options,this);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }

}