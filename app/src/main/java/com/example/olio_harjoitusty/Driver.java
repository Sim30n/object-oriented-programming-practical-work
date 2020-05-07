package com.example.olio_harjoitusty;

import java.util.ArrayList;
import java.util.Collections;

public class Driver {
    public String osakilpailu;
    public int positio_aika;
    public int positio_kisa;
    public int pisteet;
    public String nimi;
    public ArrayList<Double> kierrosajat_aika;
    public ArrayList<Double> kierrosajat_kisa;
    public String pvm;

    public Driver(){
        //Needed for firebase
    }

    // Driver constructor, variables in finnsih.
    public Driver(
                      ArrayList<Double> kierrosajat_aika,
                      ArrayList<Double> kierrosajat_kisa,
                      String nimi,
                      String osakilpailu,
                      int pisteet,
                      int positio_aika,
                      int positio_kisa,
                      String pvm){
        this.positio_aika = positio_aika;
        this.positio_kisa = positio_kisa;
        this.nimi = nimi;
        this.kierrosajat_aika = kierrosajat_aika;
        this.kierrosajat_kisa = kierrosajat_kisa;
        this.pisteet = pisteet;
        this.osakilpailu = osakilpailu;
        this.pvm = pvm;
    }

    public String getOsakilpailu(){return osakilpailu;}

    public int getPositio_kisa(){
        return positio_kisa;
    }

    public int getPositio_aika(){
        return positio_aika;
    }

    public String getNimi(){
        return nimi;
    }

    public long getPisteet(){
        return pisteet;
    }

    public String getPvm() {
        return pvm;
    }

    //  Method for best lap time in qualification.
    public Double getParasKierrosaika(){
        ArrayList<Double> kierrosajat_sorted = kierrosajat_aika;
        Collections.sort(kierrosajat_sorted);
        Double paras_aika = kierrosajat_sorted.get(0);
        return paras_aika;
    }

    // Method for best lap time in race.
    public Double getBestRacetime(){
        ArrayList<Double> kierrosajat_sorted = kierrosajat_kisa;
        Collections.sort(kierrosajat_sorted);
        Double paras_aika = kierrosajat_sorted.get(0);
        return paras_aika;
    }

    // Method for all the qualification lap times (not used).
    public ArrayList<Double> getKierrosajat_aika(){
        return kierrosajat_aika;
    }

}
