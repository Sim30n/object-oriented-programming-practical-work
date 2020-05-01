package com.example.olio_harjoitusty;

import java.util.ArrayList;

public class Circuit {

    public boolean ajettu;
    public String nimi;
    public ArrayList<String> osallistujat;
    public String pvm;

    public Circuit(){

    }

    public Circuit(boolean ajettu, String nimi, ArrayList<String> osallistujat, String pvm){
        this.ajettu = ajettu;
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

}
