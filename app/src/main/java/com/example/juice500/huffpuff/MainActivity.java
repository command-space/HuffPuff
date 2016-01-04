package com.example.juice500.huffpuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements MainActivityFolderFragment.OnFragmentInteractionListener {
    private ArrayList<ImageItem> imageItems;
    private HuffDatabase huffDatabase;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.huffDatabase = HuffDatabase.getInstance(MainActivity.this);
        this.imageItems = huffDatabase.getFolderList();

        MainActivityFolderFragment folderFragment = MainActivityFolderFragment.newInstance(imageItems);
        this.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        this.fragmentTransaction.add(R.id.fragment_container, folderFragment);
        this.fragmentTransaction.commit();
    }

    public void onFolderSelected(int position) {
        Log.d("ASDF",String.valueOf(position));
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
