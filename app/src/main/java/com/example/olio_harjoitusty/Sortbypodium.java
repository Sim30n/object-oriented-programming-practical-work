package com.example.olio_harjoitusty;

import java.util.Comparator;

/*Sort list by the qualification result.*/
public class Sortbypodium implements Comparator<Driver> {

    public int compare(Driver a, Driver b){
        return a.positio_aika - b.positio_aika;
    }

}
