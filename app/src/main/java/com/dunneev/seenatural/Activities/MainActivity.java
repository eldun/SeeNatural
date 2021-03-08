package com.dunneev.seenatural.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.dunneev.seenatural.Utilities.ScreenManager;
import com.dunneev.seenatural.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnSystemUiVisibilityChangeListener {
    private final String LOG_TAG = this.getClass().getSimpleName();


    public SharedPreferences sharedPreferences;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;


    public interface onWindowFocusChangedListener {
        void onWindowFocusChanged(boolean hasFocus);
    }

    private onWindowFocusChangedListener onWindowFocusChangedListener = null;

    public void addOnWindowFocusChangedListener(onWindowFocusChangedListener listener) {
        this.onWindowFocusChangedListener = listener;
    }

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
                        .findFragmentById(R.id.mainContentContainerView);
        NavController navController = navHostFragment.getNavController();


//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.reading_setup_fragment);
        topLevelDestinations.add(R.id.piano_setup_fragment);
        topLevelDestinations.add(R.id.theory_fragment);
        topLevelDestinations.add(R.id.settingsFragment);

        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        ConfigureBottomNavigationBar(navController);

        binding.getRoot().setOnSystemUiVisibilityChangeListener(this);


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
                        navController.navigate(R.id.theory_fragment);
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
        NavController navController = Navigation.findNavController(this, R.id.mainContentContainerView);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void hideAppNavigation() {
        binding.bottomNavigationBar.setVisibility(View.GONE);
    }

    public void showAppNavigation() {
        binding.bottomNavigationBar.setVisibility(View.VISIBLE);
    }

    public void hideAppBar() {
        binding.appBarLayout.setVisibility(View.GONE);
    }

    public void showAppBar() {
        binding.appBarLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onSystemUiVisibilityChange(int visibility) {
        Log.i(LOG_TAG, "systemUiVisChange");

        if ((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0) {
            hideAppBar();
            hideAppNavigation();
        } else {
            showAppBar();
            showAppNavigation();
        }
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (onWindowFocusChangedListener != null) {
            onWindowFocusChangedListener.onWindowFocusChanged(hasFocus);
        }

        if (hasFocus) {

        }
        else {

        }


    }


}