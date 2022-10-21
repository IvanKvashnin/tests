package ru.sber.gsz.autotests.util.diaog;

import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.util.cardGsz.EditDialog;

public class ConfirmDialog extends EditDialog {
    public ConfirmDialog(WebDriver driver, String dialogId) {
        super(driver, dialogId);
    }

    public void approve() {
        save();
    }
}
