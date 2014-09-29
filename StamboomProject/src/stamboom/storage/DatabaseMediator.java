/*   1:    */ package stamboom.storage;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.sql.Connection;
/*   6:    */ import java.sql.DriverManager;
/*   7:    */ import java.sql.PreparedStatement;
/*   8:    */ import java.sql.ResultSet;
/*   9:    */ import java.sql.SQLException;
/*  10:    */ import java.sql.Statement;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.GregorianCalendar;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Properties;
/*  15:    */ import stamboom.domain.Administratie;
/*  16:    */ import stamboom.domain.Geslacht;
/*  17:    */ import stamboom.domain.Gezin;
/*  18:    */ import stamboom.domain.Persoon;
/*  19:    */ import stamboom.util.StringUtilities;
/*  20:    */ 
/*  21:    */ public class DatabaseMediator
/*  22:    */   implements IStorageMediator
/*  23:    */ {
/*  24:    */   private Properties props;
/*  25:    */   private Connection conn;
/*  26:    */   
/*  27:    */   public DatabaseMediator(Properties props)
/*  28:    */   {
/*  29: 34 */     configure(props);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Administratie load()
/*  33:    */     throws IOException
/*  34:    */   {
/*  35: 39 */     if (!isCorrectlyConfigured()) {
/*  36: 40 */       throw new IllegalArgumentException("config incorrect");
/*  37:    */     }
/*  38: 42 */     Administratie admin = new Administratie();
/*  39:    */     try
/*  40:    */     {
/*  41: 44 */       initConnection();
/*  42: 45 */       Statement stat = this.conn.createStatement();
/*  43:    */       
/*  44:    */ 
/*  45: 48 */       ResultSet rs = stat.executeQuery("SELECT * FROM Persoon");
/*  46: 49 */       ArrayList<Integer> oudersNummers = new ArrayList();
/*  47: 50 */       while (rs.next())
/*  48:    */       {
/*  49: 51 */         String[] datumdelen = rs.getString("Geboortedatum").split("-");
/*  50: 52 */         GregorianCalendar gebdat = new GregorianCalendar(new Integer(datumdelen[2]).intValue(), new Integer(datumdelen[1]).intValue() - 1, new Integer(datumdelen[0]).intValue());
/*  51:    */         
/*  52:    */ 
/*  53:    */ 
/*  54: 56 */         String gebplaats = rs.getString("Geboorteplaats");
/*  55: 57 */         String achternaam = rs.getString("Achternaam");
/*  56: 58 */         String[] voornamen = rs.getString("Voornamen").split("-");
/*  57: 59 */         String tussenvoegsel = rs.getString("Tussenvoegsel");
/*  58:    */         Geslacht geslacht;
/*  60: 61 */         if (rs.getString("Geslacht").equals("M")) {
/*  61: 62 */           geslacht = Geslacht.MAN;
/*  62:    */         } else {
/*  63: 64 */           geslacht = Geslacht.VROUW;
/*  64:    */         }
/*  65: 66 */         oudersNummers.add(Integer.valueOf(rs.getInt("Ouders")));
/*  66: 67 */         admin.addPersoon(geslacht, voornamen, achternaam, tussenvoegsel, gebdat, gebplaats, null);
/*  67:    */       }
/*  68: 72 */       rs = stat.executeQuery("SELECT * FROM Gezin");
/*  69: 73 */       while (rs.next())
/*  70:    */       {
/*  71: 74 */         int nrOuder1 = rs.getInt("Ouder1");
/*  72: 75 */         int nrOuder2 = rs.getInt("Ouder2");
/*  73: 76 */         String huwelijksdatum = rs.getString("Huwelijk");
/*  74: 77 */         String scheidingsdatum = rs.getString("Scheiding");
/*  75: 78 */         Persoon ouder1 = admin.getPersoon(nrOuder1);
/*  76: 79 */         Persoon ouder2 = admin.getPersoon(nrOuder2);
/*  77: 80 */         if (huwelijksdatum.isEmpty())
/*  78:    */         {
/*  79: 81 */           admin.addOngehuwdGezin(ouder1, ouder2);
/*  80:    */         }
/*  81:    */         else
/*  82:    */         {
/*  83: 83 */           Gezin huwelijk = admin.addHuwelijk(ouder1, ouder2, StringUtilities.datum(huwelijksdatum));
/*  84: 84 */           if (!scheidingsdatum.isEmpty()) {
/*  85: 85 */             admin.setScheiding(huwelijk, StringUtilities.datum(scheidingsdatum));
/*  86:    */           }
/*  87:    */         }
/*  88:    */       }
/*  89: 91 */       for (int i = 0; i < oudersNummers.size(); i++)
/*  90:    */       {
/*  91: 92 */         Gezin ouders = admin.getGezin(((Integer)oudersNummers.get(i)).intValue());
/*  92: 93 */         admin.setOuders(admin.getPersoon(i + 1), ouders);
/*  93:    */       }
/*  94:    */     }
/*  95:    */     catch (SQLException exc)
/*  96:    */     {
/*  97: 96 */       System.err.println(exc.getMessage());
/*  98:    */     }
/*  99:    */     finally
/* 100:    */     {
/* 101: 98 */       closeConnection();
/* 102:    */     }
/* 103:100 */     return admin;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void save(Administratie admin)
/* 107:    */     throws IOException
/* 108:    */   {
/* 109:105 */     if (!isCorrectlyConfigured()) {
/* 110:106 */       throw new IllegalArgumentException("config incorrect");
/* 111:    */     }
/* 112:    */     try
/* 113:    */     {
/* 114:109 */       initConnection();
/* 115:110 */       createTables();
/* 116:    */       
/* 117:112 */       PreparedStatement psVullenPersoon = this.conn.prepareStatement("INSERT INTO Persoon VALUES(?,?,?,?,?,?,?,?)");
/* 118:    */       
/* 119:    */ 
/* 120:115 */       List<Persoon> personen = admin.getPersonen();
/* 121:116 */       for (Persoon persoon : personen)
/* 122:    */       {
/* 123:117 */         psVullenPersoon.setInt(1, persoon.getNr());
/* 124:118 */         psVullenPersoon.setString(2, persoon.getVoornamen());
/* 125:119 */         psVullenPersoon.setString(3, persoon.getTussenvoegsel());
/* 126:120 */         psVullenPersoon.setString(4, persoon.getAchternaam());
/* 127:121 */         psVullenPersoon.setString(5, persoon.getGeslacht() == Geslacht.MAN ? "M" : "V");
/* 128:    */         
/* 129:123 */         psVullenPersoon.setString(6, StringUtilities.datumString(persoon.getGebDat()));
/* 130:124 */         psVullenPersoon.setString(7, persoon.getGebPlaats());
/* 131:125 */         if (persoon.getOuderlijkGezin() == null) {
/* 132:126 */           psVullenPersoon.setNull(8, 4);
/* 133:    */         } else {
/* 134:128 */           psVullenPersoon.setInt(8, persoon.getOuderlijkGezin().getNr());
/* 135:    */         }
/* 136:130 */         psVullenPersoon.executeUpdate();
/* 137:    */       }
/* 138:133 */       PreparedStatement psVullenGezin = this.conn.prepareStatement("INSERT INTO Gezin VALUES(?,?,?,?,?)");
/* 139:    */       
/* 140:    */ 
/* 141:136 */       List<Gezin> gezinnen = admin.getGezinnen();
/* 142:137 */       for (Gezin gezin : gezinnen)
/* 143:    */       {
/* 144:138 */         psVullenGezin.setInt(1, gezin.getNr());
/* 145:139 */         psVullenGezin.setInt(2, gezin.getOuder1().getNr());
/* 146:140 */         if (gezin.getOuder2() == null) {
/* 147:141 */           psVullenGezin.setNull(3, 4);
/* 148:    */         } else {
/* 149:143 */           psVullenGezin.setInt(3, gezin.getOuder2().getNr());
/* 150:    */         }
/* 151:145 */         if (gezin.getHuwelijksdatum() == null) {
/* 152:146 */           psVullenGezin.setString(4, "");
/* 153:    */         } else {
/* 154:148 */           psVullenGezin.setString(4, StringUtilities.datumString(gezin.getHuwelijksdatum()));
/* 155:    */         }
/* 156:150 */         if (gezin.getScheidingsdatum() == null) {
/* 157:151 */           psVullenGezin.setString(5, "");
/* 158:    */         } else {
/* 159:153 */           psVullenGezin.setString(5, StringUtilities.datumString(gezin.getScheidingsdatum()));
/* 160:    */         }
/* 161:156 */         psVullenGezin.executeUpdate();
/* 162:    */       }
/* 163:    */     }
/* 164:    */     catch (SQLException exc)
/* 165:    */     {
/* 166:    */       PreparedStatement psVullenGezin;
/* 167:160 */       System.err.println(exc.getMessage());
/* 168:    */     }
/* 169:    */     finally
/* 170:    */     {
/* 171:162 */       closeConnection();
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public final boolean configure(Properties props)
/* 176:    */   {
/* 177:168 */     this.props = props;
/* 178:    */     try
/* 179:    */     {
/* 180:171 */       initConnection();
/* 181:172 */       return isCorrectlyConfigured();
/* 182:    */     }
/* 183:    */     catch (SQLException ex)
/* 184:    */     {
/* 185:174 */       System.err.println(ex.getMessage());
/* 186:175 */       this.props = null;
/* 187:176 */       return false;
/* 188:    */     }
/* 189:    */     finally
/* 190:    */     {
/* 191:178 */       closeConnection();
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   public Properties config()
/* 196:    */   {
/* 197:184 */     return this.props;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean isCorrectlyConfigured()
/* 201:    */   {
/* 202:189 */     if (this.props == null) {
/* 203:190 */       return false;
/* 204:    */     }
/* 205:192 */     if (!this.props.containsKey("driver")) {
/* 206:193 */       return false;
/* 207:    */     }
/* 208:195 */     if (!this.props.containsKey("url")) {
/* 209:196 */       return false;
/* 210:    */     }
/* 211:198 */     if (!this.props.containsKey("username")) {
/* 212:199 */       return false;
/* 213:    */     }
/* 214:201 */     if (!this.props.containsKey("password")) {
/* 215:202 */       return false;
/* 216:    */     }
/* 217:204 */     return true;
/* 218:    */   }
/* 219:    */   
/* 220:    */   private void initConnection()
/* 221:    */     throws SQLException
/* 222:    */   {
/* 223:208 */     String driver = this.props.getProperty("driver");
/* 224:209 */     if (driver != null) {
/* 225:210 */       System.setProperty("jdbc.drivers", driver);
/* 226:    */     }
/* 227:212 */     String url = this.props.getProperty("url");
/* 228:213 */     String username = this.props.getProperty("username");
/* 229:214 */     String password = this.props.getProperty("password");
/* 230:    */     
/* 231:216 */     this.conn = DriverManager.getConnection(url, username, password);
/* 232:    */   }
/* 233:    */   
/* 234:    */   private void createTables()
/* 235:    */     throws SQLException, IOException
/* 236:    */   {
/* 237:220 */     dropExistingTables();
/* 238:221 */     Statement stat = this.conn.createStatement();
/* 239:222 */     stat.executeUpdate("CREATE TABLE Persoon(PersoonNr INT NOT NULL,Voornamen VARCHAR(60) NOT NULL,Tussenvoegsel VARCHAR(10),Achternaam VARCHAR(60) NOT NULL,Geslacht CHAR(1) NOT NULL,Geboortedatum CHAR(10) NOT NULL,Geboorteplaats VARCHAR(20) NOT NULL,Ouders INT,PRIMARY KEY (PersoonNr))");
/* 240:    */     
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:233 */     stat.executeUpdate("CREATE TABLE Gezin(GezinNr INT NOT NULL,Ouder1 INT NOT NULL,Ouder2 INT,Huwelijk CHAR(10),Scheiding CHAR(10),PRIMARY KEY (GezinNr) )");
/* 251:    */   }
/* 252:    */   
/* 253:    */   private void dropExistingTables()
/* 254:    */   {
/* 255:    */     try
/* 256:    */     {
/* 257:252 */       Statement stat = this.conn.createStatement();
/* 258:253 */       stat.executeUpdate("ALTER TABLE Persoon DROP FOREIGN KEY fkOuders");
/* 259:    */       
/* 260:255 */       stat.executeUpdate("ALTER TABLE Gezin DROP FOREIGN KEY fkOuder1");
/* 261:    */       
/* 262:257 */       stat.executeUpdate("ALTER TABLE Gezin DROP FOREIGN KEY fkOuder2");
/* 263:    */       
/* 264:259 */       stat.executeUpdate("DROP TABLE Persoon");
/* 265:260 */       stat.executeUpdate("DROP TABLE Gezin");
/* 266:    */     }
/* 267:    */     catch (SQLException exc)
/* 268:    */     {
/* 269:262 */       System.err.println(exc.getMessage());
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   private void closeConnection()
/* 274:    */   {
/* 275:    */     try
/* 276:    */     {
/* 277:268 */       this.conn.close();
/* 278:269 */       this.conn = null;
/* 279:    */     }
/* 280:    */     catch (SQLException ex)
/* 281:    */     {
/* 282:271 */       System.err.println(ex.getMessage());
/* 283:    */     }
/* 284:    */   }
/* 285:    */ }



/* Location:           D:\downloads\StamboomGUI.jar

 * Qualified Name:     stamboom.storage.DatabaseMediator

 * JD-Core Version:    0.7.0.1

 */