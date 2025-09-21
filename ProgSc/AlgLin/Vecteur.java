package AlgLin;
public class Vecteur extends Matrice{
  
  /**
   * Constructeur de la classe Vecteur
   * @param nbligne nombre de ligne
   */
  public Vecteur(int nbligne){
    super(nbligne, 1);
  }

  /**
   * Constructeur de la classe Vecteur
   * @param tableau de double pour coefficient
   */
  public Vecteur(double[] tableau){
    super(new double[tableau.length][1]);
    for(int i = 0; i < tableau.length; i++)
      this.coefficient[i][0] = tableau[i];
  }

  /**
   * Constructeur de la classe Vecteur pour la lecture d'un fichier
   * @param fichier
   */
  public Vecteur(String fichier){
    super(fichier);
  }

  /**
   * Méthode qui retourne la taille du vecteur
   * @return taille
   */
  public int taille(){
    return this.nbLigne();
  }

  /**
   * Méthode qui retourne le coefficient d'une ligne
   * @param ligne
   * @return coefficient
   */
  public double getCoef(int ligne){
    return this.coefficient[ligne][0];
  }

  /**
   * Méthode qui remplace le coefficient d'une ligne
   * @param ligne
   * @param value
   * @return coefficient
   */
  public double remplacecoef(int ligne, double value){
    return this.coefficient[ligne][0] = value;
  }

  /** 
   * Méthode qui retourne le vecteur sous forme de chaine de caractère
   * @return String
   */
  @Override
  public String toString(){
    return super.toString();
  }

  /**
   * Méthode qui recopie le vecteur
   * @param arecopier
   */
  public void recopie(Vecteur arecopier){
    int ligne = arecopier.taille();
    this.coefficient = new double[ligne][1];
    for(int i = 0; i < ligne; i++)
      this.coefficient[i][0] = arecopier.getCoef(i);
  }

  /**
   * Méthode qui retourne la norme L1 du vecteur
   * @return somme
   */
  public double normeL1(){
    double somme = 0;
    for(int i = 0; i < taille(); i++)
      somme += Math.abs(this.getCoef(i));
    return somme;
  }

  /**
   * Méthode qui retourne la norme L2 du vecteur
   * @return somme
   */
  public double normeL2(){
    double somme = 0;
    for(int i = 0; i < taille(); i++)
      somme += Math.pow(this.getCoef(i), 2);
    return Math.sqrt(somme);
  }

  /**
   * Méthode qui retourne la norme Linf du vecteur
   * @return max
   */

  public double normeLinf(){
    double max = 0;
    for(int i = 0; i < taille(); i++)
      max = Math.max(max, Math.abs(this.getCoef(i)));
    return max;
  }

  /**
   * Méthode qui retourne le produit scalaire de deux vecteurs
   * @param VecteurA 
   * @param VecteurB
   * @return somme
   */
  public static double produitScalaire(Vecteur a, Vecteur b){
    double somme = 0;
    for(int i = 0; i < a.taille(); i++)
      somme += a.getCoef(i) * b.getCoef(i);
    return somme;
  }

  public static void main(String[] args) {
    // test Constructeur 0
    Vecteur v0 = new Vecteur(3);
    // test Constructeur 1
    double[] tab = {1, 2, 3};
    Vecteur v = new Vecteur(tab);
    System.out.println(v);
    System.out.println(v.taille());
    // test Constructeur 2
    Vecteur v2 = new Vecteur("vecteur.txt");
    System.out.println("Le vecteur du fichier :" + v2);
    System.out.println("La taille du vecteur :" + v2.taille());
    // test getCoef
    System.out.println(" Le Coeffiecient : " + v.getCoef(1));
    // test remplacecoef
    System.out.println("Remplacer par :" + v.remplacecoef(1, 5));
    System.out.println(v);
    // test produitScalaire
    System.out.println("Le produit Scalaire :" + produitScalaire(v, v2));
    // test normeL1
    System.out.println("La norme L1 :" + v.normeL1());
    // test normeL2
    System.out.println("La norme L2 :" + v.normeL2());
    // test normeLinf
    System.out.println("La norme Linf :" + v.normeLinf());
    //test recopie
    v0.recopie(v);
    System.out.println("Recopie de v dans v0 :" + v0);
  }
}
