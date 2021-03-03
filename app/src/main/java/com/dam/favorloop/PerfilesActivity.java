package com.dam.favorloop;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.favorloop.adapters.AmigosAdapter;
import com.dam.favorloop.adapters.LoopAdapter;
import com.dam.favorloop.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PerfilesActivity extends AppCompatActivity {

    RecyclerView rvPerfiles;
    AmigosAdapter adapter;
    DatabaseReference reference;
    ArrayList<String> idList;
    ArrayList<Usuario> listaUsuarios;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfiles);

        id = getIntent().getStringExtra(LoopAdapter.CLAVE_POST);

        listaUsuarios = new ArrayList<>();

        rvPerfiles = findViewById(R.id.rvPerfiles);
        rvPerfiles.setHasFixedSize(true);
        rvPerfiles.setLayoutManager(new LinearLayoutManager(this));
        idList = new ArrayList<>();

        readUsuarios();

    }

    private void readUsuarios() {
        reference = FirebaseDatabase.getInstance().getReference("Likes").child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    idList.add(snap.getKey());
                }
                mostrarUsuarios();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void mostrarUsuarios() {
        reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Usuario user = snap.getValue(Usuario.class);
                    for (String idUser : idList) {
                        if (user.getId().equals(idUser)) {
                            listaUsuarios.add(user);
                        }
                    }
                }
                adapter = new AmigosAdapter(listaUsuarios);
                adapter.notifyDataSetChanged();
                rvPerfiles.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}