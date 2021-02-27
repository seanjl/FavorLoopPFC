package com.dam.favorloop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.dam.favorloop.fragments.AddFragment;
import com.dam.favorloop.fragments.EventFragment;
import com.dam.favorloop.fragments.HomeFragment;
import com.dam.favorloop.fragments.MessageFragment;
import com.dam.favorloop.fragments.NotificationFragment;
import com.dam.favorloop.fragments.ProfileFragment;
import com.dam.favorloop.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavView;
    Toolbar toolbar;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ResourcesHelper.resources == null) {
            ResourcesHelper.resources = getResources();
        }

        bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setOnNavigationItemSelectedListener(listener);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(listenerToolbar);

        selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
    }

    private Toolbar.OnMenuItemClickListener listenerToolbar = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.navMessage) {
                selectedFragment = new MessageFragment();

            } else if (item.getItemId() == R.id.navNotificacion) {
                selectedFragment = new NotificationFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
            }
            return true;
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.navHome) {
                selectedFragment = new HomeFragment();

            } else if (item.getItemId() == R.id.navEvent) {
                selectedFragment = new EventFragment();

            } else if (item.getItemId() == R.id.navProfile) {
                selectedFragment = new ProfileFragment();

            } else if (item.getItemId() == R.id.navSearch) {
                selectedFragment = new SearchFragment();

            } else if (item.getItemId() == R.id.navAdd) {
                selectedFragment = new AddFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
            }

            return true;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}