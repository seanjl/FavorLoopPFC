package com.dam.favorloop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.dam.favorloop.R;

public class IntroAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public IntroAdapter(Context context) {
        this.context = context;
    }

    // Arrays
    public int[] slideImages = {
            R.drawable.intro_1,
            R.drawable.intro_2,
            R.drawable.intro_3
    };

    public String[] slideTitles = {
            "¿Qué es Favorloop?",
            "¡Solicita ayuda creando un Loop!",
            "Organiza Eventos"
    };

    public String[] slideText = {
            "FavorLoop es una plataforma que te permite ayudar y recibir ayuda",
            "Un 'Loop' es una publicación para pedir ayuda. La gente verá tus Loops y podrá contactar contigo",
            "Publica un 'Event' para reunir a un grupo de personas y realizar una actividad comunitaria"
    };

    @Override
    public int getCount() {
        return slideTitles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.intro_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.ivImg);
        TextView titulo = (TextView) view.findViewById(R.id.tvTitulo);
        TextView descripcion = (TextView) view.findViewById(R.id.tvDescripcion);

        slideImageView.setImageResource(slideImages[position]);
        titulo.setText(slideTitles[position]);
        descripcion.setText(slideText[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
