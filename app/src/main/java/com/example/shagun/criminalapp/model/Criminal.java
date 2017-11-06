package com.example.shagun.criminalapp.model;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by shagun on 6/11/17.
 */

public class Criminal implements Serializable {

    public Criminal(){}

    private static final String TAG = Criminal.class.getSimpleName();

    private String Politician_Name;

    private String Politician_Organization;

    private String charges;

    private String date;

    private int amount;

    private int Corrupton_level;

    private String location;

    private String uri;

    public Criminal(String a,String b,String c,String d,int e,String f,int g,String u)
    {
        this.Politician_Name=a;
        this.Politician_Organization=b;
        this.charges=c;
        this.date=d;
        this.Corrupton_level=e;
        this.location=f;
        this.amount=g;
        this.uri=u;
    }

    public String getPolitician_Name() {
        return Politician_Name;
    }

    public String getPolitician_Organization() {
        return Politician_Organization;
    }

    public String getCharges() {
        return charges;
    }

    public String getDate() {
        return date;
    }

    public int getCorrupton_level() {
        return Corrupton_level;
    }

    public String getLocation() {
        return location;
    }

    public int getAmount() {
        return amount;
    }

    public String getUri() {
        return uri;
    }

    public void setPolitician_Name(String politician_Name) {
        Politician_Name = politician_Name;
    }

    public void setPolitician_Organization(String politician_Organization) {
        Politician_Organization = politician_Organization;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public void setCorrupton_level(int corrupton_level) {
        Corrupton_level = corrupton_level;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
