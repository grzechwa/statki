/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statki;
import java.io.*;
/**
 *
 * @author zawalg
 */
public class Arsenal implements Serializable{
    protected static int id = 0;
    protected int obiektId = 0;
    protected static String[] stan = {"PRZEMIESZCZA SIE", "TRAFIONY", "ZATOPIONY", "TRAFIONY-ZATOPIONY"};
    protected String aktualnyStan = stan[0];
    protected int size;
    protected int constSize;
    protected int[][] pozycja;
    protected int posX; 
    protected int posY;
    protected Plansza plansza;
    
    /**
     * Ustawia wartość statycznej zmiennej id.
     *
     * @param n
     */
    public void setId(int n) {
        this.id = n;
    }
    
    /**
     * Pobiera wewnętrzne id obiektu.
     * @return 
     */
    public  int getId(){
        return this.obiektId;
    }
    
    /**
     * Zwraca stałą wielkość statku.
     * @return 
     */
    public int getStatekSize(){
        return size;
    }
    
    /**
     * Modyfikuje wielkość obiektu w zależności od syturacji na planszy.
     * @param size 
     */
    public int setStatekSize(int size){
        this.size = size;
        return size;
    }
    
    /**
     * Zwraca stala wielkosc obiektu.
     * @return 
     */
    public int getConstSize(){
        return constSize;
    }
    
    /**
     * Zwraca aktualną wielkość obiektu.
     */
    public int getSize(){
          return size;
    }
    
    /**
     * Zwraca współrzędną posX obiektu.
     * @return 
     */
    public int getPozycjaX(){
        return this.posX;
    }
    
    /**
     * Zwraca współrzędną posY obiektu.
     * @return 
     */
    public int getPozycjaY(){
        return this.posY;
    }
    
    /**
     * Ustawia współrzędne obiektu.
     * Wartości pochodzą za planszy po rozmieszczeniu obiektów.
     * @param posX
     * @param posY 
     */
    public void setPozycja(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
    
    /**
     * Zwraca aktualny stan obiektu.
     * Wartość pochodzi z tablicy stan.
     * @return 
     */
    public String getAktStan(){
        return this.aktualnyStan;
    }
    
    /**
     * Ustwia aktualny stan obiektu.
     * @param stan 
     */
    public void setAktStan(String stan){
        aktualnyStan = stan;
    }
    
    /**
     * Pobiera z tablicy stan wskazany w parametrze element.
     * @param n
     * @return 
     */
    public String getStan(int element){
        return stan[element];
    }
    
    /**
     * Wyswietla informacje o obiekcie.
     * Metoda potrzebna w kalsie bazowej do korzystania z polimorfizmu.
     * Klasy pochodne nadpisuja te metode.
     */
    public void przedstawSie(){        
    }
}
