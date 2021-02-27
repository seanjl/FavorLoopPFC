package com.dam.favorloop;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.dam.favorloop.fragments.HomeFragment;
import com.dam.favorloop.model.Loop;
import com.dam.favorloop.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoopDetail extends AppCompatActivity {

    ImageView ivLoopFoto;
    TextView tvTituloAyuda;
    TextView tvDescripcionAyuda;
    TextView tvNombreAy;
    TextView tvOcupacionAy;
    CircleImageView ivUsuarioAy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_detail);

        ivLoopFoto = findViewById(R.id.ivLoopFoto);
        tvTituloAyuda = findViewById(R.id.tvTituloAyuda);
        tvDescripcionAyuda = findViewById(R.id.tvDescripcionAyuda);

        ivUsuarioAy = findViewById(R.id.ivUsuarioAy);
        tvNombreAy = findViewById(R.id.tvNombreAy);
        tvOcupacionAy = findViewById(R.id.tvOcupacionAy);

        Loop loop = getIntent().getParcelableExtra(HomeFragment.CLAVE_LOOP);
        String id = getIntent().getStringExtra("USER");

        Glide.with(this)
                .load(loop.getImageUrl())
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .placeholder(new ColorDrawable(this.getResources().getColor(R.color.white)))
//                .placeholder(R.drawable.bg2)
                .into(ivLoopFoto);

        tvTituloAyuda.setText(loop.getTitulo());
        tvDescripcionAyuda.setText(loop.getDescripcion());

        FirebaseDatabase.getInstance().getReference("Usuarios")
                .orderByChild("id").equalTo(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Usuario user = data.getValue(Usuario.class);
                            tvNombreAy.setText(user.getFullname());
                            tvOcupacionAy.setText(user.getUsername());
                            Glide.with(getApplicationContext())
                                    .load(user.getFotoPerfilUrl())
                                    .transition(DrawableTransitionOptions.withCrossFade(500))
                                    .placeholder(new ColorDrawable(getApplication().getResources().getColor(R.color.background)))
                                    .into(ivUsuarioAy);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}