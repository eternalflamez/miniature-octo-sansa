package woordenapplicatie.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.net.URL;
import java.util.ResourceBundle;
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
    
   private static final String DEFAULT_TEXT =   "Een, twee, drie, vier\n" +
                                                "Hoedje van, hoedje van\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier"; /*\n" +
                                                "\n" +
                                                "Heb je dan geen hoedje meer\n" +
                                                "Maak er één van bordpapier\n" +
                                                "Eén, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van, hoedje van\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "En als het hoedje dan niet past\n" +
                                                "Zetten we 't in de glazenkas\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier";*/
    
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
        int spaces = DEFAULT_TEXT.length() - DEFAULT_TEXT.replace(" ", "").length();
        
        String[] lines = DEFAULT_TEXT.split("\n");
        int enters = lines.length;
        
        int words = spaces + enters;
        
        String woordenText = "Totaal aantal woorden: \t\t\t" + words;
        
        taOutput.setText(woordenText);
        /*
        Integer[] numbers = {1, 1, 2, 1, 3, 4, 5};
        Set<Integer> uniqKeys = new TreeSet<Integer>();
        uniqKeys.addAll(Arrays.asList(numbers));
        System.out.println("uniqKeys: " + uniqKeys);
                */
    }

    @FXML
    private void sorteerAction(ActionEvent event) {
         throw new UnsupportedOperationException("Not supported yet."); 
    }

    @FXML
    private void frequentieAction(ActionEvent event) {
         throw new UnsupportedOperationException("Not supported yet."); 
    }

    @FXML
    private void concordatieAction(ActionEvent event) {
         throw new UnsupportedOperationException("Not supported yet."); 
    }
   
}
