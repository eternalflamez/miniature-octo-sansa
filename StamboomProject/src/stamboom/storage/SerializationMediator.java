/*  1:   */ package stamboom.storage;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.FileInputStream;
/*  5:   */ import java.io.FileOutputStream;
/*  6:   */ import java.io.IOException;
/*  7:   */ import java.io.ObjectInputStream;
/*  8:   */ import java.io.ObjectOutputStream;
/*  9:   */ import java.util.Properties;
/* 10:   */ import stamboom.domain.Administratie;
/* 11:   */ 
/* 12:   */ public class SerializationMediator
/* 13:   */   implements IStorageMediator
/* 14:   */ {
/* 15:   */   private Properties props;
/* 16:   */   
/* 17:   */   public SerializationMediator()
/* 18:   */   {
/* 19:28 */     this.props = null;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Administratie load()
/* 23:   */     throws IOException
/* 24:   */   {
/* 25:33 */     if (!isCorrectlyConfigured()) {
/* 26:34 */       throw new RuntimeException("Serialization mediator isn't initialized correctly.");
/* 27:   */     }
/* 28:38 */     File file = (File)this.props.get("file");
/* 29:39 */     FileInputStream stream = new FileInputStream(file);
/* 30:40 */     ObjectInputStream input = new ObjectInputStream(stream);
/* 31:   */     try
/* 32:   */     {
/* 33:42 */       return (Administratie)input.readObject();
/* 34:   */     }
/* 35:   */     catch (ClassNotFoundException ex)
/* 36:   */     {
/* 37:45 */       throw new IOException("file " + file.getName() + " doesn't contain serialized object of class " + "stamboom.domein.Administratie. \n" + ex.getMessage());
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void save(Administratie admin)
/* 42:   */     throws IOException
/* 43:   */   {
/* 44:54 */     if (!isCorrectlyConfigured()) {
/* 45:55 */       throw new RuntimeException("Serialization mediator isn't initialized correctly.");
/* 46:   */     }
/* 47:59 */     FileOutputStream stream = new FileOutputStream((File)this.props.get("file"));
/* 48:60 */     ObjectOutputStream output = new ObjectOutputStream(stream);
/* 49:61 */     output.writeObject(admin);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean configure(Properties props)
/* 53:   */   {
/* 54:67 */     this.props = props;
/* 55:68 */     return isCorrectlyConfigured();
/* 56:   */   }
/* 57:   */   
/* 58:   */   public Properties config()
/* 59:   */   {
/* 60:73 */     return this.props;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public boolean isCorrectlyConfigured()
/* 64:   */   {
/* 65:83 */     if (this.props == null) {
/* 66:84 */       return false;
/* 67:   */     }
/* 68:86 */     if (this.props.containsKey("file")) {
/* 69:87 */       return this.props.get("file") instanceof File;
/* 70:   */     }
/* 71:89 */     return false;
/* 72:   */   }
/* 73:   */ }


/* Location:           D:\downloads\StamboomGUI.jar
 * Qualified Name:     stamboom.storage.SerializationMediator
 * JD-Core Version:    0.7.0.1
 */