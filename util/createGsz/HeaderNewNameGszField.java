package ru.sber.gsz.autotests.util.createGsz;

import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.field.AbstractField;
import ru.sber.gsz.autotests.util.input.InputHelper;
import ru.sber.gsz.autotests.util.input.TextInput;

import static org.openqa.selenium.By.id;

public class HeaderNewNameGszField
        extends AbstractField<String, TextInput, HeaderNewNameGszField> implements InputHelper {

    public HeaderNewNameGszField(WebElement field) {
        super(field, TextInput::new);
    }

    @Override
    public WebElement getInputHelper() {
        return getWebElement().findElement(id("mui-2-helper-text"));
    }

    @Override
    public String getInputText() {
        return getField().findElement(id("mui-1")).getAttribute("value");
    }
}
