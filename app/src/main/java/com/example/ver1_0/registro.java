package com.example.ver1_0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {
    private EditText ednombre;
    private EditText edap1;
    private EditText edap2;
    private EditText edcorreo;
    private EditText edfecha;
    private EditText edescuela;
    private EditText edcontra;

    //Variables
    private String nombre = "";
    private String ap1 = "";
    private String ap2 = "";
    private String correo = "";
    private String fecha = "";
    private String escuela = "";
    private String contra = "";

    //autenticacion
    FirebaseAuth aut;
    DatabaseReference BD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //instanciamos
        aut = FirebaseAuth.getInstance();
        BD = FirebaseDatabase.getInstance().getReference();

        ednombre = (EditText) findViewById(R.id.etnombre);
        edap1 = (EditText) findViewById(R.id.etap1);
        edap2 = (EditText) findViewById(R.id.etap2);
        edcorreo = (EditText) findViewById(R.id.etcorreo);
        edfecha = (EditText) findViewById(R.id.etfecha);
        edescuela = (EditText) findViewById(R.id.etescuela);
        edcontra = (EditText) findViewById(R.id.etcontra);
        Button registrar = (Button) findViewById(R.id.btnregistrar);


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = ednombre.getText().toString();
                ap1 = edap1.getText().toString();
                ap2 = edap2.getText().toString();
                correo = edcorreo.getText().toString();
                fecha = edfecha.getText().toString();
                escuela = edescuela.getText().toString();
                contra = edcontra.getText().toString();

                if (!nombre.isEmpty() && !correo.isEmpty() && !contra.isEmpty()) {
                    if (contra.length() >= 6) {
                        registrousuario();
                    } else {
                        Toast.makeText(registro.this, "La contraseña debe ser mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(registro.this, "Complete los campos pls", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void    registrousuario(){
        aut.createUserWithEmailAndPassword(correo,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   Map<String, Object> map = new HashMap<>();
                   map.put("Nombre",nombre);
                   map.put("Apellido1",ap1);
                   map.put("Apellido2",ap2);
                   map.put("Correo",correo);
                   map.put("Fecha",fecha);
                   map.put("Escuela",escuela);
                   map.put("Contraseña",contra);
                   String id = aut.getCurrentUser().getUid();
                BD.child("usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if (task2.isSuccessful()){
                            startActivity(new Intent(registro.this, perfil.class));
                            finish();
                        }
                        else {
                            Toast.makeText(registro.this, "Registro no exitoso", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
               }
               else{
                   Toast.makeText(registro.this, "Registro no Exitoso", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}



