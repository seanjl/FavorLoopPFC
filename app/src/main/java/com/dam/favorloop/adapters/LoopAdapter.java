package com.dam.favorloop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dam.favorloop.R;
import com.dam.favorloop.model.Loop;
import com.dam.favorloop.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LoopAdapter extends RecyclerView.Adapter<LoopAdapter.LoopViewHolder> {

    Context mContext;
    List<Loop> mPosts;
    FirebaseUser firebaseUser;

    public LoopAdapter(Context mContext, List<Loop> mPosts) {
        this.mContext = mContext;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public LoopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loop_item, parent, false);
//        v.setOnClickListener(this);
        LoopAdapter.LoopViewHolder ivh = new LoopAdapter.LoopViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull LoopViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Loop loop = mPosts.get(position);

        Glide.with(mContext).load(loop.getImageUrl())
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(holder.loopImage);

        if (loop.getDescripcion().equals("")) {
            holder.tituloAyuda.setVisibility(View.GONE);
        } else {
            holder.tituloAyuda.setVisibility(View.VISIBLE);
            holder.tituloAyuda.setText(loop.getTitulo());
        }

        loopInfo(holder.imageProfile, holder.username, loop.getPublisher());
    }

    private void loopInfo(final ImageView imageProfile,
                          final TextView username,
                          final String userId) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                Glide.with(mContext).load(user.getFotoPerfilUrl()).into(imageProfile);
                username.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    public class LoopViewHolder extends RecyclerView.ViewHolder {

        ImageView imageProfile;
        TextView username;
        ImageView loopImage;
        TextView tituloAyuda;

        public LoopViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProfile = itemView.findViewById(R.id.imageProfile);
            username = itemView.findViewById(R.id.username);
            loopImage = itemView.findViewById(R.id.loopImage);
            tituloAyuda = itemView.findViewById(R.id.tituloAyuda);
        }
    }


}
