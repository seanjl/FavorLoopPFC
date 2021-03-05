package com.dam.favorloop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.favorloop.adapters.LoopAdapter;
import com.dam.favorloop.model.Loop;
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

public class MisLoops extends AppCompatActivity {

    public static final String CLAVE_LOOP = "LOOP";

    LinearLayout alertaMisLoops;
    RecyclerView rvMisLoops;
    private FirebaseUser firebaseUser;
    LoopAdapter loopAdapter;
    List<Loop> loopList;
    private List<String> myLoops;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_loops);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        id = firebaseUser.getUid();

        alertaMisLoops = findViewById(R.id.alertaMisLoops);
        rvMisLoops = findViewById(R.id.rvMisLoops);
        rvMisLoops.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rvMisLoops.setLayoutManager(linearLayoutManager);
        loopList = new ArrayList<>();
        loopAdapter = new LoopAdapter(this, loopList);
        rvMisLoops.setAdapter(loopAdapter);

        checkFollowing();

//        if (loopList.isEmpty()) {
//            alertaMisLoops.setVisibility(View.VISIBLE);
//        }
    }

    private void checkFollowing() {
        myLoops = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Loops");
//        reference.addListenerForSingleValueEvent(valueEventListener);
        Query query = FirebaseDatabase.getInstance().getReference("Loops")
                .orderByChild("publisher")
                .equalTo(id);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            myLoops.clear();
            if (snapshot.exists()) {
                for (DataSnapshot sna : snapshot.getChildren()) {
                    Loop loop = sna.getValue(Loop.class);
                    loopList.add(loop);
                }

                loopAdapter.notifyDataSetChanged();

                if (!loopList.isEmpty()) {
                    alertaMisLoops.setVisibility(View.INVISIBLE);
                }

                loopAdapter.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = rvMisLoops.getChildAdapterPosition(v);
                        Loop loop = loopList.get(i);

                        Intent intentDatos = new Intent(MisLoops.this, LoopDetail.class);
                        intentDatos.putExtra(CLAVE_LOOP, loop);
                        intentDatos.putExtra("USER", loop.getPublisher());
                        startActivity(intentDatos);

                    }
                });
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private void imprimi() {
        for (Loop l : loopList) {
            Log.i("Loops", l.getId());
        }
    }


}