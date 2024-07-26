package com.soft.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText email, password, confirmPassword;
    private Button create;
    private ProgressBar progressBar;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initialize views
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        progressBar = findViewById(R.id.progress_bar);
        create = findViewById(R.id.create);
        login = findViewById(R.id.login);

        // Set onClickListeners
        create.setOnClickListener(v -> createAccount());
        login.setOnClickListener(v -> {
            // Start LoginActivity when user clicks on "Already have an account"
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finish the current activity
        });
    }

    private void createAccount() {
        String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        String confirmPassStr = confirmPassword.getText().toString().trim();

        if (!validateData(emailStr, passwordStr, confirmPassStr)) {
            return; // Stop execution if validation fails
        }

        createAccountInFirebase(emailStr, passwordStr);
    }

    private void createAccountInFirebase(String email, String password) {
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        changeInProgress(false);
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccountActivity.this, "Account Created Successfully.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getLocalizedMessage() : "Account creation failed";
                            Toast.makeText(CreateAccountActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void changeInProgress(boolean inProgress) {
        progressBar.setVisibility(inProgress ? View.VISIBLE : View.GONE);
        create.setVisibility(inProgress ? View.GONE : View.VISIBLE);
    }

    private boolean validateData(String email, String password, String confirmPass) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.email.setError("Invalid email address");
            return false;
        }

        if (password.length() < 6) {
            this.password.setError("Password too short, minimum 6 characters");
            return false;
        }

        if (!password.equals(confirmPass)) {
            confirmPassword.setError("Passwords do not match");
            return false;
        }

        return true;
    }
}
