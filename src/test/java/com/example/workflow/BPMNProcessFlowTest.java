package com.example.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BPMNProcessFlowTest extends AbstractProcessEngineRuleTest {

  @Autowired
  public RuntimeService runtimeService;

  @Test
  public void shouldStartLoanProcessWorkflow() {
    // given
    String processDefinitionKey = "Loan_Approval_Process_by_Type";

    // when
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);

    // then
    assertThat(processInstance).isStarted()
        .task()
        .hasDefinitionKey("BasicCheckActivity")
        .isNotAssigned();
  }

}
