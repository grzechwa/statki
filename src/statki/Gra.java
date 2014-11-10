package statki;

import java.util.*;
import java.io.*;

/**
 *
 * @author Grzegorz Zawal
 */
public class Gra implements Serializable{
        
        private int sprId;                                          // przypisana zostanie wartosc id znalezionego metoda spr
        private int aktTrafionyId;                                  // wartos id zwracanaego przez metode sprJakiStatkeTrafiony                                        
        private int przerwij1 = 0; 
        private int przerwij2 		 = 0;

        private boolean kontynuuj 	= true;   
        boolean ustrzelony 			= false;
        boolean wTrakcieStrzelania 	= false;
        boolean trafiony 			= false;
        int licznik 				= 0;
        boolean zatopStatek 		= false; 
        
        private long czas1 = 500; 
        private long czas2 = 1000;
        
        public Integer[][] strzal;
        public Integer[][] pamietajAktStrzal = 	new Integer[1][2];    // tymczasowa tablica
        public Integer[][] otoczenieStrzal 	 = 	new Integer[1][2];
        public Integer[][] h_vStrzal 		 = 	new Integer[1][2];
        public Integer[][] precyzjaStrzal 	 = 	new Integer[1][2];
        public Integer[][] aktualnaPozycja 	 = 	new Integer[1][2];
        
        private int ostrzalX;
        private int ostrzalY;
        private int strzalMLicz = 0;             // M - maszyna
        private int strzalGLicz = 0;             // G - gracz
        
        private int arg;                         // argument wyboru gr
        
        
        /**
         * Konstruktor.
         * Wyświetla plansze do gry.
         * @param plansza1
         * @param plansza2 
         */
        Gra(Plansza plansza1, Plansza plansza2){
            // Menu.ready = true;
        }
        
        /**
         * Pobiera otrzymany w klasie Menu argument wyboru rodzaju gry.
         * @return 
         */
        public int getArg(){
            return arg;
        }
    
