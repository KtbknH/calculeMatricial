package AlgLin;

public class SysDiagonal extends SysLin {

  /**
   * Constructeur de la classe SysDiagonal.
   * 
   * @param matrice  la matrice du système linéaire
   * @param seconde  le vecteur des second membres
   * @throws IrregularSysLinException si la matrice n'est pas diagonale
   */

  public SysDiagonal(Matrice matrice , Vecteur seconde) throws IrregularSysLinException {
    super(matrice, seconde);
  }
  
  /**
   * Résolution du système linéaire.
   * 
   * @return le vecteur solution
   * @throws IrregularSysLinException si la matrice n'est pas diagonale
   */
  
  @Override
  public Vecteur resolution() throws IrregularSysLinException {
    Vecteur solution = new Vecteur(this.getOrdre());
    for (int i = 0; i < this.getOrdre(); i++) {
      if(Math.abs(this.getMatriceSystem().getCoef(i, i)) < Matrice.EPSILON) throw new IrregularSysLinException("Division par 0");
      solution.remplacecoef(i, this.getSecondMembre().getCoef(i) / this.getMatriceSystem().getCoef(i, i));
    }
    return solution;
  }

  public static void main(String[] args) {
    // Test du constructeur
    System.out.println("================SysDiagonal=============");
    double[][] coeff = {{1, 0, 0}, {0, -6, 0}, {0, 0, 3}};
    Matrice matrice = new Matrice(coeff);
    System.out.println(matrice.toString());
    double[] secondMembre = {3, -7, 3.666666666666666};
    Vecteur vecteur = new Vecteur(secondMembre);
    System.out.println(vecteur.taille());
    Matrice recopie = new Matrice(matrice.nbLigne(), matrice.nbColonne());
    recopie.recopie(matrice);
    try {
      SysDiagonal sys = new SysDiagonal(matrice, vecteur);
      System.out.println(sys.resolution());
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
