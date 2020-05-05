package com.example.olio_harjoitusty;

import java.util.ArrayList;

public class Circuit {

    public boolean ajettu;
    public String nimi;
    public ArrayList<String> osallistujat;
    public String pvm;
    public String info;
    String i_d;

    public Circuit(){

    }


    public Circuit(boolean ajettu, String info, String nimi, ArrayList<String> osallistujat, String pvm){
        this.ajettu = ajettu;
        this.info = info;
        this.nimi = nimi;
        this.osallistujat = osallistujat;
        this.pvm = pvm;
        i_d = "";
    }


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
