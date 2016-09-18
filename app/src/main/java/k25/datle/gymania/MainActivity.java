package k25.datle.gymania;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.pm.Signature;

import com.facebook.FacebookSdk;

import k25.datle.gymania.Exercise.CardioExercise;
import k25.datle.gymania.Exercise.Exercise;
import k25.datle.gymania.Exercise.TrainingEvent;
import k25.datle.gymania.Fragment.PhotoFragment;
import k25.datle.gymania.Fragment.PracticeFragment;
import k25.datle.gymania.Fragment.ProfileFragment;
import k25.datle.gymania.Fragment.TimerFragment;
import k25.datle.gymania.Profile.Profile;
import k25.datle.gymania.Utils.SplashActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public class NavigationItem {
        public static final int PROFILE = 1;
        public static final int PRACTICE = 2;
        public static final int PHOTO = 3;

        public static final int SHARE = 4;
        public static final int SEND = 5;
    }

    private Fragment m_ProfileFragment;
    private FragmentManager m_FragmentManger;
    private DrawerLayout m_DrawerLayout;
    private ActionBarDrawerToggle m_ActionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView m_NavigationView;
    private TimerFragment m_TimerFragment;
    private PracticeFragment m_PracticeFragment;
    private PhotoFragment m_PhotoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SetupNavDrawer();

        m_ProfileFragment = new ProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, m_ProfileFragment);
        transaction.addToBackStack(null);

        transaction.commit();



        try {
            PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("HaskKey", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("HaskKey: " ,"" + e);
        } catch (Exception e) {
            Log.e("HaskKey", "printHashKey()", e);
        }

    }

    public void SetupNavDrawer() {
        m_DrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        m_ActionBarDrawerToggle = new ActionBarDrawerToggle(
                this, m_DrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        m_DrawerLayout.setDrawerListener(m_ActionBarDrawerToggle);
        m_ActionBarDrawerToggle.syncState();

        m_NavigationView = (NavigationView) findViewById(R.id.nav_view);
        m_NavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void OnNavItemSelected(int idx) {
        switch (idx) {
            case NavigationItem.PROFILE: {

            }
        }
    }

    public void DisplayProfileView() {
        if (m_ProfileFragment == null) {
            m_ProfileFragment = new PracticeFragment();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, m_ProfileFragment);
        transaction.addToBackStack(null);

        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void DisplayPracticeView() {
        if (m_PracticeFragment == null) {
            m_PracticeFragment = new PracticeFragment();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, m_PracticeFragment);
        transaction.addToBackStack(null);

        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void DisplayTimeCountDownView(Exercise ex) {
        if (m_TimerFragment == null || m_TimerFragment.IsDone()) {
            m_TimerFragment = new TimerFragment();
            TrainingEvent trainingEvent = new TrainingEvent(getApplicationContext(),ex);
            m_TimerFragment.InitTrainingEvent(trainingEvent);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, m_TimerFragment);
        transaction.addToBackStack(null);

        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment newFragment = new Fragment();

        if (id == R.id.nav_profile) {
            newFragment = new ProfileFragment();
        } else if (id == R.id.nav_practice) {
            if (m_TimerFragment!=null && !m_TimerFragment.IsDone()) {
                newFragment = m_TimerFragment;
            } else if (m_PracticeFragment == null) {
                m_PracticeFragment = new PracticeFragment();
                newFragment = m_PracticeFragment;
            } else {
                newFragment = m_PracticeFragment;
            }
        } else if (id == R.id.nav_photo) {

            if (m_PhotoFragment == null) {
                m_PhotoFragment = new PhotoFragment();
            }
            newFragment = m_PhotoFragment;
        } else if (id == R.id.nav_share) {
                //TODO : Facebook share
        } else if (id == R.id.nav_send) {

        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public PracticeFragment GetPracticeFragment() {
        return m_PracticeFragment;
    }

    public PhotoFragment GetPhotoFragment() {
        return m_PhotoFragment;
    }
}
