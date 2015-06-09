package com.example.shyamsunder.shyam_udacity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button spotify_Streamer = (Button)findViewById(R.id.spotify_streamer);
        Button scores_App = (Button)findViewById(R.id.scores_app);
        Button library_App = (Button)findViewById(R.id.library_app);
        Button build_it_Bigger = (Button)findViewById(R.id.build_itbig);
        Button capstone_App = (Button)findViewById(R.id.capstone_myApp);

        spotify_Streamer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showToast("Spotify Streamer is Under Construction");
            }
        });
        scores_App.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showToast("Scores App is Under Construction");
            }
        });
        library_App.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showToast("Library App is Under Construction");
            }
        });
        build_it_Bigger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showToast("Build It Bigger is Under Construction");
            }
        });
        capstone_App.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showToast("My Capstone App is  Under Construction");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void showToast(String toastString){
        Toast.makeText(this.getBaseContext(), toastString,
                Toast.LENGTH_SHORT).show();
    }
}
