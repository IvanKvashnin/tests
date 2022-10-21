package ru.sber.gsz.autotests.util.input;

import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.wrappers.WebElementWrapper;

public interface InputHelper extends WebElementWrapper {
    WebElement getInputHelper();

    default String getInputHelperText() {
        return getInputHelper().getText();
    }

    default String getInputHelperColor() {
        return getInputHelper().getCssValue("color");
    }
}
