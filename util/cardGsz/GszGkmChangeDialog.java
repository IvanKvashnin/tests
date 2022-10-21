package ru.sber.gsz.autotests.util.cardGsz;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.util.input.TextInput;
import ru.sber.gsz.autotests.util.wrappers.AlertWrapper;

import static org.openqa.selenium.By.id;

@Getter
public class GszGkmChangeDialog extends EditDialog implements AlertWrapper {
    private final TextInput input;

    public GszGkmChangeDialog(WebDriver driver) {
        super(driver, "gsz-gkm-change-dialog");
        input = new TextInput(dialogWindow.findElement(id("gsz-gkm-input")));
    }

    // TODO: 05.09.2022 Подумать, как сделать так чтобы AlertHolder отвчал за несколько алертов 
    public GkmInfoAlert getInfoAlert() {
        return new GkmInfoAlert(dialogWindow.findElement(id("gsz-gkm-change-info")));
    }

    @Override
    public String getAlertId() {
        return "gsz-gkm-change-error";
    }
}