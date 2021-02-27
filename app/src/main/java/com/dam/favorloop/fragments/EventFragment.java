package com.dam.favorloop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.favorloop.EventoDetail;
import com.dam.favorloop.LoopDetail;
import com.dam.favorloop.R;
import com.dam.favorloop.adapters.EventAdapter;
import com.dam.favorloop.model.Evento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment {

    public static final String CLAVE_EVENTO = "EVENTO";

    RecyclerView rvEventos;
    EventAdapter adapter;
    List<Evento> listaEventos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        rvEventos = view.findViewById(R.id.rvEventos);
        rvEventos.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvEventos.setLayoutManager(linearLayoutManager);
        listaEventos = new ArrayList<>();
        adapter = new EventAdapter(getContext(), listaEventos);
        rvEventos.setAdapter(adapter);

        readEvents();
        return view;
    }

    private void readEvents() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Eventos");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaEventos.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Evento event = snap.getValue(Evento.class);
                    listaEventos.add(event);
                }

                adapter.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = rvEventos.getChildAdapterPosition(v);
                        Evento evento = listaEventos.get(i);

                        Intent intentDatos = new Intent(getContext(), EventoDetail.class);
                        intentDatos.putExtra(CLAVE_EVENTO, evento);
                        startActivity(intentDatos);
                    }
                });
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}