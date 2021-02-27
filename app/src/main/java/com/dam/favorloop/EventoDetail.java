package com.dam.favorloop;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detail);

        ivEventoFoto = findViewById(R.id.ivEventoFoto);
        tvEventoTitulo = findViewById(R.id.tvEventoTitulo);
        tvDescripcionEvento = findViewById(R.id.tvDescripcionEvento);

        Evento e = getIntent().getParcelableExtra(EventFragment.CLAVE_EVENTO);

        Glide.with(this)
                .load(e.getImageUrl())
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .placeholder(new ColorDrawable(this.getResources().getColor(R.color.white)))
//                .placeholder(R.drawable.bg2)
                .into(ivEventoFoto);
        tvEventoTitulo.setText(e.getTitulo());
        tvDescripcionEvento.setText(e.getDescripcion());
    }
}