package com.dunneev.seenatural.Utilities;

import android.app.Activity;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.dunneev.seenatural.Activities.MainActivity;

public final class ScreenManager {


    private static Handler handler = new Handler();

    private static Runnable navHider = new Runnable() {
        private Activity activity;
        @Override
        public void run() {
            enterFullscreen((MainActivity) activity);
        }
    };

    public static final Handler getHandler() {
        return handler;
    }



    public static void enterFullscreen(MainActivity activity) {

        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

    }

    /**
     * @param activity
     * @param delay ms to wait before entering fullscreen
     */
    public static void enterFullscreen(MainActivity activity, long delay) {

        handler.postDelayed(() -> enterFullscreen(activity), delay);

    }

    public static void exitFullscreenTemporarily(MainActivity activity, long delay) {
        exitFullscreen(activity);

        enterFullscreen(activity, delay);
    }

    public static void exitFullscreen(MainActivity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    }


}