        /**
         * Rozgrywka
         * @param plansza1
         * @param plansza2
         * @throws InterruptedException 
         */
        public void start(Plansza plansza1, Plansza plansza2, int arg) throws InterruptedException, IOException{
            boolean kontynuuj = true;
            strzal = new Integer[1][2];
            strzalyWpelnij(plansza2);
            int dlugoscGry = plansza1.getSizePlansza()*plansza1.getSizePlansza();
            int przerwij = 1;
            System.out.println("Testowa gra ma " + dlugoscGry + " ruchow");
            przerwij2 = iloscStatkowDoOdstrzalo(plansza1);
            this.arg = arg;
            int graLicz = 0;

            do {
               do {
                    System.out.println("");
                    System.out.println("\t...Twoj ruch");
                    if(!pobierzWsp(plansza2)){przerwij = 0;break;}
                    strzalGLicz++;
                    if (
                         ((plansza2.plansza[plansza2.gettabWsp0()][plansza2.gettabWsp1()] == 0) 
                                &&(plansza2.plansza[plansza2.gettabWsp0()][plansza2.gettabWsp1()] == 0))
                                ||((plansza2.plansza[plansza2.gettabWsp0()][plansza2.gettabWsp1()] == 12) 
                                &&(plansza2.plansza[plansza2.gettabWsp0()][plansza2.gettabWsp1()] == 12))
                        ){
                        
                        plansza2.plansza[plansza2.gettabWsp0()][plansza2.gettabWsp1()] = 8;
                        
                        Thread.sleep(czas1);
                        System.out.println("\n");
                            System.out.println("\t...atakujesz pozycje " + plansza2.getPosX() + " " + plansza2.getPosY());
                        System.out.println("\n");
                        Thread.sleep(czas2);
                                   System.out.println("\n");
                            System.out.println("\t\t\t...Pudlo!!");
                        System.out.println("\n");
                        Thread.sleep(czas2);
                            kontynuuj = false;
                            Panel.pokazPanel(plansza1, plansza2);
                            Panel.infoPanel(przerwij1, strzalGLicz);
                    } else if ((plansza2.plansza[plansza2.gettabWsp0()][plansza2.gettabWsp1()] != 0)
                                && (plansza2.plansza[plansza2.gettabWsp0()][plansza2.gettabWsp1()] != 8)
                                && (plansza2.plansza[plansza2.gettabWsp0()][plansza2.gettabWsp1()] != 7) 
                                && (plansza2.plansza[plansza2.gettabWsp0()][plansza2.gettabWsp1()] != 12)) {
                        
                        plansza2.plansza[plansza2.gettabWsp0()][plansza2.gettabWsp1()] = 7;
                        kontynuuj = true;
                        Thread.sleep(czas1);
                        System.out.println("\n");
                            System.out.println("\t...atakujesz pozycje " + plansza2.getPosX() + " " + plansza2.getPosY());
                        System.out.println("\n");
                        Thread.sleep(czas2);
                        System.out.println("\t\t\t...Trafiony ...kontynuuj");
                        System.out.println("");
                        Thread.sleep(czas2);
                        sprawdzJakiObiektTrafiony(plansza2.getPosX(), plansza2.getPosY(), plansza2);
                        Panel.pokazPanel(plansza1, plansza2);
                        Panel.infoPanel(przerwij1, strzalGLicz);
                    }
                    
                    przerwij1 = iloscStatkowDoOdstrzalo(plansza2);
                } while ((kontynuuj == true) && (przerwij1 > 0));
                
                if(przerwij == 0){przerwij = 1; break;}
                
                                    //przerwij1 = 1;              // dla testow
                
                if (przerwij1 > 0) {
                    
                    do {
                        switch(arg){
                            case 0: strzalMaszyny1(plansza1);break;
                            case 1:
                                    if (ustrzelony != true) {
                                        if (wTrakcieStrzelania == false) {
                                            strzalMaszyny1(plansza1);
                                        }
                                    }

                                    if (ustrzelony == true) {
                                        if (wTrakcieStrzelania == false) {
                                            sprId = sprawdzJakiObiektTrafiony(plansza1.gettabWsp0(), plansza1.gettabWsp1(), plansza1);
                                            pobierzZListyPozycjiStatkiPrzezID(aktTrafionyId, plansza1);

                                            wTrakcieStrzelania = true;
                                            ustrzelony = true;
                                            ostrzalX = plansza1.gettabWsp0();
                                            ostrzalY = plansza1.gettabWsp1();
                                        }

                                        strzalMaszyny3(plansza1);

                                    } else if (plansza1.precyzjaListaStrzalow.isEmpty()) {
                                        wTrakcieStrzelania = false;
                                    }
                                break;
                            default: System.out.println("Nastapil bunt maszyn, Apokalipsa!!!");
                        }

                        strzalMLicz++;
                        
                        if (
                                ((plansza1.plansza[plansza1.gettabWsp0()][plansza1.gettabWsp1()] == 0) 
                                &&(plansza1.plansza[plansza1.gettabWsp0()][plansza1.gettabWsp1()] == 0))
                                ||((plansza1.plansza[plansza1.gettabWsp0()][plansza1.gettabWsp1()] == 12) 
                                &&(plansza1.plansza[plansza1.gettabWsp0()][plansza1.gettabWsp1()] == 12))
                           ){
                            System.out.println("\n");
                            System.out.println("\t\t\t...Pudlo!!");
                            czysciciel(plansza1);
                            trafiony = false;
                            System.out.println("\n");
                            Thread.sleep(czas2);
                            plansza1.plansza[plansza1.gettabWsp0()][plansza1.gettabWsp1()] = 8;
                            kontynuuj = false;
                            Panel.pokazPanel(plansza1, plansza2);
                            Panel.infoPanel(przerwij2, strzalMLicz);
                        } else if ((plansza1.plansza[plansza1.gettabWsp0()][plansza1.gettabWsp1()] != 0)
                                && (plansza1.plansza[plansza1.gettabWsp0()][plansza1.gettabWsp1()] != 8)
                                && (plansza1.plansza[plansza1.gettabWsp0()][plansza1.gettabWsp1()] != 7) 
                                && (plansza1.plansza[plansza1.gettabWsp0()][plansza1.gettabWsp1()] != 12)) {
                            
                            System.out.println("\n");
                            System.out.println("\t\t\t...Trafiony ....kontynuuj");
                            ustrzelony = true;
                            czysciciel(plansza1);
                            trafiony = true;
                            plansza1.plansza[plansza1.gettabWsp0()][plansza1.gettabWsp1()] = 9;
                            kontynuuj = true;
                            ustrzelony = true;
                            System.out.println("\n");
                            Thread.sleep(czas2);
                            sprawdzJakiObiektTrafiony(plansza1.getPosX(), plansza1.getPosY(), plansza1);
                            Panel.pokazPanel(plansza1, plansza2);
                            Panel.infoPanel(przerwij2, strzalMLicz);
                            czysciciel(plansza1);
                            
                        } else if ((plansza1.plansza[plansza1.gettabWsp0()][plansza1.gettabWsp1()] == 8)
                                || (plansza1.plansza[plansza1.gettabWsp0()][plansza1.gettabWsp1()] == 7)) {
                            
                            kontynuuj = true;
                            czysciciel(plansza1);
                        }

                        if(zatopStatek==true){
                            przerwij2 = iloscStatkowDoOdstrzalo(plansza1);
                            zatopStatek=false;
                        }
                        
                    } while ((kontynuuj == true) && ((przerwij2 > 0)));
                } else {
                }
                graLicz++;
                
                        if (przerwij1 == 0) 
                        {
                            System.out.println("Zwyciężyłeś");
                        }
                        else if (przerwij2 == 0) //                    
                        {
                            System.out.println("Ups, maszyna wygrała");
                        }
                
            } while ((graLicz < 100) && (przerwij1 > 0) && (przerwij2 > 0));
        }
        
