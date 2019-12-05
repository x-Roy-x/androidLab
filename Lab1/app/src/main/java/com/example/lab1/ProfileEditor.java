package com.example.lab1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class ProfileEditor extends Fragment {

    private FirebaseAuth auth;
    private TextInputLayout nameLayout;
    private TextInputEditText nameInput;
    private TextInputLayout emailLayout;
    private TextInputEditText emailInput;
    private Button nameRefresh;
    private Button emailRefresh;
    private Button photoUpload;
    private TextView name;
    private TextView email;
    private ImageView avatar;
    private FirebaseUser user;
    private StorageReference folder;
    private StorageReference imageName;
    private final int codeStatus = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();

        View root = inflater.inflate(R.layout.fragment_profile_editor,container, false);
        nameLayout = root.findViewById(R.id.name_layout);
        nameInput = root.findViewById(R.id.new_name);
        emailLayout = root.findViewById(R.id.email_layout);
        emailInput = root.findViewById(R.id.new_email);
        name = root.findViewById(R.id.current_name);
        email = root.findViewById(R.id.current_email);
        nameRefresh = root.findViewById(R.id.refresh_name);
        emailRefresh = root.findViewById(R.id.refresh_email);
        photoUpload = root.findViewById(R.id.upload_avatar);
        avatar = root.findViewById(R.id.user_avatar);
        folder = FirebaseStorage
                .getInstance()
                .getReference()
                .child("ImageFolder");

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        user = auth.getCurrentUser();
        if (user != null) {
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            imageName = folder.child(user.getUid());
            imageName.getDownloadUrl()
                    .addOnSuccessListener(uri -> Picasso.get().load(uri.toString()).into(avatar));
        } else {
            Toast.makeText(getActivity(), getString(R.string.wrong),
                    Toast.LENGTH_SHORT).show();
        }
        updateName();
        updateEmail();
        uploadAvatar();
    }

    private void uploadAvatar() {
        photoUpload.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, codeStatus);});
    }

    private void updateEmail() {
        emailRefresh.setOnClickListener(view -> {
            String newEmail = Objects
                    .requireNonNull(emailInput.getText())
                    .toString()
                    .trim();

            isValidEmail(newEmail);
        });
    }

    private void isValidEmail(final String emailValue) {
        if (emailValue.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            emailLayout.setError(getString(R.string.enter_email_valid));
            emailLayout.requestFocus();
        } else {
            emailLayout.setError(null);
            email.setText(emailValue);
            updateEmailData(user, emailValue);
        }
    }

    private void updateEmailData(FirebaseUser user, final String newEmail) {
        user.updateEmail(newEmail)
                .addOnCompleteListener(task ->
                        Toast.makeText(getActivity(),
                                getString(R.string.updated),
                                Toast.LENGTH_SHORT)
                                .show());
    }

    private void updateName() {
        nameRefresh.setOnClickListener(view -> {
            String newUsername = Objects
                    .requireNonNull(nameInput.getText())
                    .toString()
                    .trim();

            isValidUsername(newUsername);
        });
    }

    private void isValidUsername(final String username) {
        if (username.isEmpty()) {
            nameLayout.setError(getString(R.string.enter_name));
            nameLayout.requestFocus();
        } else {
            nameLayout.setError(null);
            name.setText(username);
            updateUsernameData(user, username);
        }
    }

    private void updateUsernameData(final FirebaseUser user, final String newName) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(),
                                getString(R.string.updated),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == codeStatus) {
            if (resultCode == RESULT_OK) {
                Uri ImageData = Objects.requireNonNull(data).getData();
                imageName.putFile(Objects.requireNonNull(ImageData))
                        .addOnSuccessListener(taskSnapshot ->
                                Toast.makeText(getActivity(), getString(R.string.updated),
                                Toast.LENGTH_SHORT).show());
                imageName.getDownloadUrl()
                        .addOnSuccessListener(uri ->
                                Picasso.get()
                                .load(uri.toString())
                                .into(avatar));
            }
        }
    }
}