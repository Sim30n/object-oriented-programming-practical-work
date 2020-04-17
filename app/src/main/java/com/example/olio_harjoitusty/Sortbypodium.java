package com.example.olio_harjoitusty;

import java.util.Comparator;

public class Sortbypodium implements Comparator<Kilpailija> {

    public int compare(Kilpailija a, Kilpailija b){
        return a.positio_aika - b.positio_aika;
    }

}
