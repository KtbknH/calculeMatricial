package AlgLin;

public class SysTriangInf extends SysLin{

  /**
   * Constructeur de la classe SysTriangInf
   * @param matrice
   * @param seconde
   * @throws IrregularSysLinException
   */

  public SysTriangInf(Matrice matrice , Vecteur seconde) throws IrregularSysLinException {
    super(matrice, seconde);
  }
  
  /**
   * Methode de resolution du systeme triangulaire inferieur
   * @return solution
   * @throws IrregularSysLinException
   */
  @Override
  public Vecteur resolution() throws IrregularSysLinException {
    Vecteur solution = new Vecteur(this.getOrdre());
    for (int i = 0; i < this.getOrdre(); i++) {
      double somme = 0;
      for (int j = 0; j < i; j++) {
        somme += this.getMatriceSystem().getCoef(i, j) * solution.getCoef(j);
      }
      if(Math.abs(this.getMatriceSystem().getCoef(i, i) - somme) < Matrice.EPSILON) throw new IrregularSysLinException("Division par 0");
      solution.remplacecoef(i, (this.getSecondMembre().getCoef(i) - somme) / this.getMatriceSystem().getCoef(i, i));
    }
    return solution;
  }


  public static void main(String[] args) {
      // Test du constructeur
    // Test du constructeur
    System.out.println("===========SysTriangInf=================");
    double[][] coeff = {{1, 0, 0}, {4, 1, 0}, {3, 0.666666666666667, 1}};
    Matrice matrice = new Matrice(coeff);
    System.out.println(matrice.toString());
    double[] secondMembre = {3,5,8};
    Vecteur vecteur = new Vecteur(secondMembre);
    System.out.println(vecteur.taille());
    Matrice recopie = new Matrice(matrice.nbLigne(), matrice.nbColonne());
    recopie.recopie(matrice);
    try {
      // Ax = b
      SysTriangInf sys = new SysTriangInf(matrice, vecteur);
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


