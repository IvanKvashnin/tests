package ru.sber.gsz.autotests.util.taskCardPage;

import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.util.taskCardPage.mainButtonContainer.MainButtonContainer;

import static org.openqa.selenium.By.id;
import static ru.sber.gsz.autotests.util.SeleniumUtils.waitUntilTextIsMatch;
import static ru.sber.gsz.autotests.util.constants.UUIDPattern.UUID_REGEX;
import static ru.sber.gsz.autotests.util.taskCardPage.mainButtonContainer.MainButtonContainer.mainButtonContainer;


public class TaskCardPage {
    private final WebDriver driver;

    public static TaskCardPage taskCardPage(WebDriver driver) {
        return new TaskCardPage(driver);
    }

    public TaskCardPage(WebDriver driver) {
        this.driver = driver;
    }

    public MainButtonContainer getMainButtonContainer() {
        return mainButtonContainer(driver);
    }

    public String getTaskInstanceIdFromHeader() {
        waitUntilTextIsMatch(driver, "task-id", UUID_REGEX);
        var task = driver.findElement(id("task-id")).getText();
        return task.substring(task.indexOf(" ") + 1);
    }
}
