package ru.sber.gsz.autotests.util.createGsz;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ru.sber.gsz.autotests.util.field.AbstractField;
import ru.sber.gsz.autotests.util.field.ProcessableField;
import ru.sber.gsz.autotests.util.input.InputHelper;
import ru.sber.gsz.autotests.util.input.TextInput;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.tagName;

@Getter
public class OtherOrganizationField extends AbstractField<String, TextInput, OtherOrganizationField>
        implements ProcessableField<String, OtherOrganizationField>, InputHelper {

    public OtherOrganizationField(WebElement field) {
        super(field, TextInput::new);
    }

    @Override
    public WebElement getInputHelper() {
        return getWebElement().findElement(id("sa-input-helper-text"));
    }

    @Override
    public void process() {
        new Actions(getDriver()).moveToElement(getField().findElement(tagName("button"))).click().perform();
    }

    @Override
    public String getInputText() {
        return getDriver().findElement(id("new-link-criterion-sa-input")).getText();
    }
}

