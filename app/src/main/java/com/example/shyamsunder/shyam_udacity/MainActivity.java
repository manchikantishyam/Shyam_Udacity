package com.example.shyamsunder.shyam_udacity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showToast(String toastString){
        Toast.makeText(this.getBaseContext(), toastString,
                Toast.LENGTH_SHORT).show();
    }


    public void popularMoviesClick(View v){
        showToast("PopularMovies is Under Construction");
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
