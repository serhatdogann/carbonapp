package com.example.carbonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.util.Log;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Stack<Fragment> fragmentStack; // Fragmentlerin tutulacağı Stack
    private boolean isFragmentBackStackEnabled; // Geri yığına eklenip eklenmediğini kontrol etmek için flag
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fragmentStack = new Stack<>(); // Fragmentlerin tutulacağı Stack'i oluşturuyoruz
        isFragmentBackStackEnabled = false; // Başlangıçta geri yığına eklenmemesi için flag'i false olarak ayarlıyoruz

        if (savedInstanceState == null) {
            // Emulator açıldığında LoginFragment'in yüklenmesi için
            replaceCurrentFragment(new HomeFragment());
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!fragmentStack.isEmpty() && isFragmentBackStackEnabled) {
            // Menü üzerinden fragmente geçildiyse ve geri tuşuna basıldıysa en son eklenen fragmente dön
            Fragment previousFragment = fragmentStack.pop(); // En son eklenen fragmenti Stack'ten çıkar
            replaceCurrentFragment(previousFragment);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId(); // MenuItem'in ID'sini alın
        Fragment selectedFragment = null;
        if (itemId == R.id.nav_home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.nav_carbon) {
            selectedFragment = new HesaplaFragment();
        } else if (itemId == R.id.nav_about) {
            selectedFragment = new InfoFragment();
        } else if (itemId == R.id.nav_tree) {
            selectedFragment = new BagisFragment();
        } else if (itemId == R.id.nav_iletisim) {
            selectedFragment = new HakkimizdaFragment();
        } else if (itemId == R.id.nav_logout) {
            System.exit(0);
        }
        if (selectedFragment != null) {
            if (isFragmentBackStackEnabled) {
                fragmentStack.push(currentFragment); // En son fragmenti Stack'e ekle
            } else {
                isFragmentBackStackEnabled = true; // Flag'i true olarak ayarla
            }
            replaceCurrentFragment(selectedFragment);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceCurrentFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        currentFragment = fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Geri tuşuna basıldığında geri dön
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
