package ru.sber.gsz.autotests.task.changeExecutor;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.dto.executor.ExecutorStatus;
import ru.sber.gsz.autotests.dto.executor.ExecutorType;
import ru.sber.gsz.autotests.util.taskCardPage.TaskCardPage;
import ru.sber.gsz.autotests.util.taskCardPage.setExecutorDialog.SetExecutorDialog;
import ru.sber.gsz.autotests.util.taskSearchPage.TaskSearchPage;
import ru.sber.lm.Pair;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static ru.sber.gsz.autotests.util.taskCardPage.TaskCardPage.taskCardPage;
import static ru.sber.gsz.autotests.util.taskCardPage.cardTeam.TeamCard.teamCard;
import static ru.sber.gsz.autotests.util.taskCardPage.dialog.DialogExecutorIsNotAvailable.dialogExecutorIsNotAvailable;
import static ru.sber.gsz.autotests.util.taskCardPage.setExecutorDialog.SetExecutorDialog.setExecutorDialog;
import static ru.sber.gsz.autotests.util.taskSearchPage.TaskSearchPage.taskSearchPage;
import static ru.sber.gsz.autotests.util.taskSearchResultPage.TaskSearchResultPage.taskSearchResultPage;
import static ru.sber.lm.Pair.pair;

@RequiredArgsConstructor
public class ChangeExecutorUtils {
    private final WebDriver driver;
    private TaskSearchPage taskSearchPage;
    private TaskCardPage taskCardPage;
    private SetExecutorDialog setExecutorDialog;

    public static ChangeExecutorUtils changeExecutorUtils(WebDriver driver) {
        return new ChangeExecutorUtils(driver);
    }

    public ChangeExecutorUtils searchTaskByInstanceId(String instanceId) {
        taskSearchPage = taskSearchPage(driver);
        taskSearchPage.getSearchField().input(instanceId);
        taskSearchPage.getSearchButton().click();
        return this;
    }

    public ChangeExecutorUtils openTaskByInstanceId(String instanceId) {
        var taskSearchResultPage = taskSearchResultPage(driver);
        taskSearchResultPage.openTaskByInstanceId(instanceId);
        var taskCardPage = taskCardPage(driver);
        assertThat(instanceId).contains(taskCardPage.getTaskInstanceIdFromHeader());
        return this;
    }

    public ChangeExecutorUtils assertTaskPage(String instanceId) {
        taskCardPage = taskCardPage(driver);
        assertThat(instanceId).contains(taskCardPage.getTaskInstanceIdFromHeader());
        return this;
    }

    public ChangeExecutorUtils setExecutorCheckedCheckBox() {
        taskCardPage.getMainButtonContainer().getAppointExecutorButton().click();
        setExecutorDialog = setExecutorDialog(driver);
        setExecutorDialog.getChekBox().isChecked();
        setExecutorDialog.appointButton().click();
        return this;
    }

    public ChangeExecutorUtils assertButtonSetExecutorIsDisabled() {
        taskCardPage.getMainButtonContainer().getAppointExecutorButton().isDisabled();
        return this;
    }

    public ChangeExecutorUtils assertButtonCancelTaskIsDisabled() {
        taskCardPage.getMainButtonContainer().getCancelTaskButton().isDisabled();
        return this;
    }

    public ChangeExecutorUtils setExecutorUnchekedCheckBox(String tableNumber) {
        taskCardPage.getMainButtonContainer().getAppointExecutorButton().click();
        setExecutorDialog = setExecutorDialog(driver);
        setExecutorDialog.getChekBox().isChecked();
        setExecutorDialog.getChekBox().click();
        setExecutorDialog.getField().input(tableNumber);
        setExecutorDialog.appointButton().click();
        return this;
    }

    public ChangeExecutorUtils assertExecutorIsNotAvailable() {
        taskCardPage.getMainButtonContainer().getAppointExecutorButton().click();
        dialogExecutorIsNotAvailable(driver).assertAlert();
        return this;
    }

    public ChangeExecutorUtils assertError() {
        assertTrue(setExecutorDialog.getErrorAlert().getDescription().contains("В ЕПК указан некорректный т.н. ВКО"));
        return this;
    }

    public ChangeExecutorUtils checkTeam(List<Pair<ExecutorType, ExecutorStatus>> expected) {
        var expectedResult = expected.stream()
                .map(ex -> pair(ex.getFirst().getUiType(), ex.getSecond().getUiStatus()))
                .collect(Collectors.toList());
        var actualResult = teamCard(driver).getFields().stream()
                .map(ex -> pair(ex.getRole(), ex.getStatus()))
                .collect(Collectors.toList());
        assertThat(actualResult).isEqualTo(expectedResult);
        return this;
    }
}
