package ru.sber.gsz.autotests.task.cancel;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.util.taskCardPage.TaskCardPage;
import ru.sber.gsz.autotests.util.taskSearchPage.TaskSearchPage;
import ru.sber.gsz.autotests.util.taskSearchResultPage.TaskSearchResultPage;
import ru.sber.lm.Pair;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ru.sber.gsz.autotests.util.SeleniumUtils.waitForPageLoaded;
import static ru.sber.gsz.autotests.util.SeleniumUtils.waitUntilTextIs;
import static ru.sber.gsz.autotests.util.span.Span.spanById;
import static ru.sber.gsz.autotests.util.taskCardPage.TaskCardPage.taskCardPage;
import static ru.sber.gsz.autotests.util.taskCardPage.cancleDialog.CancelTaskDialog.cancelTaskDialog;
import static ru.sber.gsz.autotests.util.taskCardPage.cardTeam.TeamCard.teamCard;
import static ru.sber.gsz.autotests.util.taskSearchPage.TaskSearchPage.taskSearchPage;
import static ru.sber.gsz.autotests.util.taskSearchResultPage.TaskSearchResultPage.taskSearchResultPage;
import static ru.sber.lm.CollectionUtils.map;
import static ru.sber.lm.Pair.pair;

@RequiredArgsConstructor
public class CancelTaskUtils {
    private final WebDriver driver;
    private TaskSearchPage taskSearchPage;
    private TaskCardPage taskCardPage;
    private TaskSearchResultPage taskSearchResultPage;

    public static CancelTaskUtils cancelTaskUtils(WebDriver driver) {
        return new CancelTaskUtils(driver);
    }

    public CancelTaskUtils searchTaskByInstanceId(String instanceId) {
        taskSearchPage = taskSearchPage(driver);
        taskSearchPage.getSearchField().input(instanceId);
        taskSearchPage.getSearchButton().click();
        return this;
    }

    public CancelTaskUtils openTaskByInstanceId(String instanceId) {
        taskSearchResultPage = taskSearchResultPage(driver);
        taskSearchResultPage.openTaskByInstanceId(instanceId);
        taskCardPage = taskCardPage(driver);
        assertThat(instanceId).contains(taskCardPage.getTaskInstanceIdFromHeader());
        return this;
    }

    public CancelTaskUtils assertTaskPage(String instanceId) {
        assertThat(instanceId).contains(taskCardPage.getTaskInstanceIdFromHeader());
        return this;
    }

    public CancelTaskUtils cancelTaskAndWait() {
        taskCardPage.getMainButtonContainer().getCancelTaskButton().click();
        cancelTaskDialog(driver).save();
        waitUntilTextIs(driver, "status", "Отменена");
        waitForPageLoaded(driver);
        return this;
    }

    public CancelTaskUtils checkTaskStatus() {
        assertEquals("Отменена", spanById(driver, "status").getWebElement().getText());
        return this;
    }

    public CancelTaskUtils checkEndDate() {
        assertFalse(spanById(driver, "task-processing-date").getWebElement().getText().contains("- -"));
        return this;
    }

    public CancelTaskUtils checkTeam(Set<Pair<String, String>> expected) {
        assertTrue(map(teamCard(driver).getFields(), ex -> pair(ex.getRole(), ex.getStatus())).containsAll(expected));
        return this;
    }
}
