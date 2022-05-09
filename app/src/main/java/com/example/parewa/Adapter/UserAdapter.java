package com.example.parewa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parewa.R;
import com.example.parewa.data.model.Follow;
import com.example.parewa.data.model.User;
import com.example.parewa.databinding.UserSampleBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder>{
    Context context;
    ArrayList<User>list;

    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_sample,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        User user = list.get(position);
        Picasso.get().load(user.getProfile()).
                placeholder(R.drawable.ic_add_photo)
                .into(holder.binding.profileImage);
        holder.binding.profileName.setText(user.getFirstname());
        holder.binding.lastName.setText(user.getLastname());
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(user.getUserId())
                .child("followers")
                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    holder.binding.followbtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.follow_active_btn));
                    holder.binding.followbtn.setText("Following");
                    holder.binding.followbtn.setTextColor(context.getResources().getColor(R.color.grey));
                    holder.binding.followbtn.setEnabled(false);
                }else  {
                    holder.binding.followbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Follow follow = new Follow();
                            follow.setFollowedBy(FirebaseAuth.getInstance().getUid());
                            follow.setFollowedAt(new Date().getTime());
                            FirebaseDatabase.getInstance().getReference()
                                    .child("Users")
                                    .child(user.getUserId())
                                    .child("followers")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(follow).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    FirebaseDatabase.getInstance().getReference().child("Users")
                                            .child(user.getUserId())
                                            .child("followerCount")
                                            .setValue(user.getFollowerCount()+1).addOnSuccessListener(
                                            new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    holder.binding.followbtn.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.follow_active_btn));
                                                    holder.binding.followbtn.setText("Following");
                                                    holder.binding.followbtn.setTextColor(context.getResources().getColor(R.color.grey));
                                                    holder.binding.followbtn.setEnabled(false);
                                                    Toast.makeText(context, "You Followed " + user.getFirstname()+" "+user.getLastname(), Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder{
        UserSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = UserSampleBinding.bind(itemView);
        }
    }
}
