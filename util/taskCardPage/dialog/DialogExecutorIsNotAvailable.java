package ru.sber.gsz.autotests.util.taskCardPage.dialog;

import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.util.diaog.Dialog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.xpath;
import static ru.sber.gsz.autotests.util.constants.AlertMessage.ALERT_EXECUTOR_NOT_AVAILABLE;

public class DialogExecutorIsNotAvailable extends Dialog {

    public static DialogExecutorIsNotAvailable dialogExecutorIsNotAvailable(WebDriver driver) {
        return new DialogExecutorIsNotAvailable(driver);
    }

    public DialogExecutorIsNotAvailable(WebDriver driver) {
        super(driver, "executor-not-available");
    }

    public void assertAlert() {
        assertThat(dialogWindow.findElement(xpath("//*[@id='executor-not-available']//p")).getText().contains(ALERT_EXECUTOR_NOT_AVAILABLE));
    }
}
