/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statki;
import java.util.*;
import java.io.*;

/**
 *
 * @author greg
 */
public class Archiwum{
    
     /**
     * Zapisuje stan obiektu plansza.
     * @param nazwaPl
     * @param plansza
     * @throws IOException 
     */
     public static void zapisPlansza(String nazwaPl, Plansza plansza) throws IOException{
         ObjectOutputStream pl=null;
                    try{
                        pl=new ObjectOutputStream(new FileOutputStream(nazwaPl));
                        pl.writeObject(plansza);
                        pl.flush();
                    }
                    catch(Exception ex){
                        System.out.println("Nie mozna zapisac");
                    }
                    finally{
                        if(pl!=null)
                            pl.close();
                    }
     }
    
     /**
      * Zapisuje stan obiektu gra.
      * @param nazwaPl
      * @param gra
      * @throws FileNotFoundException
      * @throws IOException 
      */
     public static void zapisGra(String nazwaPl, Gra gra) throws FileNotFoundException, IOException{
         ObjectOutputStream pl=null;
                try{
                    pl=new ObjectOutputStream(new FileOutputStream(nazwaPl));
      
                    pl.writeObject(gra);
                    pl.flush();
                }
                catch(Exception ex){
                    System.out.println("Nie mozna zapisac");
                }
                finally{
                    if(pl!=null)
                        pl.close();
                }   
     }
     

     /**
      * Odczytuje stan obiektu gra.
      * @param nazwaPl
      * @return gra
      * @throws FileNotFoundException
      * @throws IOException
      * @throws ClassNotFoundException 
      */
     public static Gra odczytGra(String nazwaPl) throws FileNotFoundException, IOException, ClassNotFoundException{
         
        ObjectInputStream pl2=null;
        Gra gra = null;

        try{
            pl2=new ObjectInputStream(new FileInputStream(nazwaPl));
            gra = (Gra)pl2.readObject();
            return gra;
        } catch (EOFException ex) {
            System.out.println("Koniec pliku");
        }
 
        finally{
            if(pl2!=null)
                pl2.close();
        }
 
        return gra;
     }
     
     /**
      * Odczytuje stan obiektu plansza.
      * 
      * @param nazwaPl
      * @return plansza
      * @throws FileNotFoundException
      * @throws IOException
      * @throws ClassNotFoundException 
      */
     public static Plansza odczytPlansza(String nazwaPl) throws FileNotFoundException, IOException, ClassNotFoundException{
         
        ObjectInputStream pl2=null;
        Plansza plansza = null;

        try{
            pl2=new ObjectInputStream(new FileInputStream(nazwaPl));
            plansza = (Plansza)pl2.readObject();
            return plansza;
        } catch (EOFException ex) {
            System.out.println("Koniec pliku");
        }

        finally{
            if(pl2!=null)
                pl2.close();
        }
        return plansza;
     }

}
