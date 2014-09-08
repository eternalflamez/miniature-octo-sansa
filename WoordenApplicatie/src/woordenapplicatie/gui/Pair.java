/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package woordenapplicatie.gui;


/**
 *
 * @author tim
 */
public class Pair implements Comparable{

    public Pair(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
    String key;
    Integer value;


    @Override
    public int compareTo(Object o) {   
        return this.value.compareTo( ((Pair)o).getValue());
    }
    
    
}
