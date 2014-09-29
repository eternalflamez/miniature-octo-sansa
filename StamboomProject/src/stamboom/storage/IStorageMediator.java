package stamboom.storage;

import java.io.IOException;
import java.util.Properties;
import stamboom.domain.Administratie;

public abstract interface IStorageMediator
{
  public abstract Administratie load()
    throws IOException;
  
  public abstract void save(Administratie paramAdministratie)
    throws IOException;
  
  public abstract boolean configure(Properties paramProperties);
  
  public abstract Properties config();
  
  public abstract boolean isCorrectlyConfigured();
}


/* Location:           D:\downloads\StamboomGUI.jar
 * Qualified Name:     stamboom.storage.IStorageMediator
 * JD-Core Version:    0.7.0.1
 */