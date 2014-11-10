package statki;

import java.util.*;
import java.io.*;

/**
 *
 * @author Grzegorz Zawal
 */
public class Panel {
	
	public Panel(){}

    /**
     * Wyświetla na wyjściu 2 tablice, gracza oraz przeciwnika.
     * Tablica gracza pokazuje tylko układ statków, tablica przeciwnika
     * jest początkowo pusta, zapełniana będzie po kolejnym ruchu gracza.
     * W zależności od powodzenia - pudło lub trafiony.
     */
    public static void pokazPanel(Plansza plansza1, Plansza plansza2) {
            String znak = " ";
            
            System.out.print("      -- TWOJA PLANSZA --");
            System.out.println("        --  PRZECIWNIK  --\n");
            
            System.out.print("    ");
            for (int k = 0; k < plansza1.plansza.length; k++) {
                System.out.printf("%2s", k);
            }
            
            rysujPanelNumeracja(plansza1);
            rysujPanelTopBottom(plansza1);

            for (int i = 0; i < plansza1.plansza.length; i++) {
                System.out.printf("%2s ", i);
                System.out.print("|");

                for (int j = 0; j < plansza1.plansza[i].length; j++) {
                    if ((plansza1.plansza[i][j] > 0) && (plansza1.plansza[i][j] < 5)) {
                        System.out.printf("%2s", Character.toChars(88)[0]);
                    } else if (plansza1.plansza[i][j] == 12) {
                        System.out.printf("%2s", Character.toChars(126)[0]);
                    } else if ((plansza1.plansza[i][j] == 7)) {
                        System.out.printf("%2s", Character.toChars(45)[0]);
                    } else if ((plansza1.plansza[i][j] == 8)) {
                        System.out.printf("%2s", Character.toChars(79)[0]);
                    } else if ((plansza1.plansza[i][j] == 9)) {
                        System.out.printf("%2s", Character.toChars(35)[0]);
                    } else if ((plansza1.plansza[i][j] > 20) && (plansza1.plansza[i][j] < 25)) {
                        System.out.printf("%2s", Character.toChars(86)[0]);
                    } else if ((plansza1.plansza[i][j] > 30) && (plansza1.plansza[i][j] < 35)) {
                        System.out.printf("%2s", Character.toChars(84)[0]);
                    }
                    
                    else {
                        System.out.printf("%2s", znak);
                    }
                }

                System.out.print(" | ");
                System.out.printf("%2s ", i);
                System.out.print("|");

                for (int j = 0; j < plansza2.plansza[i].length; j++) {
                    if ((plansza2.plansza[i][j] < 5) && (plansza2.plansza[i][j] >= 0)) {
                        System.out.printf("%2s", znak);
                    } else if (plansza2.plansza[i][j] == 12) {
                        System.out.printf("%2s", znak);
                    } else if ((plansza2.plansza[i][j] == 9)) {
                        System.out.printf("%2s", znak);
                    } else if ((plansza2.plansza[i][j] > 20) && (plansza1.plansza[i][j] < 25)) {
                        System.out.printf("%2s", znak);
                    }else if ((plansza1.plansza[i][j] > 30) && (plansza1.plansza[i][j] < 35)) {
                        System.out.printf("%2s", znak);
                    }else if (plansza2.plansza[i][j] == 8) {
                        System.out.printf("%2s", Character.toChars(79)[0]);
                    }else if (plansza2.plansza[i][j] == 7) {
                        System.out.printf("%2s", Character.toChars(45)[0]);
                    }
                }
                System.out.print(" | ");
                System.out.println("");
            }

            rysujPanelTopBottom(plansza1);
            System.out.println("   [z] - Zapisz gre         [q] - przerwij gre");
    }
    
    /**
     * Wypisuje numeracje planszy.
     * @param plansza 
     */
    public static void rysujPanelNumeracja(Plansza plansza){
        System.out.print("       ");
            for(int k = 0; k < plansza.plansza.length; k++){
                System.out.printf("%2s", k);
            }
        System.out.println("");
    }
    
    /**
     * Rysuje dolne obramowanie planszy.
     * @param plansza 
     */
    public static void rysujPanelTopBottom(Plansza plansza){
        System.out.print("    ");
            for (int i = 0; i < plansza.plansza.length; i++) {
                System.out.print("--");
            }
        System.out.print("- ");

        System.out.print("    ");
            for (int i = 0; i < plansza.plansza.length; i++) {
                System.out.print("--");
            }
        System.out.println("- ");
    }

    /**
     * Informacje o ilosci pojazdow i ilosci oddanych strzlow w grze.
     */
    public static void infoPanel(int ilePojazdow, int liczStrzal){
        System.out.println("\n --------------------------------------------- ");
        System.out.println("   Pozostalo pojazdow: " + ilePojazdow + "\tStrzal nr: " + liczStrzal);
        System.out.println(" --------------------------------------------- ");
    }
}
