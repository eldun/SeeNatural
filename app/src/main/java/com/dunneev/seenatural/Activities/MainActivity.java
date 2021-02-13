package com.dunneev.seenatural.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.dunneev.seenatural.R;
import com.dunneev.seenatural.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    public SharedPreferences sharedPreferences;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

//        When creating the NavHostFragment using FragmentContainerView or if manually
//        adding the NavHostFragment to your activity via a FragmentTransaction,
//        attempting to retrieve the NavController in onCreate() of an Activity via
//        Navigation.findNavController(Activity, @IdRes int) will fail.
//        You should retrieve the NavController directly from the NavHostFragment instead.
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_content_main);
        NavController navController = navHostFragment.getNavController();


//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        BottomNavigationView bottomNavigationView = binding.bottomNavigationBar;

        bottomNavigationView.getMenu().findItem(R.id.nav_sight_read).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.nav_sight_read:
                        navController.navigate(R.id.reading_nav_graph);
                        return true;

                    case R.id.nav_play:
                        navController.navigate(R.id.play_nav_graph);
                        return true;

                    case R.id.nav_theory:
                        navController.navigate(R.id.theoryFragment);
                        return true;

                    case R.id.nav_settings:
                        navController.navigate(R.id.settings_nav_graph);
                        return true;


                }

                return false;
            }

        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}