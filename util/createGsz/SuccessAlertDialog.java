package ru.sber.gsz.autotests.util.createGsz;

import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.util.diaog.Dialog;
import ru.sber.gsz.autotests.util.wrappers.AlertWrapper;

public class SuccessAlertDialog extends Dialog implements AlertWrapper {
    public SuccessAlertDialog(WebDriver driver) {
        super(driver, "alert-dialog");
    }

    @Override
    public String getAlertId() {
        return "result-alert";
    }
}

