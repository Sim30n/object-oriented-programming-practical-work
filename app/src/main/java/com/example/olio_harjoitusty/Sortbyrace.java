package com.example.olio_harjoitusty;

import java.util.Comparator;

/*Sort list by the race result.*/
public class Sortbyrace implements Comparator<Driver> {

    public int compare(Driver a, Driver b){
        return a.positio_kisa - b.positio_kisa;
    }

}
