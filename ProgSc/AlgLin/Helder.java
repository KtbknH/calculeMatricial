package AlgLin;

public class Helder extends SysLin {
  private Matrice m;
  private Vecteur v;

  /**
   * Constructeur de la classe Helder
   * @param Matrice
   * @param Vecteur
   * @throws IrregularSysLinException
   */
  public Helder(Matrice m, Vecteur v) throws IrregularSysLinException {
    super(m, v);
    this.m = m;
    this.v = v;
  }

  /**
   * Méthode de factorisation LDR
   * @return void
   */  
  public void factorisationLDR() throws IrregularSysLinException {
    int n = this.getOrdre();

    //L
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < i; j++) {
        double somme = 0;
        for (int k = 0; k < j; k++) {
          somme += m.getCoef(i, k) * m.getCoef(k, k) * m.getCoef(k, j);
        }
        m.remplacecoef(i, j, (m.getCoef(i, j) - somme) / m.getCoef(j, j));
      }

      // D
      double somme = 0;
      for (int k = 0; k < i; k++) {
        somme += m.getCoef(i, k) * m.getCoef(k, k) * m.getCoef(k, i);
      }
      m.remplacecoef(i, i, m.getCoef(i, i) - somme);
      if(Math.abs(m.getCoef(i, i)) < Matrice.EPSILON) throw new IrregularSysLinException("Division par 0");

      //R
      for (int j = i + 1; j < n; j++) {
        double sommeL = 0;
        for (int k = 0; k < i; k++) {
          sommeL += m.getCoef(i, k) * m.getCoef(k, k) * m.getCoef(k, j);
        }
        m.remplacecoef(i, j, (m.getCoef(i, j) - sommeL) / m.getCoef(i, i));
      }
    }
  }

  /**
   * Méthode de résolution partielle d'un système de Helder
   * @return un Vecteur solution
   * @throws IrregularSysLinException
   */
  public Vecteur resolutionPartielle() throws IrregularSysLinException {

    SysTriangInfUnite l = new SysTriangInfUnite(m, v);
    Vecteur y = l.resolution();

    SysDiagonal d = new SysDiagonal(m, y);
    Vecteur z = d.resolution();

    SysTriangSupUnite r = new SysTriangSupUnite(m, z);
    Vecteur x = r.resolution();

    return x;
  }

    /**
   * Méthode de résolution d'un système de Helder
   * @return un Vecteur solution
   * @throws IrregularSysLinException
   */

   @Override
   public Vecteur resolution() throws IrregularSysLinException {
     factorisationLDR();
     return resolutionPartielle();
   }

  /**
   * Méthode de modification du Vecteur second membre
   * @return void
   */
  public void setSecondMembre(Vecteur v){
    this.v = v;
  }

  public static void main(String[] args) {
    try {
      // Exemple de matrice et de second membre lus depuis un fichier

      double[][] coeff = {{1, 1, -2}, {4, -2, 1}, {3, -1, 3}};
      double[] secondMembre = {3, 5, 8};
      Matrice matrice = new Matrice(coeff);
      Vecteur vecteur = new Vecteur(secondMembre);

      Helder systeme = new Helder(matrice, vecteur);
      System.out.println("Matruce Helder :\n " + systeme.getMatriceSystem());
      System.out.println("Second membre : \n" + systeme.getSecondMembre());
      System.out.println("Ordre du système : " + systeme.getOrdre());
      systeme.factorisationLDR();
      System.out.println("Matrice LDR : \n" + systeme.getMatriceSystem());
      System.out.println("===========Test de la résolution partielle===========");
      Vecteur solution = systeme.resolutionPartielle();
      System.out.println("Solution du système :\n " + solution);

      // Test avec A x = b
      System.out.println("===========Test avec A x = b===========");
      double[][] coeffA = {{1, 1, -2}, {4, -2, 1}, {3, -1, 3}};
      double[] secondMembreA = {3, 5, 8};
      Matrice matriceA = new Matrice(coeffA);
      Vecteur vecteurA = new Vecteur(secondMembreA);
      Helder systemeA = new Helder(matriceA, vecteurA);
      System.out.println("Matrice A : \n" + systemeA.getMatriceSystem());
      Vecteur solutionA = systemeA.resolution();
      System.out.println("Matrice A : \n" + systemeA.getMatriceSystem());
      System.out.println("Solution pour A x = b :\n " + solutionA);
    
      // Test avec A²x = b
      System.out.println("==========Test avec A²x = b==========");
      double[][] coeffB = {{1, 1, -2}, {4, -2, 1}, {3, -1, 3}};
      double[] secondMembreB = {3, 5, 8};
      Matrice matriceB = new Matrice(coeffB);
      Matrice recopie = new Matrice(matriceB.nbLigne(), matriceB.nbColonne());
      recopie.recopie(matriceB);
      Vecteur vecteurB = new Vecteur(secondMembreB);
      Helder systemeB = new Helder(matriceB, vecteurB);
      System.out.println("Matrice B : \n" + systemeB.getMatriceSystem());
      Vecteur solutionB = systemeB.resolution();
      Matrice resultat = Matrice.addition(Matrice.produit(recopie, solutionB), vecteurB.produit(-1));
      Vecteur recopiVecteur = new Vecteur(resultat.nbLigne());
      recopiVecteur.recopie(resultat);
      System.out.println("Matrice B : \n" + systemeB.getMatriceSystem());
      System.out.println("la norme L1 de la solution est : " + (recopiVecteur.normeL1() < Matrice.EPSILON ? 0 : recopiVecteur.normeL1()));
      System.out.println("la norme L2 de la solution est : " + (recopiVecteur.normeL2() < Matrice.EPSILON ? 0 : recopiVecteur.normeL2()));
      System.out.println("la norme infinie de la solution est : " + (recopiVecteur.normeLinf() < Matrice.EPSILON ? 0 : recopiVecteur.normeLinf()));
    } catch (IrregularSysLinException e) {
      System.out.println(e.toString());
    }
  }

}
