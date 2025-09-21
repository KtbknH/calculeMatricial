package AlgLin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class ModPoly {
  private double[] coef;
  private int taille;
  
  public ModPoly(int m) {
    taille = m + 1;
    coef = new double[taille]; 
  }

  public double fonction(double x , int i){
    return Math.pow(x, i);
  }

  public double p(double x) {
    double res = 0;
    for (int i = 0; i < taille; i++) {
      res += coef[i] * fonction(x, i);
    }
    return res;
  }

  public void identifie(double[] x, double[] y) throws Exception {
    if(x.length != y.length) throw new Exception("Les tableaux x et y doivent avoir la même taille");
    int n = x.length;
    int m = taille;

    Matrice matrice = new Matrice(n, m);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        matrice.remplacecoef(i, j, fonction(x[i], j));
      }
    }

    Matrice matriceT = matrice.transpose();
    Matrice matriceProduit = Matrice.produit(matriceT, matrice);
    
    Vecteur yVecteur = new Vecteur(y);

    Matrice vecteurProduit = Matrice.produit(matriceT, yVecteur);
    Vecteur yProduit = new Vecteur(m);

    for (int i = 0; i < m; i++) {
      yProduit.remplacecoef(i, vecteurProduit.getCoef( i, 0));
    }

    Helder ldr = new Helder(matriceProduit, yProduit);
    Vecteur solution = ldr.resolution();

    for (int i = 0; i < m; i++) {
      coef[i] = solution.getCoef(i);
    }

  }

 public static void main(String[] args) {
        try {
            double[] x = null;
            double[] y = null;
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez le nom du fichier contenant les points de support : ");
            String filename = scanner.nextLine();
            System.out.print("Entrez le degré du polynôme : ");
            int degre = scanner.nextInt();
            scanner.close();

            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            if (line != null) {
                int size = Integer.parseInt(line);
                x = new double[size];
                y = new double[size];

                line = br.readLine();
                String[] xParts = line.split(" ");
                for (int i = 0; i < size; i++) {
                    x[i] = Double.parseDouble(xParts[i]);
                }

                line = br.readLine();
                String[] yParts = line.split(" ");
                for (int i = 0; i < size; i++) {
                    y[i] = Double.parseDouble(yParts[i]);
                }
            }
            br.close();

            ModPoly modPoly = new ModPoly(degre);
            modPoly.identifie(x, y);

            XYChart chart = new XYChartBuilder().width(800)
                                                .height(600).title("Modèle Polynomial")
                                                .xAxisTitle("X").yAxisTitle("Y")
                                                .build();
            chart.addSeries("Points de support", x, y).setMarker(SeriesMarkers.CIRCLE).setLineStyle(SeriesLines.NONE);

            double[] interpolX = new double[100];
            double[] interpolY = new double[100];
            double minX = Arrays.stream(x).min().getAsDouble();
            double maxX = Arrays.stream(x).max().getAsDouble();
            for (int i = 0; i < 100; i++) {
                interpolX[i] = minX + i * (maxX - minX) / 99;
                interpolY[i] = modPoly.p(interpolX[i]);
            }
            chart.addSeries("Interpolation", interpolX, interpolY).setLineStyle(SeriesLines.SOLID);

            new SwingWrapper<>(chart).displayChart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
