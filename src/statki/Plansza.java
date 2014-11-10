/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statki;
import java.util.*;
import java.io.*;

/**
 *
 * @author Grzegorz Zawal
 */
public class Plansza implements Serializable{

        private int posX;                       
        private int posY;
        public 	int dlugoscH;
        public 	int dlugoscV;
        public 	int h;
        private int[] tabWsp;
        private int uklad;
        private String[] h_v = {"h", "v"};
        private boolean ustawione;
        
        private int SIZE = 10;
        
        public int[][] plansza;
        private int pozycjaTrafionegoStatku;
        
        Integer[] pozycje;
        Integer[][] posTraf = new Integer[1][2];
        
        public Integer[][] pozStat;
        public List<Integer[][] > pozycjeStatki = new ArrayList<Integer[][]>();            // = new ArrayList<Integer[]>(); // przechowuje pozycje statkow
        public Integer[] idPozycje;                                                        // pomocnicza zmienna dla listy
        public List<Integer[][]> pelnaListaStrzalow = new ArrayList<Integer[][]>();        // wszystkie strzaly
        public List<Integer[][]> otoczenieListaStrzalow = new ArrayList<Integer[][]>();    // strzaly wokol trafionegeo
        public List<Integer[][]> precyzjaListaStrzalow = new ArrayList<Integer[][]>();     // mozliwe strzaly wokol trafionego
        public List<Integer[][]> h_vListaStrzalow = new ArrayList<Integer[][]>();          // mozliwe strzaly wokol trafionego w poziomie i pionie
        public List<Integer[][]> listaTrafionych = new ArrayList<Integer[][]>();           // lista strzalow trafionych
       
        private boolean wyjscie;                                                     	   // niezbędne do wyjscie rSpr...
        
        Arsenal[] statek = {                                                   			   // tablica wszystkich obiektow
                            new Statek(4),
                            new Statek(3),
                            new Statek(3),
                            new Statek(2),
                            new Statek(2),
                            new Statek(2),
                            new Statek(1),
                            new Statek(1),
                            new Statek(1),
                            new Statek(1)
                            };

        /**
         * Konstruktor.
         */
        Plansza(){
            dlugoscH 	= 2;
            dlugoscV 	= 2;
            wyjscie 	= true;
            h 			= 0;
            uklad 		= 0;
            ustawione 	= false;
            posX 		= 0; 
            posY 		= 0;
            tabWsp 		= new int[2];
            plansza 	= new int[getSizePlansza()][getSizePlansza()];
        }
        
        public void setSizePlansza(int nowySIZE){
            this.SIZE = nowySIZE;
        }
        
        public int getSizePlansza(){
            return this.SIZE;
        }
        
        /**
         * Ustawia pozycję statku w tablicy.
         * @param pos 
         */
        public void setPozTrafStat(int pos){
            this.pozycjaTrafionegoStatku = pos;
        }
        
        /**
         * Zwraca pozycję statku w tablicy.
         * @return 
         */
        public int getPozTrafStat(){
            return this.pozycjaTrafionegoStatku;
        }
        
        /**
         * Modyfikuje zawartość pols posX.
         * @param posX 
         */
        public void setPosX(int posX){
            this.posX = posX;
        }
    
        /**
         * Modyfikuje zawartość pols posY.
         * @param posX 
         */
        public void setPosY(int posY){
            this.posY = posY;
        }
        
        /**
         * Zwraca id trafionego statku.
         * @return 
         */
        public int getStatekId(){
             return this.getStatekId();
        }
   
        /**
         * Pobiera wartość posX.
         * @return 
         */
        public int getPosX(){
            return posX;
        }
        
        /**
         * Pobiera wartość posY.
         * @return 
         */
        public int getPosY(){
            return posY;
        }
       
        /**
         * Pobiera 1 element tablicy przechowującej współrzędne.
         * @return 
         */
        public int gettabWsp0(){
            return tabWsp[0];
        }
        
        /**
         * Pobiera 2 element tablicy przechowującej współrzędne.
         * @return 
         */
        public int gettabWsp1(){
            return tabWsp[1];
        }
        
        /**
         * Ustawia 1 element tablicy.
         * Parametrem jest 1 współrzędna posX.
         * @param liczba 
         */
        public void settabWsp0(int liczba){
            tabWsp[0] = liczba;
        }
        
        /**
         * Ustawia 2 element tablicy.
         * Parametrem jest 2 współrzędna posY.
         * @param liczba 
         */
        public void settabWsp1(int liczba){
            tabWsp[1] = liczba;
        }
        
