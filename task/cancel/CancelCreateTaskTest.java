package ru.sber.gsz.autotests.task.cancel;

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
import ru.sber.gsz.autotests.client.task.TaskClient;
import ru.sber.gsz.autotests.ui.TaskTab;

import java.util.Set;

import static ru.sber.gsz.autotests.dto.executor.ExecutorStatus.CANCELLED;
import static ru.sber.gsz.autotests.dto.executor.ExecutorStatus.MARKED;
import static ru.sber.gsz.autotests.dto.executor.ExecutorType.*;
import static ru.sber.gsz.autotests.task.TaskUtils.createTaskFromUser;
import static ru.sber.gsz.autotests.task.cancel.CancelTaskUtils.cancelTaskUtils;
import static ru.sber.gsz.autotests.util.SeleniumUtils.clearLocalStorage;
import static ru.sber.lm.Pair.pair;

@ExtendWith({SeleniumExtension.class})
@Import({TmCommonClientConfig.class, TaskClient.class})
@SpringBootTest
public class CancelCreateTaskTest {
    @Autowired
    private TaskTab taskTab;

    @Autowired
    private TaskClient taskClient;


    @BeforeEach
    public void prepareToTest(WebDriver driver) {
        driver.get(taskTab.getTabUrl());
        clearLocalStorage(driver);
        driver.navigate().refresh();
    }

    @Test
    @Description("Cancel create task in VKO step")
    public void cancelCreateTask(WebDriver driver) {
        var instanceId = createTaskFromUser(taskClient, VKO_EMPLOYEE);
        cancelTaskUtils(driver)
                .searchTaskByInstanceId(instanceId)
                .openTaskByInstanceId(instanceId)
                .assertTaskPage(instanceId)
                .cancelTaskAndWait()
                .checkTaskStatus()
                .checkEndDate()
                .checkTeam(Set.of(
                        pair(VKO_EMPLOYEE.getUiType(), CANCELLED.getUiStatus()),
                        pair(PM_EMPLOYEE.getUiType(), MARKED.getUiStatus()),
                        pair(CURATOR_EMPLOYEE.getUiType(), MARKED.getUiStatus())
                ));
    }
}
