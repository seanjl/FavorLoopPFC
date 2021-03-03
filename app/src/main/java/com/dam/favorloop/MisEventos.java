package com.dam.favorloop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.favorloop.adapters.EventAdapter;
import com.dam.favorloop.model.Evento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MisEventos extends AppCompatActivity {

    RecyclerView rvMisEventos;
    LinearLayout alertaMisEventos;
    private FirebaseUser firebaseUser;
    EventAdapter eventAdapter;
    List<Evento> eventosList;
    private List<String> myEvents;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_eventos);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        id = firebaseUser.getUid();

        alertaMisEventos = findViewById(R.id.alertaMisEventos);
        rvMisEventos = findViewById(R.id.rvMisEventos);
        rvMisEventos.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvMisEventos.setLayoutManager(linearLayoutManager);
        eventosList = new ArrayList<>();
        eventAdapter = new EventAdapter(this, eventosList);
        rvMisEventos.setAdapter(eventAdapter);

        checkFollowing();

//        if (eventosList.isEmpty()) {
//            alertaMisEventos.setVisibility(View.VISIBLE);
//        }
    }

    private void checkFollowing() {
        myEvents = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Eventos");
//        reference.addListenerForSingleValueEvent(valueEventListener);
        Query query = FirebaseDatabase.getInstance().getReference("Eventos")
                .orderByChild("publisher")
                .equalTo(id);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            myEvents.clear();
            if (snapshot.exists()) {
                for (DataSnapshot sna : snapshot.getChildren()) {
                    Evento event = sna.getValue(Evento.class);
                    eventosList.add(event);
                }

                eventAdapter.notifyDataSetChanged();

                if (!eventosList.isEmpty()) {
                    alertaMisEventos.setVisibility(View.INVISIBLE);
                }

                eventAdapter.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = rvMisEventos.getChildAdapterPosition(v);
                        Evento evento = eventosList.get(i);

                        Intent intentDatos = new Intent(MisEventos.this, EventoDetail.class);
                        intentDatos.putExtra("EVENTO", evento);
                        intentDatos.putExtra("PUBLISHER_ID", evento.getPublisher());
                        startActivity(intentDatos);

                    }
                });

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}