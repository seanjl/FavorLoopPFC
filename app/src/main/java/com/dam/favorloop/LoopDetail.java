package com.dam.favorloop;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
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
    Button btnAyudar;
    ConstraintLayout infoUsuario;

    String fotoPerfil = "";

    Usuario userBusqueda;

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
        btnAyudar = findViewById(R.id.btnAyudar);
        infoUsuario = findViewById(R.id.infoUsuario);

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
                            userBusqueda = data.getValue(Usuario.class);
                            tvNombreAy.setText(userBusqueda.getFullname());
                            tvOcupacionAy.setText(userBusqueda.getUsername());
                            fotoPerfil = userBusqueda.getFotoPerfilUrl();
                            Glide.with(LoopDetail.this)
                                    .load(fotoPerfil)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(ivUsuarioAy);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        btnAyudar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), CommentActivity.class);
                i.putExtra("POST_ID", loop.getId());
                i.putExtra("PUBLISHER_ID", loop.getPublisher());
                startActivity(i);
            }
        });

        infoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MiPerfilActivity.class);
                intent.putExtra("USUARIO", userBusqueda);
                startActivity(intent);
            }
        });
    }

}