        /**
         * Zapewnia po strzale czyszczenie planszy i zablokowanie dalszych ruchów.
         * @param plansza 
         */
        public void czysciciel(Plansza plansza){
            if ((licznik == plansza.statek[sprId].getConstSize())&&(plansza.h_vListaStrzalow.isEmpty())) {
                System.out.println("");        
                System.out.println("     Ten pojazd juz nie moze byc wiekszy   ");        
                System.out.println("   ----------TRAFIONY-ZATOPIONY-----------");
                System.out.println("");        
                        zatopStatek=true;
                        this.usunResztki(plansza);
                        plansza.h_vListaStrzalow.clear();
                        ustrzelony = false;
                        wTrakcieStrzelania = false;
                        licznik = 0;
                    }
//    
            }
    
        public void strzalyWpelnij(Plansza plansza) {
            for (int i = 0; i < plansza.plansza.length; i++) {
                for (int j = 0; j < plansza.plansza.length; j++) {
                    strzal = new Integer[1][2];
                    strzal[0][0] = i;
                    strzal[0][1] = j;
                    plansza.pelnaListaStrzalow.add(strzal);
                }
            }
        }
    
        /**
         * Losowanie z listy pelnaListaStrzalow elementu. Wylosowany element (para liczb) tworzy warosc pozycji posX oraz posY. 
         * Wylosowany element jest usuwany. Ilosć strzalów jest mniejsza o 1.
         * @param plansza 
         */
        public void strzalMaszyny1(Plansza plansza) {
            Random los = new Random();
            System.out.println("");
            System.out.println("");
            
            System.out.print("Teraz strzela maszyna: ");
            if(przerwij2==0){
                kontynuuj = false;
            }
                try{           
                int liczba = los.nextInt(plansza.pelnaListaStrzalow.size()-1);

                pamietajAktStrzal = plansza.pelnaListaStrzalow.get(liczba);

                plansza.setPosX(pamietajAktStrzal[0][0]);
                plansza.setPosY(pamietajAktStrzal[0][1]);
                plansza.settabWsp0(plansza.getPosX());
                plansza.settabWsp1(plansza.getPosY());
                    System.out.println("");
                    System.out.println("");
                    System.out.println("");
                    System.out.println("");
                    Thread.sleep(czas2);
                    System.out.println(" \t\t...atakuje pole: " + plansza.gettabWsp0() + " " + plansza.gettabWsp1());
                    Thread.sleep(czas2);
                    System.out.println("");
                plansza.pelnaListaStrzalow.remove(liczba);
                }
            catch(Exception ex){

            System.out.println("Koniec strzelania");
                kontynuuj = false;
            }
        }
        
