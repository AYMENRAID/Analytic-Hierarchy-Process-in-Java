import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.util.*;

public class Main {

    Main(Document document) {
        this.document = document;
    }

    Document document;
    String[] listaCeli;

    double[] ahp() {
        Element root = document.getDocumentElement();
        NodeList listaDzieci = root.getElementsByTagName("cele");
        listaCeli = listaDzieci.item(0).getTextContent().split("\\n");
        Element kryteria = (Element) root.getElementsByTagName("kryteria").item(0);
        return rekurencyjnePrzechodzenieKryteriow(kryteria, 1).vector;
    }

    Vektor rekurencyjnePrzechodzenieKryteriow(Element element, double waga) {
        if (element.getElementsByTagName("kryterium").getLength() > 0) {
            NodeList listaPodkryteriow = element.getChildNodes();
            List<Vektor> listaKryteriow = new ArrayList<>();
            double[] wektor = wektorWlasnyMaxMacierzy(element, "wagi");
            int counter = 0;
            for (int i = 0; i < listaPodkryteriow.getLength(); ++i)
                if (listaPodkryteriow.item(i).getNodeName().equals("kryterium")) {
                    listaKryteriow.add(rekurencyjnePrzechodzenieKryteriow((Element) listaPodkryteriow.item(i), wektor[counter++]));
                }
            wektor = new double[listaKryteriow.get(0).vector.length];
            for (int i = 0; i < listaKryteriow.size(); ++i) {
                for (int j = 0; j < listaKryteriow.get(0).vector.length; ++j)
                    wektor[j] += listaKryteriow.get(i).vector[j] * listaKryteriow.get(i).waga;
            }
            double suma = 0;
            for (int i = 0; i < listaKryteriow.get(0).vector.length; ++i)
                suma += wektor[i];
            for (int i = 0; i < listaKryteriow.get(0).vector.length; ++i)
                wektor[i] = wektor[i] / suma;

            return new Vektor(wektor, 1);
        } else
            return new Vektor(wektorWlasnyMaxMacierzy(element, "porownanie"), waga);

    }

    double[] wektorWlasnyMaxMacierzy(Element element, String nazwa) {
        String s = element.getElementsByTagName(nazwa).item(0).getTextContent();
        String[] macierzPoSplicie = s.split(" ");
        double[][] macierz = new double[(int) Math.sqrt(macierzPoSplicie.length)][(int) Math.sqrt(macierzPoSplicie.length)];

        for (int i = 0; i < (int) Math.sqrt(macierzPoSplicie.length); ++i) {
            for (int j = 0; j < (int) Math.sqrt(macierzPoSplicie.length); ++j) {
                macierz[i][j] = Double.parseDouble(macierzPoSplicie[(int) Math.sqrt(macierzPoSplicie.length) * i + j]);
            }
        }

        RealMatrix matrix = MatrixUtils.createRealMatrix(macierz);
        double maxWartoscWlasna = 0;
        int maxIndex = 0;
        EigenDecomposition decomposedMatrix = new EigenDecomposition(matrix);
        double[] wartosciWlasne = decomposedMatrix.getRealEigenvalues();
        for (int i = 0; i < wartosciWlasne.length; ++i) {
            wartosciWlasne[i] = Math.abs(wartosciWlasne[i]);
            if (wartosciWlasne[i] > maxWartoscWlasna) {
                maxWartoscWlasna = wartosciWlasne[i];
                maxIndex = i;
            }
        }
        RealMatrix vector = decomposedMatrix.getV();
        double[] wektor = new double[vector.getColumnDimension()];
        double suma = 0;

        for (int i = 0; i < vector.getColumnDimension(); ++i) {
            wektor[i] = vector.getEntry(i, maxIndex);
            suma += wektor[i];
        }
        for (int i = 0; i < vector.getColumnDimension(); ++i)
            wektor[i] = wektor[i] / suma;
        return wektor;
    }

    public static void main(String[] args) {
        try {
            File plik = new File("xmlik123.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(plik);
            Main main = new Main(document);
            double[] ahpVector = main.ahp();
            for(int i = 0; i < ahpVector.length; ++i)
                System.out.print("" + main.listaCeli[i+1] + ": " + ahpVector[i] + "  \n" );
            int index = 0;
            for(int i = 1; i < ahpVector.length; ++i) {
                if(ahpVector[i] > ahpVector[index])
                    index = i;
            }
            System.out.println("Najlepszym wyborem jest: " + main.listaCeli[index+1]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class Vektor {
    double[] vector;
    double waga;
    public Vektor(double[] vector, double waga) {
        this.vector = vector;
        this.waga = waga;
    }
}