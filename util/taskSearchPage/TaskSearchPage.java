package ru.sber.gsz.autotests.util.taskSearchPage;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.button.Button;
import ru.sber.gsz.autotests.util.checkbox.CheckBox;
import ru.sber.gsz.autotests.util.taskSearchPage.taskSearchField.TaskSearchField;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.tagName;
import static ru.sber.gsz.autotests.util.button.Button.buttonByName;
import static ru.sber.gsz.autotests.util.checkbox.CheckBox.checkBox;
import static ru.sber.gsz.autotests.util.taskSearchPage.taskSearchField.TaskSearchField.taskSearchField;

@Getter
public class TaskSearchPage {
    private final WebDriver driver;
    private final WebElement taskSearchPage;
    private final TaskSearchField searchField;
    private final Button searchButton;
    private final CheckBox searchByInstance;

    public static TaskSearchPage taskSearchPage(WebDriver driver) {
        return new TaskSearchPage(driver);
    }

    public TaskSearchPage(WebDriver driver) {
        this.driver = driver;
        taskSearchPage = driver.findElement(tagName("main"));
        searchField = taskSearchField(taskSearchPage.findElement(id("search-badge-field")));
        searchButton = buttonByName(driver, "Искать");
        searchByInstance = checkBox(taskSearchPage.findElement(id("checkbox-task-search-page")));
    }
}
