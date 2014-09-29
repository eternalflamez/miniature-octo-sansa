/*   1:    */ package stamboom.console;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Calendar;
/*   8:    */ import java.util.InputMismatchException;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Scanner;
/*  11:    */ import stamboom.controller.StamboomController;
/*  12:    */ import stamboom.domain.Administratie;
/*  13:    */ import stamboom.domain.Geslacht;
/*  14:    */ import stamboom.domain.Gezin;
/*  15:    */ import stamboom.domain.Persoon;
/*  16:    */ import stamboom.util.StringUtilities;
/*  17:    */ 
/*  18:    */ public class StamboomConsole
/*  19:    */ {
/*  20:    */   private final Scanner input;
/*  21:    */   private final StamboomController controller;
/*  22:    */   
/*  23:    */   public StamboomConsole(StamboomController controller)
/*  24:    */   {
/*  25: 26 */     this.input = new Scanner(System.in);
/*  26: 27 */     this.controller = controller;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void startMenu()
/*  30:    */   {
/*  31: 32 */     MenuItem choice = kiesMenuItem();
/*  32: 33 */     while (choice != MenuItem.EXIT)
/*  33:    */     {
/*  34: 34 */       switch (choice.ordinal())
/*  35:    */       {
/*  36:    */       case 1: 
/*  37: 36 */         invoerNieuwePersoon();
/*  38: 37 */         break;
/*  39:    */       case 2: 
/*  40: 39 */         invoerNieuwGezin();
/*  41: 40 */         break;
/*  42:    */       case 3: 
/*  43: 42 */         invoerHuwelijk();
/*  44:    */       case 4: 
/*  45: 44 */         invoerScheiding();
/*  46: 45 */         break;
/*  47:    */       case 5: 
/*  48: 47 */         toonPersoonsgegevens();
/*  49: 48 */         break;
/*  50:    */       case 6: 
/*  51: 50 */         toonGezinsgegevens();
/*  52: 51 */         break;
/*  53:    */       case 7: 
/*  54: 54 */         laadGegevens();
/*  55: 55 */         break;
/*  56:    */       case 8: 
/*  57: 57 */         slaGegevensOp();
/*  58: 58 */         break;
/*  59:    */       case 9: 
/*  60: 60 */         toonStamboom();
/*  61:    */       }
/*  62: 63 */       choice = kiesMenuItem();
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   Administratie getAdmin()
/*  67:    */   {
/*  68: 68 */     return this.controller.getAdministratie();
/*  69:    */   }
/*  70:    */   
/*  71:    */   void invoerNieuwePersoon()
/*  72:    */   {
/*  73: 72 */     Geslacht geslacht = null;
/*  74: 73 */     while (geslacht == null)
/*  75:    */     {
/*  76: 74 */       String g = readString("wat is het geslacht (m/v)");
/*  77: 75 */       if (g.toLowerCase().charAt(0) == 'm') {
/*  78: 76 */         geslacht = Geslacht.MAN;
/*  79:    */       }
/*  80: 78 */       if (g.toLowerCase().charAt(0) == 'v') {
/*  81: 79 */         geslacht = Geslacht.VROUW;
/*  82:    */       }
/*  83:    */     }
/*  84: 84 */     String[] vnamen = readString("voornamen gescheiden door spatie").split(" ");
/*  85:    */     
/*  86:    */ 
/*  87: 87 */     String tvoegsel = readString("tussenvoegsel");
/*  88:    */     
/*  89:    */ 
/*  90: 90 */     String anaam = readString("achternaam");
/*  91:    */     
/*  92:    */ 
/*  93: 93 */     Calendar gebdat = readDate("geboortedatum");
/*  94:    */     
/*  95:    */ 
/*  96: 96 */     String gebplaats = readString("geboorteplaats");
/*  97:    */     
/*  98:    */ 
/*  99: 99 */     toonGezinnen();
/* 100:100 */     String gezinsString = readString("gezinsnummer van ouderlijk gezin");
/* 101:    */     Gezin ouders;
/* 103:101 */     if (gezinsString.equals("")) {
/* 104:102 */       ouders = null;
/* 105:    */     } else {
/* 106:104 */       ouders = getAdmin().getGezin(Integer.parseInt(gezinsString));
/* 107:    */     }
/* 108:107 */     getAdmin().addPersoon(geslacht, vnamen, anaam, tvoegsel, gebdat, gebplaats, ouders);
/* 109:    */   }
/* 110:    */   
/* 111:    */   void invoerNieuwGezin()
/* 112:    */   {
/* 113:112 */     System.out.println("wie is de eerste partner?");
/* 114:113 */     Persoon partner1 = selecteerPersoon();
/* 115:114 */     if (partner1 == null)
/* 116:    */     {
/* 117:115 */       System.out.println("onjuiste invoer eerste partner");
/* 118:116 */       return;
/* 119:    */     }
/* 120:118 */     System.out.println("wie is de tweede partner?");
/* 121:119 */     Persoon partner2 = selecteerPersoon();
/* 122:120 */     Gezin gezin = getAdmin().addOngehuwdGezin(partner1, partner2);
/* 123:121 */     if (gezin == null) {
/* 124:122 */       System.out.println("gezin is niet geaccepteerd");
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   void invoerHuwelijk()
/* 129:    */   {
/* 130:127 */     System.out.println("wie is de eerste partner?");
/* 131:128 */     Persoon partner1 = selecteerPersoon();
/* 132:129 */     if (partner1 == null)
/* 133:    */     {
/* 134:130 */       System.out.println("onjuiste invoer eerste partner");
/* 135:131 */       return;
/* 136:    */     }
/* 137:133 */     System.out.println("wie is de tweede partner?");
/* 138:134 */     Persoon partner2 = selecteerPersoon();
/* 139:135 */     if (partner2 == null)
/* 140:    */     {
/* 141:136 */       System.out.println("onjuiste invoer tweede partner");
/* 142:137 */       return;
/* 143:    */     }
/* 144:139 */     Calendar datum = readDate("datum van huwelijk");
/* 145:140 */     Gezin g = getAdmin().addHuwelijk(partner1, partner2, datum);
/* 146:141 */     if (g != null) {
/* 147:142 */       System.out.println("huwelijk niet voltrokken");
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   void invoerScheiding()
/* 152:    */   {
/* 153:147 */     selecteerGezin();
/* 154:148 */     int gezinsNr = readInt("kies gezinsnummer");
/* 155:149 */     this.input.nextLine();
/* 156:150 */     Calendar datum = readDate("datum van scheiding");
/* 157:151 */     Gezin g = getAdmin().getGezin(gezinsNr);
/* 158:152 */     if (g != null)
/* 159:    */     {
/* 160:153 */       boolean gelukt = getAdmin().setScheiding(g, datum);
/* 161:154 */       if (!gelukt) {
/* 162:155 */         System.out.println("scheiding niet geaccepteerd");
/* 163:    */       }
/* 164:    */     }
/* 165:    */     else
/* 166:    */     {
/* 167:158 */       System.out.println("gezin onbekend");
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   Persoon selecteerPersoon()
/* 172:    */   {
/* 173:163 */     String naam = readString("wat is de achternaam");
/* 174:164 */     ArrayList<Persoon> personen = getAdmin().getPersonenMetAchternaam(naam);
/* 175:165 */     for (Persoon p : personen) {
/* 176:166 */       System.out.println(p.getNr() + "\t" + p.getNaam() + " " + datumString(p.getGebDat()));
/* 177:    */     }
/* 178:168 */     int invoer = readInt("selecteer persoonsnummer");
/* 179:169 */     this.input.nextLine();
/* 180:170 */     Persoon p = getAdmin().getPersoon(invoer);
/* 181:171 */     return p;
/* 182:    */   }
/* 183:    */   
/* 184:    */   Gezin selecteerGezin()
/* 185:    */   {
/* 186:175 */     String naam = readString("gezin van persoon met welke achternaam");
/* 187:176 */     ArrayList<Persoon> kandidaten = getAdmin().getPersonenMetAchternaam(naam);
/* 188:177 */     for (Persoon p : kandidaten)
/* 189:    */     {
/* 190:178 */       List<Gezin> gezinnen = p.getAlsOuderBetrokkenIn();
/* 191:179 */       System.out.print(p.getNr() + "\t" + p.getNaam() + " " + datumString(p.getGebDat()));
/* 192:180 */       System.out.print(" gezinnen: ");
/* 193:181 */       for (Gezin gezin : gezinnen) {
/* 194:182 */         System.out.print(" " + gezin.getNr());
/* 195:    */       }
/* 196:184 */       System.out.println();
/* 197:    */     }
/* 198:186 */     int invoer = readInt("selecteer gezinsnummer");
/* 199:187 */     this.input.nextLine();
/* 200:188 */     return getAdmin().getGezin(invoer);
/* 201:    */   }
/* 202:    */   
/* 203:    */   MenuItem kiesMenuItem()
/* 204:    */   {
/* 205:192 */     System.out.println();
/* 206:193 */     for (MenuItem m : MenuItem.values()) {
/* 207:194 */       System.out.println(m.ordinal() + "\t" + m.getOmschr());
/* 208:    */     }
/* 209:196 */     System.out.println();
/* 210:197 */     int maxNr = MenuItem.values().length - 1;
/* 211:198 */     int nr = readInt("maak een keuze uit 0 t/m " + maxNr);
/* 212:199 */     while ((nr < 0) || (nr > maxNr)) {
/* 213:200 */       nr = readInt("maak een keuze uit 0 t/m " + maxNr);
/* 214:    */     }
/* 215:202 */     this.input.nextLine();
/* 216:203 */     return MenuItem.values()[nr];
/* 217:    */   }
/* 218:    */   
/* 219:    */   void toonPersoonsgegevens()
/* 220:    */   {
/* 221:207 */     Persoon p = selecteerPersoon();
/* 222:208 */     if (p == null) {
/* 223:209 */       System.out.println("persoon onbekend");
/* 224:    */     } else {
/* 225:211 */       System.out.println(p.beschrijving());
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   void toonGezinsgegevens()
/* 230:    */   {
/* 231:216 */     Gezin g = selecteerGezin();
/* 232:217 */     if (g == null) {
/* 233:218 */       System.out.println("gezin onbekend");
/* 234:    */     } else {
/* 235:220 */       System.out.println(g.beschrijving());
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   void toonGezinnen()
/* 240:    */   {
/* 241:225 */     int nr = 1;
/* 242:226 */     Gezin r = getAdmin().getGezin(nr);
/* 243:227 */     while (r != null)
/* 244:    */     {
/* 245:228 */       System.out.println(r.toString());
/* 246:229 */       nr++;
/* 247:230 */       r = getAdmin().getGezin(nr);
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   void slaGegevensOp()
/* 252:    */   {
/* 253:236 */     String naamBestand = readString("geef de naam van het doelbestand");
/* 254:    */     try
/* 255:    */     {
/* 256:238 */       this.controller.serialize(new File(naamBestand));
/* 257:    */     }
/* 258:    */     catch (IOException ex)
/* 259:    */     {
/* 260:240 */       System.out.println("Het opslaan is niet gelukt:\n" + ex.getMessage());
/* 261:    */     }
/* 262:    */   }
/* 263:    */   
/* 264:    */   void laadGegevens()
/* 265:    */   {
/* 266:246 */     String naamBestand = readString("geef de naam van het bestand");
/* 267:    */     try
/* 268:    */     {
/* 269:248 */       this.controller.deserialize(new File(naamBestand));
/* 270:    */     }
/* 271:    */     catch (IOException ex)
/* 272:    */     {
/* 273:250 */       System.out.println("Het ophalen is niet gelukt:\n" + ex.getMessage());
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   void toonStamboom()
/* 278:    */   {
/* 279:256 */     Persoon p = selecteerPersoon();
/* 280:257 */     if (p == null) {
/* 281:258 */       System.out.println("persoon onbekend");
/* 282:    */     } else {
/* 283:260 */       System.out.println(p.stamboomAlsString());
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   static void printSpaties(int n)
/* 288:    */   {
/* 289:265 */     System.out.print(StringUtilities.spaties(n));
/* 290:    */   }
/* 291:    */   
/* 292:    */   Calendar readDate(String helptekst)
/* 293:    */   {
/* 294:269 */     String datumString = readString(helptekst + "; voer datum in (dd-mm-jjjj)");
/* 295:    */     try
/* 296:    */     {
/* 297:271 */       return StringUtilities.datum(datumString);
/* 298:    */     }
/* 299:    */     catch (IllegalArgumentException exc)
/* 300:    */     {
/* 301:273 */       System.out.println(exc.getMessage());
/* 302:    */     }
/* 303:274 */     return readDate(helptekst);
/* 304:    */   }
/* 305:    */   
/* 306:    */   int readInt(String helptekst)
/* 307:    */   {
/* 308:279 */     boolean invoerOk = false;
/* 309:280 */     int invoer = -1;
/* 310:281 */     while (!invoerOk) {
/* 311:    */       try
/* 312:    */       {
/* 313:283 */         System.out.print(helptekst + " ");
/* 314:284 */         invoer = this.input.nextInt();
/* 315:285 */         invoerOk = true;
/* 316:    */       }
/* 317:    */       catch (InputMismatchException exc)
/* 318:    */       {
/* 319:287 */         System.out.println("Let op, invoer moet een getal zijn!");
/* 320:288 */         this.input.nextLine();
/* 321:    */       }
/* 322:    */     }
/* 323:292 */     return invoer;
/* 324:    */   }
/* 325:    */   
/* 326:    */   String readString(String helptekst)
/* 327:    */   {
/* 328:296 */     System.out.print(helptekst + " ");
/* 329:297 */     String invoer = this.input.nextLine();
/* 330:298 */     return invoer;
/* 331:    */   }
/* 332:    */   
/* 333:    */   String datumString(Calendar datum)
/* 334:    */   {
/* 335:302 */     return StringUtilities.datumString(datum);
/* 336:    */   }
/* 337:    */   
/* 338:    */   public static void main(String[] arg)
/* 339:    */   {
/* 340:306 */     StamboomController controller = new StamboomController();
/* 341:    */     
/* 342:308 */     StamboomConsole console = new StamboomConsole(controller);
/* 343:309 */     console.startMenu();
/* 344:    */   }
/* 345:    */ }



/* Location:           D:\downloads\StamboomGUI.jar

 * Qualified Name:     stamboom.console.StamboomConsole

 * JD-Core Version:    0.7.0.1

 */