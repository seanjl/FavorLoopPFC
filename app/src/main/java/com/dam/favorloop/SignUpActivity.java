package com.dam.favorloop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText fullname;
    EditText username;
    EditText email;
    EditText password;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        auth = FirebaseAuth.getInstance();

    }

    public void signUpUser(View view) {
        pd = new ProgressDialog(SignUpActivity.this);
        pd.setMessage("Please wait...");
        pd.show();

        String sFullname = fullname.getText().toString().trim();
        String sUsername = username.getText().toString().trim();
        String sEmail = email.getText().toString().trim();
        String sPassword = password.getText().toString().trim();

        if (sFullname.isEmpty() || sUsername.isEmpty() || sEmail.isEmpty() || sPassword.isEmpty()) {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            pd.dismiss();

        } else if (sPassword.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();

        } else {
            registrarUsuario(sFullname, sUsername, sEmail, sPassword);
        }
    }

    private void registrarUsuario(String sFullname, String sUsername, String sEmail, String sPassword) {
        auth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(userId);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("fullname", sFullname);
                            hashMap.put("username", sUsername);
                            hashMap.put("fotoPerfilUrl", "https://firebasestorage.googleapis.com/v0/b/fir-1-4cac7.appspot.com/o/user.png?alt=media&token=256e0a83-bc34-436e-b78f-d012aa5ad14f");
                            hashMap.put("bio", "");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        pd.dismiss();
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });

                        } else {
                            pd.dismiss();
                            Toast.makeText(SignUpActivity.this, "Ya hay un usuario registrado con este mail",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void irLogin(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void irMain(View view) {
        Intent i = new Intent(this, IntroActivity.class);
        startActivity(i);
    }

}