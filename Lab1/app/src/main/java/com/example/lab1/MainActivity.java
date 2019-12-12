package com.example.lab1;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_helper);
        ViewPager viewPager = findViewById(R.id.helper);
        TabNavigation adapter = new TabNavigation(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        progressBar = findViewById(R.id.progress_bar);
        mAuth = getApplicationEx().getAuth();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signOut();
        progressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        return true;
    }

    private ApplicationEx getApplicationEx() {
        return ((ApplicationEx) getApplication());
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}