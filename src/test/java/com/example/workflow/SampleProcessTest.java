package com.example.workflow;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
//import static org.camunda.bpm.extension.mockito.DelegateExpressions.autoMock;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.camunda.bpm.client.spring.impl.subscription.SubscriptionConfiguration;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Ensure the sample.bpmn Process is working correctly.
 */
@Deployment(resources = "LoanProcessByType.bpmn")
@SpringBootTest
@RunWith(SpringRunner.class)
public class SampleProcessTest extends AbstractProcessEngineRuleTest {

    @Mock
    private ProcessScenario insuranceApplication;
    @Test
    public void start_and_finish_process() throws InterruptedException {
        //autoMock("LoanProcessByType.bpmn");

        // given
        when(insuranceApplication.waitsAtServiceTask("dispatchActivity")).thenReturn(externalTaskDelegate -> {
                    externalTaskDelegate.complete(withVariables("check", true));
                }
        );

       // final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("Loan_Approval_Process_by_Type");

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
       // complete(task());
        //execute(job());
        Assert.assertEquals(externalTaskService().getTopicNames().get(0), "loanDispatch");
        taskService().complete("dispatchActivity");
        assertThat(processInstance).isWaitingAt("dispatchActivity");
        assertThat(processInstance).isEnded();
    }
}
