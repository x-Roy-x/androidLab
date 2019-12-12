package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class HospitalData extends AppCompatActivity {

    private TextView building;
    private TextView ward;
    private TextView namePatient;
    private TextView pressure;
    private TextView temperature;
    private TextView palpitation;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_data);
        building = findViewById(R.id.building);
        ward = findViewById(R.id.ward_hospital);
        namePatient = findViewById(R.id.name_of_patient);
        pressure = findViewById(R.id.pressure_patient);
        temperature = findViewById(R.id.temperature_patient);
        palpitation = findViewById(R.id.palpitation_patient);
        photo = findViewById(R.id.photo);

        setDataInHospitalData();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void setDataInHospitalData() {
        if (getIntent().hasExtra("building") && getIntent().hasExtra("ward") &&
                getIntent().hasExtra("name") && getIntent().hasExtra("pressure")&&
                getIntent().hasExtra("temperature") &&
                getIntent().hasExtra("palpitation") ){
            building.setText(getIntent().getStringExtra("building"));
            ward.setText(getIntent().getStringExtra("ward"));
            namePatient.setText(getIntent().getStringExtra("name"));
            pressure.setText(getIntent().getStringExtra("pressure"));
            temperature.setText(getIntent().getStringExtra("temperature"));
            palpitation.setText(getIntent().getStringExtra("palpitation"));
            Picasso.get().load(getIntent().getStringExtra("avatar")).into(photo);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(HospitalData.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}