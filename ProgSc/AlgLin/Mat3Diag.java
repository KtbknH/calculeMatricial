package AlgLin;

public class Mat3Diag extends Matrice {


  public Mat3Diag(int dim1, int dim2) throws Exception {
    super(dim1, dim2);
    if(dim1 != 3) throw new Exception("La matrice doit être de ligne 3");
  }

  public Mat3Diag(double tab[][]) throws Exception {
    super(tab);
    if(tab.length != 3) throw new Exception("La matrice doit être de ligne 3");
  }

  public Mat3Diag(int dim){
    super(3, dim);
  }

  public int taille(){
    return this.nbColonne();
  } 
 
  public static Vecteur produit(Mat3Diag mat ,Vecteur vecteur) {
    Vecteur produit = new Vecteur(vecteur.taille());
    
    produit.remplacecoef(0, mat.getCoef(1, 0) * vecteur.getCoef(0) + mat.getCoef(2, 0) * vecteur.getCoef(1));

    for(int i = 1; i < mat.nbColonne() - 1; i++){
      produit.remplacecoef(i, mat.getCoef(0, i) * vecteur.getCoef(i - 1) + mat.getCoef(1, i) * vecteur.getCoef(i) + mat.getCoef(2, i) * vecteur.getCoef(i + 1));
    }

    produit.remplacecoef(mat.nbColonne() - 1, mat.getCoef(0, mat.nbColonne() - 1) * vecteur.getCoef(mat.nbColonne() - 2) + mat.getCoef(1, mat.nbColonne() - 1) * vecteur.getCoef(mat.nbColonne() - 1));

    return produit;
  }

  public static void main(String[] args) {
    double[][] coeff = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    Mat3Diag matrice = null;
    
    try {
      matrice = new Mat3Diag(coeff);
    } catch (Exception e) {
      e.printStackTrace();
    
    }
    double[] secondMembre = {3,5,8};
    Vecteur vecteur = new Vecteur(secondMembre);
    System.out.println("Ordre considéré = " + vecteur.taille() + "\n");
    System.out.println("Matrice = \n" + matrice.toString() + "\n");
    System.out.println("Vecteur = \n" + vecteur.toString() + "\n");
    System.out.println("Produit = \n" + Mat3Diag.produit(matrice, vecteur));
  }
}