package com.example.tunijob.adapter.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tunijob.R;
import com.example.tunijob.*;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class offeradapter extends FirebaseRecyclerAdapter<Offres,offeradapter.ViewHolde> {


    public offeradapter(@NonNull FirebaseRecyclerOptions<Offres> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolde holder, int position, @NonNull Offres model) {
        holder.Titre.setText(model.getTitre());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(),one_offer_editer.class);
                intent.putExtra("de" ,model.getDescription());
                intent.putExtra("ex",model.getExperience());

                intent.putExtra("post" ,model.getPostes());
                intent.putExtra("tit",model.getTitre());
                intent.putExtra("ido",model.getIdoffre());
                holder.itemView.getContext().startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.titre_offer_item,parent,false)    ;
        return new ViewHolde(view);
    }

    public class ViewHolde extends RecyclerView.ViewHolder{

        TextView Titre;
        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            Titre=itemView.findViewById(R.id.titreoffre12);
        }
    }
}
