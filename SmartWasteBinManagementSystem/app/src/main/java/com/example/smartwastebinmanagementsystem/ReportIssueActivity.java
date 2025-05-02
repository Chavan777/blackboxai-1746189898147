package com.example.smartwastebinmanagementsystem;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ReportIssueActivity provides a simple contact form to report issues.
 * The form data is sent to a dummy API or stored locally (mock implementation).
 */
public class ReportIssueActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, messageEditText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> sendReport());
    }

    private void sendReport() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Please enter your name");
            nameEditText.requestFocus();
            return;
        }

        if (!isValidEmail(email)) {
            emailEditText.setError("Please enter a valid email");
            emailEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(message)) {
            messageEditText.setError("Please enter a message");
            messageEditText.requestFocus();
            return;
        }

        // Mock sending data to API or local storage
        Toast.makeText(this, "Report sent successfully. Thank you!", Toast.LENGTH_LONG).show();

        // Clear form
        nameEditText.setText("");
        emailEditText.setText("");
        messageEditText.setText("");
    }

    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
