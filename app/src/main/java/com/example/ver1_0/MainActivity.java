package com.example.ver1_0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

private EditText mtxtcontra;
private EditText mtxtcorreo;


    //variables
    private String correo = "";
    private String contra = "";

    //firebase auth
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button registrar = (Button) findViewById(R.id.btnregistrar);
        Button iniciar = (Button) findViewById(R.id.btniniciar);
        mtxtcontra = (EditText) findViewById(R.id.txtcontra);
        mtxtcorreo = (EditText) findViewById(R.id.txtcorreo);
        mauth = FirebaseAuth.getInstance();

        //funcion boton iniciar sesion
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = mtxtcorreo.getText().toString();
                contra = mtxtcontra.getText().toString();
                if (!correo.isEmpty() && !contra.isEmpty()){
                    iniciar_sesion();
                }
                else {
                    Toast.makeText(MainActivity.this, "Debe Introducir correo y contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this, registro.class));
                //finish();
            }
        });
    }

    private void iniciar_sesion(){
    mauth.signInWithEmailAndPassword(correo,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                startActivity(new Intent(MainActivity.this, perfil.class));
                finish();
            }
            else {
                Toast.makeText(MainActivity.this, "Fallo al iniciar sesión, compruebe los datos", Toast.LENGTH_SHORT).show();
            }

        }
    });

    }

}
