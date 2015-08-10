package com.example.shyamsunder.shyam_udacity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showToast(String toastString){
        Toast.makeText(this.getBaseContext(), toastString,
                Toast.LENGTH_SHORT).show();
    }


    public void popularMoviesClick(View v){
        Intent localIntent = new Intent(this,PopularMoviesHome.class);
        startActivity(localIntent);

    }
    public void scoresAppClick(View v){
        showToast("Scores App is Under Construction");
    }
    public void buildItBigClick(View v){
        showToast("Build It Bigger is Under Construction");
    }
    public void libraryAppClick(View v){
        showToast("Library App is Under Construction");
    }
    public void capstoneAppClick(View v){
        showToast("My Capstone App is  Under Construction");
    }
}
