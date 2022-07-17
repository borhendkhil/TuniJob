package com.example.tunijob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tunijob.adapter.model.EntrepriseModal;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class new_entreprise extends AppCompatActivity {
    String[] value ={"Ariana","Béja","Ben Arous","Bizerte","Gabès","Gafsa","Jendouba","Kairouan","Kébili","Kasserine","Le Kef","Mahdia","La Manouba","Médenine","Monastir","Nabeul","Sfax","Sidi Bouzid","Siliana","Sousse","Tataouine","Tozeur","Tunis","Zaghouan"};
    AutoCompleteTextView localisation;
    ArrayAdapter<String> adapterItems;
    private TextInputEditText idE,emailE, passE ,cpassE ,nomE,telE ;
    private Button btn ;
    private ProgressBar PB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entreprise);

        idE=findViewById(R.id.edEid);
        emailE=findViewById(R.id.edemailE);
        passE=findViewById(R.id.edpassE);
        cpassE=findViewById(R.id.edconfermpassE);
        nomE=findViewById(R.id.ednameE);
        telE=findViewById(R.id.edtelE);
        localisation=findViewById(R.id.edlocC);
        adapterItems = new ArrayAdapter<String>(this,R.layout.item_localisation,value);
        localisation.setAdapter(adapterItems);
        localisation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Entreprise");


        btn=findViewById(R.id.Enregistre);
        PB=findViewById(R.id.prgresscnx);






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
                String image="";
                EntrepriseModal entrepriseModal=new EntrepriseModal(id,email,pass,nom,tel,adr,image);




                if(TextUtils.isEmpty(id) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(nom) || TextUtils.isEmpty(tel) || TextUtils.isEmpty(adr)) {
                    PB.setVisibility(View.GONE);
                    Toast.makeText(new_entreprise.this, "Remplire tout les case s'il vous plaît", Toast.LENGTH_SHORT).show();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    PB.setVisibility(View.GONE);
                    Toast.makeText(new_entreprise.this, "vérifier votre email", Toast.LENGTH_SHORT).show();






                }else if(!pass.equals(cpass)){
                    PB.setVisibility(View.GONE);

                    Toast.makeText(new_entreprise.this, "vérifier la confermation de mot de passs", Toast.LENGTH_SHORT).show();
                }else {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(id)){
                                PB.setVisibility(View.GONE);
                                Toast.makeText(new_entreprise.this, "compte deja exister", Toast.LENGTH_SHORT).show();

                                
                            }else {
                                PB.setVisibility(View.GONE);
                                databaseReference.child(id).setValue(entrepriseModal);

                                Toast.makeText(new_entreprise.this, "succès du Enregistrer du compte", Toast.LENGTH_LONG).show();

                               finish();
                                
                            }
                            
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            PB.setVisibility(View.GONE);
                            Toast.makeText(new_entreprise.this, "error"+error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}