package AlgLin;

public class SysTriangSup extends SysLin {

  /**
   * Constructeur de la classe SysTriangSup
   * @param matrice
   * @param seconde
   * @throws IrregularSysLinException
   */

  public SysTriangSup(Matrice matrice , Vecteur seconde) throws IrregularSysLinException {
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
      for (int j = 0; j < i ; j++) {
        somme += this.getMatriceSystem().getCoef(i, j) * solution.getCoef(j);
      }
      if(Math.abs(this.getMatriceSystem().getCoef(i, i) - somme) < Matrice.EPSILON) throw new IrregularSysLinException("Division par 0");
      solution.remplacecoef(i, (this.getSecondMembre().getCoef(i) - somme) / this.getMatriceSystem().getCoef(i, i));
    }
    return solution;
    
  }

  public static void main(String[] args) {
    // Test du constructeur
    System.out.println("===========SysTriangSup=================");
    double[][] coeff = {{1, 1, -2}, {0, 1, -1.5}, {0, 0, 1}};
    Matrice matrice = new Matrice(coeff);
    System.out.println(matrice.toString());
    double[] secondMembre = {3, 1.66666666666667, 1.222222222222222};
    Vecteur vecteur = new Vecteur(secondMembre);
    System.out.println(vecteur.taille());
    Matrice recopie = new Matrice(matrice.nbLigne(), matrice.nbColonne());
    recopie.recopie(matrice);
    try {
        // Ax = b
        SysTriangSup sys = new SysTriangSup(matrice, vecteur);
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

