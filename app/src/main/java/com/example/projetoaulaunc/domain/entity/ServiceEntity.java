package com.example.projetoaulaunc.domain.entity;

import android.util.Log;
import android.util.TimeUtils;

import com.example.projetoaulaunc.app.pages.ServiceActivity;
import com.example.projetoaulaunc.domain.source.Crash;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ServiceEntity {
    private String userId, name, email, service, observation;

    public ServiceEntity(String userId, String name, String email, String service, String observation) {
        setUserId(userId);
        setEmail(email);
        setName(name);
        setService(service);
        setObservation(observation);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Map<String, Object> toJson() {
        Date date = Calendar.getInstance().getTime();
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", getUserId());
        map.put("name", getName());
        map.put("email", getEmail());
        map.put("service", getService());
        map.put("observation", getObservation());
        map.put("created_at", date.toString());

        return map;
    }

    public void sendtoDb(Map<String, Object> params) {
        Crash crash = new Crash();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("services")
                .add(params)
                .addOnSuccessListener(success -> Log.i("sucesso", "top")
                ).addOnFailureListener(
                        failure -> {
                            crash.log(failure.getMessage());
                        }
                );
    }
}
