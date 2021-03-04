package com.dam.favorloop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.dam.favorloop.fragments.AddFragment;
import com.dam.favorloop.fragments.AmigosFragment;
import com.dam.favorloop.fragments.EventFragment;
import com.dam.favorloop.fragments.HomeFragment;
import com.dam.favorloop.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavView;
    Toolbar toolbar;
    Fragment selectedFragment = null;
    FirebaseAuth firebaseAuth;
    private DrawerLayout drawer;

    // 04 Mar 2021 19:07 - FavorLoop v1 ha sido completado por Santi, Nacho, Sean y Juan de la UEM :)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Navigation Drawr
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (ResourcesHelper.resources == null) {
            ResourcesHelper.resources = getResources();
        }

        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setOnNavigationItemSelectedListener(listener);

        toolbar = findViewById(R.id.toolbar);

        selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // Abre fragments tras pulsar en elementos del NavigationDrawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_misloops:
                Intent i = new Intent(MainActivity.this, MisLoops.class);
                startActivity(i);
                break;
            case R.id.nav_misevents:
                Intent i2 = new Intent(MainActivity.this, MisEventos.class);
                startActivity(i2);
                break;
            case R.id.nav_aboutus:
                Intent i3 = new Intent(this, AboutUsActivity.class);
                startActivity(i3);
                break;
            case R.id.nav_tutorial:
                Intent i4 = new Intent(this, IntroActivity.class);
                startActivity(i4);
                break;
            case R.id.nav_miperfil:
                Intent i5 = new Intent(MainActivity.this, EditarPerfil.class);
                startActivity(i5);
                break;
            case R.id.nav_logout:
                MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(MainActivity.this);
                alertDialog.setMessage("¿Estás seguro que deseas salir?");
                alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.create().show();
                break;
        }

        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.navHome) {
                selectedFragment = new HomeFragment();

            } else if (item.getItemId() == R.id.navEvent) {
                selectedFragment = new EventFragment();

            } else if (item.getItemId() == R.id.navProfile) {
                selectedFragment = new AmigosFragment();

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