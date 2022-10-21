package ru.sber.gsz.autotests.util.createGsz;

import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.util.diaog.Dialog;
import ru.sber.gsz.autotests.util.wrappers.AlertWrapper;

public class ErrorAlertDialog extends Dialog implements AlertWrapper {
    public ErrorAlertDialog(WebDriver driver) {
        super(driver, "alert-dialog");
    }

    @Override
    public String getAlertId() {
        return "result-alert";
    }
}
