package ru.sber.gsz.autotests.util.createGsz;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ru.sber.gsz.autotests.util.field.AbstractField;
import ru.sber.gsz.autotests.util.field.ProcessableField;
import ru.sber.gsz.autotests.util.input.InputHelper;
import ru.sber.gsz.autotests.util.input.TextInput;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.tagName;

public class MainOrganizationField
        extends AbstractField<String, TextInput, MainOrganizationField>
        implements ProcessableField<String, MainOrganizationField>, InputHelper {

    public MainOrganizationField(WebElement field) {
        super(field, TextInput::new);
    }

    @Override
    public WebElement getInputHelper() {
        return getWebElement().findElement(id("mui-2-helper-text"));
    }

    @Override
    public void process() {
        new Actions(getDriver()).moveToElement(getField().findElement(tagName("button"))).click().perform();
    }

    @Override
    public String getInputText() {
        return getField().findElement(id("mui-2")).getAttribute("value");
    }
}
