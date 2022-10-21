package ru.sber.gsz.autotests.util.cardGsz;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ru.sber.gsz.autotests.util.diaog.Dialog;

import static org.openqa.selenium.By.id;
import static ru.sber.gsz.autotests.util.SeleniumUtils.waitUntilElementIsVisible;

public class EditDialog extends Dialog {
    private final WebDriver driver;
    private final WebElement saveButton;
    private final WebElement cancelButton;


    public EditDialog(WebDriver driver, String dialogId) {
        super(driver, dialogId);
        saveButton = waitUntilElementIsVisible(driver, dialogWindow.findElement(id("save-btn")));
        cancelButton = waitUntilElementIsVisible(driver, dialogWindow.findElement(id("cancel-btn")));
        this.driver = driver;
    }

    public boolean isSaveButtonEnabled() {
        return saveButton.isEnabled();
    }

    public void save() {
        new Actions(driver).moveToElement(saveButton).click(saveButton).perform();
    }

    public void cancel() {
        cancelButton.click();
    }
}