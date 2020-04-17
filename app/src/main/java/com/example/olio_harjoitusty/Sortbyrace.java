package com.example.olio_harjoitusty;

import java.util.Comparator;

public class Sortbyrace implements Comparator<Kilpailija> {

    public int compare(Kilpailija a, Kilpailija b){
        return a.positio_kisa - b.positio_kisa;
    }

}
