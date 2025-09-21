package AlgLin;

public abstract class SysLin {
  private   int ordre;
  private   Matrice matriceSystem;
  private   Vecteur secondMembre;

  /**
    * Constructeur de la classe SysLin
    * @param Matrice
    * @param Vecteur
    * @throws IrregularSysLinException
  */

  public SysLin(Matrice matrice , Vecteur seconde) throws IrregularSysLinException {
/*    if (matrice.nbColonne() != matrice.nbLigne()) {
      throw new IrregularSysLinException();
    }
    if (matrice.nbLigne() != seconde.taille()) {
      throw new IrregularSysLinException();
    }*/
    this.matriceSystem = matrice;
    this.secondMembre = seconde;
    this.ordre = matrice.nbLigne();
  } 

  /**
   * Methode qui retourne la taille d'une ligne
   * @return int
   */
  public int getOrdre() {
    return this.ordre;
  }

  /**
   * Methode qui retourne la matrice du systeme
   * @return Matrice
   */
  public Matrice getMatriceSystem() {
    return this.matriceSystem;
  }

  /**
   * Methode qui retourne le second membre du systeme
   * @return Vecteur
   */
  public Vecteur getSecondMembre() {
    return this.secondMembre;
  }

  /**
   * Methode qui modifie la taille d'une ligne
   * @param int
   */

  public void setOrdre(int ordre) {
    this.ordre = ordre;
  }

  /**
   * Methode qui modifie la matrice du systeme
   * @param Matrice
   */

  public void setMatriceSystem(Matrice matrice) {
    this.matriceSystem = matrice;
  }

  /**
   * Methode qui retourne la resolution du systeme
   * @return Vecteur
   * @throws IrregularSysLinException
   */
  public abstract Vecteur resolution() throws IrregularSysLinException;
}
