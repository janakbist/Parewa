package com.example.parewa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parewa.R;
import com.example.parewa.data.model.Follow;

import java.util.ArrayList;

public class FollowsAdapter extends RecyclerView.Adapter<FollowsAdapter.viewHolder>{
    ArrayList<Follow>list;
    Context context;

    public FollowsAdapter(ArrayList<Follow> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //use layout for followerRv Design
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_profile,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Follow follow = list.get(position);
        //get followers from firebase
//        FirebaseDatabase.getInstance().getReference()
//                .child("Users")
//                .child(follow.getFollowedBy()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//                Picasso.get()
//                        .load(user.getProfile())
//                        .placeholder(R.drawable.ic_add_photo)
//                        .into(holder.binding.profileimage);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        public viewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
