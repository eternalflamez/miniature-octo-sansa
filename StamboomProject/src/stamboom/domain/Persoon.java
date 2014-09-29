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
/*  11:    */ import stamboom.util.StringUtilities;
/*  12:    */ 
/*  13:    */ public class Persoon
/*  14:    */   implements Serializable
/*  15:    */ {
/*  16:    */   private final int nr;
/*  17:    */   private final String[] voornamen;
/*  18:    */   private final String achternaam;
/*  19:    */   private final String tussenvoegsel;
/*  20:    */   private final Geslacht geslacht;
/*  21:    */   private final Calendar gebDat;
/*  22:    */   private final String gebPlaats;
/*  23:    */   private Gezin ouderlijkGezin;
/*  24:    */   private final List<Gezin> alsOuderBetrokkenIn;
/*  25:    */   private transient ObservableList<Gezin> observableAlsOuderBetrokkenIn;
/*  26:    */   
/*  27:    */   Persoon(int persNr, String[] vnamen, String anaam, String tvoegsel, Calendar gebdat, String gebplaats, Geslacht g, Gezin ouderlijkGezin)
/*  28:    */   {
/*  29: 45 */     this.nr = persNr;
/*  30: 46 */     this.voornamen = new String[vnamen.length];
/*  31: 47 */     for (int i = 0; i < vnamen.length; i++) {
/*  32: 48 */       this.voornamen[i] = StringUtilities.withFirstCapital(vnamen[i].trim());
/*  33:    */     }
/*  34: 50 */     this.achternaam = StringUtilities.withFirstCapital(anaam.trim());
/*  35: 51 */     this.tussenvoegsel = tvoegsel.trim().toLowerCase();
/*  36: 52 */     this.gebDat = gebdat;
/*  37: 53 */     this.gebPlaats = StringUtilities.withFirstCapital(gebplaats.trim());
/*  38: 54 */     this.geslacht = g;
/*  39: 55 */     this.ouderlijkGezin = ouderlijkGezin;
/*  40: 56 */     this.alsOuderBetrokkenIn = new ArrayList();
/*  41:    */     
/*  42: 58 */     this.observableAlsOuderBetrokkenIn = FXCollections.observableList(this.alsOuderBetrokkenIn);
/*  43:    */   }
/*  44:    */   
/*  45:    */   private void readObject(ObjectInputStream is)
/*  46:    */     throws IOException, ClassNotFoundException
/*  47:    */   {
/*  48: 64 */     is.defaultReadObject();
/*  49: 65 */     this.observableAlsOuderBetrokkenIn = FXCollections.observableList(this.alsOuderBetrokkenIn);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getAchternaam()
/*  53:    */   {
/*  54: 72 */     return this.achternaam;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Calendar getGebDat()
/*  58:    */   {
/*  59: 79 */     return this.gebDat;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getGebPlaats()
/*  63:    */   {
/*  64: 87 */     return this.gebPlaats;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Geslacht getGeslacht()
/*  68:    */   {
/*  69: 95 */     return this.geslacht;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getInitialen()
/*  73:    */   {
/*  74:105 */     StringBuilder initialen = new StringBuilder();
/*  75:106 */     for (String s : this.voornamen) {
/*  76:107 */       initialen.append(s.charAt(0)).append('.');
/*  77:    */     }
/*  78:109 */     return initialen.toString();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getNaam()
/*  82:    */   {
/*  83:120 */     if ((this.tussenvoegsel != null) && (!this.tussenvoegsel.isEmpty())) {
/*  84:121 */       return getInitialen() + " " + this.tussenvoegsel + " " + this.achternaam;
/*  85:    */     }
/*  86:123 */     return getInitialen() + " " + this.achternaam;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getNr()
/*  90:    */   {
/*  91:131 */     return this.nr;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Gezin getOuderlijkGezin()
/*  95:    */   {
/*  96:138 */     return this.ouderlijkGezin;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public ObservableList<Gezin> getAlsOuderBetrokkenIn()
/* 100:    */   {
/* 101:146 */     return FXCollections.unmodifiableObservableList(this.observableAlsOuderBetrokkenIn);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getTussenvoegsel()
/* 105:    */   {
/* 106:154 */     return this.tussenvoegsel;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getVoornamen()
/* 110:    */   {
/* 111:161 */     StringBuilder init = new StringBuilder();
/* 112:162 */     for (String s : this.voornamen) {
/* 113:163 */       init.append(s).append(' ');
/* 114:    */     }
/* 115:165 */     return init.toString().trim();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String standaardgegevens()
/* 119:    */   {
/* 120:172 */     return getNaam() + " (" + getGeslacht() + ") " + StringUtilities.datumString(this.gebDat);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String toString()
/* 124:    */   {
/* 125:177 */     return standaardgegevens();
/* 126:    */   }
/* 127:    */   
/* 128:    */   void setOuders(Gezin ouderlijkGezin)
/* 129:    */   {
/* 130:190 */     if ((ouderlijkGezin != null) && (this.ouderlijkGezin == null))
/* 131:    */     {
/* 132:191 */       this.ouderlijkGezin = ouderlijkGezin;
/* 133:192 */       ouderlijkGezin.breidUitMet(this);
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String beschrijving()
/* 138:    */   {
/* 139:202 */     StringBuilder sb = new StringBuilder();
/* 140:    */     
/* 141:204 */     sb.append(standaardgegevens());
/* 142:206 */     if (this.ouderlijkGezin != null)
/* 143:    */     {
/* 144:207 */       sb.append("; 1e ouder: ").append(this.ouderlijkGezin.getOuder1().getNaam());
/* 145:208 */       sb.append("; 2e ouder: ").append(this.ouderlijkGezin.getOuder2().getNaam());
/* 146:    */     }
/* 147:210 */     if (!this.alsOuderBetrokkenIn.isEmpty())
/* 148:    */     {
/* 149:211 */       sb.append("; is ouder in gezin ");
/* 150:213 */       for (Gezin g : this.alsOuderBetrokkenIn) {
/* 151:214 */         sb.append(g.getNr()).append(" ");
/* 152:    */       }
/* 153:    */     }
/* 154:218 */     return sb.toString();
/* 155:    */   }
/* 156:    */   
/* 157:    */   void wordtOuderIn(Gezin g)
/* 158:    */   {
/* 159:229 */     if (!this.alsOuderBetrokkenIn.contains(g)) {
/* 160:231 */       this.observableAlsOuderBetrokkenIn.add(g);
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public Gezin heeftOngehuwdGezinMet(Persoon andereOuder)
/* 165:    */   {
/* 166:244 */     for (Gezin gezin : this.alsOuderBetrokkenIn) {
/* 167:245 */       if (((gezin.getOuder1() == this) && (gezin.getOuder2() == andereOuder)) || ((gezin.getOuder1() == andereOuder) && (gezin.getOuder2() == this))) {
/* 168:247 */         if (gezin.isOngehuwd()) {
/* 169:248 */           return gezin;
/* 170:    */         }
/* 171:    */       }
/* 172:    */     }
/* 173:252 */     return null;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean isGetrouwdOp(Calendar datum)
/* 177:    */   {
/* 178:261 */     for (Gezin gezin : this.alsOuderBetrokkenIn) {
/* 179:262 */       if (gezin.heeftGetrouwdeOudersOp(datum)) {
/* 180:263 */         return true;
/* 181:    */       }
/* 182:    */     }
/* 183:266 */     return false;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean kanTrouwenOp(Calendar datum)
/* 187:    */   {
/* 188:276 */     for (Gezin gezin : this.alsOuderBetrokkenIn)
/* 189:    */     {
/* 190:277 */       if (gezin.heeftGetrouwdeOudersOp(datum)) {
/* 191:278 */         return false;
/* 192:    */       }
/* 193:280 */       Calendar huwdatum = gezin.getHuwelijksdatum();
/* 194:281 */       if ((huwdatum != null) && (huwdatum.after(datum))) {
/* 195:282 */         return false;
/* 196:    */       }
/* 197:    */     }
/* 198:286 */     return true;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public boolean isGescheidenOp(Calendar datum)
/* 202:    */   {
/* 203:296 */     Calendar scheidingsdatum = null;
/* 204:297 */     for (Gezin gezin : this.alsOuderBetrokkenIn) {
/* 205:298 */       if (gezin.heeftGescheidenOudersOp(datum)) {
/* 206:299 */         scheidingsdatum = gezin.getScheidingsdatum();
/* 207:301 */       } else if ((gezin.getHuwelijksdatum().before(datum)) && (scheidingsdatum != null) && (gezin.getHuwelijksdatum().after(scheidingsdatum))) {
/* 208:305 */         scheidingsdatum = null;
/* 209:    */       }
/* 210:    */     }
/* 211:308 */     return scheidingsdatum != null;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public int afmetingStamboom()
/* 215:    */   {
/* 216:318 */     int afmeting = 1;
/* 217:319 */     if (this.ouderlijkGezin != null)
/* 218:    */     {
/* 219:320 */       afmeting += this.ouderlijkGezin.getOuder1().afmetingStamboom();
/* 220:321 */       if (this.ouderlijkGezin.getOuder2() != null) {
/* 221:322 */         afmeting += this.ouderlijkGezin.getOuder2().afmetingStamboom();
/* 222:    */       }
/* 223:    */     }
/* 224:325 */     return afmeting;
/* 225:    */   }
/* 226:    */   
/* 227:    */   void voegJouwStamboomToe(ArrayList<PersoonMetGeneratie> lijst, int g)
/* 228:    */   {
/* 229:342 */     lijst.add(new PersoonMetGeneratie(standaardgegevens(), g));
/* 230:343 */     if (this.ouderlijkGezin != null)
/* 231:    */     {
/* 232:344 */       this.ouderlijkGezin.getOuder1().voegJouwStamboomToe(lijst, g + 1);
/* 233:345 */       if (this.ouderlijkGezin.getOuder2() != null) {
/* 234:346 */         this.ouderlijkGezin.getOuder2().voegJouwStamboomToe(lijst, g + 1);
/* 235:    */       }
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public String stamboomAlsString()
/* 240:    */   {
/* 241:375 */     StringBuilder builder = new StringBuilder();
/* 242:    */     
/* 243:377 */     ArrayList<PersoonMetGeneratie> lijst = new ArrayList();
/* 244:378 */     voegJouwStamboomToe(lijst, 0);
/* 245:380 */     for (PersoonMetGeneratie p : lijst)
/* 246:    */     {
/* 247:381 */       builder.append(StringUtilities.spaties(p.getGeneratie() * 2));
/* 248:382 */       builder.append(p.getPersoonsgegevens()).append('\n');
/* 249:    */     }
/* 250:384 */     return builder.toString();
/* 251:    */   }
/* 252:    */ }


/* Location:           D:\downloads\StamboomGUI.jar
 * Qualified Name:     stamboom.domain.Persoon
 * JD-Core Version:    0.7.0.1
 */