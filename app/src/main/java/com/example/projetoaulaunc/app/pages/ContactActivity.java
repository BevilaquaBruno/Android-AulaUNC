package com.example.projetoaulaunc.app.pages;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.projetoaulaunc.R;
import com.example.projetoaulaunc.domain.source.AppEvents;

import java.util.Objects;

import mehdi.sakout.aboutpage.AboutPage;

public class ContactActivity extends AppCompatActivity {
    AppEvents appEvents = new AppEvents();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar appBar = Objects.requireNonNull(getSupportActionBar());
        appBar.setDisplayHomeAsUpEnabled(true);
        appBar.setTitle(R.string.appbar_contact);
        appEvents.sendScreen(this, "contact");

        setContentView(aboutPage());
    }

    View aboutPage(){
        View aboutPage = new AboutPage(this)
            .isRTL(false)
            //.setCustomFont() // or Typeface
            //.setImage(R.drawable.dummy_image)
            //.addItem(versionElement)
            //.addItem(adsElement)
                .setDescription("Veja todas as formas que pode nos encontrar!")
            .addGroup("Sobre nós")
            .addEmail("bbbevilaqua@gmail.com")
            .addWebsite("https://bevilaqua.surge.sh")
            .addFacebook("the.medy")
            .addTwitter("medyo80")
            .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
            .addPlayStore("com.ideashower.readitlater.pro")
            .addGitHub("bevilaquabruno")
            .addInstagram("medyo80")
            .create();

        return(aboutPage);
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