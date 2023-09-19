package org.example;

import java.util.Arrays;

import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import lombok.extern.slf4j.Slf4j;



public class App 
{
    public static void main( String[] args )
    {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();


        DMNRuntime dmnRuntime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);
        String namespace = "http://www.redhat.com/_c7328033-c355-43cd-b616-0aceef80e52a";
        String modelName = "dmn-movieticket-ageclassification";
        DMNModel dmnModel = dmnRuntime.getModel(namespace, modelName);

        DMNContext dmnContext = dmnRuntime.newContext();  

        for (Integer age : Arrays.asList(1,12,13,64,65,66)) {
            dmnContext.set("Age", age);  
            DMNResult dmnResult =
                dmnRuntime.evaluateAll(dmnModel, dmnContext);  

            for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {  
                log.info("Age: " + age + ", " +
                        "Decision: '" + dr.getDecisionName() + "', " +
                        "Result: " + dr.getResult());
        }
        }
    }
}
