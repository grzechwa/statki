package statki;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Grzegorz Zawal
 */
public class Menu {
    public static boolean zapis 			= false;
    public static boolean przerwanie 		= false;
    
    Gra gra 			= new Gra(Gra.plansza1, Gra.plansza2);
        
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
        					case 1: gra.gra();
                            case 0: break;
                            default: System.out.println("Nieprawidlowa wartosc");
                        }
                }while(liczba!=0);
        wej.close();

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
