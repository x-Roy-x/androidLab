package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalData extends AppCompatActivity {

    static final String HOSPITAL_NAME = "HOSPITAL_NAME";

    private TextView buildingTv;
    private TextView wardTv;
    private TextView namePatientTv;
    private TextView pressureTv;
    private TextView temperatureTv;
    private TextView palpitationTv;
    private ImageView photoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_data);
        buildingTv = findViewById(R.id.building);
        wardTv = findViewById(R.id.ward_hospital);
        namePatientTv = findViewById(R.id.name_of_patient);
        pressureTv = findViewById(R.id.pressure_patient);
        temperatureTv = findViewById(R.id.temperature_patient);
        palpitationTv = findViewById(R.id.palpitation_patient);
        photoIv = findViewById(R.id.photo);

        if (getIntent().hasExtra(HOSPITAL_NAME)) {
            showHospitalList();
            Log.d("TAG","1");
        } else {
            setDataInHospitalFragment();
            Log.d("TAG","2");

        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void setDataInHospitalFragment(){
        if (getIntent().hasExtra("building") && getIntent().hasExtra("ward") &&
                getIntent().hasExtra("name") && getIntent().hasExtra("pressure") &&
                getIntent().hasExtra("temperature") &&
                getIntent().hasExtra("palpitation")) {

            setViewData(getIntent().getStringExtra("building"),
                    getIntent().getStringExtra("ward"),
                    getIntent().getStringExtra("name"),
                    getIntent().getStringExtra("pressure"),
                    getIntent().getStringExtra("temperature"),
                    getIntent().getStringExtra("palpitation"),
                    getIntent().getStringExtra("avatar")
            );
        }
    }

    private void setViewData(final String building, final String ward, final String name,
                             final String pressure, final String temperature, final String palpitation,
                             final String avatar){
        buildingTv.setText(building);
        wardTv.setText(ward);
        namePatientTv.setText(name);
        pressureTv.setText(pressure);
        temperatureTv.setText(temperature);
        palpitationTv.setText(palpitation);
        Picasso.get().load(avatar).into(photoIv);
    }

    private void showHospitalList(){
        ApplicationEx applicationEx = (ApplicationEx) this.getApplication();
        final ApiService apiService = applicationEx.getApiService();
        final Call<List<Hospital>> call = apiService.getHospitalData();

        call.enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse(final Call<List<Hospital>> call, final Response<List<Hospital>> response){
                for (Hospital hospital : Objects.requireNonNull(response.body())) {
                    if (hospital.getHospitalBuilding().equals(getIntent().getStringExtra(HOSPITAL_NAME))) {
                        setViewData(hospital.getHospitalBuilding(),
                                hospital.getWard(),
                                hospital.getFullNamePatient(),
                                hospital.getPressure(),
                                hospital.getTemperature(),
                                hospital.getPalpitation(),
                                hospital.getPhotoUrl());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Hospital>> call, Throwable t){
                Log.d("MY_TAG", getString(R.string.error_update));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
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