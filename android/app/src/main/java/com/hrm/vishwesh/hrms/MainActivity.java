package com.hrm.vishwesh.hrms;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity /*implements home.OnFragmentInteractionListener,salary.OnSalaryFragmentInteractionListener*/ {
    private DrawerLayout myDrawer;
     private    Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle nvdrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_login_activity);
        toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDrawer=(DrawerLayout)findViewById(R.id.myDrawer);
        nvdrawer=setupDrawerToggle();
        myDrawer.addDrawerListener(nvdrawer);

        navigationView=(NavigationView)findViewById(R.id.nvView);
        setupDrawerContent(navigationView);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, myDrawer, toolbar, R.string.open,  R.string.close);
    }

    //navigation drawer handeling
       public void setupDrawerContent(NavigationView navigationView){
         navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(MenuItem menuItem) {
                 selectDrawerItem(menuItem);
                 return true;

             }
         });
       }
      //selectDrawerItem
       public void selectDrawerItem(MenuItem menuItem){
           Fragment fragment= null;
           Class fragmentClass;

           switch (menuItem.getItemId()){
               case R.id.home:
                     fragmentClass=home.class;
                   break;
               case R.id.leave_status:
                   fragmentClass= leave_status.class;
                   break;
               case R.id.salary:
                    fragmentClass= salary.class;
                    break;
               case R.id.attendance:
                     fragmentClass=attendance.class;
                     break;
               default:
                      fragmentClass=home.class;
           }
           try{
                fragment=(Fragment)fragmentClass.newInstance();
           }catch (Exception e){
                e.printStackTrace();
           }

           android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
           ft.replace(R.id.flContent,fragment);
           ft.commit();
           menuItem.setChecked(true);
           // Set action bar title
           setTitle(menuItem.getTitle());
           // Close the navigation drawer
           myDrawer.closeDrawers();

       }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        nvdrawer.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        nvdrawer.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()){
             case android.R.id.home:
                  myDrawer.openDrawer(GravityCompat.START);
                  return true;

         }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }
//
//    @Override
//    public void onSalaryFragmentInteraction(Uri uri) {
//        //
//    }
}
