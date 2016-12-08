package com.hackthefuture.florianzjef.loggingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hackthefuture.florianzjef.loggingapp.R;
import com.hackthefuture.florianzjef.loggingapp.fragments.SamplesFragment;
import com.hackthefuture.florianzjef.loggingapp.fragments.NewSampleFragment;
import com.hackthefuture.florianzjef.loggingapp.fragments.OnFragmentInteractionListener;
import com.hackthefuture.florianzjef.loggingapp.repo.Repository;
import com.hackthefuture.florianzjef.loggingapp.session.UserSessionManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener{

    SamplesFragment samplesFragment;
    NewSampleFragment newSampleFragment;
    private FloatingActionButton fab;
    ActionBarDrawerToggle toggle;

    private UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        UserSessionManager sessionfromintent = i.getParcelableExtra("SESSION");
        if (sessionfromintent != null) {
            session = sessionfromintent;
        } else {
            session = new UserSessionManager(getApplicationContext());
        }

        if (!session.checkLogin()) {
            finish();
            return;
        }


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFABVisibility(false);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_bottom_right, R.anim.exit_to_top_left, R.anim.enter_from_top_left, R.anim.exit_to_bottom_right)
                        .replace(R.id.fragmentPane, NewSampleFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        samplesFragment = SamplesFragment.newInstance(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPane, samplesFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        setActionbarArrow(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (toggle.onOptionsItemSelected(item)) {
            return false;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        int id = item.getItemId();
        if(id == R.id.nav_profile){

        }else if (id == R.id.nav_my_samples) {
            if (samplesFragment == null || !samplesFragment.isVisible()) {
                samplesFragment = SamplesFragment.newInstance(false);
                transaction.replace(R.id.fragmentPane, samplesFragment);
                transaction.commit();
            }else{
                samplesFragment.reloadSamples(false);
            }
        }else if (id == R.id.nav_samples) {
            if (samplesFragment == null || !samplesFragment.isVisible()) {
                samplesFragment = SamplesFragment.newInstance(true);
                transaction.replace(R.id.fragmentPane, samplesFragment);
                transaction.commit();
            }else{
                samplesFragment.reloadSamples(true);
            }

        } else if (id == R.id.nav_photos) {

        } else if (id == R.id.nav_stats) {

        }else if (id == R.id.nav_settings) {

        }else if (id == R.id.nav_logout) {
            Repository.logoutResearcher();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(OnFragmentInteractionListener.InteractedFragment interactedFragment, int pos) {

    }

    public void toggleFABVisibility(boolean isVisible){
        if(fab!=null) {
            fab.setVisibility(View.VISIBLE);
            if (isVisible) {
                fab.animate().alpha(1.0f);
                fab.animate().translationY(0);
                fab.setEnabled(true);
            } else {
                fab.animate().alpha(0.0f);
                fab.animate().translationY(fab.getHeight());
                fab.setEnabled(false);
            }
        }
    }

    public void setActionbarArrow(boolean isArrow){
        if(toggle!=null) {
            toggle.setDrawerIndicatorEnabled(!isArrow);
        }
    }
}
