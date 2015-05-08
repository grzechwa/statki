package statki;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Grzegorz Zawal
 */
public class Menu {
    private static int wypelnijArg 			= 0;
    public static boolean zapis 			= false;
    public static boolean przerwanie 		= false;
    
    static Plansza plansza1 = new Plansza();
    static Plansza plansza2 = new Plansza();
    static Gra gra 			= new Gra(plansza1, plansza2);
    
    public void menu() throws InterruptedException, IOException, ClassNotFoundException{

        Scanner wej = new Scanner(System.in);
        int liczba = 0;
        
                do{
                    System.out.println("    ----------------------------------------"   );
                    System.out.println("                    MENU                "       );
                    System.out.println("    [1] - Gra           [0] - koniec"  );
                    System.out.println("    ----------------------------------------"   );
                        
                     liczba = pobierzLiczbe("Wybierz");
                        switch(liczba){
                            case 1: gra();break;
                            case 0: break;
                            default: System.out.println("Nieprawidlowa wartosc");
                        }
                }while(liczba!=0);

        wej.close();
    }
 /*   
    public void wczytajGre(	Plansza plansza1, 
    						Plansza plansza2, 
    						Gra gra) throws FileNotFoundException, IOException, ClassNotFoundException, InterruptedException{
       
                if(		  (Plansza.sprPlik("plansza1.dat"))
                        &&(Plansza.sprPlik("plansza2.dat"))
                        &&(Plansza.sprPlik("gra.dat"))
                	){
                    plansza1 = Archiwum.odczytPlansza("plansza1.dat");
                    plansza2 = Archiwum.odczytPlansza("plansza2.dat");
                    gra = Archiwum.odczytGra("gra.dat");
                    gra.start(plansza1, plansza2, gra.getArg()); 
                }
                else{
                    System.out.println("Wystapil blad, nie mozna wczytac gry!");
                }
    }
 */
    // menu item - domyslna gra
    public void gra() throws InterruptedException, IOException{
                   
            plansza1.wypelnij(wypelnijArg);
            plansza2.wypelnij(wypelnijArg);
            
            plansza1.rozmiesc();
            plansza2.rozmiesc();  

            gra.strzalyWpelnij(plansza1); 
            gra.start(plansza1, plansza2, 1);

    }
    
    // metoda do pobierania liczb
    public static int pobierzLiczbe(String komunikat) {

        Scanner sc = new Scanner(System.in);
        System.out.println(komunikat);
        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.out.println("Blad. Podaj raz jeszcze: ");
        }

        return sc.nextInt();

    }
}