        /**
         * Tworzy liste pozycji statków na planszy.
         * Wypełnia listę pozycjeStatki współrzędnymi rozmieszczonych
         * statków na planszy.
         * @param stat
         * @param posX
         * @param posY 
         */
        public void addPozStat(Arsenal stat, int posX, int posY){
                  pozycje = new Integer[2];
                  idPozycje = new Integer[1];
                  this.pozycje[0] = posX;
                  this.pozycje[1] = posY;
                  this.idPozycje[0] = stat.getId();
                  
                  pozStat = new Integer[1][3];
                  pozStat[0][0] = pozycje[0];
                  pozStat[0][1] = pozycje[1];
                  pozStat[0][2] = idPozycje[0];
                  this.pozycjeStatki.add(pozStat);
        }
        
        /**
         * Wypelnia tabliece wartosciami liczbowymi.
         */
        public void wypelnij(int arg) {
            plansza = new int[getSizePlansza()][getSizePlansza()];
            int fragment1 = 2;           
            int fragment2 = 5;
           for (int i = 0; i < plansza.length; i++) {
                            for (int j = 0; j < plansza[i].length; j++) {
                                        plansza[i][j] = 0;
                            }
                        };

        }
        
        /**
         * Wielkość tablicy obiektów - statek.
         * Zwraca wartosc length tablicy statek.
         * @param statek
         * @return 
         */
        public int getWielkoscStatek(Arsenal[] statek){
            return statek.length;
        }
        
        /**
         * Sprawdza pola wokół podanej pozycji w posX-1 oraz posY-1.
         * H w nazwzeie metody oznacza, że dotyczy ona statku o układzie horyzontalnym.
         * W przpadku natrafienia na pole z wartością różną od zera zwrac false. 
         * Gdy wszystkie pola posiadają wartość 0, zwrac true, następuje wyjście z pętli
         * sprawdzającej, a następnie uruchamiania jest metoda ustawienia statku na pozycji
         * posX oraz  posY
         * @param statek
         * @param posX
         * @param posY
         * @return statek.length
         */
        public boolean rSprPlanszeH(Arsenal statek, int posX, int posY) {
           
        if(posX<0) {posX++; h++; dlugoscH--;}                 // kontroluje ilosc wierszy, gdy posX = 0
        if(posX==h+(1+dlugoscH)) return false;      
                                                                            // kontroluje ilosc wierszy dla statku poziomego -h 
        if(posY<0) { posY++; dlugoscV--;}                     // kontroluje ilosc kolumn, gdy posY = 0
        if((plansza.length)-posY<statek.getStatekSize()+dlugoscV)dlugoscV--; // kontroluje ilosc kolumn, gdy posY = length
        if(posY+statek.getStatekSize()>plansza.length-1)return false;          // kontroluje ilosc miejsca na planszy dla statku

        if(posX>plansza.length-1) return false;
        if(posY>plansza.length-1) return false;

        int k = posY;                                      // zmienna k będzie modyfikowana w pętli
        int rozmiar = posY+statek.getStatekSize()+dlugoscV;                  // miejsce graniczne pętli
        
        while(rozmiar>plansza.length)                   // kontroluje przekroczenie plansza.length
            rozmiar--;
                for (; k < rozmiar; k++) {              // pętla dla kolumn, sprawdzanie czy mozna ustawic statek
                    if(statek instanceof Statek){
                        if (plansza[posX][k] != 0) {
                            wyjscie = false;
                            return false;
                        }
                    }
                }
                ++posX;
        rSprPlanszeH(statek, posX, posY);
        if(wyjscie==false)return false;

        return true;
    }
        
        /**
         * Sprawdza pola wokół podanej pozycji w @param posX-1 oraz @param posY-1.
         * V w nazwie metody oznacza, że dotyczy ona statku o układzie horyzontalnym.
         * W przpadku natrafienia na pole z wartością różną od zera zwrac false. 
         * Gdy wszystkie pola posiadają wartość 0, zwrac true, następuje wyjście z pętli
         * sprawdzającej, a następnie uruchamiania jest metoda ustawienia statku na pozycji
         * @param posX oraz @param posY
         * @param statek
         * @param posX
         * @param posY
         * @return 
         */
        public boolean rSprPlanszeV(Arsenal statek, int posX, int posY){
            
        if(posX<0) {posX++; h++; dlugoscH--;}                 // kontroluje ilosc wierszy, gdy posX = 0
        if(posX==h+(statek.getStatekSize()+dlugoscH)) return false;          // kontroluje ilosc wierszy dla statku poziomego -h 
        if(posY<0) { posY++; dlugoscV--;}                     // kontroluje ilosc kolumn, gdy posY = 0
        int ogranicznik = (plansza.length-1)-h;
        if(ogranicznik<statek.getStatekSize()) return false;

        if(posX>plansza.length-1) return false;
        if(posY>plansza.length-1) return false;
    
        int k = posY;                                      // zmienna k będzie modyfikowana w pętli
        int rozmiar = posY+1+dlugoscV;                     // miejsce graniczne pętli
        
        while(rozmiar>plansza.length)                   // kontroluje przekroczenie plansza.length
            rozmiar--;
                for (; k < rozmiar; k++) {              // pętla dla kolumn, sprawdzanie czy mozna ustawic statek
                if(statek instanceof Statek){
                    if (plansza[posX][k] != 12) {
                            wyjscie = false;
                            return false;
                        }
                    }
                }
                setPosX(++posX);
        rSprPlanszeV(statek, posX, posY);
        if(wyjscie==false)return false;

        return true;
    }    
        
