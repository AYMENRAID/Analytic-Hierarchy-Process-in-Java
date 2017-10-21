/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahp_gui1;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import org.apache.commons.math3.linear.MatrixUtils;

/**
 *
 * @author Un1kalny
 */
public class Wazenie extends SwingWorker<String, Object>{
    
    AHP ahp;
    NewJFrame frame;
    
    public Wazenie(AHP ahp, NewJFrame frame) {
        this.ahp = ahp;
        this.frame = frame;
    }
    
    double wczytajDouble() {
        while(true) {
            try {
                return Double.valueOf(frame.p11.getWczytywanieTextu());
            }catch(Exception ex) {
                frame.p11.ustawTextLabela2("Błędna wartość. Spróbuj ponownie.");
            }
        }
    }
    
     @Override
     public String doInBackground() {
         ustalWaznoscKryteriow();
         return "done";
     }
    
    public void ustalWaznoscKryteriow() {
        
        ////dodane
        
        List<Kryterium> kryteriaBezParenta =  new ArrayList<>();
        for(int i = 0; i < ahp.kryteria.size(); ++i) {
            if(ahp.kryteria.get(i).parent == null) {
                kryteriaBezParenta.add(ahp.kryteria.get(i));
            }
        }
        
        Kryterium kr = new Kryterium("NULL", null, kryteriaBezParenta);
        
        double[][] tab = new double[kryteriaBezParenta.size()][kryteriaBezParenta.size()];

                        for(int k = 0; k < kryteriaBezParenta.size(); ++k)
                            for(int l = 0; l < kryteriaBezParenta.size(); ++l) {
                                if(k == l)
                                  tab[k][l] = 1;
                                else
                                 tab[k][l] = 1;///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            }
                        frame.p11.updateMatrix(tab);
                    for(int k = 0; k < kryteriaBezParenta.size(); ++k) {
                        for(int l = 0; l < kryteriaBezParenta.size(); ++l) {

                            if(k == l || k > l) //!!!
                                continue;
                            //ahp.wspolczynnikSpojnosci = frame.getSliderValue();
                            //System.out.println("Porownaj " + parent.listaPodkryteriow.get(k) + " z " + parent.listaPodkryteriow.get(l) + " pod wzgledem waznosci.\n");
                            frame.p11.ustawTextLabela("<html><center>Ile razy<br>" + kryteriaBezParenta.get(k) + " jest ważniejsze od " + kryteriaBezParenta.get(l) 
                                    + "?</center><html>");
                            //tab[k][l] = wczytajZKonsoliDouble(odczyt);
                            //tab[k][l] = Double.parseDouble(frame.p11.getWczytywanieTextu());
                            tab[k][l] = wczytajDouble();
                            frame.p11.ustawTextLabela2("");
                            frame.p11.updateMatrix(tab);

                            if(l > k)
                                tab[l][k] = 1 / tab[k][l];
                            frame.p11.updateMatrix(tab);

                            ///////////////////
                            ahp.wspolczynnikSpojnosci = frame.getSliderValue();
                            while(!ahp.sprawdzSpojnosc(MatrixUtils.createRealMatrix(tab), ahp.wspolczynnikSpojnosci)) {
                                //System.out.println("Macierz jest niespojna. Podaj inna wartosc.");
                                //System.out.println("Porownaj " + parent.listaPodkryteriow.get(k) + " z " + parent.listaPodkryteriow.get(l) + " pod wzgledem waznosci.\n");
                                frame.p11.ustawTextLabela("<html><center>Macierz jest niespójna. Podaj inną wartość.<br>Ile razy<br>" + kryteriaBezParenta.get(k) 
                                        + " jest ważniejsze od " + kryteriaBezParenta.get(l) + "?</center><html>");
                                //tab[k][l] = wczytajZKonsoliDouble(odczyt);
                                //tab[k][l] = Double.parseDouble(frame.p11.getWczytywanieTextu());
                                tab[k][l] = wczytajDouble();
                                frame.p11.ustawTextLabela2("");
                                frame.p11.updateMatrix(tab);
                                if(l > k){
                                    tab[l][k] = 1 / tab[k][l];
                                    frame.p11.updateMatrix(tab);
                                }
                                ahp.wspolczynnikSpojnosci = frame.getSliderValue();    
                            }
                            ///////////////////

                            //sprawdzSpojnosc(MatrixUtils.createRealMatrix(tab), main.wspolczynnikSpojnosci);
                        }
                    }
                    ahp.wagi.put(kr, tab);
        //// dodane
        
       
        
        
        Kryterium parent = null;
        for(int i = 0; i < ahp.kryteria.size(); ++i) {
            if(ahp.kryteria.get(i).listaPodkryteriow == null || ahp.kryteria.get(i).listaPodkryteriow.isEmpty()) {
                if(ahp.kryteria.get(i).parent == null)
                    continue;
                ahp.kryteria.get(i).zwazoneKryterium = true;
                parent = ahp.kryteria.get(i).parent;
                parent.zwazoneKryterium = true;
                for(int j = 0; j < parent.listaPodkryteriow.size(); ++j) {
                    if(parent.listaPodkryteriow.get(j).zwazoneKryterium == false) {
                        parent.zwazoneKryterium = false;
                        break;
                    }
                }
                if(parent.zwazoneKryterium) {
                    //System.out.println("Parent: " + parent.nazwa);
                        tab = new double[parent.listaPodkryteriow.size()][parent.listaPodkryteriow.size()];

                        for(int k = 0; k < parent.listaPodkryteriow.size(); ++k)
                            for(int l = 0; l < parent.listaPodkryteriow.size(); ++l) {
                                if(k == l)
                                  tab[k][l] = 1;
                                else
                                 tab[k][l] = 1;//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            }
                        frame.p11.updateMatrix(tab);
                    for(int k = 0; k < parent.listaPodkryteriow.size(); ++k) {
                        for(int l = 0; l < parent.listaPodkryteriow.size(); ++l) {

                            if(k == l || k > l) //!!!
                                continue;
                            //ahp.wspolczynnikSpojnosci = frame.getSliderValue();
                            //System.out.println("Porownaj " + parent.listaPodkryteriow.get(k) + " z " + parent.listaPodkryteriow.get(l) + " pod wzgledem waznosci.\n");
                            frame.p11.ustawTextLabela("<html><center>Ile razy<br>" + parent.listaPodkryteriow.get(k) + " jest ważniejsze od " + parent.listaPodkryteriow.get(l) 
                                    + "?</center><html>");
                            //tab[k][l] = wczytajZKonsoliDouble(odczyt);
                            //tab[k][l] = Double.parseDouble(frame.p11.getWczytywanieTextu());
                            tab[k][l] = wczytajDouble();
                            frame.p11.ustawTextLabela2("");
                            frame.p11.updateMatrix(tab);

                            if(l > k)
                                tab[l][k] = 1 / tab[k][l];
                            frame.p11.updateMatrix(tab);

                            ///////////////////
                            ahp.wspolczynnikSpojnosci = frame.getSliderValue();
                            while(!ahp.sprawdzSpojnosc(MatrixUtils.createRealMatrix(tab), ahp.wspolczynnikSpojnosci)) {
                                //System.out.println("Macierz jest niespojna. Podaj inna wartosc.");
                                //System.out.println("Porownaj " + parent.listaPodkryteriow.get(k) + " z " + parent.listaPodkryteriow.get(l) + " pod wzgledem waznosci.\n");
                                frame.p11.ustawTextLabela("<html><center>Macierz jest niespójna. Podaj inną wartość.<br>Ile razy<br>" + parent.listaPodkryteriow.get(k) 
                                        + " jest ważniejsze od " + parent.listaPodkryteriow.get(l) + "?</center><html>");
                                //tab[k][l] = wczytajZKonsoliDouble(odczyt);
                                //tab[k][l] = Double.parseDouble(frame.p11.getWczytywanieTextu());
                                tab[k][l] = wczytajDouble();
                                frame.p11.ustawTextLabela2("");
                                frame.p11.updateMatrix(tab);
                                if(l > k){
                                    tab[l][k] = 1 / tab[k][l];
                                    frame.p11.updateMatrix(tab);
                                }
                                ahp.wspolczynnikSpojnosci = frame.getSliderValue();    
                            }
                            ///////////////////

                            //sprawdzSpojnosc(MatrixUtils.createRealMatrix(tab), main.wspolczynnikSpojnosci);
                        }
                    }
                    ahp.wagi.put(parent, tab);
                
                    }
                }
            }
        boolean wszystkieZwazone = false;
        while(!wszystkieZwazone) {
           
            for(int i = 0; i < ahp.kryteria.size(); ++i) {
                if(ahp.kryteria.get(i).listaPodkryteriow != null) {
                    if(!ahp.kryteria.get(i).listaPodkryteriow.isEmpty()) {
                        if(!ahp.kryteria.get(i).zwazoneKryterium) {
                            ahp.kryteria.get(i).zwazoneKryterium = true;
                            for(int j = 0; j < ahp.kryteria.get(i).listaPodkryteriow.size(); ++j) {
                                if(!ahp.kryteria.get(i).listaPodkryteriow.get(j).zwazoneKryterium) {
                                    ahp.kryteria.get(i).zwazoneKryterium = false;
                                    break;
                                }
                            }
                            if(ahp.kryteria.get(i).zwazoneKryterium) {
                                //System.out.println("LOL: " + ahp.kryteria.get(i).nazwa);
                        tab = new double[ahp.kryteria.get(i).listaPodkryteriow.size()][ahp.kryteria.get(i).listaPodkryteriow.size()];

                        for(int k = 0; k < ahp.kryteria.get(i).listaPodkryteriow.size(); ++k)
                            for(int l = 0; l < ahp.kryteria.get(i).listaPodkryteriow.size(); ++l) {
                                if(k == l)
                                  tab[k][l] = 1;
                                else
                                 tab[k][l] = 1; //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            }
                        frame.p11.updateMatrix(tab);

                    for(int k = 0; k < ahp.kryteria.get(i).listaPodkryteriow.size(); ++k) {
                        for(int l = 0; l < ahp.kryteria.get(i).listaPodkryteriow.size(); ++l) {

                            if(k == l || k > l) //!!!
                                continue;

                            //ahp.wspolczynnikSpojnosci = frame.getSliderValue();
                            frame.p11.ustawTextLabela("<html><center>Ile razy<br>" + ahp.kryteria.get(i).listaPodkryteriow.get(k) + " jest ważniejsze od " + ahp.kryteria.get(i).listaPodkryteriow.get(l) 
                                    + "?</center><html>");
                            //System.out.println("Porownaj " + ahp.kryteria.get(i).listaPodkryteriow.get(k) + " z " + ahp.kryteria.get(i).listaPodkryteriow.get(l) + " pod wzgledem waznosci.\n");
                            //tab[k][l] = wczytajZKonsoliDouble(odczyt);
                            //tab[k][l] = Double.parseDouble(frame.p11.getWczytywanieTextu());
                            tab[k][l] = wczytajDouble();
                            frame.p11.ustawTextLabela2("");
                            frame.p11.updateMatrix(tab);

                            if(l > k) {
                                tab[l][k] = 1 / tab[k][l];
                                frame.p11.updateMatrix(tab);
                            }

                            ///////////////////
                            ahp.wspolczynnikSpojnosci = frame.getSliderValue();
                            while(!ahp.sprawdzSpojnosc(MatrixUtils.createRealMatrix(tab), ahp.wspolczynnikSpojnosci)) {
                                frame.p11.ustawTextLabela("<html><center>Macierz jest niespójna. Podaj inną wartość.<br>Ile razy<br>" + ahp.kryteria.get(i).listaPodkryteriow.get(k) 
                                        + " jest ważniejsze od " + ahp.kryteria.get(i).listaPodkryteriow.get(l) + "?</center><html>");
                                //System.out.println("Macierz jest niespojna. Podaj inna wartosc.");
                                //System.out.println("Porownaj " + parent.listaPodkryteriow.get(k) + " z " + parent.listaPodkryteriow.get(l) + " pod wzgledem waznosci.\n");
                                //tab[k][l] = wczytajZKonsoliDouble(odczyt);
                                //tab[k][l] = Double.parseDouble(frame.p11.getWczytywanieTextu());
                                tab[k][l] = wczytajDouble();
                                frame.p11.ustawTextLabela2("");
                                frame.p11.updateMatrix(tab);
                                if(l > k) {
                                    tab[l][k] = 1 / tab[k][l];
                                    frame.p11.updateMatrix(tab);
                                } 
                                ahp.wspolczynnikSpojnosci = frame.getSliderValue();
                            }
                            ///////////////////

                            //sprawdzSpojnosc(MatrixUtils.createRealMatrix(tab), main.wspolczynnikSpojnosci);
                        }
                    }
                    ahp.wagi.put(ahp.kryteria.get(i), tab);
                
                            }
                        }
                    }
                }
            }
            // Sprawdzam czy juz wszystkie zwazone
            wszystkieZwazone = true;
            for(int i = 0; i < ahp.kryteria.size(); ++i) {
                if(!ahp.kryteria.get(i).zwazoneKryterium) {
                    if(ahp.kryteria.get(i).parent == null && (ahp.kryteria.get(i).listaPodkryteriow == null || ahp.kryteria.get(i).listaPodkryteriow.isEmpty()))
                        continue;
                    wszystkieZwazone = false;
                    break;
                }
            }
        }
        //frame.p11.ustawTextLabela("KONIEC?");
        Porownania p = new Porownania(ahp, frame);
        p.porownajCele();
        TworzenieXML xml = new TworzenieXML(ahp, frame);
        xml.stworzXML();
        
    }
}
