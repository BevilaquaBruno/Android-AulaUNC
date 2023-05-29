package com.example.projetoaulaunc.app.pages;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.projetoaulaunc.R;
import com.example.projetoaulaunc.app.core.EmployeesAdapter;
import com.example.projetoaulaunc.data.Result;
import com.example.projetoaulaunc.data.datasource.EmployeesDataSource;
import com.example.projetoaulaunc.data.datasource.model.EmployeesModel;
import com.example.projetoaulaunc.data.repository.EmployeesRepository;
import com.example.projetoaulaunc.domain.source.AppEvents;

import java.util.ArrayList;
import java.util.Objects;

public class EmployeesActivity extends AppCompatActivity {
    private ArrayList<EmployeesModel> listEmployees;
    AppEvents appEvents = new AppEvents();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        ActionBar appBar = Objects.requireNonNull(getSupportActionBar());
        appBar.setDisplayHomeAsUpEnabled(true);
        appBar.setTitle(R.string.appbar_company);

        getEmployees();

        appEvents.sendScreen(this, "company");

    }

    void getEmployees() {
        listEmployees = new ArrayList<>();

        EmployeesRepository employeesRepository = EmployeesRepository.getInstance(new EmployeesDataSource());
        Result<ArrayList<EmployeesModel>> resultOrFail = employeesRepository.getEmployees();

        if(resultOrFail instanceof  Result.Success){
            setList(((Result.Success<ArrayList<EmployeesModel>>) resultOrFail).getData());
        }

        //inserir na lista
        ListView listView = findViewById(R.id.lv_employees);
        ArrayAdapter<EmployeesModel> adapter = new EmployeesAdapter(this, listEmployees);
        listView.setAdapter(adapter);
    }

    private void setList(ArrayList<EmployeesModel> list) {
        this.listEmployees = list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == 16908332){
            onBackPressed();
            return (true);
        }
        return(super.onOptionsItemSelected(item));
    }
}