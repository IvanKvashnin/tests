package ru.sber.gsz.autotests.task.changeExecutor;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.sber.gsz.autotests.SeleniumExtension;
import ru.sber.gsz.autotests.client.common.TmCommonClientConfig;
import ru.sber.gsz.autotests.client.criteria.CriterionClient;
import ru.sber.gsz.autotests.client.task.TaskClient;
import ru.sber.gsz.autotests.ui.TaskTab;

import static ru.sber.gsz.autotests.dto.executor.ExecutorType.VKO_EMPLOYEE;
import static ru.sber.gsz.autotests.task.TaskUtils.*;
import static ru.sber.gsz.autotests.task.changeExecutor.ChangeExecutorUtils.changeExecutorUtils;
import static ru.sber.gsz.autotests.util.SeleniumUtils.clearLocalStorage;
import static ru.sber.gsz.autotests.util.constants.TeamStatus.*;

@ExtendWith({SeleniumExtension.class})
@Import({TmCommonClientConfig.class, TaskClient.class})
@SpringBootTest
public class ChangeExecutorTest {
    @Autowired
    private TaskTab taskTab;

    @Autowired
    private TaskClient taskClient;

    @Autowired
    private CriterionClient criterionClient;

    @BeforeEach
    public void prepareToTest(WebDriver driver) {
        driver.get(taskTab.getTabUrl());
        clearLocalStorage(driver);
        driver.navigate().refresh();
    }

    @Test
    @Description("Re-assign VKO executor with out extId")
    public void reAssignVkoExecutorWithOutExtId(WebDriver driver) {
        var instanceId = createTaskFromUser(taskClient, VKO_EMPLOYEE);
        changeExecutorUtils(driver)
                .searchTaskByInstanceId(instanceId)
                .openTaskByInstanceId(instanceId)
                .assertTaskPage(instanceId)
                .checkTeam(VKO_EXECUTOR_TEAM_STATUS)
                .setExecutorCheckedCheckBox()
                .checkTeam(REASSIGN_VKO_TEAM_STATUS);
    }

    @Test
    @Description("Re-assign VKO executor letter extId")
    public void reAssignVkoExecutorLetterExtId(WebDriver driver) {
        var instanceId = createTaskFromUser(taskClient, VKO_EMPLOYEE);
        changeExecutorUtils(driver)
                .searchTaskByInstanceId(instanceId)
                .openTaskByInstanceId(instanceId)
                .assertTaskPage(instanceId)
                .checkTeam(VKO_EXECUTOR_TEAM_STATUS)
                .setExecutorUnchekedCheckBox("TestLetterExtId")
                .assertError();
    }

    @Test
    @Description("Re-assign VKO executor with uncheked chekbox - Without table number")
    public void reAssignVkoExecutorWithoutTableNumber(WebDriver driver) {
        var instanceId = createTaskFromUser(taskClient, VKO_EMPLOYEE);
        changeExecutorUtils(driver)
                .searchTaskByInstanceId(instanceId)
                .openTaskByInstanceId(instanceId)
                .assertTaskPage(instanceId)
                .checkTeam(VKO_EXECUTOR_TEAM_STATUS)
                .setExecutorUnchekedCheckBox("")
                .checkTeam(REASSIGN_VKO_TEAM_STATUS);
    }

    @Test
    @Description("Re-assign PM executor [first lap]")
    public void reAssignPmExecutorFirstLap(WebDriver driver) {
        var instanceId = goToVkoSubmittedFirsLap(taskClient, criterionClient);
        changeExecutorUtils(driver)
                .searchTaskByInstanceId(instanceId)
                .openTaskByInstanceId(instanceId)
                .assertTaskPage(instanceId)
                .checkTeam(PM_EXECUTOR_TEAM_STATUS)
                .setExecutorCheckedCheckBox()
                .checkTeam(REASSIGN_PM_TEAM_STATUS);
    }

    @Test
    @Description("Re-assign PM executor when PM submitted [first lap]")
    public void reAssignPmExecutorPmSubmittedFirstLap(WebDriver driver) {
        var instanceId = goToPmSubmittedFirstLap(taskClient, criterionClient);
        changeExecutorUtils(driver)
                .searchTaskByInstanceId(instanceId)
                .openTaskByInstanceId(instanceId)
                .assertTaskPage(instanceId)
                .checkTeam(PM_SUBMITTED_TEAM_STATUS)
                .assertExecutorIsNotAvailable();
    }

    @Test
    @Description("Re-assign VKO executor[second lap]")
    public void reAssignVkoExecutorFirstLap(WebDriver driver) {
        var instanceId = goToArSubmitted(taskClient, criterionClient);
        changeExecutorUtils(driver)
                .searchTaskByInstanceId(instanceId)
                .openTaskByInstanceId(instanceId)
                .assertTaskPage(instanceId)
                .checkTeam(VKO_EXECUTOR_TEAM_STATUS_SECOND_LAP)
                .setExecutorCheckedCheckBox()
                .checkTeam(REASSIGN_VKO_TEAM_STATUS_SECOND_LAP);
    }

    @Test
    @Description("Re-assign PM executor [second lap]")
    public void reAssignPmExecutorSecondLap(WebDriver driver) {
        var instanceId = goToVkoSubmittedSecondLap(taskClient, criterionClient);
        changeExecutorUtils(driver)
                .searchTaskByInstanceId(instanceId)
                .openTaskByInstanceId(instanceId)
                .assertTaskPage(instanceId)
                .checkTeam(PM_EXECUTOR_TEAM_STATUS_SECOND_LAP)
                .setExecutorCheckedCheckBox()
                .checkTeam(REASSIGN_PM_TEAM_STATUS_SECOND_LAP);
    }

    @Test
    @Description("Re-assign PM executor when PM submitted [second lap]")
    public void reAssignPmExecutorPmSubmittedSecondLap(WebDriver driver) {
        var instanceId = goToPmSubmittedSecondLap(taskClient, criterionClient);
        changeExecutorUtils(driver)
                .searchTaskByInstanceId(instanceId)
                .openTaskByInstanceId(instanceId)
                .assertTaskPage(instanceId)
                .checkTeam(PM_SUBMITTED_TEAM_STATUS_SECOND_LAP)
                .assertExecutorIsNotAvailable();
    }

    @Test
    @Description("Check re-assign employee when final ar submitted [second lap]")
    public void CheckReAssignEmployeeWhenFinalArSubmittedSecondLap(WebDriver driver) {
        var instanceId = goToFinalArSubmitted(taskClient, criterionClient);
        changeExecutorUtils(driver)
                .searchTaskByInstanceId(instanceId)
                .openTaskByInstanceId(instanceId)
                .assertTaskPage(instanceId)
                .checkTeam(FINAL_AR_SUBMITTED_TEAM_STATUS)
                .assertButtonSetExecutorIsDisabled()
                .assertButtonCancelTaskIsDisabled();
    }
}
