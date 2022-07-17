package com.example.tunijob;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn;
    ImageView logo;

    TextView textView,textView2;
    Animation topAnim ,botAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Animation
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo=findViewById(R.id.imageView);
        logo.setAnimation(topAnim);
        textView2=findViewById(R.id.textView2);
        textView2.setAnimation(botAnim);






        textView=findViewById(R.id.newaccount);
        textView.setAnimation(botAnim);



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });
        btn=findViewById(R.id.cnx);
        btn.setAnimation(botAnim);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,login.class);
                startActivity(intent);


            }
        });



    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.pop_up_new_account);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Button btn1;
        Button btn2;
        btn1 = dialog.findViewById(R.id.entreprise);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, new_entreprise.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        btn2 = dialog.findViewById(R.id.condidat);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, new_codidat.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }
}