        /**
         * Nasladuje ruchy czlowieka.
         * @param plansza
         * @throws InterruptedException 
         */
        public void strzalMaszyny3(Plansza plansza) throws InterruptedException{
            Random los = new Random();
            Integer[][] x;
            Integer[][] y;
            
            if (trafiony == true) {                 // kontroler
                int a = plansza.getPosX();
                int b = plansza.getPosY();
                plansza.posTraf = new Integer[1][2];
                plansza.posTraf[0][0] = a;
                plansza.posTraf[0][1] = b;
                licznik++;
            }

            int liczba;

            if (licznik < 5) {                      // sprawdza, czy czasami licznik nie 'uciekł', ale nie powinien:)
                switch (licznik) {
                    case 0:
                        if (trafiony == true) {
                            plansza.listaTrafionych.add(0, plansza.posTraf);
                        } else {
                        }
                        break;
                    case 1:
                        if (trafiony == true) {
                            plansza.listaTrafionych.add(0, plansza.posTraf);
                            if (plansza.h_vListaStrzalow.isEmpty() && (licznik < 2)) {
                                if (sprPozycje(plansza, ostrzalX, ostrzalY - 1)) {
                                    tworzStrzaly(plansza, ostrzalX, ostrzalY - 1);
                                }
                                if (sprPozycje(plansza, ostrzalX, ostrzalY + 1)) {
                                    tworzStrzaly(plansza, ostrzalX, ostrzalY + 1);
                                }
                                if (sprPozycje(plansza, ostrzalX - 1, ostrzalY)) {
                                    tworzStrzaly(plansza, ostrzalX - 1, ostrzalY);
                                }
                                if (sprPozycje(plansza, ostrzalX + 1, ostrzalY)) {
                                    tworzStrzaly(plansza, ostrzalX + 1, ostrzalY);
                                }
                            } 
                            //</editor-fold>
                        } else {
                        }
                        break;
                    case 2:
                        if (trafiony == true) {
                            plansza.listaTrafionych.add(0, plansza.posTraf);
                            x = plansza.listaTrafionych.get(0);
                            y = plansza.listaTrafionych.get(1);
                            //<editor-fold defaultstate="collapsed" desc="gdy statek rowny 2">
                            if (x[0][0] == y[0][0]) {
                                Integer[][] z;
                                plansza.h_vListaStrzalow.clear();
                                for (int i = 0; i < plansza.precyzjaListaStrzalow.size(); i++) {
                                    z = plansza.precyzjaListaStrzalow.get(i);
                                    if ((z[0][0]) == (x[0][0])) {
                                        tworzStrzaly(plansza, z[0][0], z[0][1]);
                                    }
                                }
                            } else if(x[0][1] == y[0][1]){ // pion
                                Integer[][] z;// = new Integer[1][2];
                                plansza.h_vListaStrzalow.clear();
                                for (int i = 0; i < plansza.precyzjaListaStrzalow.size(); i++) {
                                    z = plansza.precyzjaListaStrzalow.get(i);
                                    if ((z[0][1]) == (x[0][1])) {
                                        tworzStrzaly(plansza, z[0][0], z[0][1]);
                                    }
                                }
                            }
                            //</editor-fold>
                        }
                        break;
                    default:
                        if (trafiony == true) {
                            plansza.listaTrafionych.add(0, plansza.posTraf);
                            for (int i = 0; i < plansza.precyzjaListaStrzalow.size(); i++) {
                                x = plansza.precyzjaListaStrzalow.get(i);
                                plansza.h_vListaStrzalow.add(x);
                            }
                            //<editor-fold defaultstate="collapsed" desc="umieszczanie">
                            x = plansza.listaTrafionych.get(0);
                            y = plansza.listaTrafionych.get(1);

                            if ((x[0][0]) == (y[0][0])) {                   // poziom
                                Integer[][] z;// = new Integer[1][2];
                                plansza.h_vListaStrzalow.clear();
                                for (int i = 0; i < plansza.precyzjaListaStrzalow.size(); i++) {
                                    z = plansza.precyzjaListaStrzalow.get(i);
                                    if ((z[0][0]) == (x[0][0])) {
                                        tworzStrzaly(plansza, z[0][0], z[0][1]);
                                    }
                                }
                            } else { // pion
                                Integer[][] z;// = new Integer[1][2];
                                plansza.h_vListaStrzalow.clear();
                                for (int i = 0; i < plansza.precyzjaListaStrzalow.size(); i++) {
                                    z = plansza.precyzjaListaStrzalow.get(i);
                                    if ((z[0][1]) == (x[0][1])) {
                                        tworzStrzaly(plansza, z[0][0], z[0][1]);
                                    }
                                }

                            }
                        } else {
                        }
                }
            }
            
                // zakres losowania w zależności od warunku switch
                if (plansza.statek[sprId].getConstSize() == 1) {
                    if (plansza.h_vListaStrzalow.size() > 0) {
                        liczba = los.nextInt(plansza.h_vListaStrzalow.size());
                        pamietajAktStrzal = plansza.h_vListaStrzalow.get(liczba);
                    } else {
                        liczba = los.nextInt(plansza.pelnaListaStrzalow.size());
                        pamietajAktStrzal = plansza.pelnaListaStrzalow.get(liczba);
                    }
                } else if ((licznik == plansza.statek[sprId].getConstSize()) 
                        && (plansza.h_vListaStrzalow.isEmpty())) {
                            if(plansza.precyzjaListaStrzalow.size()>0){
                            liczba = los.nextInt(plansza.precyzjaListaStrzalow.size());
                            pamietajAktStrzal = plansza.precyzjaListaStrzalow.get(liczba);
                            }else{
                            liczba = los.nextInt(plansza.pelnaListaStrzalow.size());
                            pamietajAktStrzal = plansza.pelnaListaStrzalow.get(liczba);
                            }
                } else if ((plansza.precyzjaListaStrzalow.size() == 0) && (plansza.h_vListaStrzalow.isEmpty())) {
                    liczba = los.nextInt(plansza.pelnaListaStrzalow.size());
                    pamietajAktStrzal = plansza.pelnaListaStrzalow.get(liczba);
                } else {
                    liczba = los.nextInt(plansza.h_vListaStrzalow.size());
                    pamietajAktStrzal = plansza.h_vListaStrzalow.get(liczba);
                }
                    
                    System.out.println("");
                    System.out.println("");
                    System.out.println("Teraz strzela maszyna: ");
                    plansza.setPosX(pamietajAktStrzal[0][0]);
                    plansza.setPosY(pamietajAktStrzal[0][1]);
                    plansza.settabWsp0(plansza.getPosX());
                    plansza.settabWsp1(plansza.getPosY());

                    System.out.println("");
                    System.out.println("");
                    System.out.println("");
                    System.out.println("");
                    Thread.sleep(czas2);
                    System.out.println("\t\t...atakuje pole: " + plansza.gettabWsp0()
                            + " " + plansza.gettabWsp1());
                    Thread.sleep(czas2);
                    System.out.println("");
                    
                    if(!plansza.h_vListaStrzalow.isEmpty()){
                        porownajLStrzLnowAndKill(liczba, plansza);
                        plansza.h_vListaStrzalow.remove(liczba);
                    }
        }

