package ru.sber.gsz.autotests.util.cardGsz;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.util.input.TextInput;
import ru.sber.gsz.autotests.util.wrappers.AlertWrapper;

import static org.openqa.selenium.By.id;

@Getter
public class GszNameChangeDialog extends EditDialog implements AlertWrapper {
    public final TextInput input;

    public GszNameChangeDialog(WebDriver driver) {
        super(driver, "gsz-name-change-dialog");
        input = new TextInput(dialogWindow.findElement(id("gsz-name-input")));
    }

    @Override
    public String getAlertId() {
        return "gsz-name-change-error";
    }
}