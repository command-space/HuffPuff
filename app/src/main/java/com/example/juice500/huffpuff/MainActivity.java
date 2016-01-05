package com.example.juice500.huffpuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements MainActivityFolderFragment.OnFragmentInteractionListener,
        MainActivityImageFragment.OnFragmentInteractionListener,
        MainActivitySingleFragment.OnFragmentInteractionListener {
    private ArrayList<ImageItem> folderItems;
    private ArrayList<ImageItem> imageItems;
    private HuffDatabase huffDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.huffDatabase = HuffDatabase.getInstance(MainActivity.this);
        this.folderItems = this.huffDatabase.getFolderList();

        MainActivityFolderFragment folderFragment = MainActivityFolderFragment.newInstance(this.folderItems);
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, folderFragment);
        fragmentTransaction.commit();
    }

    public void onFolderSelected(String folderName) {
        this.imageItems = this.huffDatabase.getImageList(folderName);
        MainActivityImageFragment imageFragment = MainActivityImageFragment.newInstance(this.imageItems);

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out, android.R.anim.slide_in_left, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container, imageFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onImageSelected(String imagePath, boolean isHuff) {
        MainActivitySingleFragment singleFragment = MainActivitySingleFragment.newInstance(imagePath, isHuff);

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container, singleFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
