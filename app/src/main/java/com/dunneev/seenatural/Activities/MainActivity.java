package com.dunneev.seenatural.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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


        ConfigureBottomNavigationBar(navController);


    }

    private void ConfigureBottomNavigationBar(NavController navController) {
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

    public void hideSystemUI(){
        View decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

    }

    public void showSystemUI(){

        // Shows the system bars by removing all the flags
        // except for the ones that make the content appear under the system bars.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void hideAppNavigation(){

    }

    public void showAppNavigation(){

    }

    public void hideAppBar(){

    }

    public void showAppBar(){

    }
}