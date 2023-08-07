package com.uygulamam.carbon;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uygulamam.carbonapp.R;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Stack<Fragment> fragmentStack;
    private boolean isFragmentBackStackEnabled;
    private Fragment currentFragment;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = findViewById(R.id.nav_view);

        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            replaceCurrentFragment(new HomeFragment(), "Ana Sayfa");
        } else {
            replaceCurrentFragment(new GirisFragment(), "Giriş Yap");
        }

        View headerView = navigationView.getHeaderView(0);
        TextView textViewEmail = headerView.findViewById(R.id.gmailText);

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            textViewEmail.setText(userEmail);
        } else {
            textViewEmail.setText(""); // Eğer oturum açık değilse boş bırakabilirsiniz veya bir varsayılan değer atayabilirsiniz.
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fragmentStack = new Stack<>();
        isFragmentBackStackEnabled = false;

        ImageView imageViewUser = findViewById(R.id.userid);
        imageViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserFragment userFragment = new UserFragment();
                replaceCurrentFragment(userFragment, "Kullanıcı");
            }
        });

        // Set the initial title for the toolbar
        setTitle("Ana Sayfa");
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (isFragmentBackStackEnabled) {
            if (!fragmentStack.isEmpty()) {
                Fragment previousFragment = fragmentStack.pop();
                replaceCurrentFragment(previousFragment, getTitleFromFragment(previousFragment));
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Fragment selectedFragment = null;
        String fragmentTitle = "";
        if (itemId == R.id.nav_home) {
            selectedFragment = new HomeFragment();
            fragmentTitle = "Ana Sayfa";
        } else if (itemId == R.id.nav_carbon) {
            selectedFragment = new HesaplaFragment();
            fragmentTitle = "Karbon Ayakizi Hesapla";
        } else if (itemId == R.id.nav_about) {
            selectedFragment = new InfoFragment();
            fragmentTitle = "Bilgiler";
        } else if (itemId == R.id.nav_tree) {
            selectedFragment = new BagisFragment();
            fragmentTitle = "Bağış Yap";
        } else if (itemId == R.id.nav_iletisim) {
            selectedFragment = new HakkimizdaFragment();
            fragmentTitle = "Hakkımızda";
        } else if (itemId == R.id.nav_flight) {
            selectedFragment = new AirportFragment();
            fragmentTitle = "Mesafe Emisyon";
        } else if (itemId == R.id.nav_logout) {
            System.exit(0);
        }

        if (selectedFragment != null) {
            if (isFragmentBackStackEnabled) {
                fragmentStack.push(currentFragment);
            } else {
                isFragmentBackStackEnabled = true;
            }
            replaceCurrentFragment(selectedFragment, fragmentTitle);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceCurrentFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container2, fragment);
        fragmentTransaction.commit();
        currentFragment = fragment;
        updateToolbarTitle(title); // Toolbar üzerindeki başlığı güncelleyin
    }


    private String getTitleFromFragment(Fragment fragment) {
        if (fragment instanceof HomeFragment) {
            return "Ana Sayfa";
        } else if (fragment instanceof HesaplaFragment) {
            return "Karbon Ayakizi Hesapla";
        } else if (fragment instanceof InfoFragment) {
            return "Bilgiler";
        } else if (fragment instanceof BagisFragment) {
            return "Bağış Yap";
        } else if (fragment instanceof HakkimizdaFragment) {
            return "Hakkımızda";
        } else if (fragment instanceof AirportFragment) {
            return "Mesafe Emisyon";
        }
        return ""; // Varsayılan başlık
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateToolbarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setTitle(title);

        }
    }

}