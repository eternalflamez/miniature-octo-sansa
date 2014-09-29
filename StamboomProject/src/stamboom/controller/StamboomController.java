/*   1:    */ package stamboom.controller;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.Properties;
/*   7:    */ import stamboom.domain.Administratie;
/*   8:    */ import stamboom.storage.DatabaseMediator;
/*   9:    */ import stamboom.storage.IStorageMediator;
/*  10:    */ import stamboom.storage.SerializationMediator;
/*  11:    */ 
/*  12:    */ public class StamboomController
/*  13:    */ {
/*  14:    */   private Administratie admin;
/*  15:    */   private IStorageMediator storageMediator;
/*  16:    */   
/*  17:    */   public StamboomController()
/*  18:    */   {
/*  19: 34 */     this.admin = new Administratie();
/*  20:    */     
/*  21: 36 */     this.storageMediator = null;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Administratie getAdministratie()
/*  25:    */   {
/*  26: 40 */     return this.admin;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void clearAdministratie()
/*  30:    */   {
/*  31: 47 */     this.admin = new Administratie();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void serialize(File bestand)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 58 */     if (!(this.storageMediator instanceof SerializationMediator)) {
/*  38: 59 */       this.storageMediator = new SerializationMediator();
/*  39:    */     }
/*  40: 61 */     Properties props = new Properties();
/*  41: 62 */     props.put("file", bestand);
/*  42: 63 */     this.storageMediator.configure(props);
/*  43: 64 */     this.storageMediator.save(this.admin);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void deserialize(File bestand)
/*  47:    */     throws IOException
/*  48:    */   {
/*  49: 76 */     if (!(this.storageMediator instanceof SerializationMediator)) {
/*  50: 77 */       this.storageMediator = new SerializationMediator();
/*  51:    */     }
/*  52: 79 */     Properties props = new Properties();
/*  53: 80 */     props.put("file", bestand);
/*  54: 81 */     this.storageMediator.configure(props);
/*  55: 82 */     this.admin = this.storageMediator.load();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void loadFromDatabase()
/*  59:    */     throws IOException
/*  60:    */   {
/*  61: 92 */     initDatabaseMedium();
/*  62: 93 */     this.admin = this.storageMediator.load();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void saveToDatabase()
/*  66:    */     throws IOException
/*  67:    */   {
/*  68:103 */     initDatabaseMedium();
/*  69:104 */     this.storageMediator.save(this.admin);
/*  70:    */   }
/*  71:    */   
/*  72:    */   private void initDatabaseMedium()
/*  73:    */     throws IOException
/*  74:    */   {
/*  75:109 */     if (!(this.storageMediator instanceof DatabaseMediator))
/*  76:    */     {
/*  77:110 */       Properties props = new Properties();
/*  78:111 */       FileInputStream in = new FileInputStream("database.properties");Throwable localThrowable2 = null;
/*  79:    */       try
/*  80:    */       {
/*  81:112 */         props.load(in);
/*  82:    */       }
/*  83:    */       catch (Throwable localThrowable1)
/*  84:    */       {
/*  85:111 */         localThrowable2 = localThrowable1;throw localThrowable1;
/*  86:    */       }
/*  87:    */       finally
/*  88:    */       {
/*  89:113 */         if (in != null) {
/*  90:113 */           if (localThrowable2 != null) {
/*  91:    */             try
/*  92:    */             {
/*  93:113 */               in.close();
/*  94:    */             }
/*  95:    */             catch (Throwable x2)
/*  96:    */             {
/*  97:113 */               localThrowable2.addSuppressed(x2);
/*  98:    */             }
/*  99:    */           } else {
/* 100:113 */             in.close();
/* 101:    */           }
/* 102:    */         }
/* 103:    */       }
/* 104:114 */       this.storageMediator = new DatabaseMediator(props);
/* 105:    */     }
/* 106:    */   }
/* 107:    */ }


/* Location:           D:\downloads\StamboomGUI.jar
 * Qualified Name:     stamboom.controller.StamboomController
 * JD-Core Version:    0.7.0.1
 */