package ru.sber.gsz.autotests.util.createGsz;

import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.DropDownList;
import ru.sber.gsz.autotests.util.field.AbstractField;
import ru.sber.gsz.autotests.util.input.SelectInput;
import ru.sber.gsz.autotests.util.input.StringSelectInput;

import java.util.List;

import static org.openqa.selenium.By.id;

public class LinkMainOrgField extends AbstractField<String, SelectInput<String>, LinkMainOrgField> implements DropDownList<LinkMainOrgField> {

    public LinkMainOrgField(WebElement field) {
        super(field, StringSelectInput::new);
    }

    @Override
    public String getInputText() {
        return getField().findElement(id("new-link-criterion-main-org-input")).getText();
    }

    public LinkMainOrgField openDropDownList() {
        getField().findElement(id("new-link-criterion-main-org-input")).click();
        return this;
    }

    @Override
    public WebElement getDropDownList() {
        return getDriver().findElement(id("new-link-criterion-sa-input-options"));
    }

    @Override
    public List<WebElement> getElementFromDropDownList() {
        return getDriver().findElements(id("new-link-criterion-main-org-input-label"));
    }
}
