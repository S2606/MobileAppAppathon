package com.example.shagun.criminalapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shagun.criminalapp.CriminalDetailsActivity;
import com.example.shagun.criminalapp.R;
import com.example.shagun.criminalapp.model.Criminal;

import java.util.List;

/**
 * Created by shagun on 6/11/17.
 */

public class CriminalAdapter extends RecyclerView.Adapter<CriminalAdapter.CriminalView>{

        List<Criminal> list;
        Context context;
        String[] colorarray;

public CriminalAdapter(List<Criminal> list,String[]f, Context context) {
        this.list = list;
        this.colorarray=f;
        this.context = context;
        }

@Override
public CriminalView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.criminal_activity_list,parent,false);
        CriminalView criminalView=new CriminalView(view);
        return criminalView;
        }

@Override
public void onBindViewHolder(CriminalView holder, int position) {
final Criminal criminal=list.get(position);
        holder.a.setText(criminal.getPolitician_Name());
        holder.d.setText(criminal.getLocation());
        //holder.b.setText(Criminal.get);
        if(criminal.getCorrupton_level()==0)
        {
                holder.c.setBackgroundColor(Color.parseColor(colorarray[0]));
                holder.c.setText(Integer.toString(criminal.getCorrupton_level()));
                holder.b.setText("NOT CORRUPT");
                holder.b.setBackgroundColor(Color.parseColor("#1b9610"));
        }
        else if(criminal.getCorrupton_level()==1)
        {
        holder.c.setBackgroundColor(Color.parseColor(colorarray[0]));
        holder.c.setText(Integer.toString(criminal.getCorrupton_level()));
        holder.b.setText("NOT CORRUPT");
        holder.b.setBackgroundColor(Color.parseColor("#1b9610"));
        }
        else if(criminal.getCorrupton_level()==2)
        {
        holder.c.setBackgroundColor(Color.parseColor(colorarray[1]));
        holder.c.setText(Integer.toString(criminal.getCorrupton_level()));
        holder.b.setText("NOT CORRUPT");
        holder.b.setBackgroundColor(Color.parseColor("#1b9610"));
        }
        else if(criminal.getCorrupton_level()==3)
        {
        holder.c.setBackgroundColor(Color.parseColor(colorarray[2]));
        holder.c.setText(Integer.toString(criminal.getCorrupton_level()));
        holder.b.setText("NOT CORRUPT");
        holder.b.setBackgroundColor(Color.parseColor("#1b9610"));
        }
        else if(criminal.getCorrupton_level()==4)
        {
        holder.c.setBackgroundColor(Color.parseColor(colorarray[3]));
        holder.c.setText(Integer.toString(criminal.getCorrupton_level()));
        holder.b.setText("NOT CORRUPT");
        holder.b.setBackgroundColor(Color.parseColor("#1b9610"));
        }
        else if(criminal.getCorrupton_level()==5)
        {
        holder.c.setBackgroundColor(Color.parseColor(colorarray[4]));
        holder.c.setText(criminal.getCorrupton_level());
        holder.b.setText("NOT CORRUPT");
        holder.b.setBackgroundColor(Color.parseColor("#1b9610"));
        }
        else if(criminal.getCorrupton_level()==6)
        {
        holder.c.setBackgroundColor(Color.parseColor(colorarray[5]));
        holder.c.setText(Integer.toString(criminal.getCorrupton_level()));
        }
        else if(criminal.getCorrupton_level()==7)
        {
        holder.c.setBackgroundColor(Color.parseColor(colorarray[6]));
        holder.c.setText(Integer.toString(criminal.getCorrupton_level()));
        }
        else if(criminal.getCorrupton_level()==8)
        {
        holder.c.setBackgroundColor(Color.parseColor(colorarray[7]));
        holder.c.setText(Integer.toString(criminal.getCorrupton_level()));
        }
        else if(criminal.getCorrupton_level()==9)
        {
        holder.c.setBackgroundColor(Color.parseColor(colorarray[8]));
        holder.c.setText(Integer.toString(criminal.getCorrupton_level()));
        }
        else if(criminal.getCorrupton_level()==10)
        {
        holder.c.setBackgroundColor(Color.parseColor(colorarray[9]));
        holder.c.setText(Integer.toString(criminal.getCorrupton_level()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent i=new Intent(context, CriminalDetailsActivity.class);
        i.putExtra("details",criminal);
        context.startActivity(i);
        }
        });

        }

@Override
public int getItemCount() {
        return list.size();
        }

public class CriminalView extends RecyclerView.ViewHolder{
    TextView a,c,d;
    Button b;


    public CriminalView(View itemView) {
        super(itemView);
        a=(TextView)itemView.findViewById(R.id.cr_name);
        b=(Button) itemView.findViewById(R.id.isornotcorrupt);
        c=(TextView)itemView.findViewById(R.id.danger);
        d=(TextView)itemView.findViewById(R.id.location);

    }
}

    public void updateList(List<Criminal> data) {
        list = data;
        notifyDataSetChanged();
    }
}
