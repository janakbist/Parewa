package com.example.parewa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parewa.Adapter.DashboardAdapter;
import com.example.parewa.Adapter.StoryAdapter;
import com.example.parewa.data.model.DashboardModel;
import com.example.parewa.data.model.StoryModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView storyRv,dashboardRV;
    ArrayList<StoryModel>list;
    ArrayList<DashboardModel>dashboardList;

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
        dashboardList = new ArrayList<>();
        dashboardList.add(new DashboardModel(R.drawable.ranbir,R.drawable.ranbir,"Ranbir","300","20"));
        dashboardList.add(new DashboardModel(R.drawable.prabhas,R.drawable.prabhas,"Prabhas","300","20"));
        dashboardList.add(new DashboardModel(R.drawable.parewa,R.drawable.parewa,"Parewa","300","20"));
        dashboardList.add(new DashboardModel(R.drawable.ranbir,R.drawable.ranbir,"Ranbir","300","20"));
        dashboardList.add(new DashboardModel(R.drawable.prabhas,R.drawable.prabhas,"Prabhas","300","20"));
        dashboardList.add(new DashboardModel(R.drawable.parewa,R.drawable.parewa,"Parewa","300","20"));
        dashboardList.add(new DashboardModel(R.drawable.parewalogo,R.drawable.parewalogo,"Company","300","20"));
        DashboardAdapter dashboardAdapter = new DashboardAdapter(dashboardList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dashboardRV.setLayoutManager(layoutManager);
        dashboardRV.setNestedScrollingEnabled(false);
        dashboardRV.setAdapter(dashboardAdapter);


        return view;
    }
}