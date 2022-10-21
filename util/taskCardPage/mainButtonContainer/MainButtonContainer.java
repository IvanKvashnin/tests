package ru.sber.gsz.autotests.util.taskCardPage.mainButtonContainer;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.button.Button;

import static org.openqa.selenium.By.id;
import static ru.sber.gsz.autotests.util.button.Button.buttonByName;

@Getter
public class MainButtonContainer {
    private final WebElement mainButtonContainer;
    private final Button taskHistoryButton;
    private final Button appointExecutorButton;
    private final Button cancelTaskButton;

    public static MainButtonContainer mainButtonContainer(WebDriver driver) {
        return new MainButtonContainer(driver);
    }

    public MainButtonContainer(WebDriver driver) {
        mainButtonContainer = driver.findElement(id("main-button-container"));
        taskHistoryButton = buttonByName(driver, "История задачи");
        appointExecutorButton = buttonByName(driver, "Назначить исполнителя");
        cancelTaskButton = buttonByName(driver, "Отменить задачу");
    }
}
