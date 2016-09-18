package k25.datle.gymania.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import k25.datle.gymania.MainActivity;
import k25.datle.gymania.R;
import 	android.view.Window;
import 	android.view.WindowManager;

/**
 * Created by Nguyen on 9/10/2016.
 */

public class SplashActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_layout);
        StartLoadingPhase();
    }

    public void StartLoadingPhase() {
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    DataManager.GetInstance().Init(getApplicationContext());
                    sleep(1000);
                } catch (Exception e) {

                } finally {
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        splashThread.start();
    }
}
