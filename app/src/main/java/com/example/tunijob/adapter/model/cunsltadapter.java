package com.example.tunijob.adapter.model;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tunijob.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class cunsltadapter extends FirebaseRecyclerAdapter<OfferSubmitted,cunsltadapter.Viewholder> {


    public cunsltadapter(@NonNull FirebaseRecyclerOptions<OfferSubmitted> options) {
        super(options);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position, @NonNull OfferSubmitted model) {
        String D;
        D = model.getIdcandidat();
        Uri uri = null;


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Candidat");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(D)){
                    String tel = snapshot.child(D).child("nom").getValue(String.class);
                    holder.Description.setText(model.getTitreoffre());
                    holder.Titre.setText(tel);
                    holder.dwn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String CV =model.getCvurl();
                            DownloadManager.Request request=new DownloadManager.Request(Uri.parse(CV));
                            String title = URLUtil.guessFileName(CV,null,null);


                            downloadFile(holder.Titre.getContext(),"cv"+tel,".pdf", Environment.DIRECTORY_DOWNLOADS,model.getCvurl());


                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);
        downloadmanager.enqueue(request);
        Toast.makeText(context, "fin de telechargement", Toast.LENGTH_SHORT).show();


    }



    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_consultation,parent,false);
        return new Viewholder(view);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView Titre;
        TextView Description;
        ImageView dwn;
        public Viewholder(@NonNull View itemView){
            super(itemView);
            Titre=itemView.findViewById(R.id.nom);
            Description=itemView.findViewById(R.id.offert100);
            dwn=itemView.findViewById(R.id.download);

        }
    }
}
