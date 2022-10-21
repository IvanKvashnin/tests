package ru.sber.gsz.autotests.util.input;

import org.openqa.selenium.WebElement;

public class EnumSelectInput<T extends Enum<T>> extends SelectInput<T> {
    public EnumSelectInput(WebElement input) {
        super(input);
    }
}
