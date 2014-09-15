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
        
        for (int i = 0; i < words.length; i++) 
        {
            hm.put(words[i], i);
        }
        
        woordenText += "Aantal verschillende woorden: \t" + hm.size();

        taOutput.setText(woordenText);
    }

    @FXML
    private void sorteerAction(ActionEvent event) {
        String[] words = splitWords();
        HashMap hm = new HashMap();
        
        for (int i = 0; i < words.length; i++) 
        {
            hm.put(words[i], i);
        }
        
        Object[] uniqueWords = hm.keySet().toArray();
        Arrays.sort(uniqueWords);
        
        String output = "";
        
        for (int i = uniqueWords.length - 1; i >= 0; i--) 
        {
            output += uniqueWords[i] + "\n";
        }
        
        taOutput.setText(output);
    }

    @FXML
    private void frequentieAction(ActionEvent event) {
        Map totaal;
        totaal = new TreeMap();
        String[] uniekwoorden = getUnique();

        for (String s : uniekwoorden) {
            totaal.put(s, 0);
        }
        String[] woorden = splitWords();
        for (String s : woorden) {
            totaal.replace(s, (int) totaal.get(s) + 1);
        }
        String output = "";

        ArrayList<Pair> totaal2 = new ArrayList<Pair>();
        
        for (String s : uniekwoorden) {
            totaal2.add(new Pair(s, (Integer) totaal.get(s)));
        }
        Collections.sort(totaal2);
        
        for (Pair s : totaal2) {
            
            output += s.getKey() + " " + s.getValue() + "\n";
        }

        taOutput.setText(output);

    }

    class ValueComparator implements Comparator<String> {

    Map<String, Double> base;
    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}

    @FXML
    private void concordatieAction(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String[] getUnique() {
        Set<String> uniqKeys = new TreeSet<String>();
        uniqKeys.addAll(Arrays.asList(splitWords()));
        return uniqKeys.toArray(new String[0]);
    }

    /**
     * Filters all the words and puts them in an array.
     * @return An array containing all the words from the text.
     */
    private String[] splitWords() 
    {
        String stripped = DEFAULT_TEXT.replace("\n", " ");
        stripped = stripped.replace("  ", " ");
        stripped = stripped.replace(",", "");
        stripped = stripped.toLowerCase();

        String[] words = stripped.split(" ");
        return words;
    }
}
