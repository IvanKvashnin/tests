package ru.sber.gsz.autotests.util.createGsz;

import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.dto.gsz.common.GszType;
import ru.sber.gsz.autotests.util.DropDownList;
import ru.sber.gsz.autotests.util.field.AbstractField;
import ru.sber.gsz.autotests.util.input.EnumSelectInput;
import ru.sber.gsz.autotests.util.input.SelectInput;

import java.util.List;

import static org.openqa.selenium.By.id;

public class HeaderNewTypeGszField
        extends AbstractField<GszType, SelectInput<GszType>, HeaderNewTypeGszField> implements DropDownList<HeaderNewTypeGszField> {

    public HeaderNewTypeGszField(WebElement field) {
        super(field, EnumSelectInput::new);
    }

    @Override
    public String getInputText() {
        return getField().findElement(id("create-gsz-type-input")).getText();
    }

    @Override
    public HeaderNewTypeGszField openDropDownList() {
        getField().findElement(id("create-gsz-type-input")).click();
        return this;
    }

    @Override
    public WebElement getDropDownList() {
        return getDriver().findElement(id("create-gsz-type-input-options"));
    }

    @Override
    public List<WebElement> getElementFromDropDownList() {
        return getDropDownList().findElements(id("create-gsz-type-input-options"));
    }
}
