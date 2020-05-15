package com.example.ver1_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class perfil extends AppCompatActivity {

    private Button mbtnsginout;
    private FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        mbtnsginout = (Button) findViewById(R.id.btnsignout);
        mauth = FirebaseAuth.getInstance();

        mbtnsginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mauth.signOut();
            startActivity(new Intent(perfil.this, MainActivity.class));
            //finish();
            }
        });
    }
}
