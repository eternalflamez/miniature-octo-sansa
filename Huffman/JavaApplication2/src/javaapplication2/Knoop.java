/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication2;

/**
 *
 * @author Sander
 */
public class Knoop implements Comparable {
    public Knoop Links;
    public Knoop Rechts;
    
    public String Waarde;
    public Integer Frequentie;
    
    public Knoop parent;
    public Boolean transversed = false;
    
    public Knoop(Integer frequentie, String waarde)
    {
        this.Waarde = waarde;
        this.Frequentie = frequentie;
    }
    
    public Knoop(Knoop links, Knoop rechts)
    {
        this.Links = links;
        this.Rechts = rechts;
        
        this.Frequentie = links.Frequentie + rechts.Frequentie;
    }

    @Override
    public int compareTo(Object o) {
        Knoop other = (Knoop) o;
        
        return this.Frequentie.compareTo(other.Frequentie);
    }
}
