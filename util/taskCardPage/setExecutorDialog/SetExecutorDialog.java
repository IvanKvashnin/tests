package ru.sber.gsz.autotests.util.taskCardPage.setExecutorDialog;

import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.util.alert.ErrorAlert;
import ru.sber.gsz.autotests.util.button.Button;
import ru.sber.gsz.autotests.util.checkbox.CheckBox;

import static org.openqa.selenium.By.id;
import static ru.sber.gsz.autotests.util.checkbox.CheckBox.checkBox;

public class SetExecutorDialog {
    private final WebDriver driver;

    public static SetExecutorDialog setExecutorDialog(WebDriver driver) {
        return new SetExecutorDialog(driver);
    }

    public SetExecutorDialog(WebDriver driver) {
        this.driver = driver;
    }


    public SetExecutorDialogField getField() {
        return new SetExecutorDialogField(driver.findElement(id("text-field-set-executor-dialog")));
    }

    public Button appointButton() {
        return Button.buttonByName(driver, "Назначить");
    }

    public CheckBox getChekBox() {
        return checkBox(driver.findElement(id("checkbox-set-executor")));
    }

    public ErrorAlert getErrorAlert() {
        return new ErrorAlert(driver.findElement(id("error-alert-set-executor-dialog")));
    }
}
