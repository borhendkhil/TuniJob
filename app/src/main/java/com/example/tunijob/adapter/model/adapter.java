package com.example.tunijob.adapter.model;


import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.tunijob.AllOffre;
import com.example.tunijob.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class adapter extends FirebaseRecyclerAdapter<Offres,adapter.ViewHolder> {
    private RecycleViewOnItemClik recycleViewOnItemClik;

    public adapter(@NonNull FirebaseRecyclerOptions<Offres> options, RecycleViewOnItemClik recycleViewOnItemClik) {
        super(options);
        this.recycleViewOnItemClik = recycleViewOnItemClik;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Offres model) {
        String s;
        if (model.getDescription().length()>=60){
            s = model.getDescription().substring(0,60);

        }else {
            s= model.getDescription();
        }
        holder.Titre.setText(model.getTitre());
        holder.Description.setText(s);
        Glide.with(holder.imag.getContext()).load(model.getLogoentreprise())
                .placeholder(R.drawable.logo_entreprise)
                .into(holder.imag);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), AllOffre.class);
                intent.putExtra("description" ,model.getDescription());
                intent.putExtra("ex",model.getExperience());
                intent.putExtra("logoE",model.getLogoentreprise());
                intent.putExtra("postes" ,model.getPostes());
                intent.putExtra("tit",model.getTitre());

                intent.putExtra("ident",model.getIdentreprise());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offre,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder  {
         TextView Titre;
         TextView Description;
         ImageView imag;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Titre = itemView.findViewById(R.id.titreoffre1);
            Description = itemView.findViewById(R.id.description1);
            imag = itemView.findViewById(R.id.iconentreprise);
        }
    }
}
