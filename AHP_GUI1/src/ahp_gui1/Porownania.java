/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahp_gui1;

import javax.swing.SwingWorker;
import org.apache.commons.math3.linear.MatrixUtils;

/**
 *
 * @author Un1kalny
 */
public class Porownania /*extends SwingWorker<String, Object>*/ {
    AHP ahp;
    NewJFrame frame;
    
    public Porownania(AHP ahp, NewJFrame frame) {
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
    
    /*@Override
     public String doInBackground() {
         porownajCele();
         return "done";
     }*/
    
    public void porownajCele() {
        for(int i = 0; i < ahp.kryteria.size(); ++i) {
        Kryterium kryterium1 = ahp.kryteria.get(i);
        if(kryterium1.listaPodkryteriow == null || kryterium1.listaPodkryteriow.isEmpty()) {
                if(!ahp.porownania.containsKey(kryterium1)) {
                    //System.out.println(kryterium1);
                    double[][] tab = new double[ahp.cele.size()][ahp.cele.size()];

                    for(int k = 0; k < ahp.cele.size(); ++k)
                        for(int l = 0; l < ahp.cele.size(); ++l) {
                            if(k == l)
                                tab[k][l] = 1;
                            else
                                tab[k][l] = 1;///////////////////////////////////////////////////////////////////////////////////////////
                        }

                    frame.p11.updateMatrix(tab);
                    
                    for(int k = 0; k < ahp.cele.size(); ++k) {
                        for(int l = 0; l < ahp.cele.size(); ++l) {

                            if(k == l || k > l) //!!!
                                continue;

                            frame.p11.ustawTextLabela("<html><center>Ile razy<br>" + kryterium1.nazwa + " jest lepsze dla<br>" + ahp.cele.get(k) + " niż " + ahp.cele.get(l) + "?</center><html>");
                            //System.out.println("Porownaj " + kryterium1.nazwa + " dla " + main.cele.get(k) + " z " + main.cele.get(l));
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
                                //System.out.println("Macierz jest niespojna. Podaj inna wartosc.");
                                //System.out.println("Porownaj " + kryterium1.nazwa + " dla " + main.cele.get(k) + " z " + main.cele.get(l));
                                frame.p11.ustawTextLabela("<html><center>Macierz jest niespójna. Podaj inną wartość.<br>Ile razy<br>" + kryterium1.nazwa + " jest lepsze dla<br>" 
                                        + ahp.cele.get(k) + " niż " + ahp.cele.get(l) + "?</center><html>");
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
                    ahp.porownania.put(kryterium1, tab);
                }
    } }
    }
     
}
