package com.example.olio_harjoitusty;

import java.util.ArrayList;
import java.util.Collections;

public class Kilpailija implements Comparable {
    public String osakilpailu;
    public int positio_aika;
    public int positio_kisa;
    public int pisteet;
    public String nimi;
    public ArrayList<Double> kierrosajat_aika;
    public ArrayList<Double> kierrosajat_kisa;
    public String pvm;

    public Kilpailija(){
        //Needed for firebase
    }

    public Kilpailija(
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

    public Double getParasKierrosaika(){
        ArrayList<Double> kierrosajat_sorted = kierrosajat_aika;
        Collections.sort(kierrosajat_sorted);
        Double paras_aika = kierrosajat_sorted.get(0);
        return paras_aika;
    }

    public ArrayList<Double> getKierrosajat_aika(){
        return kierrosajat_aika;
    }

    @Override
    public int compareTo(Object o) {
        int comparePoints=((Kilpailija)o).getPositio_aika();
        /* For Ascending order*/
        return this.positio_aika-comparePoints;

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }
}