        public boolean sprPozycje(Plansza plansza, int posX, int posY){
            if(posX<0) return false;                               // kontroluje ilosc wierszy, gdy posX = 0
            if(posY<0) return false;                                  // kontroluje ilosc kolumn, gdy posY = 0
            if(posX>plansza.plansza.length-1) return false;
            if(posY>plansza.plansza.length-1) return false;

            if((plansza.plansza[posX][posY]==0))return true;
            if((plansza.plansza[posX][posY]==7))return false;
            if((plansza.plansza[posX][posY]==8))return false;
            
            return true;
        }
 
        /**
         * Tworzy dynamicznie liste strzalow. W zaleznosci od obiektu trafionego powstaj lista h_vListaStrzalow.
         * @param plansza
         * @param ostrzalX
         * @param ostrzalY 
         */
        public void tworzStrzaly(Plansza plansza, int ostrzalX, int ostrzalY){
            h_vStrzal = new Integer[1][2];
                    h_vStrzal[0][0] = ostrzalX;
                    h_vStrzal[0][1] = ostrzalY;
                    plansza.h_vListaStrzalow.add(h_vStrzal);
        }

        /**
         * Sprawdza, który obiekt został trafiony. Wykorzystuje pozycje statków umieszczone w liście pozycjeStatki.
         * Przez jej elementy następuje iteracja, porównanie kolejnych wartości z otrzymany parametrami posX oraz posY i zapisanie w trafioneId nr id trafionego obiektu (przechowywanego jako 3 wartość tablicy).
         * Następna pętla przechodzi przez tablicę statków, porownuje, ktory obiekt posiada pole o warotości trafioneId. W tak wyszukanym obiekcie sa dodkonywane zmiany (zmiana stanu, zmiana wielkosc, zmiana współrzędnych)
         * Dodatkowo metoda zwraca wartość trafionego id.
         * @param posX
         * @param posY
         * @param plansza
         * @return 
         */
        public int sprawdzJakiObiektTrafiony(int posX, int posY, Plansza plansza){
            int trafioneId = 0;                         // lokalna zmienna przechowujaca id obiektu trafionego z listyPozycji
            int pozStat = 0;
            // Wyszukanie trafionego id
            for (int i = 0; i < plansza.pozycjeStatki.size(); i++) {
                if ((posX == pobierzZListyPozcyjiStatku(i, plansza, 0))
                        && (posY == pobierzZListyPozcyjiStatku(i, plansza, 1))) {
                    trafioneId = pobierzZListyPozcyjiStatku(i, plansza, 2);
                }
            }
            
            // Wyszukanie obiektu o wskazanym id
            for(int i = 0; i < plansza.statek.length; i++){
                if(plansza.statek[i].getId()==trafioneId){
                    pozStat = i;
                    plansza.setPozTrafStat(i);
                    plansza.statek[i].setStatekSize(plansza.statek[i].getSize()-1);
                    if (plansza.statek[i].getSize() != 0) {
                        if (!(plansza.statek[i].getAktStan().equals("ZATOPIONY"))) {
                            plansza.statek[i].setAktStan(plansza.statek[i].getStan(1));
                        } 
                    }
                    else if(plansza.statek[i].getSize() == 0){
                            plansza.statek[i].setAktStan(plansza.statek[i].getStan(2));
                        }
                }
            }
          aktTrafionyId= trafioneId;
            return pozStat;
        }
        
        // POBIERANIE
        
