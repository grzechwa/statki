
package statki;

/**
 *
 * @author greg
 */
public class Statek extends Arsenal{
    
    /**
     * Konstruktor.
     * Ustawia wielkość statku.
     * @param size 
     */
    public Statek(int size){
        super();
        this.size = size;
        this.constSize = size;
        obiektId = id++;
    }
    
    @Override
    /**
     * Informacja na temat obiektu.
     */
    public void przedstawSie(){
        System.out.println("");
        System.out.println("\tStatek o nr.:  " + this.getId());
        System.out.println("\tWielkosc:      " + this.getConstSize());
        System.out.println("\tStan:          " + this.getAktStan());
    }
}
