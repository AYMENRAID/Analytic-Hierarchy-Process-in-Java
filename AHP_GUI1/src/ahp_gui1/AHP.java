/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahp_gui1;

import java.awt.Frame;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;


/**
 *
 * @author Un1kalny
 */
public class AHP {
    
    public NewJFrame frame;
    public List<String> cele = new ArrayList<>();
    public List<Kryterium> kryteria = new ArrayList<>();
    public Map<Kryterium, double[][]> wagi = new HashMap<>();
    public Map<Kryterium, double[][]> porownania = new HashMap<>();
    public double wspolczynnikSpojnosci;
    public boolean AHP_CREATED = false;
    public boolean XML_CREATED = false;
    
    public AHP(NewJFrame frame) {
        this.frame = frame;
    }
    
    public boolean sprawdzSpojnosc(RealMatrix macierz, double wspolczynnikSpojnosci) {
        int rozmiarMacierzy = macierz.getColumnDimension();
        if(rozmiarMacierzy == 1 || rozmiarMacierzy == 2) {
            frame.p11.ustawTextLabela3("");
            return true;
        }
        else {
            double[] randomIndex = {1, 1, 1, 0.5247, 0.8816, 1.1086, 1.2479, 1.3417, 1.4057, 1.4499, 1.4854};
            EigenDecomposition decomposedMatrix = new EigenDecomposition(macierz);
            double[] realEigenvalues = decomposedMatrix.getRealEigenvalues();
            double max = Math.abs(realEigenvalues[0]);
            for (int i = 1; i < realEigenvalues.length; ++i) {
                realEigenvalues[i] = Math.abs(realEigenvalues[i]);
                if (realEigenvalues[i] > max)
                    max = realEigenvalues[i];
            }
            double cIndex = (max - rozmiarMacierzy) / (rozmiarMacierzy - 1);
            double cRatio = cIndex / randomIndex[rozmiarMacierzy];
            //System.out.println("Wspolczynik spojnosci macierzy:" +  cRatio);
            frame.p11.ustawTextLabela3("Wspolczynik spojnosci macierzy:" + cRatio);
            //System.out.println(macierz.toString());
            if(cRatio > wspolczynnikSpojnosci)
                return false;
            return true;
        }
    }
    
    
    
}
