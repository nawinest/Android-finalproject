package com.nawinc27.mac.findbuffet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nawinc27.mac.findbuffet.Main_menu.MainPageFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new MainPageFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }



}
