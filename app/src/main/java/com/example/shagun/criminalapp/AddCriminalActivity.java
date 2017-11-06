package com.example.shagun.criminalapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shagun.criminalapp.model.Criminal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

/**
 * Created by shagun on 6/11/17.
 */

public class AddCriminalActivity extends Activity {
    EditText obj;
    EditText obj2;
    EditText obj3;
    EditText obj4;
    DatePicker datePicker;
    Calendar calendar;
    TextView dateView;
    int year, month, day;
    CheckBox check1;
    Button btnsubmitsame, btnsubmit;
    Spinner sc;
    String url;
    String date;
    SeekBar sb;
    int level;

    Button chooseImg, uploadImg;
    ImageView imgView;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ProgressDialog pd;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://criminal-data.appspot.com");
    DatabaseReference df= FirebaseDatabase.getInstance().getReference("criminals");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_criminal);
        chooseImg = (Button)findViewById(R.id.chooseImg);

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        obj = (EditText) findViewById(R.id.edit1);
        obj3 = (EditText) findViewById(R.id.edit3);
        obj2= (EditText) findViewById(R.id.editcharges);
        sb=(SeekBar) findViewById(R.id.seekBar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                level=seekBar.getProgress();
                Log.d("data",Integer.toString(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        sc=(Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.department, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sc.setAdapter(adapter);

        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date=showDate(year, month+1, day);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {

                String spintext = sc.getSelectedItem().toString();
                if (obj.getText().toString().equals("")  || obj3.getText().toString().equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddCriminalActivity.this);

                    builder.setTitle("Fields Empty");
                    builder.setMessage("Please Enter all the Details Properly");
                    builder.setPositiveButton("Got it!!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(AddCriminalActivity.this, MainActivity.class);
                            startService(i);
                            Toast.makeText(AddCriminalActivity.this, "Add data", Toast.LENGTH_LONG).show();

                        }
                    });

//                   builder.setNegativeButton("cancel it", new DialogInterface.OnClickListener() {
//                       public void onClick(DialogInterface dialog, int which) {
//                           Intent i = new Intent(LayoutAct.this,LayoutAct.class);
//                           startService(i);
//                           Toast.makeText(LayoutAct.this, "Negative  Button", Toast.LENGTH_LONG).show();
//
//                       }
//                   });
                    builder.setCancelable(false);
                    builder.show();

                } else {
                    //Toast.makeText(AddCriminalActivity.this, "Page 1   " + obj.getText().toString(), Toast.LENGTH_LONG).show();
                    //Intent i = new Intent(AddCriminalActivity.this, MainActivity.class);
                    //i.putExtra("A", "Page   2  " + obj.getText().toString());
                    //startActivity(i);
                    if(filePath != null) {
                        pd.show();

                        SharedPreferences sh=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed=sh.edit();
//                String n= txt.getText().toString();
//                String x=n+"1";
//                Integer y= parseInt;
                        int val=sh.getInt("count2",0);
                        val++;
                        ed.putInt("count2",val);
                        ed.apply();
                        ed.commit();



                        int count=sh.getInt("count2",0);



                        StorageReference childRef = storageRef.child("image"+count+".jpg");

                        //uploading the image
                        UploadTask uploadTask = childRef.putFile(filePath);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                url= String.valueOf(taskSnapshot.getDownloadUrl());
                                pd.dismiss();
                                Toast.makeText(AddCriminalActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(AddCriminalActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                            }
                        });

                        df.child(Integer.toString(count)).setValue(new Criminal(obj.getText().toString(),spintext,obj2.getText().toString(),date,level,"Vellore", Integer.parseInt(obj3.getText().toString()),url));

                    }
                    else {
                        Toast.makeText(AddCriminalActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
                    }
                }





            }
        });

        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });


    }



    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private String showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        return new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year).toString();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                imgView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}