package ru.sber.gsz.autotests.util.createGsz;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.DropDownList;
import ru.sber.gsz.autotests.util.field.AbstractField;
import ru.sber.gsz.autotests.util.input.SelectInput;
import ru.sber.gsz.autotests.util.input.StringSelectInput;

import java.util.List;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.tagName;

@Getter
public class LinkedOrgField extends AbstractField<String, SelectInput<String>, LinkedOrgField> implements DropDownList<LinkedOrgField> {
    public LinkedOrgField(WebElement field) {
        super(field, StringSelectInput::new);
    }

    @Override
    public String getInputText() {
        return getField().findElement(id("new-link-criterion-sa-input")).getText();
    }

    public LinkedOrgField openDropDownList() {
        getField().findElement(id("new-link-criterion-sa-input")).click();
        return this;
    }

    @Override
    public WebElement getDropDownList() {
        return getDriver().findElement(id("new-link-criterion-main-org-input-options"));
    }

    @Override
    public List<WebElement> getElementFromDropDownList() {
        return getDropDownList().findElements(tagName("li"));
    }
}
