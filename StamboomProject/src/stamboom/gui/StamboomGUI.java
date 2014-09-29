/*  1:   */ package stamboom.gui;
/*  2:   */ 
/*  3:   */ import javafx.application.Application;
/*  4:   */ import javafx.fxml.FXMLLoader;
/*  5:   */ import javafx.scene.Parent;
/*  6:   */ import javafx.scene.Scene;
/*  7:   */ import javafx.stage.Stage;
/*  8:   */ 
/*  9:   */ public class StamboomGUI
/* 10:   */   extends Application
/* 11:   */ {
/* 12:   */   public void start(Stage stage)
/* 13:   */     throws Exception
/* 14:   */   {
/* 15:22 */     Parent root = (Parent)FXMLLoader.load(getClass().getResource("StamboomGUI.fxml"));
/* 16:   */     
/* 17:24 */     Scene scene = new Scene(root);
/* 18:25 */     stage.setScene(scene);
/* 19:26 */     stage.show();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static void main(String[] args)
/* 23:   */   {
/* 24:39 */     launch(args);
/* 25:   */   }
/* 26:   */ }


/* Location:           D:\downloads\StamboomGUI.jar
 * Qualified Name:     stamboom.gui.StamboomGUI
 * JD-Core Version:    0.7.0.1
 */