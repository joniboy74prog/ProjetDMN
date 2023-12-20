package org.example;

public class ExceptionFichierMalFormule extends Exception {
    // Vous pouvez ajouter des constructeurs supplémentaires selon vos besoins
    public ExceptionFichierMalFormule() {
        super("Veuillez introduire le nombre correct de valeurs");
    }
}
