package com.example.parewa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parewa.CommentActivity;
import com.example.parewa.R;
import com.example.parewa.data.model.PostModel;
import com.example.parewa.data.model.User;
import com.example.parewa.databinding.DashboardRvSampleBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
//6:23:25
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.viewHolder>{
    ArrayList<PostModel>list;
    Context context;

    public PostAdapter(ArrayList<PostModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_rv_sample,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PostModel model =list.get(position);
        Picasso.get().load(model.getPostImage())
                .placeholder(R.drawable.ic_add_photo)
                .into(holder.binding.postImg);
        holder.binding.likes.setText(model.getPostLike()+"");
        String description = model.getPostDescription();
        if (description.equals("")) {
            holder.binding.postDescription.setVisibility(View.GONE);
        }else {
            holder.binding.postDescription.setText(model.getPostDescription());
            holder.binding.postDescription.setVisibility(View.VISIBLE);
        }

        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(model.getPostedBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                //remove All three if condition
                if (user != null) {
                    Picasso.get().load(user.getProfile())
                            .placeholder(R.drawable.ic_person)
                            .into(holder.binding.profileImage);
                }
                if(user!=null) {
                    holder.binding.firstname.setText(user.getFirstname());
                    holder.binding.lastname.setText(user.getLastname());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference()
                .child("posts")
                .child(model.getPostId())
                .child("likes")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    holder.binding.likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_liked,0,0,0);
                }
                else {
                    holder.binding.likes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("posts")
                                    .child(model.getPostId())
                                    .child("likes")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("posts")
                                            .child(model.getPostId())
                                            .child("postLikes")
                                            .setValue(model.getPostLike() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.binding.likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_liked,0,0,0);
                                            Toast.makeText(view.getContext(), "You Liked ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.binding.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentActivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        DashboardRvSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DashboardRvSampleBinding.bind(itemView);

        }
    }
}
