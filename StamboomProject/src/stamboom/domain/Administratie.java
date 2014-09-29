/*   1:    */ package stamboom.domain;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Calendar;
/*   8:    */ import java.util.List;
/*   9:    */ import javafx.collections.FXCollections;
/*  10:    */ import javafx.collections.ObservableList;
/*  11:    */ 
/*  12:    */ public class Administratie
/*  13:    */   implements Serializable
/*  14:    */ {
/*  15:    */   private int nextGezinsNr;
/*  16:    */   private int nextPersNr;
/*  17:    */   private final List<Persoon> personen;
/*  18:    */   private final List<Gezin> gezinnen;
/*  19:    */   private transient ObservableList<Persoon> observablePersonen;
/*  20:    */   private transient ObservableList<Gezin> observableGezinnen;
/*  21:    */   
/*  22:    */   public Administratie()
/*  23:    */   {
/*  24: 36 */     this.nextGezinsNr = 1;
/*  25: 37 */     this.nextPersNr = 1;
/*  26: 38 */     this.personen = new ArrayList();
/*  27: 39 */     this.gezinnen = new ArrayList();
/*  28:    */     
/*  29:    */ 
/*  30: 42 */     this.observablePersonen = FXCollections.observableList(this.personen);
/*  31: 43 */     this.observableGezinnen = FXCollections.observableList(this.gezinnen);
/*  32:    */   }
/*  33:    */   
/*  34:    */   private void readObject(ObjectInputStream is)
/*  35:    */     throws IOException, ClassNotFoundException
/*  36:    */   {
/*  37: 49 */     is.defaultReadObject();
/*  38: 50 */     this.observablePersonen = FXCollections.observableList(this.personen);
/*  39: 51 */     this.observableGezinnen = FXCollections.observableList(this.gezinnen);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Persoon addPersoon(Geslacht geslacht, String[] vnamen, String anaam, String tvoegsel, Calendar gebdat, String gebplaats, Gezin ouderlijkGezin)
/*  43:    */   {
/*  44: 81 */     if (vnamen.length == 0) {
/*  45: 82 */       throw new IllegalArgumentException("ten minst 1 voornaam");
/*  46:    */     }
/*  47: 84 */     for (String voornaam : vnamen) {
/*  48: 85 */       if (voornaam.trim().isEmpty()) {
/*  49: 86 */         throw new IllegalArgumentException("lege voornaam is niet toegestaan");
/*  50:    */       }
/*  51:    */     }
/*  52: 90 */     if (anaam.trim().isEmpty()) {
/*  53: 91 */       throw new IllegalArgumentException("lege achternaam is niet toegestaan");
/*  54:    */     }
/*  55: 94 */     if (gebplaats.trim().isEmpty()) {
/*  56: 95 */       throw new IllegalArgumentException("lege geboorteplaats is niet toegestaan");
/*  57:    */     }
/*  58: 98 */     Persoon p = getPersoon(vnamen, anaam, tvoegsel, gebdat, gebplaats);
/*  59: 99 */     if (p != null) {
/*  60:100 */       return null;
/*  61:    */     }
/*  62:102 */     p = new Persoon(this.nextPersNr, vnamen, anaam, tvoegsel, gebdat, gebplaats, geslacht, ouderlijkGezin);
/*  63:    */     
/*  64:104 */     this.nextPersNr += 1;
/*  65:105 */     if (ouderlijkGezin != null) {
/*  66:106 */       ouderlijkGezin.breidUitMet(p);
/*  67:    */     }
/*  68:109 */     this.observablePersonen.add(p);
/*  69:110 */     return p;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Gezin addOngehuwdGezin(Persoon ouder1, Persoon ouder2)
/*  73:    */   {
/*  74:128 */     if (ouder1 == ouder2) {
/*  75:129 */       return null;
/*  76:    */     }
/*  77:132 */     Calendar nu = Calendar.getInstance();
/*  78:133 */     if ((ouder1.isGetrouwdOp(nu)) || ((ouder2 != null) && (ouder2.isGetrouwdOp(nu))) || (ongehuwdGezinBestaat(ouder1, ouder2))) {
/*  79:136 */       return null;
/*  80:    */     }
/*  81:139 */     Gezin gezin = new Gezin(this.nextGezinsNr, ouder1, ouder2);
/*  82:140 */     this.nextGezinsNr += 1;
/*  83:    */     
/*  84:142 */     this.observableGezinnen.add(gezin);
/*  85:    */     
/*  86:144 */     ouder1.wordtOuderIn(gezin);
/*  87:145 */     if (ouder2 != null) {
/*  88:146 */       ouder2.wordtOuderIn(gezin);
/*  89:    */     }
/*  90:149 */     return gezin;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setOuders(Persoon persoon, Gezin ouderlijkGezin)
/*  94:    */   {
/*  95:162 */     persoon.setOuders(ouderlijkGezin);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean setScheiding(Gezin gezin, Calendar datum)
/*  99:    */   {
/* 100:175 */     return gezin.setScheiding(datum);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean setHuwelijk(Gezin gezin, Calendar datum)
/* 104:    */   {
/* 105:188 */     return gezin.setHuwelijk(datum);
/* 106:    */   }
/* 107:    */   
/* 108:    */   boolean ongehuwdGezinBestaat(Persoon ouder1, Persoon ouder2)
/* 109:    */   {
/* 110:198 */     return ouder1.heeftOngehuwdGezinMet(ouder2) != null;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Gezin addHuwelijk(Persoon ouder1, Persoon ouder2, Calendar huwdatum)
/* 114:    */   {
/* 115:217 */     if (ouder1 == ouder2) {
/* 116:218 */       return null;
/* 117:    */     }
/* 118:220 */     if ((!ouder1.kanTrouwenOp(huwdatum)) || ((ouder2 != null) && (!ouder2.kanTrouwenOp(huwdatum)))) {
/* 119:222 */       return null;
/* 120:    */     }
/* 121:225 */     Gezin ongehuwdGezin = ouder1.heeftOngehuwdGezinMet(ouder2);
/* 122:226 */     if (ongehuwdGezin != null)
/* 123:    */     {
/* 124:227 */       ongehuwdGezin.setHuwelijk(huwdatum);
/* 125:228 */       return ongehuwdGezin;
/* 126:    */     }
/* 127:230 */     Gezin gezin = new Gezin(this.nextGezinsNr, ouder1, ouder2);
/* 128:231 */     ouder1.wordtOuderIn(gezin);
/* 129:232 */     if (ouder2 != null) {
/* 130:233 */       ouder2.wordtOuderIn(gezin);
/* 131:    */     }
/* 132:235 */     this.observableGezinnen.add(gezin);
/* 133:236 */     this.nextGezinsNr += 1;
/* 134:237 */     gezin.setHuwelijk(huwdatum);
/* 135:238 */     return gezin;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int aantalGeregistreerdePersonen()
/* 139:    */   {
/* 140:247 */     return this.nextPersNr - 1;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public int aantalGeregistreerdeGezinnen()
/* 144:    */   {
/* 145:255 */     return this.nextGezinsNr - 1;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Persoon getPersoon(int nr)
/* 149:    */   {
/* 150:267 */     if ((1 <= nr) && (nr <= this.personen.size())) {
/* 151:268 */       return (Persoon)this.personen.get(nr - 1);
/* 152:    */     }
/* 153:270 */     return null;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public ArrayList<Persoon> getPersonenMetAchternaam(String achternaam)
/* 157:    */   {
/* 158:280 */     ArrayList<Persoon> gezocht = new ArrayList();
/* 159:281 */     for (Persoon p : this.personen) {
/* 160:282 */       if (p.getAchternaam().equalsIgnoreCase(achternaam)) {
/* 161:283 */         gezocht.add(p);
/* 162:    */       }
/* 163:    */     }
/* 164:286 */     return gezocht;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public ObservableList<Persoon> getPersonen()
/* 168:    */   {
/* 169:297 */     return FXCollections.unmodifiableObservableList(this.observablePersonen);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Persoon getPersoon(String[] vnamen, String anaam, String tvoegsel, Calendar gebdat, String gebplaats)
/* 173:    */   {
/* 174:315 */     Persoon dummy = new Persoon(0, vnamen, anaam, tvoegsel, gebdat, gebplaats, null, null);
/* 175:316 */     for (Persoon persoon : this.personen) {
/* 176:317 */       if ((persoon.getNaam().equals(dummy.getNaam())) && (persoon.getGebDat().equals(dummy.getGebDat())) && (persoon.getGebPlaats().equals(dummy.getGebPlaats()))) {
/* 177:319 */         return persoon;
/* 178:    */       }
/* 179:    */     }
/* 180:322 */     return null;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public ObservableList<Gezin> getGezinnen()
/* 184:    */   {
/* 185:332 */     return FXCollections.unmodifiableObservableList(this.observableGezinnen);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Gezin getGezin(int gezinsNr)
/* 189:    */   {
/* 190:343 */     if ((1 <= gezinsNr) && (1 <= this.gezinnen.size())) {
/* 191:344 */       return (Gezin)this.gezinnen.get(gezinsNr - 1);
/* 192:    */     }
/* 193:346 */     return null;
/* 194:    */   }
/* 195:    */ }


/* Location:           D:\downloads\StamboomGUI.jar
 * Qualified Name:     stamboom.domain.Administratie
 * JD-Core Version:    0.7.0.1
 */