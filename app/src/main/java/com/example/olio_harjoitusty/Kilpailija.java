package com.example.olio_harjoitusty;

import java.util.ArrayList;

public class Kilpailija implements Comparable {
    private Integer positio;
    private String nimi;
    private Double parasKierrosaika;
    private ArrayList<Double> kierrosajat;

    public Kilpailija(int positio, String nimi, Double kierrosaika, ArrayList<Double> kierrosajat){
        this.positio = positio;
        this.nimi = nimi;
        this.parasKierrosaika = kierrosaika;
        this.kierrosajat = kierrosajat;
    }

    public Integer getPositio(){
        return positio;
    }

    public String getNimi(){
        return nimi;
    }

    public Double getParasKierrosaika(){
        return parasKierrosaika;
    }
    public ArrayList<Double> getKierrosajat(){
        return kierrosajat;
    }


    @Override
    public int compareTo(Object o) {
        int comparePos=((Kilpailija)o).getPositio();
        /* For Ascending order*/
        return this.positio-comparePos;

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }
}
