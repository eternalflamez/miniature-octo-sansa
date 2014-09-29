/*  1:   */ package stamboom.util;
/*  2:   */ 
/*  3:   */ import java.util.Calendar;
/*  4:   */ import java.util.GregorianCalendar;
/*  5:   */ 
/*  6:   */ public class StringUtilities
/*  7:   */ {
/*  8:   */   static final String SPATIES = "                                                                  ";
/*  9:   */   
/* 10:   */   public static Calendar datum(String datumaanduiding)
/* 11:   */     throws IllegalArgumentException
/* 12:   */   {
/* 13:18 */     if (datumaanduiding.isEmpty()) {
/* 14:19 */       return null;
/* 15:   */     }
/* 16:   */     try
/* 17:   */     {
/* 18:22 */       String[] datumSplit = datumaanduiding.split("-");
/* 19:23 */       if (datumSplit.length != 3) {
/* 20:24 */         throw new IllegalArgumentException("Datum bestaat niet uit drie delen");
/* 21:   */       }
/* 22:27 */       int dag = Integer.parseInt(datumSplit[0]);
/* 23:28 */       if ((dag <= 0) || (dag > 31)) {
/* 24:29 */         throw new IllegalArgumentException("Ongeldige dag");
/* 25:   */       }
/* 26:32 */       int maand = Integer.parseInt(datumSplit[1]);
/* 27:33 */       if ((maand <= 0) || (maand > 12)) {
/* 28:34 */         throw new IllegalArgumentException("Ongeldige maand");
/* 29:   */       }
/* 30:37 */       int jaar = Integer.parseInt(datumSplit[2]);
/* 31:   */       
/* 32:   */ 
/* 33:40 */       return new GregorianCalendar(jaar, maand - 1, dag);
/* 34:   */     }
/* 35:   */     catch (NumberFormatException e)
/* 36:   */     {
/* 37:42 */       throw new IllegalArgumentException("dag, maand of jaar heeft niet het juiste getalsformat");
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static String datumString(Calendar datum)
/* 42:   */   {
/* 43:47 */     return datum.get(5) + "-" + (datum.get(2) + 1) + "-" + datum.get(1);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public static String spaties(int nr)
/* 47:   */   {
/* 48:54 */     if (nr > "                                                                  ".length()) {
/* 49:55 */       return "                                                                  ";
/* 50:   */     }
/* 51:57 */     return "                                                                  ".substring(0, nr);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public static String withFirstCapital(String text)
/* 55:   */   {
/* 56:61 */     if (text.isEmpty()) {
/* 57:62 */       return text;
/* 58:   */     }
/* 59:64 */     return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
/* 60:   */   }
/* 61:   */ }


/* Location:           D:\downloads\StamboomGUI.jar
 * Qualified Name:     stamboom.util.StringUtilities
 * JD-Core Version:    0.7.0.1
 */