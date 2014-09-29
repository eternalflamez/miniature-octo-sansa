/*  1:   */ package stamboom.console;
/*  2:   */ 
/*  3:   */ public enum MenuItem
/*  4:   */ {
/*  5: 5 */   EXIT("exit"),  NEW_PERS("registreer persoon"),  NEW_ONGEHUWD_GEZIN("registreer ongehuwd gezin"),  NEW_HUWELIJK("registreer huwelijk"),  SCHEIDING("registreer scheiding"),  SHOW_PERS("toon gegevens persoon"),  SHOW_GEZIN("toon gegevens gezin"),  LOAD("haal gegevens op uit een bestand"),  SAVE("sla gegevens op in een bestand"),  SHOW_STAMBOOM("toon stamboom persoon");
/*  6:   */   
/*  7:   */   private final String omschr;
/*  8:   */   
/*  9:   */   private MenuItem(String omschr)
/* 10:   */   {
/* 11:22 */     this.omschr = omschr;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getOmschr()
/* 15:   */   {
/* 16:30 */     return this.omschr;
/* 17:   */   }
/* 18:   */ }


/* Location:           D:\downloads\StamboomGUI.jar
 * Qualified Name:     stamboom.console.MenuItem
 * JD-Core Version:    0.7.0.1
 */