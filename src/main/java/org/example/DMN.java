package org.example;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;

import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.DecisionServiceImpl;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.repository.DecisionDefinition;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.camunda.bpm.model.dmn.instance.Decision;
import org.camunda.bpm.model.dmn.instance.DecisionTable;
import org.camunda.bpm.model.dmn.instance.Input;
import org.camunda.bpm.model.dmn.instance.InputExpression;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DMN
{
    //fonction qui retourne une variable de type inputStream à partir du fichier dmn dans le répertoire resources
    //commentaire: le chemin doit commencer par un / pour que le fichier soit trouvé
    public static InputStream inputStream = DMN.class.getResourceAsStream("/dmn.xml");
    public static List<String> getVariableNames(DecisionDefinition decisionDefinition, RepositoryService repositoryService) {
        // Get the DMN model instance for the decision definition
        DmnModelInstance dmnModelInstance = repositoryService.getDmnModelInstance(decisionDefinition.getId());
        // Get the decision element from the model
        Decision decision = dmnModelInstance.getModelElementsByType(Decision.class).iterator().next();
        // Get the input elements (representing the variables)
        Collection<Input> inputs = decision.getChildElementsByType(Input.class);
        // Extract variable names from the input elements
        return inputs.stream()
                .map(input -> {
                    InputExpression inputExpression = input.getChildElementsByType(InputExpression.class).iterator().next();
                    return inputExpression.getTextContent();
                })
                .collect(Collectors.toList());
    }
    public static List<String> getInputExpression(InputStream inputStream){

        DmnModelInstance dmnModelInstance = Dmn.readModelFromStream(inputStream);

        DecisionTable decisionTable = dmnModelInstance.getModelElementById("decisionTable");
        List<String> expressions = new ArrayList<>();
        Collection<Input> inputs = decisionTable.getInputs();
        inputs.stream().forEach(input->{
            //add your logic to getInputExpression
            InputExpression inputExpression = input.getInputExpression();
            expressions.add(inputExpression.getText().getTextContent()); //check this returns expected o/p
        });

        return expressions;
    }


    public static void main(String[] args )
    {
        DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
/*        DmnDecision decision = dmnEngine.parseDecision("decision", inputStream);


        java.util.Map<String, Object> variables = new java.util.HashMap<String, Object>();
        variables.put("season", "Winter");
        variables.put("guestCount", 10);
        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);*/
        System.out.println(getInputExpression(inputStream));

    }
}
