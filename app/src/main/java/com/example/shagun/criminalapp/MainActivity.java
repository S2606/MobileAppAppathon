package com.example.shagun.criminalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.shagun.criminalapp.adapter.CriminalAdapter;
import com.example.shagun.criminalapp.model.Criminal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference myRef ;
    CriminalAdapter recyclerViewAdapter;
    ProgressDialog progress;
    List<Criminal> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progress = new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setMessage("fetching criminal data!!");

        progress.show();
        myRef = FirebaseDatabase.getInstance().getReference("criminals");
        list=new ArrayList<Criminal>();
        final String[] colorarray=getResources().getStringArray(R.array.dangerColors);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Criminal criminal=dataSnapshot1.getValue(Criminal.class);
                    if (criminal != null) {
                        Log.d("ClassName",criminal.getPolitician_Name());
                        list.add(criminal);
                    }

                }
                progress.dismiss();
                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                recyclerViewAdapter=new CriminalAdapter(list,colorarray,MainActivity.this);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
                Log.d("ClassName","menu initialized");
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(recyclerViewAdapter);
                Log.d("ClassName","set adapter");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),AddCriminalActivity.class));
            }
        });
    }

    @Override
    public void onResume()
    {
        // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
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
        switch (item.getItemId()) {
            case R.id.submenu_level:
                //TODO: Implement the sort action
                Collections.sort(list, new Comparator<Criminal>() {
                    @Override
                    public int compare(Criminal o1, Criminal o2) {
                        return o2.getCorrupton_level()-o1.getCorrupton_level();
                    }
                });
                recyclerViewAdapter.updateList(list);
                return true;
            case R.id.action_settings:
                /*Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);*/
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}

