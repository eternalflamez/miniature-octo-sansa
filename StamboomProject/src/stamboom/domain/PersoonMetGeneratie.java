/*  1:   */ package stamboom.domain;
/*  2:   */ 
/*  3:   */ public class PersoonMetGeneratie
/*  4:   */ {
/*  5:   */   private final String persoonsgegevens;
/*  6:   */   private final int generatie;
/*  7:   */   
/*  8:   */   public PersoonMetGeneratie(String tekst, int generatie)
/*  9:   */   {
/* 10:10 */     this.persoonsgegevens = tekst;
/* 11:11 */     this.generatie = generatie;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public int getGeneratie()
/* 15:   */   {
/* 16:16 */     return this.generatie;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getPersoonsgegevens()
/* 20:   */   {
/* 21:20 */     return this.persoonsgegevens;
/* 22:   */   }
/* 23:   */ }


/* Location:           D:\downloads\StamboomGUI.jar
 * Qualified Name:     stamboom.domain.PersoonMetGeneratie
 * JD-Core Version:    0.7.0.1
 */