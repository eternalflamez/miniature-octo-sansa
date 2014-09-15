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

        Object[] uniekwoorden = getUnique();
        ArrayList<Pair> aantal = new ArrayList();
        HashMap aantallen = new HashMap();

        for (Object s : uniekwoorden) {
            aantallen.put(s, 0);
        }
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
                return o1.getKey().compareTo(o2.getKey())*-1;
            }
            return o1.getValue().compareTo(o2.getValue());
        }
    }

    

    @FXML
    private void concordatieAction(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Object[] getUnique() {
        HashMap uniqKeys = new HashMap();
        String[] woorden = splitWords();
        for (int i = 0; i < woorden.length; i++) {
            uniqKeys.put(woorden[i], i);
        }
        return uniqKeys.keySet().toArray();
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

