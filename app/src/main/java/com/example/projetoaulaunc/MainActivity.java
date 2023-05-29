package com.example.projetoaulaunc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.projetoaulaunc.app.pages.ClientActivity;
import com.example.projetoaulaunc.app.pages.ContactActivity;
import com.example.projetoaulaunc.app.pages.EmployeesActivity;
import com.example.projetoaulaunc.app.pages.ServiceActivity;
import com.example.projetoaulaunc.domain.source.AppEvents;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    AppEvents appEvents = new AppEvents();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appEvents.sendScreen(this, "main");
        ActionBar appBar = Objects.requireNonNull(getSupportActionBar());
        appBar.hide();

        openCompany();
        openClient();
        openContact();
        openService();
    }

    void openClient() {
        Bundle bundle = new Bundle();
        appEvents.globalEvent("main_open_client", bundle);
        ImageView client = findViewById(R.id.btnClients);
        client.setOnClickListener(
            v-> startActivity(
                new Intent(this,
                    ClientActivity.class
                )
            )
        );
    }

    void openContact() {
        Bundle bundle = new Bundle();
        appEvents.globalEvent("main_open_contact", bundle);
        ImageView contact = findViewById(R.id.btnContact);
        contact.setOnClickListener(
                v-> startActivity(
                        new Intent(this,
                                ContactActivity.class
                        )
                )
        );
    }

    void openService() {
        Bundle bundle = new Bundle();
        appEvents.globalEvent("main_open_service", bundle);
        ImageView service = findViewById(R.id.btnService);
        service.setOnClickListener(
                v-> startActivity(
                        new Intent(this,
                                ServiceActivity.class
                        )
                )
        );
    }

    void openCompany() {
        Bundle bundle = new Bundle();
        appEvents.globalEvent("main_open_company", bundle);
        ImageView company = findViewById(R.id.btnCompany);
        company.setOnClickListener(
                v-> startActivity(
                        new Intent(this,
                                EmployeesActivity.class
                        )
                )
        );
    }
}