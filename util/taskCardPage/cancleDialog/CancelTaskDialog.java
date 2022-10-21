package ru.sber.gsz.autotests.util.taskCardPage.cancleDialog;

import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.util.diaog.ConfirmDialog;

public class CancelTaskDialog extends ConfirmDialog {

    public static CancelTaskDialog cancelTaskDialog(WebDriver driver) {
        return new CancelTaskDialog(driver);
    }

    public CancelTaskDialog(WebDriver driver) {
        super(driver, "confirm-dialog");
    }
}
