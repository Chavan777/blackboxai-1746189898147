package com.example.smartwastebinmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwastebinmanagementsystem.adapters.BinAdapter;
import com.example.smartwastebinmanagementsystem.models.Bin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * DashboardActivity shows the user's name and a list of nearby smart bins with their status.
 */
public class DashboardActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private RecyclerView binsRecyclerView;
    private BinAdapter binAdapter;
    private List<Bin> binList;

    private FirebaseAuth mAuth;
    private DatabaseReference binsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        binsRecyclerView = findViewById(R.id.binsRecyclerView);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            // User not logged in, redirect to LoginActivity
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
            return;
        }

        // Display user's email as name (can be replaced with actual user name if stored)
        welcomeTextView.setText("Welcome, " + currentUser.getEmail());

        binList = new ArrayList<>();
        binAdapter = new BinAdapter(this, binList);
        binsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binsRecyclerView.setAdapter(binAdapter);

        // Reference to bins in Firebase Realtime Database
        binsRef = FirebaseDatabase.getInstance().getReference("bins");

        // Listen for bin data changes
        binsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binList.clear();
                for (DataSnapshot binSnapshot : snapshot.getChildren()) {
                    Bin bin = binSnapshot.getValue(Bin.class);
                    if (bin != null) {
                        binList.add(bin);
                    }
                }
                binAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "Failed to load bins: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
