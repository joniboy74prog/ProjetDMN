package org.example;

import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.model.dmn.*;
import org.camunda.bpm.model.dmn.instance.DecisionTable;
import java.io.File;

public class App {
    public static void main(String[] args) {
        DmnEngine dmnEngine = DmnEngineConfiguration
                .createDefaultDmnEngineConfiguration()
                .buildEngine();


    }
}
