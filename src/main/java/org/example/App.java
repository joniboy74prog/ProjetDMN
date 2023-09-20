package org.example;

import org.camunda.bpm.model.dmn.*;

import java.io.File;

public class App {
    public static void main(String[] args) {
        File fichier = new File("DMN/dmn.xml");
        DmnModelInstance dmn_de_test = Dmn.readModelFromFile(fichier);
        System.out.print(dmn_de_test);
    }
}
