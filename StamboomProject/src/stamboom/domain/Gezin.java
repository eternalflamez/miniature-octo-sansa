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
/*  13:    */ public class Gezin
/*  14:    */   implements Serializable
/*  15:    */ {
/*  16:    */   private final int nr;
/*  17:    */   private final Persoon ouder1;
/*  18:    */   private final Persoon ouder2;
/*  19:    */   private final List<Persoon> kinderen;
/*  20:    */   private transient ObservableList<Persoon> observableKinderen;
/*  21:    */   private Calendar huwelijksdatum;
/*  22:    */   private Calendar scheidingsdatum;
/*  23:    */   
/*  24:    */   Gezin(int gezinsNr, Persoon ouder1, Persoon ouder2)
/*  25:    */   {
/*  26: 47 */     if (ouder1 == null) {
/*  27: 48 */       throw new RuntimeException("Eerste ouder mag niet null zijn");
/*  28:    */     }
/*  29: 50 */     if (ouder1 == ouder2) {
/*  30: 51 */       throw new RuntimeException("ouders hetzelfde");
/*  31:    */     }
/*  32: 53 */     this.nr = gezinsNr;
/*  33: 54 */     this.ouder1 = ouder1;
/*  34: 55 */     this.ouder2 = ouder2;
/*  35: 56 */     this.kinderen = new ArrayList();
/*  36:    */     
/*  37: 58 */     this.observableKinderen = FXCollections.observableList(this.kinderen);
/*  38: 59 */     this.huwelijksdatum = null;
/*  39: 60 */     this.scheidingsdatum = null;
/*  40:    */   }
/*  41:    */   
/*  42:    */   private void readObject(ObjectInputStream is)
/*  43:    */     throws IOException, ClassNotFoundException
/*  44:    */   {
/*  45: 67 */     is.defaultReadObject();
/*  46: 68 */     this.observableKinderen = FXCollections.observableList(this.kinderen);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public ObservableList<Persoon> getKinderen()
/*  50:    */   {
/*  51: 76 */     return FXCollections.unmodifiableObservableList(this.observableKinderen);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int aantalKinderen()
/*  55:    */   {
/*  56: 84 */     return this.kinderen.size();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getNr()
/*  60:    */   {
/*  61: 92 */     return this.nr;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Persoon getOuder1()
/*  65:    */   {
/*  66: 99 */     return this.ouder1;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Persoon getOuder2()
/*  70:    */   {
/*  71:106 */     return this.ouder2;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String toString()
/*  75:    */   {
/*  76:117 */     StringBuilder s = new StringBuilder();
/*  77:118 */     s.append(this.nr).append(" ");
/*  78:119 */     s.append(this.ouder1.getNaam());
/*  79:120 */     if (this.ouder2 != null)
/*  80:    */     {
/*  81:121 */       s.append(" met ");
/*  82:122 */       s.append(this.ouder2.getNaam());
/*  83:    */     }
/*  84:124 */     if (heeftGetrouwdeOudersOp(Calendar.getInstance())) {
/*  85:125 */       s.append(" ").append(StringUtilities.datumString(this.huwelijksdatum));
/*  86:    */     }
/*  87:127 */     return s.toString();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Calendar getHuwelijksdatum()
/*  91:    */   {
/*  92:134 */     return this.huwelijksdatum;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Calendar getScheidingsdatum()
/*  96:    */   {
/*  97:141 */     return this.scheidingsdatum;
/*  98:    */   }
/*  99:    */   
/* 100:    */   boolean setScheiding(Calendar datum)
/* 101:    */   {
/* 102:153 */     if ((this.scheidingsdatum == null) && (this.huwelijksdatum != null) && (datum.after(this.huwelijksdatum)))
/* 103:    */     {
/* 104:155 */       this.scheidingsdatum = datum;
/* 105:156 */       return true;
/* 106:    */     }
/* 107:158 */     return false;
/* 108:    */   }
/* 109:    */   
/* 110:    */   boolean setHuwelijk(Calendar datum)
/* 111:    */   {
/* 112:173 */     if (this.huwelijksdatum != null) {
/* 113:174 */       return false;
/* 114:    */     }
/* 115:176 */     if (this.ouder2 == null) {
/* 116:177 */       return false;
/* 117:    */     }
/* 118:179 */     if ((this.ouder1.isGetrouwdOp(datum)) || (this.ouder2.isGetrouwdOp(datum))) {
/* 119:180 */       return false;
/* 120:    */     }
/* 121:182 */     this.huwelijksdatum = datum;
/* 122:183 */     return true;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String beschrijving()
/* 126:    */   {
/* 127:194 */     StringBuilder s = new StringBuilder();
/* 128:195 */     s.append(toString());
/* 129:197 */     if (!this.kinderen.isEmpty())
/* 130:    */     {
/* 131:198 */       s.append("; kinderen:");
/* 132:199 */       for (Persoon kind : this.kinderen) {
/* 133:200 */         s.append(" -").append(kind.getVoornamen());
/* 134:    */       }
/* 135:    */     }
/* 136:203 */     return s.toString();
/* 137:    */   }
/* 138:    */   
/* 139:    */   void breidUitMet(Persoon kind)
/* 140:    */   {
/* 141:207 */     if (!this.kinderen.contains(kind)) {
/* 142:209 */       this.observableKinderen.add(kind);
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean heeftGetrouwdeOudersOp(Calendar datum)
/* 147:    */   {
/* 148:220 */     return (isHuwelijkOp(datum)) && ((this.scheidingsdatum == null) || (this.scheidingsdatum.after(datum)));
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean isHuwelijkOp(Calendar datum)
/* 152:    */   {
/* 153:231 */     if (this.huwelijksdatum == null) {
/* 154:232 */       return false;
/* 155:    */     }
/* 156:234 */     return this.huwelijksdatum.before(datum);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean isOngehuwd()
/* 160:    */   {
/* 161:242 */     return this.huwelijksdatum == null;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean heeftGescheidenOudersOp(Calendar datum)
/* 165:    */   {
/* 166:252 */     if (this.huwelijksdatum == null) {
/* 167:253 */       return false;
/* 168:    */     }
/* 169:255 */     if (this.huwelijksdatum.after(datum)) {
/* 170:256 */       return false;
/* 171:    */     }
/* 172:258 */     return (this.scheidingsdatum != null) && (this.scheidingsdatum.before(datum));
/* 173:    */   }
/* 174:    */ }


/* Location:           D:\downloads\StamboomGUI.jar
 * Qualified Name:     stamboom.domain.Gezin
 * JD-Core Version:    0.7.0.1
 */