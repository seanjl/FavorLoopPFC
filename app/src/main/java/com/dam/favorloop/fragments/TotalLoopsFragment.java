package com.dam.favorloop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.favorloop.LoopDetail;
import com.dam.favorloop.R;
import com.dam.favorloop.adapters.LoopAdapter;
import com.dam.favorloop.model.Loop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TotalLoopsFragment extends Fragment {

    public static final String CLAVE_LOOP = "LOOP";

    RecyclerView rvLoop;
    LoopAdapter loopAdapter;
    List<Loop> loopList;
    private List<String> followingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_total_loops, container, false);

        rvLoop = view.findViewById(R.id.rvTotalLoops);
        rvLoop.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvLoop.setLayoutManager(linearLayoutManager);
        loopList = new ArrayList<>();
        loopAdapter = new LoopAdapter(getContext(), loopList);
        rvLoop.setAdapter(loopAdapter);

        readPosts();

        return view;
    }

    private void readPosts() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Loops");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loopList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Loop loop = snap.getValue(Loop.class);
                    loopList.add(loop);
                }

                loopAdapter.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = rvLoop.getChildAdapterPosition(v);
                        Loop loop = loopList.get(i);

                        Intent intentDatos = new Intent(getContext(), LoopDetail.class);
                        intentDatos.putExtra(CLAVE_LOOP, loop);
                        intentDatos.putExtra("USER", loop.getPublisher());
                        startActivity(intentDatos);

                    }
                });
                loopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}