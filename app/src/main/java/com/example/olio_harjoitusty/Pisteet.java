package com.example.olio_harjoitusty;

import java.util.ArrayList;

public class Pisteet {

    private String nimi;
    private int summa;

    ArrayList<Integer> pisteet = new ArrayList<Integer>();

    public Pisteet(String nimi){
        this.nimi = nimi;
    }

    public String getNimi(){
        return nimi;
    }

    public Integer summa(){
        for (int i: pisteet) {
            summa += i;
        }
        return summa;
    }

    public void addPoint(Integer piste){
        pisteet.add(piste);
    }
}
