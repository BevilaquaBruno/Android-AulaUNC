package com.example.projetoaulaunc.app.pages;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.projetoaulaunc.R;
import com.example.projetoaulaunc.domain.source.AppEvents;

import java.util.Objects;

public class ClientActivity extends AppCompatActivity {
    AppEvents appEvents = new AppEvents();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        ActionBar appBar = Objects.requireNonNull(getSupportActionBar());
        appBar.setDisplayHomeAsUpEnabled(true);
        appBar.setTitle(R.string.appbar_client);

        appEvents.sendScreen(this, "client");
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