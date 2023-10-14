package org.example;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import java.io.*;

public class ParseurDMN {
    private DmnDecisionTableResult result;
    private DmnDecision decision;

    public ParseurDMN(String season, String guestCount) throws FileNotFoundException{
            // prepare variables for decision evaluation
            VariableMap variables = Variables
                    .putValue("season", season)
                    .putValue("guestCount", guestCount);

            // create a new default DMN engine
            DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
            // parse decision from resource input stream
            File file = new File("DMN/dmn.xml");

            // Créer un InputStream à partir du fichier
            InputStream inputStream = new FileInputStream(file);
            decision = dmnEngine.parseDecision("decision", inputStream);
            // evaluate decision
            result = dmnEngine.evaluateDecisionTable(decision, variables);


                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    System.err.println("Could not close stream: "+e.getMessage());
                }
        }
        public String getResultat(){
            return result.getFirstResult().toString();
        }
}