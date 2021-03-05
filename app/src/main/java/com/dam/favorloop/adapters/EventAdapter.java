package com.dam.favorloop.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dam.favorloop.CommentActivity;
import com.dam.favorloop.R;
import com.dam.favorloop.model.Evento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class EventAdapter
        extends RecyclerView.Adapter<EventAdapter.EventViewHolder>
        implements View.OnClickListener {

    Context mContext;
    List<Evento> mPosts;
    FirebaseUser firebaseUser;

    private View.OnClickListener listener;

    public EventAdapter(Context mContext, List<Evento> mPosts) {
        this.mContext = mContext;
        this.mPosts = mPosts;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        v.setOnClickListener(this);
        EventAdapter.EventViewHolder evh = new EventAdapter.EventViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Evento event = mPosts.get(position);

        Glide.with(mContext).load(event.getImageUrl())
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(holder.ivImageEvento);

        if (event.getDescripcion().equals("")) {
            holder.tvTituloEvento.setVisibility(View.GONE);
        } else {
            holder.tvTituloEvento.setVisibility(View.VISIBLE);
            holder.tvTituloEvento.setText(event.getTitulo());
            holder.tvFecha.setText(event.getFecha());
        }

//        loopInfo(holder.imageProfile, holder.username, loop.getPublisher());

        getComments(event.getId(), holder.commentsCounter);
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), CommentActivity.class);
                i.putExtra("POST_ID", event.getId());
                i.putExtra("PUBLISHER_ID", event.getPublisher());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImageEvento;
        TextView tvTituloEvento;
        TextView tvLugar;
        TextView tvFecha;
        ImageView comment;
        TextView commentsCounter;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImageEvento = itemView.findViewById(R.id.ivImageEvento);
            tvTituloEvento = itemView.findViewById(R.id.tvTituloEvento);
            tvLugar = itemView.findViewById(R.id.tvLugar);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            comment = itemView.findViewById(R.id.comment);
            commentsCounter = itemView.findViewById(R.id.commentsCounter);
        }
    }

    private void getComments(String postid, TextView comments) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Comentarios").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments.setText(snapshot.getChildrenCount() + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
