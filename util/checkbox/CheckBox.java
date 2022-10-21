package ru.sber.gsz.autotests.util.checkbox;

import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckBox {
    public final WebElement checkbox;

    public static CheckBox checkBox(WebElement checkbox) {
        return new CheckBox(checkbox);
    }

    public CheckBox(WebElement checkbox) {
        this.checkbox = checkbox;
    }

    public void isChecked() {
        assertThat(checkbox.isSelected());
    }

    public void click() {
        checkbox.click();
    }
}
