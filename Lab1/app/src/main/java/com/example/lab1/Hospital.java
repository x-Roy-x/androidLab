package com.example.lab1;

public class Hospital {
    final String hospitalBuilding;
    final String ward;
    final String fullNamePatient;
    final String pressure;
    final String temperature;
    final String palpitation;
    final String photoUrl;

    public Hospital(String hospitalBuilding, String ward, String fullNamePatient, String pressure,
                    String temperature, String palpitation, String photoUrl) {
        this.hospitalBuilding = hospitalBuilding;
        this.ward = ward;
        this.fullNamePatient = fullNamePatient;
        this.pressure = pressure;
        this.temperature = temperature;
        this.palpitation = palpitation;
        this.photoUrl = photoUrl;
    }

    public String getHospitalBuilding() {
        return hospitalBuilding;
    }

    public String getWard() { return ward; }

    public String getFullNamePatient() {
        return fullNamePatient;
    }

    public String getPressure() {
        return pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getPalpitation() {
        return palpitation;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}