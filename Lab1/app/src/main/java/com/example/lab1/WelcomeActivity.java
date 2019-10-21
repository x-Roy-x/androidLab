package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {
    private TextView userField;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        userField = findViewById(R.id.welcomeUser);

        getUserParam();

        userField.setText("Welcome," + name);

        findViewById(R.id.signOutButton).setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            final Intent intent = new Intent(this,SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void getUserParam(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            name = user.getDisplayName();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }
}