        /**
         * Pobiera dane od uzytkownika.
         * @param plansza 
         */
        public static boolean pobierzWsp(Plansza plansza) throws IOException {

            boolean ok = true;
            int value = 0;
            int value2 = 0;

            do {
                Scanner wej = new Scanner(System.in);
                String val = wej.next();

                if(Menu.zapis==true){ok = true;Menu.zapis = false;}
                
                if (val.equals("z")) {
                    ok = false;
                    Archiwum.zapisPlansza("plansza1.dat", Menu.plansza1);
                    Archiwum.zapisPlansza("plansza2.dat", Menu.plansza2);
                    Archiwum.zapisGra("gra.dat", Menu.gra);
                    Menu.zapis = true;
                    System.out.println("        Zapisano");
                    continue;
                }
                
                if(val.equals("q")){
                    Menu.przerwanie = true;
                    return false;
                }

                while (!Character.isDigit(val.charAt(0))) {
                    System.out.println("Nieprawidlowa wartosc");
                    val = wej.next();
                }

                value = Integer.parseInt(val);

                while (((value < 0) || (value >= plansza.getSizePlansza()))) {
                    System.out.println("Nieprawidlowa wartosc");
                    val = wej.next();

                    while (!Character.isDigit(val.charAt(0))) {
                        System.out.println("Nieprawidlowa wartosc");
                        val = wej.next();
                        value = Integer.parseInt(val);
                    }
                    value = Integer.parseInt(val);
                }
                ok = true;

                String val2 = wej.next();
                while (!Character.isDigit(val2.charAt(0))) {
                    System.out.println("Nieprawidlowa wartosc");
                    val2 = wej.next();
                }
                value2 = Integer.parseInt(val2);
                while (((value2 < 0) || (value2 >= plansza.getSizePlansza()))) {
                    System.out.println("Wartosc spoza zakresu");
                    val2 = wej.next();

                    while (!Character.isDigit(val2.charAt(0))) {
                        System.out.println("Nieprawidlowa wartosc");
                        val2 = wej.next();
                        value2 = Integer.parseInt(val2);
                    }
                    value2 = Integer.parseInt(val2);
                }

                plansza.setPosX(value);
                plansza.setPosY(value2);
            } while (((plansza.plansza[plansza.getPosX()][plansza.getPosY()] == 8)
                    || (plansza.plansza[plansza.getPosX()][plansza.getPosY()] == 7)));

            if (ok == true) {
                plansza.settabWsp0(plansza.getPosX());
                plansza.settabWsp1(plansza.getPosY());
                return true;
            } else {
                return false;
            }
        }
 
        /**
         * Pobiera pozycję i zwraca nowa liste otoczenia statku trafionego. Porównuje otrzymane id z id następnego elementu tablicy i w ten sposób  tworzy nową listę otoczenieListaStrzalow (zawiera współrzędne trafionego statku).
         * Nasępnie przeszukuje tablicę i w metodach rUzupNewListaStrzalow tworzy listę z otoczeniem pozycji newListStrzlaow jest podzbiorem nowej tablicy precyzjaListaStrzalow.
         * @param id
         * @param plansza
         * @return 
         */
        public int pobierzZListyPozycjiStatkiPrzezID(int aktTrafionyId, Plansza plansza) {

            Integer[][] x;
            Integer[][] y;
            int wynik = 0;
            int k = 0;

            int posX;
            int posY;

            plansza.otoczenieListaStrzalow.clear();

            while (k < plansza.pozycjeStatki.size()) {
                x = plansza.pozycjeStatki.get(k);
                for (int i = 0; i < x.length; i++) {
                    for (int j = 0; j < x[i].length - 2; j++) {
                        wynik = x[i][2];
                        if (wynik == aktTrafionyId) {
                            otoczenieStrzal = new Integer[1][2];
                            otoczenieStrzal[0][0] = x[i][0];
                            aktualnaPozycja[0][0] = x[i][0];
                            otoczenieStrzal[0][1] = x[i][1];
                            aktualnaPozycja[0][1] = x[i][1];
                            plansza.otoczenieListaStrzalow.add(otoczenieStrzal);  // czyli tworzy obiket trafiony
                            break;
                        }
                    }
                    k++;
                }
            }
            // Sprawdzenie, czy v lub h prze porownaniem elementow listy

            String value = null;

            if (plansza.otoczenieListaStrzalow.size() > 1) {
                x = plansza.otoczenieListaStrzalow.get(0);
                y = plansza.otoczenieListaStrzalow.get(1);
            } else {
                x = plansza.otoczenieListaStrzalow.get(0);
                y = plansza.otoczenieListaStrzalow.get(0);
            }

            plansza.h = x[0][0] - 1;
            plansza.dlugoscH = 2;
            plansza.dlugoscV = 2;

            if (x[0][0] == y[0][0]) {
                value = "h";
                if (plansza.precyzjaListaStrzalow.size() > 0) {
                    plansza.precyzjaListaStrzalow.clear();
                }
                rUzupNewListaStrzalowH(plansza, x[0][0] - 1, x[0][1] - 1, value);
            } else if (x[0][1] == y[0][1]) {
                value = "v";
                if (plansza.precyzjaListaStrzalow.size() > 0) {
                    plansza.precyzjaListaStrzalow.clear();
                }
                rUzupNewListaStrzalowV(plansza, x[0][0] - 1, x[0][1] - 1, value);
            }
            return wynik;
        } 
        