        /**
         * Dla obiektu plansza uruchamia metody rSprPlanszaH i rSprPlanszaV losowo.
         * Losuje położenie dla @param posX oraz @param posY, a takze losuje układ
         * statku "h" lub "v". Na koncu wyswietlana jest plansza z nowym obiektem.
         * @param statki 
         */
        public void rozmiesc(){
            Random los = new Random();
            int licznik = 0;
            ustawione = false;
            
            while (licznik != statek.length) {
                int licznikWew = 0;
            do {
                    dlugoscV = 2;
                    dlugoscH = 2;

                    wyjscie = true;
                    uklad = los.nextInt(2);

                    setPosX(los.nextInt(getSizePlansza()));
                    setPosY(los.nextInt(getSizePlansza()));

                    tabWsp[0] = posX;
                    tabWsp[1] = posY;

                    h = posX - 1;

                    if (h_v[uklad].equals("h")) {
                        ustawione = rSprPlanszeH(this.statek[licznik], posX - 1, posY - 1);
                    } else if (h_v[uklad].equals("v")) {
                        ustawione = rSprPlanszeV(this.statek[licznik], posX - 1, posY - 1);
                    }
                    licznikWew++;
                } while ((ustawione == false));

                plansza(this.statek[licznik], this.tabWsp[0], this.tabWsp[1], licznik);    
                this.statek[licznik].setPozycja(this.tabWsp[0], this.tabWsp[1]);
                licznik++;
            }
    }
        
        /**
         * Ustawia obiekty przekazany z metod rSprPlanszeH lub rSprPlanszeV.
         * Wypełnienie macierzy wartościami size() wskazanegoe obiektu
         * @param statek
         * @param posX
         * @param posY 
         */
        public void plansza(Arsenal statek, int posX, int posY, int licznik) {
            // zmienne lokalne potrzebne, żeby nie inkrementować elementow tablicy tabWsp
            int val1 = tabWsp[0];
            int val2 = tabWsp[1];
            
            if (h_v[uklad].equals("h")) {
                for (int i = tabWsp[1]; i < (tabWsp[1] + statek.getStatekSize()); i++) {
                    if(statek instanceof Statek){
                        plansza[tabWsp[0]][i] = statek.getStatekSize();
                    }
                    addPozStat(this.statek[licznik], val1, val2++);
                }
            } else if (h_v[uklad].equals("v")) {
                for (int i = tabWsp[0]; i < (tabWsp[0] + statek.getStatekSize()); i++) {
                    if(statek instanceof Statek){
                        plansza[i][tabWsp[1]] = statek.getStatekSize();
                    }
                    addPozStat(this.statek[licznik], val1++, val2);
                }
            }
    }

        /**
         * Wyświetla raport na temat statków.
         */
        public void rodzajStatkow(){
            System.out.println("Oto armia do ustrzelenia! Naprzód!");
            for(int i = 0; i < statek.length; i++){
                if(statek[i] instanceof Statek){
                    
                System.out.println("STATEK     : " + this.statek[i].getConstSize() + " - masztowiec"
                                             + " Stan: " + this.statek[i].getAktStan());
                }
            }
            
        }
        
        /**
         * Przekazuje infomacje na temat obiektu.
         * Przenieść do klasy Statek, jako toString
         */
        public void raportStatkow(){
            System.out.println(statek[0].getStatekSize() + " - masztowiec"
                               + " Położenie: " + this.statek[0].getPozycjaX()
                               + " " + this.statek[0].getPozycjaY()
                               + " Stan: " + this.statek[0].getAktStan());
            for(int i = 1; i < statek.length; i++){
                System.out.println("     : " + this.statek[i].getStatekSize() + " - masztowiec"
                                             + " Położenie: " + this.statek[i].getPozycjaX()
                                             + " " + this.statek[i].getPozycjaY()
                                             + " Stan: " + this.statek[i].getAktStan());
            }
        }
        
        public static boolean sprPlik(String nazwa){

            File plik = new File(nazwa);
                if(!plik.exists()){
                    System.out.println("Brak pliku " + nazwa);
                    return false;
                } 
            return true;
        }
        
}