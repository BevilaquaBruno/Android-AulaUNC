package com.example.projetoaulaunc.data.datasource.model;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeesModel {
    int id;
    String name, age, function;

    public EmployeesModel(JSONObject json) throws JSONException {
        setId(json.getInt("id"));
        setName(json.getString("name"));
        setAge(json.getInt("age") + " anos");
        setFunction(json.getString("function"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
