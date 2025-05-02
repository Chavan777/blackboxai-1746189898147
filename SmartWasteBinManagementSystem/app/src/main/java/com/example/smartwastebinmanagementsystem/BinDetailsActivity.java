package com.example.smartwastebinmanagementsystem;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwastebinmanagementsystem.models.Bin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * BinDetailsActivity shows detailed information about a selected bin.
 */
public class BinDetailsActivity extends AppCompatActivity {

    private TextView binNameTextView, binStatusTextView, binLocationTextView, binLastUpdatedTextView;

    private DatabaseReference binsRef;
    private String binId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_details);

        binNameTextView = findViewById(R.id.binNameTextView);
        binStatusTextView = findViewById(R.id.binStatusTextView);
        binLocationTextView = findViewById(R.id.binLocationTextView);
        binLastUpdatedTextView = findViewById(R.id.binLastUpdatedTextView);

        binId = getIntent().getStringExtra("binId");
        if (binId == null) {
            Toast.makeText(this, "Bin ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        binsRef = FirebaseDatabase.getInstance().getReference("bins").child(binId);

        binsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Bin bin = snapshot.getValue(Bin.class);
                if (bin != null) {
                    binNameTextView.setText(bin.getName());
                    binStatusTextView.setText("Status: " + bin.getStatus());
                    binLocationTextView.setText(String.format(Locale.getDefault(),
                            "Location: %.6f, %.6f", bin.getLatitude(), bin.getLongitude()));
                    String lastUpdatedStr = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
                            .format(new Date(bin.getLastUpdated()));
                    binLastUpdatedTextView.setText("Last Updated: " + lastUpdatedStr);
                } else {
                    Toast.makeText(BinDetailsActivity.this, "Bin data not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BinDetailsActivity.this, "Failed to load bin details: " + error.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
