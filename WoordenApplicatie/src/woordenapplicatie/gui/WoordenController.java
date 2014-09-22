package woordenapplicatie.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.lang.reflect.Array;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class WoordenController implements Initializable {

    private static final String DEFAULT_TEXT = "Een, twee, drie, vier\n"
            + "Hoedje van, hoedje van\n"
            + "Een, twee, drie, vier\n"
            + "Hoedje van papier\n"
            + "\n"
            + "Heb je dan geen hoedje meer\n"
            + "Maak er één van bordpapier\n"
            + "Eén, twee, drie, vier\n"
            + "Hoedje van papier\n"
            + "\n"
            + "Een, twee, drie, vier\n"
            + "Hoedje van, hoedje van\n"
            + "Een, twee, drie, vier\n"
            + "Hoedje van papier\n"
            + "\n"
            + "En als het hoedje dan niet past\n"
            + "Zetten we 't in de glazenkas\n"
            + "Een, twee, drie, vier\n"
            + "Hoedje van papier";

    @FXML
    private Button btAantal;
    @FXML
    private TextArea taInput;
    @FXML
    private Button btSorteer;
    @FXML
    private Button btFrequentie;
    @FXML
    private Button btConcordantie;
    @FXML
    private TextArea taOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taInput.setText(DEFAULT_TEXT);
    }

    @FXML
    private void aantalAction(ActionEvent event) {
        String[] words = splitWords();

        String woordenText = "Totaal aantal woorden: \t\t\t" + words.length + "\n";

        HashMap hm = new HashMap();

        for (int i = 0; i < words.length; i++) {
            hm.put(words[i], i);
        }

        woordenText += "Aantal verschillende woorden: \t" + hm.size();

        taOutput.setText(woordenText);
    }

    @FXML
    private void sorteerAction(ActionEvent event) {
        String[] words = splitWords();
        HashMap hm = new HashMap();

        for (int i = 0; i < words.length; i++) {
            hm.put(words[i], i);
        }

        Object[] uniqueWords = hm.keySet().toArray();
        Arrays.sort(uniqueWords);

        String output = "";

        for (int i = uniqueWords.length - 1; i >= 0; i--) {
            output += uniqueWords[i] + "\n";
        }

        taOutput.setText(output);
    }

    @FXML
    private void frequentieAction(ActionEvent event) {
        
        HashMap aantallen = getUnique();

        String[] woorden = splitWords();
        
        for(String s:woorden){
            int nummer  =  (int) aantallen.get(s);
            aantallen.replace(s,nummer+1);
        }        

        String output = "";

        ArrayList<Entry<String,Integer>> totaal2 = new ArrayList<Entry<String,Integer>>();
        totaal2.addAll( aantallen.entrySet());
        
        Collections.sort(totaal2,new ValueComparator());
        Collections.reverse(totaal2);
        for (Entry s : totaal2) {

            output += s.getKey() + " " + s.getValue() + "\n";
        }

        taOutput.setText(output);

    }
    class ValueComparator implements Comparator<Entry<String,Integer>> {   

        @Override
        public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
            if(o1.getValue().equals(o2.getValue())){
                return o2.getKey().compareTo(o1.getKey());
            }
            return o1.getValue().compareTo(o2.getValue());
        }
    }
    
    @FXML
    private void concordatieAction(ActionEvent event) 
    {
        String text = DEFAULT_TEXT.toLowerCase();
        String[] lines = text.split("\n");
        ArrayList<String[]> textArr = new ArrayList<String[]>();
        
        for(int i = 0; i < lines.length; i++)
        {
            String[] split = lines[i].split(" ");
            if(split.length != 1)
            {
                textArr.add(split);
            }
        }
        
        Map<String, ArrayList<Integer>> multiMap = new HashMap<String, ArrayList<Integer>>();
        
        for (int i = 0; i < textArr.size(); i++) 
        {
            for (int j = 0; j < textArr.get(i).length; j++) 
            {
                ArrayList<Integer> old = multiMap.get(textArr.get(i)[j]);
                
                if(old == null)
                {
                    old = new ArrayList<Integer>();
                }
                
                old.add(i);
                multiMap.put(textArr.get(i)[j], old);
            }
        }
        
        String output = "";
        Iterator it = multiMap.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pairs = (Map.Entry)it.next();
            output += pairs.getKey() + " [";
            
            ArrayList<Integer> values = (ArrayList<Integer>)pairs.getValue();
            int c = 0;
            for(int i : values)
            {
                if(c != 0)
                {
                    output += ", ";
                }
                c++;
                
                output += i;
            }
            
            output += "] \n";
        }
        
        taOutput.setText(output);
    }

    private HashMap getUnique() {
        HashMap uniqKeys = new HashMap();
        String[] woorden = splitWords();
        for (int i = 0; i < woorden.length; i++) {
            uniqKeys.put(woorden[i], 0);
        }
        return uniqKeys;
    }

    /**
     * Filters all the words and puts them in an array.
     *
     * @return An array containing all the words from the text.
     */
    private String[] splitWords() {
        String stripped = DEFAULT_TEXT.replace("\n", " ");
        stripped = stripped.replace("  ", " ");
        stripped = stripped.replace(",", "");
        stripped = stripped.toLowerCase();

        String[] words = stripped.split(" ");
        return words;
    }
}

