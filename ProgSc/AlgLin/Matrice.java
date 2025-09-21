package AlgLin;

import java.io.*;
import java.util.*;

public class Matrice {

    /**
     * Définir ici les attributs de la classe *
     */
    protected double coefficient[][];
    public final static double EPSILON = 1.0E-06;

    /**
     * Définir ici les constructeur de la classe *
     */
    Matrice(int nbligne, int nbcolonne) {
        this.coefficient = new double[nbligne][nbcolonne];
    }

    Matrice(double[][] tableau) {
        coefficient = tableau;
    }


    Matrice(String fichier) {
        try {
            Scanner sc = new Scanner(new File(fichier));
            int ligne = sc.nextInt();
            int colonne = sc.nextInt();
            this.coefficient = new double[ligne][colonne];
            for (int i = 0; i < ligne; i++) {
                for (int j = 0; j < colonne; j++) {
                    this.coefficient[i][j] = sc.nextDouble();
                }
            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Fichier absent");
        }
    }

    /**
     * Definir ici les autres methodes
     */

    public void recopie(Matrice arecopier) {
        int ligne, colonne;
        ligne = arecopier.nbLigne();
        colonne = arecopier.nbColonne();
        this.coefficient = new double[ligne][colonne];
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                this.coefficient[i][j] = arecopier.coefficient[i][j];
            }
        }
    }

    public int nbLigne() {
        return this.coefficient.length;
    }

    public int nbColonne() {
        return this.coefficient[0].length;
    }

    public double getCoef(int ligne, int colonne) {
        return this.coefficient[ligne][colonne];
    }

    public void remplacecoef(int ligne, int colonne, double value) {
        this.coefficient[ligne][colonne] = value;
    }

    public String toString() {
        int ligne = this.nbLigne();
        int colonne = this.nbColonne();
        String matr = "";
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                if (j == 0) {
                    matr += this.getCoef(i, j);
                } else {
                    matr += " " + this.getCoef(i, j);
                }
            }
            matr += "\n";
        }
        return matr;
    }

    public Matrice produit(double scalaire) {
        int ligne = this.nbLigne();
        int colonne = this.nbColonne();
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                this.coefficient[i][j] *= scalaire;
            }
        }
        return this;
    }

    static Matrice addition(Matrice a, Matrice b) {
        int ligne = a.nbLigne();
        int colonne = a.nbColonne();
        Matrice mat = new Matrice(ligne, colonne);
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                mat.coefficient[i][j] = a.coefficient[i][j] + b.coefficient[i][j];
            }
        }
        return mat;
    }

    static Matrice verif_addition(Matrice a, Matrice b) throws Exception {
        if ((a.nbLigne() == b.nbLigne()) && (a.nbColonne() == b.nbColonne())) {
            int ligne = a.nbLigne();
            int colonne = a.nbColonne();
            Matrice mat = new Matrice(ligne, colonne);
            for (int i = 0; i < ligne; i++) {
                for (int j = 0; j < colonne; j++) {
                    mat.coefficient[i][j] = a.coefficient[i][j] + b.coefficient[i][j];
                }
            }
            return mat;
        } else {
            throw new Exception("Les deux matrices n'ont pas les mêmes dimensions !!!");
        }
    }

    static Matrice produit(Matrice a, Matrice b) {
        int ligne, colonne;
        ligne = a.nbLigne();
        colonne = b.nbColonne();
        Matrice mat = new Matrice(ligne, colonne);
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                mat.coefficient[i][j] = 0;
                for (int k = 0; k < a.nbColonne(); k++) {
                    mat.coefficient[i][j] += a.coefficient[i][k] * b.coefficient[k][j];
                }
            }
        }
        return mat;
    }

    static Matrice verif_produit(Matrice a, Matrice b) throws Exception {
        int ligne = 0;
        int colonne = 0;
        if (a.nbColonne() == b.nbLigne()) {
            ligne = a.nbLigne();
            colonne = b.nbColonne();
        } else {
            throw new Exception("Dimensions des matrices à multiplier incorrectes");
        }

        Matrice mat = new Matrice(ligne, colonne);
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                mat.coefficient[i][j] = 0;
                for (int k = 0; k < a.nbColonne(); k++) {
                    mat.coefficient[i][j] += a.coefficient[i][k] * b.coefficient[k][j];
                }
            }
        }
        return mat;
    }

    /**
     * La methode inverse permet de calculer l'inverse d'une matrice
     *
     * @return Matrice
     * @throws Exception
     */
    public Matrice inverse() throws Exception {
        if (this.nbLigne() != this.nbColonne()) {
            throw new Exception("La matrice n'est pas carrée");
        }
        Matrice mattrice = new Matrice(this.nbLigne(), this.nbColonne());
        Matrice matrice = new Matrice(this.nbLigne(), this.nbColonne());
        mattrice.recopie(this);
        Vecteur vecteur = new Vecteur(this.nbLigne());

        Helder ldr = new Helder(mattrice, vecteur);
        ldr.factorisationLDR();

        for (int i = 0; i < this.nbLigne(); i++) {
            if (0 < i) {
                vecteur.remplacecoef(i - 1, 0);
            }
            vecteur.remplacecoef(i, 1);
            ldr.setSecondMembre(vecteur);
            Vecteur res = ldr.resolutionPartielle();
            for (int j = 0; j < this.nbLigne(); j++) {
                matrice.remplacecoef(j, i, res.getCoef(j));
            }
        }
        return matrice;
    }

    /**
     * methode de calcul de la transposée de la matrice
     *
     * @return Matrice
     */

    public Matrice transpose() {
        Matrice mat = new Matrice(this.nbColonne(), this.nbLigne());
        for (int i = 0; i < this.nbLigne(); i++) {
            for (int j = 0; j < this.nbColonne(); j++) {
                mat.coefficient[j][i] = this.coefficient[i][j];
            }
        }
        return mat;
    }

    /**
     * norme de la matrice
     *
     * @return double
     */
    public double norme() {
        double norme = 0;
        for (int i = 0; i < this.nbLigne(); i++) {
            double somme = 0;
            for (int j = 0; j < this.nbColonne(); j++) {
                somme += Math.abs(this.getCoef(i, j));
            }
            if (somme > norme) {
                norme = somme;
            }
        }
        return norme;
    }

    /**
     * methode de calcul de la norme de la matrice infinie
     *
     * @return double
     */
    public double normeInfinie() {
        double norme = 0;
        for (int i = 0; i < this.nbLigne(); i++) {
            double somme = 0;
            for (int j = 0; j < this.nbColonne(); j++) {
                somme += Math.abs(this.getCoef(j, i));
            }
            if (somme > norme) {
                norme = somme;
            }
        }
        return norme;
    }

    /**
     * methode de calcul de conditionnement de la matrice
     *
     * @return double
     */
    public double conditionnement() throws Exception {
        return this.norme() * this.inverse().norme();
    }

    /**
     * methode de calcul de conditionnement de la matrice infinie
     *
     * @return double
     */
    public double conditionnementInfinie() throws Exception {
        return this.normeInfinie() * this.inverse().normeInfinie();
    }

    public static void main(String[] args) throws Exception {

        double mat[][] = {{2, 1}, {0, 1}};
        Matrice a = new Matrice(mat);
        System.out.println("construction d'une matrice par affectation d'un tableau :\n" + a);
        Matrice b = new Matrice("matrice.txt");
        System.out.println("Construction d'une matrice par lecture d'un fichier :\n" + b);
        Matrice c = new Matrice(2, 2);
        c.recopie(b);
        System.out.println("Recopie de la matrice b :\n" + c);
        System.out.println("Nombre de lignes et colonnes de la matrice c : " + c.nbLigne()
                + ", " + c.nbColonne());
        System.out.println("Coefficient (2,2) de la matrice b : " + b.getCoef(1, 1));
        System.out.println("Nouvelle valeur de ce coefficient : 8");
        b.remplacecoef(1, 1, 8);
        System.out.println("Vérification de la modification du coefficient");
        System.out.println("Coefficient (2,2) de la matrice b : " + b.getCoef(1, 1));
        System.out.println("Addition de 2 matrices : affichage des 2 matrices "
                + "puis de leur addition");
        System.out.println("matrice 1 :\n" + a + "matrice 2 :\n" + b + "somme :\n"
                + Matrice.addition(a, b));
        System.out.println("Produit de 2 matrices : affichage des 2 matrices "
                + "puis de leur produit");
        System.out.println("matrice 1 :\n" + a + "matrice 2 :\n" + b + "produit :\n"
                + produit(a, b));

        //test de la methode inverse
        double matrice[][] = {{-1, 1, -1}, {1, 1, 1}, {-1, 1, 1}};
        double identite[][] = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        Matrice matI = new Matrice(matrice);
        Matrice id = new Matrice(identite);
        Matrice inverse = matI.inverse();
        Matrice produit = Matrice.produit(matI, inverse);
        System.out.println("Matrice identité : \n" + id);
        System.out.println("Matrice inverse : \n" + inverse);
        System.out.println("Produit de la matrice et son inverse : \n" + produit);
        System.out.println("Norme du produit entre inverse et la matrice : " + (produit.norme() != id.norme() ? "Erreur de calcul" : "la norme est : " + produit.norme()));
        System.out.println("Norme Infinie de la matrice du prouit entre inverse et la matrice : " + (produit.normeInfinie() != id.normeInfinie() ? "Erreur de calcul" : "la norme est : " + produit.normeInfinie()));
        System.out.println("Conditionnement de la matrice : " + produit.conditionnement());
        System.out.println("Conditionnement Infinie de la matrice : " + produit.conditionnementInfinie());

    }
}
