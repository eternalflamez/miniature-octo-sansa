/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Sander
 */
public class Main {
    public String alphabet = "abcdefghijklmnopqrstuvwxyz 1234567890";
    public String testString = "bananenvla";
    
    public Main()
    {
        // Maak random string
        //CreateRandomString();
        
        ArrayList<Map.Entry<String,Integer>> count = CountCharacters();
        
        // Voor debug only: Display alle counts van iedere letter.
        System.out.println("Frequenties:");
        for (int i = 0; i < count.size(); i++) 
        {
            System.out.println(count.get(i).getKey() + " " + count.get(i).getValue());
        }
        
        ArrayList<Knoop> knopen = new ArrayList<>();
        
        for (int i = 0; i < count.size(); i++) 
        {
            Knoop k = new Knoop(count.get(i).getValue(), count.get(i).getKey());
            knopen.add(k);
        }
        
        System.out.println("Knooppunt-frequenties:");
        ArrayList<Map.Entry<String,String>> coderingen = CreateIndexArray(GroeiBoom(knopen));
        
        for (int i = 0; i < coderingen.size(); i++) 
        {
            testString = testString.replaceAll(coderingen.get(i).getKey(), coderingen.get(i).getValue());
        }
        
        System.out.println("Gecodeerde string:");
        System.out.println(testString);
        System.out.println("\r");
        
        String decoded = Decode(testString, coderingen);
        System.out.println("Decoded string: " + decoded);
    }
    
    public void CreateRandomString()
    {
        for (int i = 0; i < 1000; i++) {
            Random r = new Random();
            
            testString += alphabet.charAt(r.nextInt(alphabet.length()));
        }
    }
    
    /**
     * Counts all the characters in the testword.
     * @return A list with the length of the alphabet variable, with as value the amount of times the letter was found.
     * Ex: "aap" -> { [0: 2], [1 : 0], [ 2 : 0] ... etc until the index for "p", which has a value of 1.
     */
    public ArrayList<Map.Entry<String,Integer>> CountCharacters()
    {
        ArrayList<Map.Entry<String,Integer>> keyvaluepair = new ArrayList<Map.Entry<String,Integer>>();
        
        testString = testString.toLowerCase();
        
        for (int i = 0; i < alphabet.length(); i++) 
        {
            String letter = Character.toString(alphabet.charAt(i));
            int instancesRemoved = testString.length() - testString.replaceAll(letter, "").length();
            
            if(instancesRemoved != 0)
            {
                keyvaluepair.add(new WoordPaar<>(letter, instancesRemoved));
            }
        }
        
        // Sorteerfunctie.
        Collections.sort(keyvaluepair,new ValueComparator());
        
        return keyvaluepair;
    }
    
    public Knoop GroeiBoom(ArrayList<Knoop> knopen)
    {
        if(knopen.size() > 1)
        {
            Collections.sort(knopen);
            
            String line = "";
            
            for (int i = 0; i < knopen.size(); i++) {
                line += knopen.get(i).Frequentie + " ";
            }
            
            System.out.println(line);

            // Maak nieuwe knoop
            Knoop samen = new Knoop(knopen.get(0), knopen.get(1));
            
            // Vervang de 1e 2 door deze nieuwe knoop.
            knopen.remove(0);
            knopen.remove(0);
            knopen.add(0, samen);
            
            GroeiBoom(knopen);
        }
        
        return knopen.get(0);
    }
    
    class ValueComparator implements Comparator<Map.Entry<String,Integer>> {   
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            if(o1.getValue().equals(o2.getValue())){
                return o2.getKey().compareTo(o1.getKey());
            }
            return o1.getValue().compareTo(o2.getValue());
        }
    }
    
    public ArrayList<Map.Entry<String,String>> CreateIndexArray(Knoop boom)
    {
        Boolean done = false;
        String code = "";
        Knoop current = boom;
        ArrayList<Map.Entry<String, String>> indexes = new ArrayList<>();
        
        System.out.println("Code per waarde:");
         
        while(!done)
        {
             // uncomment voor displayen van de codes.
            if(current.Waarde != null)
            {
                System.out.println(code + ": " + current.Waarde);
            }
                    
            
            current.transversed = true;
            
            // Links bestaat?
            if(current.Links != null)
            {
                // Zijn we al bij links geweest?
                if(!current.Links.transversed)
                {
                    // Oke, ga naar links.
                    current.Links.parent = current;
                    current = current.Links;
                    code += "0";
                    continue;
                }
            }
        
            // Rechts bestaat?
           if(current.Rechts != null)
           {
               //Zijn we al bij rechts geweest?
               if(!current.Rechts.transversed)
               {
                   // Oke, ga naar rechts.
                   current.Rechts.parent = current;
                   current = current.Rechts;
                   code += "1";
                   continue;
               }
           }
           
           // Oke, we hebben links en rechts gehad, of ze zijn er niet. 
           // even controleren of we weer bovenaan zijn.
           if(current != boom)
           {
                if(current.Waarde != null)
                {
                     indexes.add(new WoordPaar<>(current.Waarde, code));
                }

                code = code.substring(0, code.length()-1);
                current = current.parent;
           }
           else
           {
               done = true;
           }
        }
        
        return indexes;
    }
    
    public String Decode(String encoded, ArrayList<Map.Entry<String,String>> cheatsheet)
    {
        String result = "";
        char[] chars = encoded.toCharArray();
        String subString = "";

        for (int i = 0; i < chars.length; i++) 
        {
            subString += chars[i];

            String text = "";
            for (int j = 0; j < cheatsheet.size(); j++) 
            {
                if(cheatsheet.get(j).getValue().equals(subString))
                {
                    text = cheatsheet.get(j).getKey();
                }
            }

            if(text != "")
            {
                result += text; 
                subString = "";
            }
        }

        return result;
    }
}
