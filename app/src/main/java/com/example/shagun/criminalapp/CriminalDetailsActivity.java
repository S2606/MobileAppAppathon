package com.example.shagun.criminalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shagun.criminalapp.model.Criminal;

/**
 * Created by shagun on 5/11/17.
 */

public class CriminalDetailsActivity extends AppCompatActivity{

    TextView politician_name,department,amount,allegations,cindex,location;
    ImageView loc;
    boolean islogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criminal_detail_activity);
        politician_name=(TextView) findViewById(R.id.pol_name);
        department=(TextView) findViewById(R.id.classify);
        amount=(TextView) findViewById(R.id.amount);
        allegations=(TextView) findViewById(R.id.charges);
        cindex=(TextView) findViewById(R.id.corruption_level);
        location=(TextView) findViewById(R.id.location);
        loc=(ImageView) findViewById(R.id.iv1);
        Intent intent = getIntent();
        final Criminal in = (Criminal) intent.getSerializableExtra("details");
        if(in!=null) {
            politician_name.setText(in.getPolitician_Name());
            department.setText(String.format(getString(R.string.classification), in.getPolitician_Organization()));
            amount.setText(String.format(getString(R.string.amount), Integer.toString(in.getAmount())));
            allegations.setText(in.getCharges());
            cindex.setText(Integer.toString(in.getCorrupton_level())+"/10");
            location.setText(in.getLocation());
            Glide.with(this).load(in.getUri()).into(loc);
            loc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(islogin)
                    {
                        islogin=false;
                        loc.setLayoutParams(new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                        loc.setAdjustViewBounds(true);
                    }else{
                        islogin=false;
                        loc.setLayoutParams(new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                        loc.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }
            });

        }



    }
}
