/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication2;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Sander
 */
public class JavaApplication2 {
    static String alphabet = "abcdefghijklmnopqrstuvwxyz 1234567890";
    static String testString = "";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CreateRandomString();
        
        ArrayList<Integer> count = CountCharacters();
        for (int i = 0; i < count.size(); i++) {
            System.out.println(alphabet.charAt(i) + " " + count.get(i));
        }
    }
    
    private static void CreateRandomString()
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
    private static ArrayList<Integer> CountCharacters()
    {
        ArrayList<Integer> result = new ArrayList<Integer>();
        
        for (int i = 0; i < alphabet.length(); i++) 
        {
            result.add(0);
        }
        
        testString = testString.toLowerCase();
        
        for (int i = 0; i < alphabet.length(); i++) 
        {
            int instancesRemoved = testString.length() - testString.replaceAll(Character.toString(alphabet.charAt(i)), "").length();
            result.set(i, instancesRemoved);
        }
        
        return result;
    }
}
