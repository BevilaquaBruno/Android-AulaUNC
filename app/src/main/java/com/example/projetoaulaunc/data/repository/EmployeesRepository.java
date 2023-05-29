package com.example.projetoaulaunc.data.repository;

import android.util.Log;

import com.example.projetoaulaunc.data.Result;
import com.example.projetoaulaunc.data.datasource.EmployeesDataSource;
import com.example.projetoaulaunc.data.datasource.LoginDataSource;
import com.example.projetoaulaunc.data.datasource.ServiceDataSource;
import com.example.projetoaulaunc.data.datasource.model.EmployeesModel;
import com.example.projetoaulaunc.data.datasource.model.UserModel;

import java.util.ArrayList;
import java.util.Map;

public class EmployeesRepository {
    private static volatile EmployeesRepository instance;

    private final EmployeesDataSource dataSource;

    private ArrayList<EmployeesModel> listEmployees;

    private EmployeesRepository(EmployeesDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static EmployeesRepository getInstance(EmployeesDataSource dataSource) {
        if (instance == null) {
            instance = new EmployeesRepository(dataSource);
        }
        return instance;
    }

    public void setList(ArrayList<EmployeesModel> list){ this.listEmployees = list;}

    public Result<ArrayList<EmployeesModel>> getEmployees() {
        Result<ArrayList<EmployeesModel>> result = dataSource.getEmployees();
        if (result instanceof Result.Success) {
            setList(((Result.Success<ArrayList<EmployeesModel>>) result).getData());
        }
        return result;
    }
}
