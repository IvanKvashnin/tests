package ru.sber.gsz.autotests.util.taskSearchResultPage;

import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.util.taskSearchResultPage.taskFields.TaskFields;

import static org.openqa.selenium.By.xpath;
import static ru.sber.gsz.autotests.util.href.Href.hrefPathContains;
import static ru.sber.gsz.autotests.util.taskSearchResultPage.taskFields.TaskFields.createTaskFields;

public class TaskSearchResultPage {
    private final WebDriver driver;

    public static TaskSearchResultPage taskSearchResultPage(WebDriver driver) {
        return new TaskSearchResultPage(driver);
    }

    public TaskSearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openTaskByInstanceId(String instanceId) {
        hrefPathContains(driver, instanceId).click();
    }

    public TaskFields getTaskFieldsByInstanceId(String instanceId) {
        return createTaskFields(driver.findElement(xpath("//a[contains(@href,'" + instanceId + "')]/ancestor::tr")));
    }
}
