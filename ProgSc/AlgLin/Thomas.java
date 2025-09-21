package AlgLin;

public class Thomas extends SysLin {

  public Thomas(Mat3Diag matrice, Vecteur secondMembre) throws IrregularSysLinException{
    super(matrice, secondMembre);
  }

  @Override
  public Vecteur resolution() throws IrregularSysLinException {
    int n = getMatriceSystem().nbColonne();
    double[] p = new double[n - 1];
    double[] q = new double[n - 1];
    Vecteur res = new Vecteur(getMatriceSystem().nbColonne());
    
    if(Math.abs(getMatriceSystem().getCoef(1, 0)) < Matrice.EPSILON) throw new IrregularSysLinException("Division par 0");
    
    p[0] = - getMatriceSystem().getCoef(2, 0) / getMatriceSystem().getCoef(1, 0);
    q[0] = getSecondMembre().getCoef(0) / getMatriceSystem().getCoef(1, 0);

    for(int i = 1; i < n - 1; i++) {
      double B = getMatriceSystem().getCoef(1, i) + getMatriceSystem().getCoef(0, i) * p[i - 1];
      
      if(Math.abs(B) < Matrice.EPSILON ) throw new IrregularSysLinException("Division par 0") ;

      p[i] = - getMatriceSystem().getCoef(2, i) / B;
      q[i] = (getSecondMembre().getCoef(i) - getMatriceSystem().getCoef(0, i) * q[i - 1]) / B;
    }
    
    double up, down;
    up = getSecondMembre().getCoef(n - 1) - getMatriceSystem().getCoef(0, n - 1) * q[n - 2];
    down = getMatriceSystem().getCoef(1, n - 1) +  getMatriceSystem().getCoef(0, n - 1) * p[n - 2];

    if(Math.abs(down) < Matrice.EPSILON ) throw new IrregularSysLinException("Division par 0") ; 
    
    res.remplacecoef(n - 1, up/down);

    for(int i = n - 2 ; 0 <= i ; i--) {
      res.remplacecoef(i, (p[i] * res.getCoef(i + 1) + q[i]));
    }

    return res;
  }

  public static void main(String[] args) throws Exception {
    double[][] coeff = {{0,-1 , -1 , -1}, {2, 2, 2 , 2}, {-1,-1, -1, 0}};
    Mat3Diag matrice = null;
    
    matrice = new Mat3Diag(coeff);

    double[] secondMembre = {-2 , -2 , -2 , 23};
    Vecteur vecteur = new Vecteur(secondMembre);
    
    // Calcul du résultat
    Thomas thomas = new Thomas(matrice, vecteur);
    Vecteur res = thomas.resolution();

    // Calcul de la différence
    Matrice diff = Matrice.addition(Mat3Diag.produit(matrice, res), vecteur.produit(-1));
    Vecteur diff_vect = new Vecteur(3);
    for(int i = 0; i < 3; i++)
      diff_vect.remplacecoef(i, diff.getCoef(i, 0));

    // Affichage
    System.out.println("A = \n" + matrice.toString());
    System.out.println("\nd = \n" + vecteur.toString());
    System.out.println("\nx = \n" + res.toString() + "\n");
    System.out.println("la norme L1 de la différence est : " + (diff_vect.normeL1() < Matrice.EPSILON ? 0 : res.normeL1()));
    System.out.println("la norme L2 de la différence est : " + (diff_vect.normeL2() < Matrice.EPSILON ? 0 : res.normeL2()));
    System.out.println("la norme Linfini de la différence est : " + (diff_vect.normeLinf() < Matrice.EPSILON ? 0 : res.normeLinf()));
  }
}