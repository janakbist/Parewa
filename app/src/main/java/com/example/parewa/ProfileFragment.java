package com.example.parewa;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parewa.Adapter.FollowsAdapter;
import com.example.parewa.data.model.Follow;
import com.example.parewa.data.model.User;
import com.example.parewa.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    public ImageView bgCover;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    FragmentProfileBinding binding;

    RecyclerView recyclerView;
    ArrayList<Follow> list;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        binding = FragmentProfileBinding.inflate(getLayoutInflater());



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false);
//        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//        recyclerView = view.findViewById(R.id.friendRV);
        list = new ArrayList<>();


        FollowsAdapter adapter = new FollowsAdapter(list,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.friendRV.setLayoutManager(linearLayoutManager);
        binding.friendRV.setNestedScrollingEnabled(false);
        binding.friendRV.setAdapter(adapter);

        database.getReference().child("Users").child(auth.getUid())
                .child("followers")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            Follow follow = dataSnapshot.getValue(Follow.class);
                            list.add(follow);

                        }
                        adapter.notifyDataSetChanged();

                        if(snapshot.exists()) {
                            User user  = snapshot.getValue(User.class);
                            //remove if condition...
                            if(user!=null) {
                                Picasso.get()
                                        .load(user.getCoverPhoto())
                                        .placeholder(R.drawable.ic_add_photo)
                                        .into(binding.profilebgimg);
                            }
                            if(user!=null) {
                                Picasso.get()
                                        .load(user.getProfile())
                                        .placeholder(R.drawable.ic_add_photo)
                                        .into(binding.profileImage);
                                }
                                binding.profileName.setText(user.getFirstname());
                                binding.lastName.setText(user.getLastname());
                                binding.followerCount.setText(user.getFollowerCount() + " ");
                            }

                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        //intent for back from profile to dashboard
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),DashboardActivity.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });
        //intent for logout
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });
        //4:31
        
        binding.addbgphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
               startActivityForResult(intent,11);
            }
        });
        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,22);
            }
        });

       return binding.getRoot();
//return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (data.getData()!=null) {
                Uri uri = data.getData();
                binding.profilebgimg.setImageURI(uri);
                final StorageReference reference = storage.getReference()
                        .child("cover_photo").child(FirebaseAuth.getInstance().getUid());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Cover photo Uploaded", Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //save  photo to database
                                database.getReference().child("Users").child(auth.getUid())
                                        .child("coverPhoto").setValue(uri.toString());
                            }
                        });
                    }
                });

            }

        }else {
            if (data.getData()!=null) {
                Uri uri = data.getData();
                binding.profileImage.setImageURI(uri);
                final StorageReference reference = storage.getReference()
                        .child("profileImage").child(FirebaseAuth.getInstance().getUid());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Profile Image Uploaded", Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //save  photo to database
                                database.getReference().child("Users").child(auth.getUid())
                                        .child("profile").setValue(uri.toString());
                            }
                        });
                    }
                });
            }

        }

    }
}