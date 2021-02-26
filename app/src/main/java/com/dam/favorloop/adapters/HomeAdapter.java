package com.dam.favorloop.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.favorloop.R;
import com.dam.favorloop.model.Home;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeVH> implements View.OnClickListener {
    private ArrayList<Home> listaHome;
    private View.OnClickListener listener;

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public HomeAdapter(ArrayList<Home> listaEvents) {
        this.listaHome = listaEvents;
    }

    @NonNull
    @Override
    public HomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item, parent, false);
        HomeVH hvh = new HomeVH(v);
        return hvh;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVH holder, int position) {
        holder.bindEvent(listaHome.get(position));

    }

    @Override
    public int getItemCount() {
        return listaHome.size();
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class HomeVH extends RecyclerView.ViewHolder {
        TextView homeName;
        TextView homeLugar;


        public HomeVH(@NonNull View itemView) {
            super(itemView);

            //TextView homeName = itemView.findViewById(R.id.tvHomeName);
            //TextView homeLugar = itemView.findViewById(R.id.tvHomeLugar);
            //ImageView homeImg = itemView.findViewById(R.id.ivHomePost);

        }

        public void bindEvent(Home home){
            homeName.setText(home.getNombre());
            homeLugar.setText(home.getLugar());

        }
    }
}
