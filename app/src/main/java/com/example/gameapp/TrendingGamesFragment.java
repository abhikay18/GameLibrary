package com.example.gameapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class TrendingGamesFragment extends Fragment {

    RecyclerView recyclerView;
    MainAdapter mainAdapter;

    FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending_games, container, false);

        // Set fitsSystemWindows to false to enable edge-to-edge display
        view.setFitsSystemWindows(false);

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.rev);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Check if Firebase is initialized
        if(FirebaseDatabase.getInstance() == null) {
            // Firebase is not initialized
            // Log an error or handle the situation accordingly
            return view;
        }

        // Construct FirebaseRecyclerOptions
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance("https://gameapp-a91c2-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Games"), MainModel.class)
                        .build();

        // Create and set up the adapter
        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Start listening for data
        if (mainAdapter != null) {
            mainAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Stop listening for data
        if (mainAdapter != null) {
            mainAdapter.stopListening();
        }
    }
}
