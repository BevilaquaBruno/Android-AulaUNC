package com.example.projetoaulaunc.app.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.projetoaulaunc.R;
import com.example.projetoaulaunc.data.datasource.model.EmployeesModel;

import java.util.ArrayList;

public class EmployeesAdapter extends ArrayAdapter<EmployeesModel> {

    private final ArrayList<EmployeesModel> listEmployees;
    private final Context context;

    public EmployeesAdapter(@NonNull Context context, @NonNull ArrayList<EmployeesModel> list) {
        super(context,0, list);

        this.listEmployees = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View view, ViewGroup group){
        View view1 = null;
        if( listEmployees != null ){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // montagem da tela
            view1 = inflater.inflate(R.layout.list_employees, group, false);

            //buscar elementos
            TextView name = view1.findViewById(R.id.name_employee);
            TextView age = view1.findViewById(R.id.age_employee);
            TextView function = view1.findViewById(R.id.function_employee);

            //setar os valores
            EmployeesModel employeesModel = listEmployees.get(position);
            name.setText(employeesModel.getName());
            age.setText(employeesModel.getAge());
            function.setText(employeesModel.getFunction());
        }

        return view1;
    }
}
