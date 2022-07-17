package com.example.tunijob;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.tunijob.adapter.model.OfferSubmitted;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;



public class AllOffre extends AppCompatActivity {
    TextView exp,pos,description,titre;
    ImageView logo;
    CoordinatorLayout upload;
    Uri uri = null;
    Button btn;
    DatabaseReference database;
    StorageReference storage;
    String idoff;
    Bundle extras;
    SharedPreferences sharedPreferences;
    private static final String myid="secret";
    String idcon;
    String identreprise;
    ImageView back ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_offre);
        back= findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


        description= findViewById(R.id.discription);
        titre=findViewById(R.id.titre);
        pos=findViewById(R.id.postes);
        exp=findViewById(R.id.experience);
        logo=findViewById(R.id.logo);
        SharedPreferences preferences = getSharedPreferences(myid,0);
        if (preferences.contains("msg")){
            idcon = preferences.getString("msg","notfound");
        }



        extras =getIntent().getExtras();
        if (extras != null){

            titre.setText(extras.getString("tit"));
            description.setText(extras.getString("description"));
            exp.setText(extras.getString("ex"));
            pos.setText(extras.getString("postes"));

            identreprise =extras.getString("ident");
            String l =extras.getString("logoE");
            Glide.with(logo).load(l)
                    .placeholder(R.drawable.logo_entreprise)
                    .error(R.drawable.logo_entreprise)
                    .into(logo);
        }else {
            Toast.makeText(this, "error sry", Toast.LENGTH_SHORT).show();
        }




        upload=findViewById(R.id.upload);
        storage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference("OfferSubmitted");
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();

            }
        });
    }
    ProgressDialog dialog;
    private void selectFile() {
        Intent intent1 = new Intent();
        intent1.setType("application/pdf");
        intent1.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent1,1);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!= null && data.getData()!=null) {
            UploadCV(data.getData());
        }
    }

    private void UploadCV(Uri data) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Téléchargement ...");
        progressDialog.show();
        StorageReference reference=storage.child("cv/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri url=uriTask.getResult();
                        OfferSubmitted applyOffer=new OfferSubmitted(extras.getString("tit"),idcon,identreprise,url.toString());
                        database.child(database.push().getKey()).setValue(applyOffer);
                        Toast.makeText(AllOffre.this, "fin du téléchargement", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();



                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress=(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        progressDialog.setMessage("téléchargé"+(int)progress+"%");
                        if (progress ==100.0){


                        }


                    }




                });
    }


}