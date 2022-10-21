package ru.sber.gsz.autotests.util.field;

import org.openqa.selenium.Keys;

import static org.openqa.selenium.By.tagName;

public interface ProcessableField<V, F extends ProcessableField<V, F>> extends Field<V, F> {
    default void process() {
        getWebElement().findElement(tagName("input")).sendKeys(Keys.ENTER);
    }
}