         // ZWRACANIE WARTOSC
        
        /**
         * Wyznacza obszar tablicy plansza. Tworzy nową listę z polami otaczajacymi obszar statku wyznaczony przez parametry posX i posY. Wyznacza horyzontalnie.
         * @param plansza
         * @param posX
         * @param posY
         * @param vlubh
         * @return 
         */
        public boolean rUzupNewListaStrzalowH(Plansza plansza, int posX, int posY, String vlubh){
            if(posX<0) {posX++; plansza.h++; plansza.dlugoscH--;}                               // kontroluje ilosc wierszy, gdy posX = 0
            if(posX==plansza.h+(1+plansza.dlugoscH)) 
                return false; 
                                                                                // kontroluje ilosc wierszy dla statku poziomego -h 
            if(posY<0) { posY++; plansza.dlugoscV--;}                                   // kontroluje ilosc kolumn, gdy posY = 0
            if((plansza.plansza.length)-posY<plansza.otoczenieListaStrzalow.size()
                    +plansza.dlugoscV)plansza.dlugoscV--;                                       // kontroluje ilosc kolumn, gdy posY = length
            if(posY+plansza.otoczenieListaStrzalow.size()>plansza.plansza.length-1)
                return false;                                                   // kontroluje ilosc miejsca na planszy dla statku

            if(posX>plansza.plansza.length-1) return false;
            if(posY>plansza.plansza.length-1) return false;

            int k = posY;                                                       // zmienna k będzie modyfikowana w pętli
            int rozmiar = posY+plansza.otoczenieListaStrzalow.size()+plansza.dlugoscV;        // miejsce graniczne pętli

            while(rozmiar>plansza.plansza.length)                               // kontroluje przekroczenie plansza.length
                rozmiar--;
            
                    for (; k < rozmiar; k++) {                                  // pętla dla kolumn, sprawdzanie czy mozna ustawic statek
                                for (int i = 0; i < 1; i++) {
                                    for (int j = 0; j < 1; j++) {
                                    precyzjaStrzal = new Integer[1][2];
                                    precyzjaStrzal[0][0] = posX;
                                    precyzjaStrzal[0][1] = k;
                                            if(porownajLStrzLnow(plansza)==true){
                                                    plansza.precyzjaListaStrzalow.add(precyzjaStrzal);  // tworzy otoczenie obiektu trafionego
                                            }
                                    }
                                }
                    }
                    ++posX;
            rUzupNewListaStrzalowH(plansza, posX, posY, vlubh);
            return true;
        }
        
        /**
         * Wyznacza obszar tablicy plansza. Tworzy nową listę z polami otaczajacymi obszar statku wyznaczony przez parametry posX i posY. Wyznacza wertykalnie.
         * @param plansza
         * @param posX
         * @param posY
         * @param vlubh
         * @return 
         */
        public boolean rUzupNewListaStrzalowV(Plansza plansza, int posX, int posY, String vlubh){
            if(posX<0) {posX++; plansza.h++; plansza.dlugoscH--;}                               // kontroluje ilosc wierszy, gdy posX = 0
            if(posX==plansza.h+(plansza.otoczenieListaStrzalow.size()+ plansza.dlugoscH)) 
                return false;                                                   // kontroluje ilosc wierszy dla statku poziomego -h 
            if(posY<0) { posY++; plansza.dlugoscV--;}                                   // kontroluje ilosc kolumn, gdy posY = 0
            int ogranicznik = (plansza.plansza.length-1)-plansza.h;
            if(ogranicznik<plansza.otoczenieListaStrzalow.size()) 
                return false;

            if(posX>plansza.plansza.length-1) return false;
            if(posY>plansza.plansza.length-1) return false;

            int k = posY;                                                       // zmienna k będzie modyfikowana w pętli
            int rozmiar = posY+1+plansza.dlugoscV;                                      // miejsce graniczne pętli

            while(rozmiar>plansza.plansza.length)                               // kontroluje przekroczenie plansza.length
                rozmiar--;

            for (; k < rozmiar; k++) {                                          // pętla dla kolumn, sprawdzanie czy mozna ustawic statek
                                for (int i = 0; i < 1; i++) {
                                    for (int j = 0; j < 1; j++) {
                                    precyzjaStrzal = new Integer[1][2];
                                    precyzjaStrzal[0][0] = posX;
                                    precyzjaStrzal[0][1] = k;
                                           if(porownajLStrzLnow(plansza)==true){
                                                plansza.precyzjaListaStrzalow.add(precyzjaStrzal);  // tworzy otoczenie obiektu trafionego
                                            }
                                    }
                                }
                            
                    }
                    ++posX;
            rUzupNewListaStrzalowV(plansza, posX, posY, vlubh);
            return true;
        }

