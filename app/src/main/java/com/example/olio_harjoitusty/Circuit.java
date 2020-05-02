package com.example.olio_harjoitusty;

import java.util.ArrayList;

public class Circuit {

    public boolean ajettu;
    public String nimi;
    public ArrayList<String> osallistujat;
    public String pvm;
    public String info;

    public Circuit(){

    }


    public Circuit(boolean ajettu, String info, String nimi, ArrayList<String> osallistujat, String pvm){
        this.ajettu = ajettu;
        this.info = info;
        this.nimi = nimi;
        this.osallistujat = osallistujat;
        this.pvm = pvm;
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

}
