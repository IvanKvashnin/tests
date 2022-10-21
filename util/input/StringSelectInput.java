package ru.sber.gsz.autotests.util.input;

import org.openqa.selenium.WebElement;

public class StringSelectInput<T extends String> extends SelectInput<T> {
    public StringSelectInput(WebElement input) {
        super(input);
    }
}
