package com.example.workflow;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Deployment(resources = "LoanProcessByType.bpmn")
@SpringBootTest
@RunWith(SpringRunner.class)
public class LoanProcessBPMNTest extends AbstractProcessEngineRuleTest {

    @Mock
    private ProcessScenario insuranceApplication;
    @Test
    public void loanProcessingStartAndFinish() throws InterruptedException {
        //autoMock("LoanProcessByType.bpmn");

        // given
       /* when(insuranceApplication.waitsAtServiceTask("dispatchActivity")).thenReturn(externalTaskDelegate -> {
                    externalTaskDelegate.complete(withVariables("check", true));
                }
        );*/

        final ProcessInstance processInstance = runtimeService().createMessageCorrelation("Msg-StartLoanProcess")
                .setVariable("name", "Suresh")
                .setVariable("age", 10)
                .setVariable("loanType", "PL")
                .setVariable("loanAmount", 300)
                .correlateStartMessage();

        assertThat(processInstance).isWaitingAt("BasicCheckActivity");

        complete(task());

        assertThat(processInstance).isWaitingAt("personalLoanActivity");

        complete(task());

        assertThat(processInstance).isWaitingAt("dispatchActivity");

        assertEquals(externalTaskService().getTopicNames().get(0), "loanDispatch");

        externalTaskService().fetchAndLock(10, "externalWorkerId", true)
                .topic("loanDispatch", 5000L)
                .execute();
       // externalTaskService().extendLock(externalTask().getId(),"externalWorkerId",10000);

        externalTaskService().complete(externalTask().getId(),"externalWorkerId", new HashMap<>());

        assertThat(processInstance).isEnded();
    }
}
