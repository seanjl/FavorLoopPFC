package com.dam.favorloop.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.dam.favorloop.MainActivity;
import com.dam.favorloop.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;

public class PublicarEventoFragment extends Fragment {

    Uri imageUri;
    String myUrl = "";
    StorageTask uploadTask;
    StorageReference storageReference;

    EditText etTitulo;
    EditText etDescripcion;
    ImageView ivFotoEvento;
    ImageView ibAdjuntarFoto;
    Button btnPostEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicar_event, container, false);

        etTitulo = view.findViewById(R.id.etTituloEvento);
        etDescripcion = view.findViewById(R.id.etDescripcionEvento);
        ivFotoEvento = view.findViewById(R.id.ivFotoEvento);
        ibAdjuntarFoto = view.findViewById(R.id.ibAdjuntarFoto);
        btnPostEvent = view.findViewById(R.id.btnPostEvent);

        storageReference = FirebaseStorage.getInstance().getReference("Eventos");

        btnPostEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        ibAdjuntarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete la acción usando"), 1);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Cargamos la imagen seleccionada en el ImageView
            imageUri = data.getData();
            Glide.with(ivFotoEvento.getContext()).load(imageUri)
                    .into(ivFotoEvento);
        }

    }

    private void uploadImage() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Posting");
        progressDialog.show();

        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "");
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Eventos");

                        String postId = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("id", postId);
                        hashMap.put("titulo", etTitulo.getText().toString());
                        hashMap.put("descripcion", etDescripcion.getText().toString());
                        hashMap.put("imageUrl", myUrl);
                        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());

                        reference.child(postId).setValue(hashMap);
                        progressDialog.dismiss();
                        startActivity(new Intent(getActivity(), MainActivity.class));

                    } else {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Image Selected!", Toast.LENGTH_SHORT).show();
        }
    }
}