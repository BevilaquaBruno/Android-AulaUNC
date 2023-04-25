package com.example.projetoaulaunc.app.pages;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetoaulaunc.R;
import com.example.projetoaulaunc.app.pages.authentication.ui.login.LoginActivity;
import com.example.projetoaulaunc.data.Result;
import com.example.projetoaulaunc.data.datasource.ServiceDataSource;
import com.example.projetoaulaunc.data.repository.ServiceRepository;
import com.example.projetoaulaunc.data.sources.local.ConfigFirebase;
import com.example.projetoaulaunc.domain.entity.ServiceEntity;
import com.example.projetoaulaunc.domain.source.AppEvents;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ServiceActivity extends AppCompatActivity {
    AppEvents appEvents = new AppEvents();
    private ServiceEntity serviceEntity;

    private Boolean successSave = false;

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

        FirebaseAuth auth = ConfigFirebase.getAuth();
        FirebaseUser user = auth.getCurrentUser();

        if( user == null ) {
            startActivityForResult(
                    new Intent(this,
                            LoginActivity.class
                    ),
                    200
            );
            return;
        }

        serviceEntity = new ServiceEntity(user.getUid(), fieldName, fieldEmail, fieldSpinner, fieldObs);
        createService();
    }

    private void createService() {
        ServiceRepository repository = ServiceRepository.getInstance(new ServiceDataSource());

        Log.i("service => ", serviceEntity.toJson().toString());
        Result<Boolean> success = repository.setService(serviceEntity.toJson());

        Log.i("success => ", success.toString());
        if (success instanceof Result.Success){
            setSuccess(((Result.Success<Boolean>) success).getData());
        }

        if (this.successSave){
            Toast.makeText(this, "Sucesso ao cadastrar o serviço", Toast.LENGTH_LONG).show();
            finish();
        }else {
            Toast.makeText(this, "Erro ao cadastrar o serviço", Toast.LENGTH_LONG).show();
        }
    }

    public void setSuccess(Boolean success) {
        this.successSave = success;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_OK) {

            String fieldName = name.getText().toString();
            String fieldEmail = email.getText().toString();
            String fieldObs = obs.getText().toString();
            String fieldSpinner = spinner.getSelectedItem().toString();

            Object response = data.getExtras().get("response");
            String[] values = response.toString().replace("{", "").replace("}", "").split(",");
            String userId = values[2].split("=")[1];
            ServiceEntity serviceEntity = new ServiceEntity(userId, fieldName, fieldEmail, fieldSpinner, fieldObs);
            createService();



        }
    }
}