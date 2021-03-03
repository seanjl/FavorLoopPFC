package com.dam.favorloop;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.dam.favorloop.fragments.EventFragment;
import com.dam.favorloop.model.Evento;

public class EventoDetail extends AppCompatActivity {

    TextView tvEventoTitulo;
    TextView tvDescripcionEvento;
    ImageView ivEventoFoto;
    Button btnParticipar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detail);

        ivEventoFoto = findViewById(R.id.ivEventoFoto);
        tvEventoTitulo = findViewById(R.id.tvEventoTitulo);
        tvDescripcionEvento = findViewById(R.id.tvDescripcionEvento);
        btnParticipar = findViewById(R.id.btnParticipar);

        Evento e = getIntent().getParcelableExtra("EVENTO");

        Glide.with(this)
                .load(e.getImageUrl())
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .placeholder(new ColorDrawable(this.getResources().getColor(R.color.white)))
//                .placeholder(R.drawable.bg2)
                .into(ivEventoFoto);
        tvEventoTitulo.setText(e.getTitulo());
        tvDescripcionEvento.setText(e.getDescripcion());

        btnParticipar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), CommentActivity.class);
                i.putExtra("POST_ID", e.getId());
                i.putExtra("PUBLISHER_ID", e.getPublisher());
                startActivity(i);
            }
        });

    }
}