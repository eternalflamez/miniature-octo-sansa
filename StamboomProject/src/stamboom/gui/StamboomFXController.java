/*   1:    */ package stamboom.gui;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.util.Calendar;
/*   7:    */ import java.util.ResourceBundle;
/*   8:    */ import javafx.collections.FXCollections;
/*   9:    */ import javafx.collections.ObservableList;
/*  10:    */ import javafx.event.Event;
/*  11:    */ import javafx.fxml.FXML;
/*  12:    */ import javafx.fxml.Initializable;
/*  13:    */ import javafx.scene.Scene;
/*  14:    */ import javafx.scene.control.Button;
/*  15:    */ import javafx.scene.control.CheckMenuItem;
/*  16:    */ import javafx.scene.control.ComboBox;
/*  17:    */ import javafx.scene.control.ListView;
/*  18:    */ import javafx.scene.control.MenuBar;
/*  19:    */ import javafx.scene.control.MenuItem;
/*  20:    */ import javafx.scene.control.SingleSelectionModel;
/*  21:    */ import javafx.scene.control.Tab;
/*  22:    */ import javafx.scene.control.TextField;
/*  23:    */ import javafx.stage.FileChooser;
/*  24:    */ import javafx.stage.Stage;
/*  25:    */ import stamboom.controller.StamboomController;
/*  26:    */ import stamboom.domain.Administratie;
/*  27:    */ import stamboom.domain.Geslacht;
/*  28:    */ import stamboom.domain.Gezin;
/*  29:    */ import stamboom.domain.Persoon;
/*  30:    */ import stamboom.util.StringUtilities;
/*  31:    */ 
/*  32:    */ public class StamboomFXController
/*  33:    */   extends StamboomController
/*  34:    */   implements Initializable
/*  35:    */ {
/*  36:    */   @FXML
/*  37:    */   MenuBar menuBar;
/*  38:    */   @FXML
/*  39:    */   MenuItem miNew;
/*  40:    */   @FXML
/*  41:    */   MenuItem miOpen;
/*  42:    */   @FXML
/*  43:    */   MenuItem miSave;
/*  44:    */   @FXML
/*  45:    */   CheckMenuItem cmDatabase;
/*  46:    */   @FXML
/*  47:    */   MenuItem miClose;
/*  48:    */   @FXML
/*  49:    */   Tab tabPersoon;
/*  50:    */   @FXML
/*  51:    */   Tab tabGezin;
/*  52:    */   @FXML
/*  53:    */   Tab tabPersoonInvoer;
/*  54:    */   @FXML
/*  55:    */   Tab tabGezinInvoer;
/*  56:    */   public ComboBox cbPersonen;
/*  57:    */   @FXML
/*  58:    */   TextField tfPersoonNr;
/*  59:    */   @FXML
/*  60:    */   TextField tfVoornamen;
/*  61:    */   @FXML
/*  62:    */   TextField tfTusenvoegsel;
/*  63:    */   @FXML
/*  64:    */   TextField tfAchternaam;
/*  65:    */   @FXML
/*  66:    */   TextField tfGeslacht;
/*  67:    */   @FXML
/*  68:    */   TextField tfGebDatum;
/*  69:    */   @FXML
/*  70:    */   TextField tfGebPlaats;
/*  71:    */   @FXML
/*  72:    */   ComboBox cbOuderlijkGezin;
/*  73:    */   @FXML
/*  74:    */   ListView lvAlsOuderBetrokkenBij;
/*  75:    */   @FXML
/*  76:    */   Button btStamboom;
/*  77:    */   @FXML
/*  78:    */   ComboBox cbGezinnen;
/*  79:    */   @FXML
/*  80:    */   TextField tfGezinNr;
/*  81:    */   @FXML
/*  82:    */   TextField tfOuder1;
/*  83:    */   @FXML
/*  84:    */   TextField tfOuder2;
/*  85:    */   @FXML
/*  86:    */   TextField tfHuwDatum;
/*  87:    */   @FXML
/*  88:    */   TextField tfScheidingsdatum;
/*  89:    */   @FXML
/*  90:    */   ListView lvKinderen;
/*  91:    */   @FXML
/*  92:    */   TextField tfVoornamenInvoer;
/*  93:    */   @FXML
/*  94:    */   TextField tfTusenvoegselInvoer;
/*  95:    */   @FXML
/*  96:    */   TextField tfAchternaamInvoer;
/*  97:    */   @FXML
/*  98:    */   ComboBox cbGeslachtInvoer;
/*  99:    */   @FXML
/* 100:    */   TextField tfGebDatumInvoer;
/* 101:    */   @FXML
/* 102:    */   TextField tfGebPlaatsInvoer;
/* 103:    */   @FXML
/* 104:    */   ComboBox cbOuderlijkGezinInvoer;
/* 105:    */   @FXML
/* 106:    */   Button btOKPersoonInvoer;
/* 107:    */   @FXML
/* 108:    */   Button btCancelPersoonInvoer;
/* 109:    */   @FXML
/* 110:    */   ComboBox cbOuder1Invoer;
/* 111:    */   @FXML
/* 112:    */   ComboBox cbOuder2Invoer;
/* 113:    */   @FXML
/* 114:    */   TextField tfHuwelijkInvoer;
/* 115:    */   @FXML
/* 116:    */   TextField tfScheidingInvoer;
/* 117:    */   @FXML
/* 118:    */   Button btOKGezinInvoer;
/* 119:    */   @FXML
/* 120:    */   Button btCancelGezinInvoer;
/* 121:    */   private boolean withDatabase;
/* 122:    */   
/* 123:    */   public void initialize(URL url, ResourceBundle rb)
/* 124:    */   {
/* 125: 92 */     initComboboxes();
/* 126:    */     
/* 127:    */ 
/* 128: 95 */     this.withDatabase = false;
/* 129:    */   }
/* 130:    */   
/* 131:    */   private void initComboboxes()
/* 132:    */   {
/* 133:101 */     ObservableList<Persoon> personen = getAdministratie().getPersonen();
/* 134:102 */     this.cbPersonen.setItems(personen);
/* 135:103 */     ObservableList<Gezin> gezinnen = getAdministratie().getGezinnen();
/* 136:104 */     this.cbGezinnen.setItems(gezinnen);
/* 137:105 */     this.cbGeslachtInvoer.getItems().setAll((Object[])Geslacht.values());
/* 138:    */     
/* 139:107 */     this.cbOuderlijkGezinInvoer.setItems(gezinnen);
/* 140:108 */     this.cbOuderlijkGezin.setItems(gezinnen);
/* 141:109 */     this.cbOuder1Invoer.setItems(personen);
/* 142:110 */     this.cbOuder2Invoer.setItems(personen);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void selectPersoon(Event evt)
/* 146:    */   {
/* 147:114 */     Persoon persoon = (Persoon)this.cbPersonen.getSelectionModel().getSelectedItem();
/* 148:115 */     showPersoon(persoon);
/* 149:    */   }
/* 150:    */   
/* 151:    */   private void showPersoon(Persoon persoon)
/* 152:    */   {
/* 153:119 */     if (persoon == null)
/* 154:    */     {
/* 155:120 */       clearTabPersoon();
/* 156:    */     }
/* 157:    */     else
/* 158:    */     {
/* 159:122 */       this.tfPersoonNr.setText(persoon.getNr() + "");
/* 160:123 */       this.tfVoornamen.setText(persoon.getVoornamen());
/* 161:124 */       this.tfTusenvoegsel.setText(persoon.getTussenvoegsel());
/* 162:125 */       this.tfAchternaam.setText(persoon.getAchternaam());
/* 163:126 */       this.tfGeslacht.setText(persoon.getGeslacht().toString());
/* 164:127 */       this.tfGebDatum.setText(StringUtilities.datumString(persoon.getGebDat()));
/* 165:128 */       this.tfGebPlaats.setText(persoon.getGebPlaats());
/* 166:129 */       if (persoon.getOuderlijkGezin() != null) {
/* 167:130 */         this.cbOuderlijkGezin.getSelectionModel().select(persoon.getOuderlijkGezin());
/* 168:    */       } else {
/* 169:132 */         this.cbOuderlijkGezin.getSelectionModel().clearSelection();
/* 170:    */       }
/* 171:134 */       this.lvAlsOuderBetrokkenBij.setItems(persoon.getAlsOuderBetrokkenIn());
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setOuders(Event evt)
/* 176:    */   {
/* 177:139 */     if (this.tfPersoonNr.getText().isEmpty()) {
/* 178:140 */       return;
/* 179:    */     }
/* 180:142 */     Gezin ouderlijkGezin = (Gezin)this.cbOuderlijkGezin.getSelectionModel().getSelectedItem();
/* 181:143 */     if (ouderlijkGezin == null) {
/* 182:144 */       return;
/* 183:    */     }
/* 184:147 */     int nr = Integer.parseInt(this.tfPersoonNr.getText());
/* 185:148 */     Persoon p = getAdministratie().getPersoon(nr);
/* 186:149 */     getAdministratie().setOuders(p, ouderlijkGezin);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void selectGezin(Event evt)
/* 190:    */   {
/* 191:154 */     Gezin gezin = (Gezin)this.cbGezinnen.getSelectionModel().getSelectedItem();
/* 192:155 */     showGezin(gezin);
/* 193:    */   }
/* 194:    */   
/* 195:    */   private void showGezin(Gezin gezin)
/* 196:    */   {
/* 197:160 */     if (gezin == null)
/* 198:    */     {
/* 199:161 */       clearTabGezin();
/* 200:    */     }
/* 201:    */     else
/* 202:    */     {
/* 203:163 */       this.tfGezinNr.setText(gezin.getNr() + "");
/* 204:164 */       this.tfOuder1.setText(gezin.getOuder1().toString());
/* 205:165 */       if (gezin.getOuder2() != null) {
/* 206:166 */         this.tfOuder2.setText(gezin.getOuder2().toString());
/* 207:    */       } else {
/* 208:168 */         this.tfOuder2.setText("");
/* 209:    */       }
/* 210:171 */       if (gezin.isOngehuwd())
/* 211:    */       {
/* 212:172 */         this.tfHuwDatum.setText("");
/* 213:173 */         this.tfHuwDatum.setEditable(true);
/* 214:    */       }
/* 215:    */       else
/* 216:    */       {
/* 217:175 */         this.tfHuwDatum.setText(StringUtilities.datumString(gezin.getHuwelijksdatum()));
/* 218:176 */         this.tfHuwDatum.setEditable(false);
/* 219:    */       }
/* 220:179 */       if (gezin.getScheidingsdatum() != null)
/* 221:    */       {
/* 222:180 */         this.tfScheidingsdatum.setText(StringUtilities.datumString(gezin.getScheidingsdatum()));
/* 223:181 */         this.tfScheidingsdatum.setEditable(false);
/* 224:    */       }
/* 225:    */       else
/* 226:    */       {
/* 227:183 */         this.tfScheidingsdatum.setText("");
/* 228:184 */         this.tfScheidingsdatum.setEditable(!gezin.isOngehuwd());
/* 229:    */       }
/* 230:187 */       this.lvKinderen.setItems(gezin.getKinderen());
/* 231:    */     }
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setHuwdatum(Event evt)
/* 235:    */   {
/* 236:    */     try
/* 237:    */     {
/* 238:195 */       Calendar huwdatum = StringUtilities.datum(this.tfHuwDatum.getText());
/* 239:196 */       int nr = Integer.parseInt(this.tfGezinNr.getText());
/* 240:197 */       Gezin gezin = getAdministratie().getGezin(nr);
/* 241:198 */       boolean gelukt = getAdministratie().setHuwelijk(gezin, huwdatum);
/* 242:199 */       if (!gelukt) {
/* 243:200 */         showDialog("Warning", "huwdatum is niet geaccepteerd");
/* 244:    */       } else {
/* 245:202 */         showGezin(gezin);
/* 246:    */       }
/* 247:    */     }
/* 248:    */     catch (IllegalArgumentException exc)
/* 249:    */     {
/* 250:205 */       showDialog("Warning", exc.getMessage());
/* 251:206 */       this.tfHuwDatum.setPromptText("dd-mm-jjjj");
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setScheidingsdatum(Event evt)
/* 256:    */   {
/* 257:    */     try
/* 258:    */     {
/* 259:214 */       Calendar scheidingsdatum = StringUtilities.datum(this.tfScheidingsdatum.getText());
/* 260:215 */       int nr = Integer.parseInt(this.tfGezinNr.getText());
/* 261:216 */       Gezin gezin = getAdministratie().getGezin(nr);
/* 262:217 */       boolean gelukt = getAdministratie().setScheiding(gezin, scheidingsdatum);
/* 263:218 */       if (!gelukt) {
/* 264:219 */         showDialog("Warning", "scheidingsdatum is niet geaccepteerd");
/* 265:    */       }
/* 266:    */     }
/* 267:    */     catch (IllegalArgumentException exc)
/* 268:    */     {
/* 269:222 */       showDialog("Warning", exc.getMessage());
/* 270:223 */       this.tfScheidingsdatum.setPromptText("dd-mm-jjjj");
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void cancelPersoonInvoer(Event evt)
/* 275:    */   {
/* 276:229 */     clearTabPersoonInvoer();
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void okPersoonInvoer(Event evt)
/* 280:    */   {
/* 281:234 */     String[] vnamen = this.tfVoornamenInvoer.getText().split(" ");
/* 282:235 */     if (vnamen.length == 0)
/* 283:    */     {
/* 284:236 */       showDialog("Warning", "voornamen zijn niet ingevoerd");
/* 285:237 */       return;
/* 286:    */     }
/* 287:239 */     String tvoegsel = this.tfTusenvoegselInvoer.getText();
/* 288:240 */     String anaam = this.tfAchternaamInvoer.getText().trim();
/* 289:241 */     if (anaam.isEmpty())
/* 290:    */     {
/* 291:242 */       showDialog("Warning", "achternaam is niet ingevoerd");
/* 292:243 */       return;
/* 293:    */     }
/* 294:245 */     Geslacht geslacht = (Geslacht)this.cbGeslachtInvoer.getSelectionModel().getSelectedItem();
/* 295:246 */     if (geslacht == null)
/* 296:    */     {
/* 297:247 */       showDialog("Warning", "geslacht is niet ingevoerd"); return;
/* 298:    */     }
/* 299:    */     Calendar gebdat;
/* 300:    */     try
/* 301:    */     {
/* 302:252 */       gebdat = StringUtilities.datum(this.tfGebDatumInvoer.getText());
/* 303:    */     }
/* 304:    */     catch (IllegalArgumentException exc)
/* 305:    */     {
/* 306:254 */       showDialog("Warning", "geboortedatum: " + exc.getMessage());
/* 307:255 */       return;
/* 308:    */     }
/* 309:257 */     String gebplaats = this.tfGebPlaatsInvoer.getText().trim();
/* 310:258 */     if (gebplaats.isEmpty())
/* 311:    */     {
/* 312:259 */       showDialog("Warning", "geboorteplaats is niet ingevoerd");
/* 313:260 */       return;
/* 314:    */     }
/* 315:262 */     Gezin ouderlijkGezin = (Gezin)this.cbOuderlijkGezinInvoer.getSelectionModel().getSelectedItem();
/* 316:263 */     Persoon p = getAdministratie().addPersoon(geslacht, vnamen, anaam, tvoegsel, gebdat, gebplaats, ouderlijkGezin);
/* 317:264 */     if (p == null) {
/* 318:265 */       showDialog("Warning", "De stamboomadministratie kende inmiddels een persoon met dezelfde naam, geboortedatum en geboorteplaats.");
/* 319:    */     }
/* 320:268 */     clearTabPersoonInvoer();
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void okGezinInvoer(Event evt)
/* 324:    */   {
/* 325:273 */     Persoon ouder1 = (Persoon)this.cbOuder1Invoer.getSelectionModel().getSelectedItem();
/* 326:274 */     if (ouder1 == null)
/* 327:    */     {
/* 328:275 */       showDialog("Warning", "eerste ouder is niet ingevoerd");
/* 329:276 */       return;
/* 330:    */     }
/* 331:278 */     Persoon ouder2 = (Persoon)this.cbOuder2Invoer.getSelectionModel().getSelectedItem();
/* 332:    */     Calendar huwdatum;
/* 333:    */     try
/* 334:    */     {
/* 335:281 */       huwdatum = StringUtilities.datum(this.tfHuwelijkInvoer.getText());
/* 336:    */     }
/* 337:    */     catch (IllegalArgumentException exc)
/* 338:    */     {
/* 339:283 */       showDialog("Warning", "huwelijksdatum :" + exc.getMessage());
/* 340:284 */       return;
/* 341:    */     }
/* 342:287 */     if (huwdatum != null)
/* 343:    */     {
/* 344:288 */       Gezin g = getAdministratie().addHuwelijk(ouder1, ouder2, huwdatum);
/* 345:289 */       if (g == null) {
/* 346:290 */         showDialog("Warning", "Invoer huwelijk is niet geaccepteerd");
/* 347:    */       } else {
/* 348:    */         try
/* 349:    */         {
/* 350:294 */           Calendar scheidingsdatum = StringUtilities.datum(this.tfScheidingInvoer.getText());
/* 351:295 */           getAdministratie().setScheiding(g, scheidingsdatum);
/* 352:    */         }
/* 353:    */         catch (IllegalArgumentException exc)
/* 354:    */         {
/* 355:297 */           showDialog("Warning", "scheidingsdatum :" + exc.getMessage());
/* 356:    */         }
/* 357:    */       }
/* 358:    */     }
/* 359:    */     else
/* 360:    */     {
/* 361:301 */       Gezin g = getAdministratie().addOngehuwdGezin(ouder1, ouder2);
/* 362:302 */       if (g == null) {
/* 363:303 */         showDialog("Warning", "Invoer ongehuwd gezin is niet geaccepteerd");
/* 364:    */       }
/* 365:    */     }
/* 366:307 */     clearTabGezinInvoer();
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void cancelGezinInvoer(Event evt)
/* 370:    */   {
/* 371:312 */     clearTabGezinInvoer();
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void showStamboom(Event evt)
/* 375:    */   {
/* 376:317 */     Persoon persoon = (Persoon)this.cbPersonen.getSelectionModel().getSelectedItem();
/* 377:318 */     if (persoon == null) {
/* 378:319 */       return;
/* 379:    */     }
/* 380:321 */     showDialog("stamboom van " + persoon.getNaam(), persoon.stamboomAlsString());
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void createEmptyStamboom(Event evt)
/* 384:    */   {
/* 385:325 */     clearAdministratie();
/* 386:326 */     clearTabs();
/* 387:327 */     initComboboxes();
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void openStamboom(Event evt)
/* 391:    */   {
/* 392:    */     try
/* 393:    */     {
/* 394:333 */       if (this.withDatabase)
/* 395:    */       {
/* 396:334 */         loadFromDatabase();
/* 397:    */       }
/* 398:    */       else
/* 399:    */       {
/* 400:336 */         FileChooser filechooser = new FileChooser();
/* 401:337 */         File file = filechooser.showOpenDialog(getStage());
/* 402:338 */         if (file != null) {
/* 403:339 */           deserialize(file);
/* 404:    */         }
/* 405:    */       }
/* 406:342 */       clearTabs();
/* 407:343 */       initComboboxes();
/* 408:    */     }
/* 409:    */     catch (IOException exc)
/* 410:    */     {
/* 411:345 */       showDialog("Error", exc.getMessage());
/* 412:    */     }
/* 413:    */   }
/* 414:    */   
/* 415:    */   public void saveStamboom(Event evt)
/* 416:    */   {
/* 417:    */     try
/* 418:    */     {
/* 419:352 */       if (this.withDatabase)
/* 420:    */       {
/* 421:353 */         saveToDatabase();
/* 422:    */       }
/* 423:    */       else
/* 424:    */       {
/* 425:355 */         FileChooser filechooser = new FileChooser();
/* 426:356 */         File file = filechooser.showSaveDialog(getStage());
/* 427:357 */         if (file != null) {
/* 428:358 */           serialize(file);
/* 429:    */         }
/* 430:    */       }
/* 431:    */     }
/* 432:    */     catch (IOException exc)
/* 433:    */     {
/* 434:362 */       showDialog("Error", exc.getMessage());
/* 435:    */     }
/* 436:    */   }
/* 437:    */   
/* 438:    */   public void closeApplication(Event evt)
/* 439:    */   {
/* 440:368 */     saveStamboom(evt);
/* 441:369 */     getStage().close();
/* 442:    */   }
/* 443:    */   
/* 444:    */   public void configureStorage(Event evt)
/* 445:    */   {
/* 446:374 */     this.withDatabase = this.cmDatabase.isSelected();
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void selectTab(Event evt)
/* 450:    */   {
/* 451:379 */     Object source = evt.getSource();
/* 452:380 */     if (source == this.tabPersoon) {
/* 453:381 */       clearTabPersoon();
/* 454:382 */     } else if (source == this.tabGezin) {
/* 455:383 */       clearTabGezin();
/* 456:384 */     } else if (source == this.tabPersoonInvoer) {
/* 457:385 */       clearTabPersoonInvoer();
/* 458:386 */     } else if (source == this.tabGezinInvoer) {
/* 459:387 */       clearTabGezinInvoer();
/* 460:    */     }
/* 461:    */   }
/* 462:    */   
/* 463:    */   private void clearTabs()
/* 464:    */   {
/* 465:392 */     clearTabPersoon();
/* 466:    */     
/* 467:394 */     clearTabPersoonInvoer();
/* 468:395 */     clearTabGezin();
/* 469:396 */     clearTabGezinInvoer();
/* 470:    */   }
/* 471:    */   
/* 472:    */   private void clearTabPersoonInvoer()
/* 473:    */   {
/* 474:401 */     this.tfAchternaamInvoer.clear();
/* 475:402 */     this.tfTusenvoegselInvoer.clear();
/* 476:403 */     this.tfVoornamenInvoer.clear();
/* 477:404 */     this.tfGebDatumInvoer.clear();
/* 478:405 */     this.tfGebPlaatsInvoer.clear();
/* 479:406 */     this.cbGeslachtInvoer.getSelectionModel().clearSelection();
/* 480:407 */     this.cbOuderlijkGezin.getSelectionModel().clearSelection();
/* 481:    */   }
/* 482:    */   
/* 483:    */   private void clearTabGezinInvoer()
/* 484:    */   {
/* 485:412 */     this.cbOuder1Invoer.getSelectionModel().clearSelection();
/* 486:413 */     this.cbOuder2Invoer.getSelectionModel().clearSelection();
/* 487:414 */     this.tfHuwelijkInvoer.clear();
/* 488:415 */     this.tfScheidingInvoer.clear();
/* 489:    */   }
/* 490:    */   
/* 491:    */   private void clearTabPersoon()
/* 492:    */   {
/* 493:419 */     this.cbPersonen.getSelectionModel().clearSelection();
/* 494:420 */     this.tfPersoonNr.clear();
/* 495:421 */     this.tfVoornamen.clear();
/* 496:422 */     this.tfTusenvoegsel.clear();
/* 497:423 */     this.tfAchternaam.clear();
/* 498:424 */     this.tfGeslacht.clear();
/* 499:425 */     this.tfGebDatum.clear();
/* 500:426 */     this.tfGebPlaats.clear();
/* 501:427 */     this.cbOuderlijkGezin.getSelectionModel().clearSelection();
/* 502:428 */     this.lvAlsOuderBetrokkenBij.setItems(FXCollections.emptyObservableList());
/* 503:    */   }
/* 504:    */   
/* 505:    */   private void clearTabGezin()
/* 506:    */   {
/* 507:433 */     this.cbGezinnen.getSelectionModel().clearSelection();
/* 508:434 */     this.tfGezinNr.clear();
/* 509:435 */     this.tfOuder1.clear();
/* 510:436 */     this.tfOuder2.clear();
/* 511:437 */     this.tfHuwDatum.clear();
/* 512:438 */     this.tfScheidingsdatum.clear();
/* 513:439 */     this.lvKinderen.setItems(FXCollections.emptyObservableList());
/* 514:    */   }
/* 515:    */   
/* 516:    */   private void showDialog(String type, String message)
/* 517:    */   {
/* 518:443 */     Stage myDialog = new Dialog(getStage(), type, message);
/* 519:444 */     myDialog.show();
/* 520:    */   }
/* 521:    */   
/* 522:    */   private Stage getStage()
/* 523:    */   {
/* 524:448 */     return (Stage)this.menuBar.getScene().getWindow();
/* 525:    */   }
/* 526:    */ }


/* Location:           D:\downloads\StamboomGUI.jar
 * Qualified Name:     stamboom.gui.StamboomFXController
 * JD-Core Version:    0.7.0.1
 */