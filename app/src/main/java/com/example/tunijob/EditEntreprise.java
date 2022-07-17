package com.example.tunijob;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tunijob.adapter.model.EntrepriseModal;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Date;


public class EditEntreprise extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseStorage mStorage;
    ImageView logo;
    TextView nom;





    String[] value ={"Ariana","Béja","Ben Arous","Bizerte","Gabès","Gafsa","Jendouba","Kairouan","Kébili","Kasserine","Le Kef","Mahdia","La Manouba","Médenine","Monastir","Nabeul","Sfax","Sidi Bouzid","Siliana","Sousse","Tataouine","Tozeur","Tunis","Zaghouan"
    };
    AutoCompleteTextView localisation;
    ArrayAdapter<String> adapterItems;
    private TextInputEditText idE,emailE, passE ,cpassE ,nomE,telE ;
    private Button btn ;
    private ProgressBar PB;

    private static final  int Gallery_Code=1;
    Uri imageUrl=null;
    ProgressDialog progressDialog;
    EntrepriseModal modal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entreprise);
        Intent intent =getIntent();
        String ide = intent.getStringExtra("ide");
        nom=findViewById(R.id.non);
        logo=findViewById(R.id.logoEntreprise);
        idE=findViewById(R.id.edEid);
        idE.setText(ide);
        emailE=findViewById(R.id.edemailE);
        passE=findViewById(R.id.edpassE);
        cpassE=findViewById(R.id.edconfermpassE);
        nomE=findViewById(R.id.ednameE);
        telE=findViewById(R.id.edtelE);
        localisation=findViewById(R.id.edlocC);
        btn=findViewById(R.id.Enregistre);
        adapterItems = new ArrayAdapter<String>(this,R.layout.item_localisation,value);

        localisation.setAdapter(adapterItems);


        databaseReference=FirebaseDatabase.getInstance().getReference().child("Entreprise");
        mStorage=FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_Code);

            }
        });
        PB=findViewById(R.id.prgresscnx);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = snapshot.child(ide).child("email").getValue(String.class);
                emailE.setText(email);
                String name = snapshot.child(ide).child("nom").getValue(String.class);
                nomE.setText(name);
                nom.setText(name);
                String tel = snapshot.child(ide).child("tel").getValue(String.class);
                telE.setText(tel);
                String adr = snapshot.child(ide).child("adresseville").getValue(String.class);


                int i=adapterItems.getPosition(adr);
                localisation.setText(adapterItems.getItem(i).toString());

                String password = snapshot.child(ide).child("motdepasse").getValue(String.class);
                passE.setText(password);
                String imaget = snapshot.child(ide).child("logourl").getValue(String.class);
                Glide.with(logo).load(imaget)
                        .placeholder(R.drawable.logo_entreprise)
                        .into(logo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        localisation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Code && resultCode==RESULT_OK)
        {
            imageUrl=data.getData();
            logo.setImageURI(imageUrl);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PB.setVisibility(View.VISIBLE);
                String id=idE.getText().toString();
                String email=emailE.getText().toString();
                String pass=passE.getText().toString();
                String cpass=cpassE.getText().toString();
                String nom=nomE.getText().toString();
                String tel=telE.getText().toString();
                String adr=localisation.getText().toString();
                if(TextUtils.isEmpty(id) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(nom) || TextUtils.isEmpty(tel) || TextUtils.isEmpty(adr)) {
                    PB.setVisibility(View.GONE);
                    Toast.makeText(EditEntreprise.this, "Remplire tout les case s'il vous plaît", Toast.LENGTH_SHORT).show();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    PB.setVisibility(View.GONE);
                    Toast.makeText(EditEntreprise.this, "vérifier votre email", Toast.LENGTH_SHORT).show();

                }else if(!pass.equals(cpass)){
                    PB.setVisibility(View.GONE);

                    Toast.makeText(EditEntreprise.this, "vérifier la confermation de mot de passs", Toast.LENGTH_SHORT).show();
                }else {

                    long time = new Date().getTime();
                    StorageReference filepath  = mStorage.getReference().child("EntrepriseLogo").child( time +"");;
                    filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t=task.getResult().toString();

                                    EntrepriseModal entrepriseModal=new EntrepriseModal(id,email,pass,nom,tel,adr,t);
                                    databaseReference.child(id).setValue(entrepriseModal);

                                    Toast.makeText(EditEntreprise.this, "Succès du Modifier du compte", Toast.LENGTH_LONG).show();

                                    finish();


                                }
                            });

                        }
                    });
                }
            }
        });
    }
}