package AlgLin;

public class SysTriangSupUnite extends SysLin {

  /**
   * Constructeur de la classe SysTriangSupUnite
   * @param matrice
   * @param seconde
   * @throws IrregularSysLinException
   */
  public SysTriangSupUnite(Matrice matrice, Vecteur seconde) throws IrregularSysLinException {
    super(matrice, seconde);
  }

  /**
   * Méthode de résolution d'un système triangulaire supérieur
   * @return solution
   * @throws IrregularSysLinException
   */
  @Override
  public Vecteur resolution() throws IrregularSysLinException {
    Vecteur solution = new Vecteur(this.getOrdre());
    for (int i = this.getOrdre() - 1; i >= 0; i--) {
      double somme = 0;
      for (int j = i + 1; j < this.getOrdre(); j++) {
        somme += this.getMatriceSystem().getCoef(i, j) * solution.getCoef(j);
      }
      solution.remplacecoef(i, (this.getSecondMembre().getCoef(i) - somme) );
    }
    return solution;
  }

  public static void main(String[] args) {
    // Test du constructeur
    double[][] coeff = {{1, 1, -2}, {0, 1, -1.5}, {0, 0, 1}};
    Matrice matrice = new Matrice(coeff);
    System.out.println(matrice.toString());
    double[] secondMembre = {3,5,8};
    Vecteur vecteur = new Vecteur(secondMembre);
    System.out.println(vecteur.taille());
    Matrice recopie = new Matrice(matrice.nbLigne(), matrice.nbColonne());
    recopie.recopie(matrice);
    try {
        // Ax = b
        SysTriangSupUnite sys = new SysTriangSupUnite(matrice, vecteur);
        System.out.println(sys.resolution());
        Matrice resultat = Matrice.addition(Matrice.produit(recopie, sys.resolution()), sys.resolution().produit(-1));
        Vecteur recopiVecteur = new Vecteur(resultat.nbLigne());
        System.out.println("la norme L1 de la solution est : " + (recopiVecteur.normeL1() < Matrice.EPSILON ? 0 : sys.resolution().normeL1()));
        System.out.println("la norme L2 de la solution est : " + (recopiVecteur.normeL2() < Matrice.EPSILON ? 0 : sys.resolution().normeL2()));
        System.out.println("la norme infinie de la solution est : " + (recopiVecteur.normeLinf() < Matrice.EPSILON ? 0 : sys.resolution().normeLinf()));

    } catch (IrregularSysLinException e) {
        System.out.println(e.toString());
    }
  }

}
