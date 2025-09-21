package AlgLin;

public class IrregularSysLinException extends Exception {
    /**
     * Constructeur de la classe IrregularSysLinException
     */
    public IrregularSysLinException() {
        super();
    }

    /**
     * Constructeur de la classe IrregularSysLinException
     * @param message
     */
    public IrregularSysLinException(String message) {
        super(message);
    }

    /**
     * Methode qui retourne le message d'erreur
     * @return String
     */
    @Override
    public String toString() {
        return "Le système linéaire est irrégulier";
    }   

}