        /**
         * Wyznacza z listy pozycjeStatki wartosc tablicy o indekse Pos. Metoda zwraca wartosc dla wskazanego elementu Pos. 
         * Lista pozycjeStatki powinna być lista o elementach będących tablicą dwuwymiarową, o utalonej wielkości [1][3]. 
         * @param nrElementu    - parametr wskazuje, którą wartośc z drugiego elementu macierzy zwrócicć
         * @param plansza
         * @param Pos
         * @return 
         */
        public int pobierzZListyPozcyjiStatku(int nrElementu, Plansza plansza, int Pos) {
            Integer[][] x;
            x = plansza.pozycjeStatki.get(nrElementu);
            int wynik = 0;
            for (int i = 0; i < x.length; i++) {
                for (int j = 0; j < x[i].length; j++) {
                    wynik = x[i][Pos];
                }
            }
            return wynik;
        }
        
        /**
         * Porownuje listeStrzalow z nowaListaStrzalow. Zwraca true jesli znaleziono takie wyniki. 
		 * Dzięki temu mozna usunąć wartosć z listyStrzalow oraz intelListyStrzalow
         * @param plansza
         * @return 
         */
        public boolean porownajLStrzLnow(Plansza plansza) {
            // zastosowac potem binary search
            Integer[][] x;
            int wynik = 0;

            for (int k = 0; k < plansza.pelnaListaStrzalow.size(); k++) {
                x = plansza.pelnaListaStrzalow.get(k);
                for (int i = 0; i < x.length; i++) {
                    for (int j = 0; j < x[i].length; j++) {
                        if ((x[0][0].equals(precyzjaStrzal[0][0])) && (x[0][1].equals(precyzjaStrzal[0][1]))) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        
        /**
         * Porównuje nowaListaStrzalow z wszystkimi elementami pelnaListaStrzalow.
         * @param liczba
         * @param plansza 
         */        
        public void porownajLStrzLnowAndKill(int liczba, Plansza plansza){
        // zastosowac potem binary search
            Integer[][] x;
            Integer[][] y;
            Integer[][] z;

            y = plansza.h_vListaStrzalow.get(liczba);
            int wynik = 0;
            int wynik2 = y[0][0];
            int wynik3 = y[0][1];

            for (int k = 0; k < plansza.precyzjaListaStrzalow.size(); k++) {
                x = plansza.precyzjaListaStrzalow.get(k);
                if ((x[0][0].equals(plansza.gettabWsp0())) && (x[0][1].equals(plansza.gettabWsp1()))) {
                    plansza.precyzjaListaStrzalow.remove(k);
                }
            }
            
            for (int k = 0; k < plansza.pelnaListaStrzalow.size(); k++) {
                x = plansza.pelnaListaStrzalow.get(k);
                if ((x[0][0].equals(plansza.gettabWsp0())) && (x[0][1].equals(plansza.gettabWsp1()))) {
                    plansza.pelnaListaStrzalow.remove(k);
                }
            }

        }
  
        /**
         * Porownuje zawartos list. Metoda niezbedna w sytuacji, gdy statek jest zestrzelony, ale pozycje wokol niego sa jeszcze aktywne. Sprawdza te pozycje z listy precyzjaListaStrzalow z podstawowa pelanaListaStrzalow i usuwa te 
         * @param plansza 
         */
        public void usunResztki(Plansza plansza) {
            Integer[][] x;
            Integer[][] y;

            for (int i = 0; i < plansza.precyzjaListaStrzalow.size(); i++) {
                x = plansza.precyzjaListaStrzalow.get(i);
                for (int k = 0; k < plansza.pelnaListaStrzalow.size(); k++) {
                    y = plansza.pelnaListaStrzalow.get(k);
                    if ((x[0][0] == y[0][0]) && (x[0][1] == y[0][1])) {
                        plansza.plansza[y[0][0]][y[0][1]] = 8;
                        plansza.pelnaListaStrzalow.remove(k);break;
                    }
                }
            }
        }

        /**
         * Zwraca ilosc statkow o stanie !ZATOPIONY
         * @param plansza
         * @return 
         */
        public int iloscStatkowDoOdstrzalo(Plansza plansza) {
            int ilosc = 0;
            for (int i = 0; i < plansza.statek.length; i++) {
                if (!(plansza.statek[i].getAktStan().equals("ZATOPIONY"))) {
                    ilosc++;
                }
            }
            return ilosc;
        }
}
