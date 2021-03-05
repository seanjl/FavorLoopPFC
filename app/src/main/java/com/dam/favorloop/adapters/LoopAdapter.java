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
import com.dam.favorloop.PerfilesActivity;
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

public class LoopAdapter
        extends RecyclerView.Adapter<LoopAdapter.LoopViewHolder>
        implements View.OnClickListener {

    public static final String CLAVE_POST = "POST";


    Context mContext;
    List<Loop> mPosts;
    FirebaseUser firebaseUser;

    private View.OnClickListener listener;

    public LoopAdapter(Context mContext, List<Loop> mPosts) {
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
    public LoopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loop_item, parent, false);
        v.setOnClickListener(this);
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
            holder.tvFecha.setText(loop.getFecha());
        }

        loopInfo(holder.imageProfile, holder.username, loop.getPublisher());

        isLikes(loop.getId(), holder.likeLoop);
        nrLikes(holder.contadorLikes, loop.getId());
        getComments(loop.getId(), holder.commentsCounter);

        holder.likeLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.likeLoop.getTag().equals("like")) {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(loop.getId())
                            .child(firebaseUser.getUid()).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(loop.getId())
                            .child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        holder.contadorLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), PerfilesActivity.class);
                i.putExtra(CLAVE_POST, loop.getId());
                mContext.startActivity(i);
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), CommentActivity.class);
                i.putExtra("POST_ID", loop.getId());
                i.putExtra("PUBLISHER_ID", loop.getPublisher());
                mContext.startActivity(i);
            }
        });


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

        ImageView likeLoop;
        TextView contadorLikes;

        ImageView comment;
        TextView commentsCounter;

        TextView tvFecha;

        public LoopViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProfile = itemView.findViewById(R.id.imageProfile);
            username = itemView.findViewById(R.id.username);
            loopImage = itemView.findViewById(R.id.loopImage);
            tituloAyuda = itemView.findViewById(R.id.tituloAyuda);

            likeLoop = itemView.findViewById(R.id.likeLoop);
            contadorLikes = itemView.findViewById(R.id.contadorLikes);

            comment = itemView.findViewById(R.id.comment);
            commentsCounter = itemView.findViewById(R.id.commentsCounter);

            tvFecha = itemView.findViewById(R.id.tvFecha);
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

    private void isLikes(String postid, ImageView imageView) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(firebaseUser.getUid()).exists()) {
                    imageView.setImageResource(R.drawable.ic_hand_green);
                    imageView.setTag("liked");
                } else {
                    imageView.setImageResource(R.drawable.ic_help_icon);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Contar número de likes
    private void nrLikes(final TextView likes, String postid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likes.setText(snapshot.getChildrenCount() + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
