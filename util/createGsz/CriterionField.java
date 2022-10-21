package ru.sber.gsz.autotests.util.createGsz;

import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.DropDownList;
import ru.sber.gsz.autotests.util.field.AbstractField;
import ru.sber.gsz.autotests.util.input.SelectInput;
import ru.sber.gsz.autotests.util.input.StringSelectInput;

import java.util.List;

import static org.openqa.selenium.By.id;

public class CriterionField extends AbstractField<String, SelectInput<String>, CriterionField> implements DropDownList<CriterionField> {
    public CriterionField(WebElement field) {
        super(field, StringSelectInput::new);
    }

    @Override
    public String getInputText() {
        return getField().findElement(id("new-link-criterion-crit-input")).getText();
    }

    @Override
    public CriterionField openDropDownList() {
        getField().findElement(id("new-link-criterion-crit-input")).click();
        return this;
    }

    @Override
    public WebElement getDropDownList() {
        getDriver().findElement(id("new-link-criterion-crit-input-options"));
        return null;
    }

    @Override
    public List<WebElement> getElementFromDropDownList() {
        getDropDownList().findElement(id("new-link-criterion-crit-input-options"));
        return null;
    }
}