package com.example.projetoaulaunc.app.pages;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetoaulaunc.R;
import com.example.projetoaulaunc.app.pages.authentication.ui.login.LoginActivity;
import com.example.projetoaulaunc.domain.entity.ServiceEntity;
import com.example.projetoaulaunc.domain.source.AppEvents;

import java.util.Objects;

public class ServiceActivity extends AppCompatActivity {
    AppEvents appEvents = new AppEvents();

    Spinner spinner;
    EditText name, email, obs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        ActionBar appBar = Objects.requireNonNull(getSupportActionBar());
        appBar.setTitle(R.string.appbar_service);
        appBar.setDisplayHomeAsUpEnabled(true);

        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(v -> {
            validateFields();
        });

        appEvents.sendScreen(this, "service");

        configureSpinner();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == 16908332){
            onBackPressed();
            return (true);
        }
        return(super.onOptionsItemSelected(item));
    }

    void configureSpinner() {
        spinner = findViewById(R.id.spinner_services);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_options,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        );
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    void validateFields (){
        Bundle bundle = new Bundle();
        name = findViewById(R.id.name_field);
        email = findViewById(R.id.email_field);
        obs = findViewById(R.id.obs_field);

        String fieldName = name.getText().toString();
        String fieldEmail = email.getText().toString();
        String fieldObs = obs.getText().toString();
        String fieldSpinner = spinner.getSelectedItem().toString();

        if(fieldName.trim().isEmpty() || fieldEmail.trim().length() < 3) {
            bundle.putString("name", fieldName);
            appEvents.globalEvent("service_error_field_name", bundle);
            Toast.makeText(this, "Informe um nome válido.", Toast.LENGTH_LONG).show();
            return;
        }

        if(fieldEmail.trim().isEmpty()) {
            bundle.putString("email", fieldEmail);
            appEvents.globalEvent("service_error_field_email", bundle);
            Toast.makeText(this, "Informe um e-mail válido.", Toast.LENGTH_LONG).show();
            return;
        }

        if(fieldObs.trim().isEmpty()) {
            bundle.putString("obs", fieldObs);
            appEvents.globalEvent("service_error_field_obs", bundle);
            Toast.makeText(this, "Informe um objetivo para contato.", Toast.LENGTH_LONG).show();
            return;
        }

        if(fieldSpinner.contains("Selecione um serviço")) {
            bundle.putString("service", fieldSpinner);
            appEvents.globalEvent("service_error_field_service", bundle);
            Toast.makeText(this, "Selecione um serviço.", Toast.LENGTH_LONG).show();
            return;
        }

        startActivityForResult(
                new Intent(this,
                        LoginActivity.class
                ),
                200
        );
        /*
        ServiceEntity serviceEntity = new ServiceEntity(fieldName, fieldEmail, fieldSpinner, fieldObs);
        serviceEntity.sendtoDb(serviceEntity.toJson());
         */
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            /*
            String fieldName = name.getText().toString();
            String fieldEmail = email.getText().toString();
            String fieldObs = obs.getText().toString();
            String fieldSpinner = spinner.getSelectedItem().toString();

            ServiceEntity serviceActivity = new ServiceEntity(
                    fieldName, fieldEmail, fieldSpinner, fieldObs
            );
            */
            Object response = data.getExtras().get("response");

        }
    }
}