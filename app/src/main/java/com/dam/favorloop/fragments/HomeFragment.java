package com.dam.favorloop.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.favorloop.R;
import com.dam.favorloop.adapters.HomeAdapter;
import com.dam.favorloop.model.Home;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView rvHome;
    HomeAdapter hAdapter;
    ArrayList<Home> listaHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvHome = view.findViewById(R.id.rvHome);
        rvHome.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHome.setHasFixedSize(true);

        listaHome = new ArrayList<>();

        cargarRecycler();

        return view;
    }

    private void cargarRecycler() {
//        Home evento1 = new Home(1, "Pachanga", "Deporte", "Una partidito por la tarde niño", "20200222", "SeanLeitch", "src");
//        Home evento2 = new Home(1, "Limpiar Parque", "Limpieza", "Limpiar el parque por la mañana", "20200222", "SeanLeitch", "src");
//        listaEvents.add(evento1);
//        listaEvents.add(evento2);

        hAdapter = new HomeAdapter(listaHome);
        rvHome.setAdapter(hAdapter);

        hAdapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}