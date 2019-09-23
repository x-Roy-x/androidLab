package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText emailField;
    private EditText nameField;
    private EditText phoneField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.signUpEmailAddress);
        nameField = findViewById(R.id.signUpPersonName);
        phoneField = findViewById(R.id.signUpPhone);
        passwordField = findViewById(R.id.signUpPassword);

        findViewById(R.id.signUpButton).setOnClickListener(view -> {
            final String email = emailField.getText().toString();
            final String name = nameField.getText().toString();
            final String phone = phoneField.getText().toString();
            final String password = passwordField.getText().toString();

            checkValidation(email,name,phone,password);
        });

        findViewById(R.id.signInLink).setOnClickListener(view -> {
            this.onBackPressed();
        });
    }

    private void checkValidation(final String email,final String name,final String phone, final String password){
        if(name.isEmpty()){
            nameField.setError("Please enter name!");
            nameField.requestFocus();
        }else if(phone.isEmpty()){
            phoneField.setError("Please enter phone!");
            phoneField.requestFocus();
        }else if (email.isEmpty()){
            emailField.setError("Please enter your email!");
            emailField.requestFocus();
        }else if(password.isEmpty()){
            passwordField.setError("Please enter password!");
            passwordField.requestFocus();
        }else if(!phone.matches(getString(R.string.phonePattern))) {
            Toast.makeText(SignUpActivity.this, "Phone must be 13 symbols, and starts with +380",
                    Toast.LENGTH_LONG).show();
        }else if(!email.matches(getString(R.string.emailPattern))) {
            Toast.makeText(SignUpActivity.this, "Invalid email!",
                    Toast.LENGTH_SHORT).show();
        }else if(!password.matches(getString(R.string.passwordPattern))){
            Toast.makeText(SignUpActivity.this, "Password must be 8+ symbols!",
                    Toast.LENGTH_SHORT).show();
        } else if(!(email.isEmpty() && password.isEmpty())){
            signUp(email,name,phone,password);
        }else {
            Toast.makeText(SignUpActivity.this, "Something gone wrong!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void signUp(final String email,final String name,final String phone, final String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(task1 -> {
                                    if (task.isSuccessful()) {
                                        forSignUpSuccess(email, password);
                                    }
                                });
                    } else {
                        forSignUpError();
                    }
                });
    }

    private void forSignUpSuccess(final String email, final String password){
        signIn(email, password);

    }

    private void forSignUpError(){
        Toast.makeText(SignUpActivity.this,"Error",Toast.LENGTH_LONG).show();
    }


    private void signIn(final String email, final String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        forSignInSuccess();
                    } else {
                        forSignInError();
                    }
                });
    }

    private void forSignInSuccess(){
        final Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    private void forSignInError(){
        Toast.makeText(SignUpActivity.this,"Error",Toast.LENGTH_LONG).show();
    }
}
