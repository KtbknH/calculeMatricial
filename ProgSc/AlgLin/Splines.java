package AlgLin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.knowm.xchart.*;
import org.knowm.xchart.style.lines.SeriesLines;

public class Splines {

  private double[] b;
  private double[] c;
  private double[] d;
  private double[] x;
  private double[] y;
  private int n;
  private Vecteur g;
  
  public Splines(double[] x, double[] y) throws Exception{
    if(x.length != y.length) throw new Exception("Les tableaux x et y doivent avoir la même taille");
    if(x.length < 4) throw new Exception("Il faut au moins 4 points pour faire une spline");
    this.n = x.length;
    this.x = x;
    this.y = y;
    b = new double[n - 2];
    c = new double[n - 2];
    d = new double[n - 2];
    b[0] = 2 * (x[2] - x[0]);
    c[0] = (x[2] - x[1]);
    d[0] = 6 * (((y[2] - y[1]) / (x[2] - x[1])) - ((y[1] - y[0]) / (x[1] - x[0]))); 
    g = deriveSecond();
  }

  private Vecteur deriveSecond() throws Exception{
    Vecteur res = new Vecteur(n);
    res.remplacecoef(0, 0);
    res.remplacecoef(n - 1, 0);

    Mat3Diag diag = new Mat3Diag(n - 2);
    Vecteur bx = new Vecteur(n - 2);
    double[] a = new double[n - 2];

    for(int i = 2; i < n - 2; i++){
      a[i - 1] = c[i - 2];
      b[i - 1] = 2 * (x[i + 1] - x[i - 1]);
      c[i - 1] = x[i + 1] - x[i];
      d[i - 1] = 6 * ((y[i + 1] - y[i]) / c[i - 1] - (y[i] - y[i - 1]) / a[i - 1]);

      if (b[i - 1] == 0) throw new Exception("Matrice tridiagonale singulière : b[" + i + "] est nul");
    }

    a[n - 3] = c[n - 4];
    b[n - 3] = 2 * (x[n - 1] - x[n - 3]);
    d[n - 3] = 6 * ((y[n - 1] - y[n - 2]) / (x[n - 1] - x[n - 2]) - (y[n - 2] - y[n - 3]) / a[n - 3]);

    for(int i = 0; i < n - 2; i++){
      diag.remplacecoef(0, i, a[i]);
      diag.remplacecoef(1, i, b[i]);
      diag.remplacecoef(2, i, c[i]);
      bx.remplacecoef(i, d[i]);
    }


    Thomas thomas = new Thomas(diag, bx);
    Vecteur par = thomas.resolution();

    for(int i = 1; i <= n - 2; i++){
      res.remplacecoef(i, par.getCoef(i -1));
    }    
    return res;
  }

  public double interpolation(double rech) throws Exception{
    double alpha , beta , gamma;
    double t1 = 0 , t2 = 0 , t3  = 0;
    int interateur = 0;
    while(interateur < n - 1 && x[interateur + 1] < rech){
      interateur++;
    }
    if(interateur == n - 1) throw new Exception("La valeur recherchée est en dehors de l'intervalle");
    alpha = x[interateur + 1] - rech;
    beta = rech - x[interateur];
    gamma = x[interateur + 1] - x[interateur];
    t1 = (g.getCoef(interateur) / 6) * ((Math.pow(alpha, 3) / gamma )- alpha * gamma);
    t2 = (g.getCoef(interateur + 1) / 6) * ((Math.pow(beta, 3) / gamma )- beta * gamma);
    t3 = (y[interateur] * alpha + y[interateur + 1] * beta) / gamma;
    return t1 + t2 + t3;
  }

  public static void main(String[] args) throws Exception {
    try {
      double[] x = null;
      double[] y = null;
      Scanner scanner = new Scanner(System.in);
      System.out.print("Entrez le nom du fichier contenant les points de support : ");
      String filename = scanner.nextLine();
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

      Splines splines = new Splines(x, y);
      XYChart chart = new XYChartBuilder().width(800).height(600).title("Spline").xAxisTitle("X").yAxisTitle("Y").build();
      XYSeries series = chart.addSeries("Spline", x, y);
      series.setLineStyle(SeriesLines.NONE);

      double[] interpolX = new double[110];
      double[] interpolY = new double[110];
      double minX = Arrays.stream(x).min().getAsDouble();
      double maxX = Arrays.stream(x).max().getAsDouble();
      for (int i = 0; i < 110; i++) {
          interpolX[i] = minX + i * (maxX - minX) / 109;
          interpolY[i] = splines.interpolation(interpolX[i]);
      }
      chart.addSeries("Interpolation", interpolX, interpolY);
      new SwingWrapper(chart).displayChart();
  } catch (Exception e) {
      e.printStackTrace();
  }
}
}