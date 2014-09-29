/*  1:   */ package stamboom.gui;
/*  2:   */ 
/*  3:   */ import javafx.collections.ObservableList;
/*  4:   */ import javafx.event.ActionEvent;
import javafx.event.Event;
/*  5:   */ import javafx.event.EventHandler;
/*  6:   */ import javafx.geometry.Insets;
/*  7:   */ import javafx.scene.Scene;
/*  8:   */ import javafx.scene.control.Button;
/*  9:   */ import javafx.scene.control.TextArea;
/* 10:   */ import javafx.scene.layout.AnchorPane;
/* 11:   */ import javafx.scene.layout.BorderPane;
/* 12:   */ import javafx.scene.layout.GridPane;
/* 13:   */ import javafx.scene.paint.Color;
/* 14:   */ import javafx.stage.Modality;
/* 15:   */ import javafx.stage.Stage;
/* 16:   */ 
/* 17:   */ public class Dialog
/* 18:   */   extends Stage
/* 19:   */ {
/* 20:   */   public Dialog(Stage owner, String header, String message)
/* 21:   */   {
/* 22:29 */     initOwner(owner);
/* 23:30 */     initModality(Modality.APPLICATION_MODAL);
/* 24:31 */     setTitle(header);
/* 25:   */     
/* 26:33 */     AnchorPane root = new AnchorPane();
/* 27:34 */     Scene scene = new Scene(root, 300.0D, 250.0D, Color.LIGHTSKYBLUE);
/* 28:35 */     setScene(scene);
/* 29:   */     
/* 30:37 */     GridPane gridPane = new GridPane();
/* 31:38 */     gridPane.setPadding(new Insets(20.0D));
/* 32:39 */     gridPane.setVgap(10.0D);
/* 33:40 */     root.getChildren().add(gridPane);
/* 34:41 */     AnchorPane.setBottomAnchor(gridPane, Double.valueOf(10.0D));
/* 35:42 */     AnchorPane.setTopAnchor(gridPane, Double.valueOf(10.0D));
/* 36:43 */     AnchorPane.setRightAnchor(gridPane, Double.valueOf(10.0D));
/* 37:44 */     AnchorPane.setLeftAnchor(gridPane, Double.valueOf(10.0D));
/* 38:   */     
/* 39:46 */     TextArea taMessage = new TextArea();
/* 40:47 */     taMessage.setText(message);
/* 41:48 */     taMessage.setWrapText(true);
/* 42:49 */     GridPane.setRowSpan(taMessage, Integer.valueOf(4));
/* 43:50 */     gridPane.add(taMessage, 0, 0);
/* 44:   */     
/* 45:52 */     Button btClose = new Button("Close");
/* 46:53 */     btClose.setOnAction(new EventHandler()
/* 47:   */     {
/* 48:   */       public void handle(Event event)
/* 49:   */       {
/* 50:56 */         Dialog.this.close();
/* 51:   */       }
/* 52:59 */     });
/* 53:60 */     BorderPane buttonRegion = new BorderPane();
/* 54:61 */     buttonRegion.setRight(btClose);
/* 55:62 */     gridPane.add(buttonRegion, 0, 4);
/* 56:   */   }
/* 57:   */ }



/* Location:           D:\downloads\StamboomGUI.jar

 * Qualified Name:     stamboom.gui.Dialog

 * JD-Core Version:    0.7.0.1

 */