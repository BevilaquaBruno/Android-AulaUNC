package com.example.projetoaulaunc.data.datasource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.projetoaulaunc.data.Result;
import com.example.projetoaulaunc.data.datasource.model.EmployeesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EmployeesDataSource {

    private ArrayList<EmployeesModel> listEmployees = new ArrayList<>();

    private Result result;

    public Result<ArrayList<EmployeesModel>> getEmployees() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://goldfish-app-eaau6.ondigitalocean.app/get-employees")
                    .build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    result = new Result.Error(new IOException("Erro inside http get employees"));
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if(response.isSuccessful() && response.code() == 200) {
                        String body = response.body().string();

                        try {
                            JSONArray array = new JSONArray(body);
                            for (int i = 0; i < array.length(); i++) {
                                Object params = array.get(i);
                                JSONObject object = new JSONObject(params.toString());
                                listEmployees.add(new EmployeesModel(object));
                            }

                            result = new Result.Success<>(listEmployees);
                        } catch (JSONException e) {
                            Log.i("Error returned params => ", e.getMessage());
                        }
                    }
                }
            });

            while(result == null){
                //Log.i("result => ", "still null");
            }

            return result;
        }catch(Exception e){
            return new Result.Error(new IOException("Error on get employees"));
        }
    }
}
