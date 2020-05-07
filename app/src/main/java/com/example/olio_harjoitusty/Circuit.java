package com.example.olio_harjoitusty;

import java.util.ArrayList;

public class Circuit {

    public boolean ajettu;
    public String nimi;
    public ArrayList<String> osallistujat;
    public String pvm;
    public String info;
    String i_d;
    ArrayList<String> kommentit = new ArrayList<String>();

    public Circuit(){
        //Needed for firebase
    }

    // Constructor with necessary information about the circuit, variables in finnish language.
    public Circuit(boolean ajettu, String info, ArrayList<String> kommentit, String nimi,
                   ArrayList<String> osallistujat, String pvm){
        this.ajettu = ajettu;
        this.info = info;
        this.nimi = nimi;
        this.osallistujat = osallistujat;
        this.pvm = pvm;
        this.kommentit = kommentit;
        i_d = "";
    }

    public ArrayList<String> getKommentit() { return kommentit; }
    public String getName(){
        return nimi;
    }
    public ArrayList<String> getPartisipants(){
        return osallistujat;
    }
    public String getPvm(){
        return pvm;
    }
    public boolean isAjettu(){ return ajettu;}
    public String getInfo() { return info; }
    public void setI_d(String i_d) {
        this.i_d = i_d;
    }
    public String getI_d() {
        return i_d;
    }
}
