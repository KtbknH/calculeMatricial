package AlgLin;

import java.util.Scanner;

public class Hilbert extends Matrice {

    public Hilbert(int n) {
        super(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.coefficient[i][j] = 1.0 / (i + j + 1);
            }
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez la taille de la matrice de Hilbert : ");
        int n = sc.nextInt();
        while (n <= 0) {
            System.out.println("Entrez une valeur positif ... : ");
            n = sc.nextInt();
        }

        System.out.println("Matrice de Hilbert de taille " + n + " ...... ");
        Hilbert hilbert = new Hilbert(n);
        System.out.println("La matrice Hilbert est : \n" + hilbert.toString());
        Hilbert recopie = new Hilbert(n);
        recopie.recopie(hilbert);
        try {
            recopie.inverse();
            Matrice produit = Matrice.produit(hilbert, recopie.inverse());
            System.out.println("La matrice inverse de la matrice Hilbert est : \n" + recopie.inverse().toString());
            System.out.println("Le produit de la matrice Hilbert et de son inverse est : \n " + produit.toString());
            System.out.println("Norme du produit entre inverse et la matrice est " + (Math.abs(produit.norme() -1 ) < Matrice.EPSILON  ? "égale à " : " différente de : ") + "celle de la matrice identité");
            System.out.println("Norme Infinie de la matrice du prouit entre inverse et la matrice est " + (Math.abs(produit.normeInfinie() -1 ) < Matrice.EPSILON  ? "égale à " : " différente de : ") + "celle de la matrice identité");
            System.out.println("Conditionnement de la matrice : " + hilbert.conditionnement());
            System.out.println("Conditionnement Infinie de la matrice : " + hilbert.conditionnementInfinie());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
