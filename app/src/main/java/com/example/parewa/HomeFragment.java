package com.example.parewa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parewa.Adapter.PostAdapter;
import com.example.parewa.Adapter.StoryAdapter;
import com.example.parewa.data.model.PostModel;
import com.example.parewa.data.model.StoryModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView storyRv,dashboardRV;
    ArrayList<StoryModel>list;
    ArrayList<PostModel>postList;
    FirebaseDatabase database;
    FirebaseAuth auth;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        storyRv = view.findViewById(R.id.storyRV);
        list = new ArrayList<>();
        list.add(new StoryModel(R.drawable.ranbir,R.drawable.ranbir,"Ranbir"));
        list.add(new StoryModel(R.drawable.prabhas,R.drawable.prabhas,"Prabhas"));
        list.add(new StoryModel(R.drawable.parewa,R.drawable.parewa,"Parewa"));
        list.add(new StoryModel(R.drawable.prabhas,R.drawable.prabhas,"Prabhas"));
        list.add(new StoryModel(R.drawable.prabhas,R.drawable.prabhas,"Prabhas"));
        StoryAdapter adapter = new StoryAdapter(list,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        storyRv.setLayoutManager(linearLayoutManager);
        storyRv.setNestedScrollingEnabled(false);
        storyRv.setAdapter(adapter);

        dashboardRV = view.findViewById(R.id.dashboardRV);
        postList = new ArrayList<>();

        PostAdapter postAdapter = new PostAdapter(postList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dashboardRV.setLayoutManager(layoutManager);
        dashboardRV.setNestedScrollingEnabled(false);
        dashboardRV.setAdapter(postAdapter);

        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PostModel post = dataSnapshot.getValue(PostModel.class);
                    post.setPostId(dataSnapshot.getKey());
                    postList.add(post);
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}