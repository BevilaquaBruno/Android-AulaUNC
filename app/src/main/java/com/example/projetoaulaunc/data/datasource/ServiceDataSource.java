package com.example.projetoaulaunc.data.datasource;

import android.util.Log;

import com.example.projetoaulaunc.data.Result;
import com.example.projetoaulaunc.data.sources.local.ConfigFirebase;
import com.example.projetoaulaunc.domain.entity.ServiceEntity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ServiceDataSource {
    private FirebaseFirestore db;

    private Boolean successSave;
    public Result<Boolean> setService(Map<String, Object> json) {
        db = ConfigFirebase.getDb();

        db.collection("Services").add(json)
                .addOnSuccessListener(success -> this.successSave = true)
                .addOnFailureListener(failure -> this.successSave = false);

        return new Result.Success(true);
    }

    public Result<List<ServiceEntity>> getServices(String userId){
        try {
            db = ConfigFirebase.getDb();
            List<ServiceEntity> listServices = null;
            db.collection("services")
                    .whereEqualTo("user_id", userId).get()
                    .addOnSuccessListener(
                        response -> {
                            List<DocumentSnapshot> doc = response.getDocuments();
                            for( int i = 0; i < doc.size(); i++){
                                DocumentSnapshot currentDoc = doc.get(i);
                                listServices.add(new ServiceEntity(
                                        currentDoc.get("user_id").toString(),
                                        currentDoc.get("name").toString(),
                                        currentDoc.get("email").toString(),
                                        currentDoc.get("service").toString(),
                                        currentDoc.get("observation").toString()
                                ));
                            }
                        }
                    );
            return new Result.Success<>(listServices);
        }catch (Exception e){
            return new Result.Error(new IOException("Erro get services", e));
        }
    }
}
