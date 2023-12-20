package org.example;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;

import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.camunda.bpm.model.dmn.instance.DecisionTable;
import org.camunda.bpm.model.dmn.instance.Input;
import org.camunda.bpm.model.dmn.instance.InputExpression;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DMN
{
    private java.util.Map<String, Object> map_variables = new java.util.HashMap<String, Object>();
    private List<String> input_names;
    private DmnDecision decision;
    private DmnDecisionTableResult result;
    //constructeur qui initialise un moteur dmn, une variable inputStream et une variable decision
    public DMN(List<String> valeurs, String fichier) throws IOException {
        //on recupere le fichier dmn et on le met dans une variable de type fichier
        //on recupere une inputStream à partir du fichier sourceDmn
        //BUG: le stream est nul alors que la chaine de caractères est bien reçue (heureusement)
        //BUG Corrigé: il fallait mettre le fichier dans un byte array
        InputStream sourceVariables = new ByteArrayInputStream(fichier.getBytes());
        InputStream sourceFichier= new ByteArrayInputStream(fichier.getBytes());
        //pour chaque input, on cree une variable
        input_names = recupererInputs(sourceVariables);
        //on itère sur les input_names et on les associe aux valeurs
        for (int i = 0; i < input_names.size(); i++) {
            map_variables.put(input_names.get(i), valeurs.get(i));
        }
        DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
        this.decision = dmnEngine.parseDecision("decision", sourceFichier);
        this.result = dmnEngine.evaluateDecisionTable(decision, map_variables);
    }

    //la méthode suivante recuperera les entrees de la table et les stockera dans une liste d'entrees
    public List<String> recupererInputs(InputStream inputStream){
        DmnModelInstance dmnModelInstance = Dmn.readModelFromStream(inputStream);
        DecisionTable decisionTable = dmnModelInstance.getModelElementById("decisionTable");
        List<String> expressions = new ArrayList<>();
        Collection<Input> inputs = decisionTable.getInputs();
        inputs.stream().forEach(input->{
            // chaque variable dans "input" sera mise dans expressions. Cela facilitera la manipulation après
            InputExpression inputExpression = input.getInputExpression();
            expressions.add(inputExpression.getText().getTextContent());
        });
        return expressions;
    }

    public String montreResultat(){
        return this.result.getSingleResult().toString();
    }
}
