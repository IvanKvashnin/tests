package ru.sber.gsz.autotests.util.cardGsz;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.dto.gsz.common.GszType;
import ru.sber.gsz.autotests.util.DropDownList;
import ru.sber.gsz.autotests.util.input.EnumSelectInput;
import ru.sber.gsz.autotests.util.input.SelectInput;
import ru.sber.gsz.autotests.util.wrappers.AlertWrapper;

import java.util.List;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.tagName;

@Getter
public class GszTypeChangeDialog extends EditDialog implements AlertWrapper, DropDownList<GszTypeChangeDialog> {
    private final SelectInput<GszType> selectInput;
    private final WebDriver driver;

    public GszTypeChangeDialog(WebDriver driver) {
        super(driver, "gsz-type-change-dialog");
        this.driver = driver;
        selectInput = new EnumSelectInput<>(dialogWindow.findElement(id("gsz-type-input")));
    }

    @Override
    public String getAlertId() {
        return "gsz-type-change-error";
    }

    @Override
    public GszTypeChangeDialog openDropDownList() {
        dialogWindow.findElement(id("gsz-type-input")).click();
        return this;
    }

    @Override
    public WebElement getDropDownList() {
        return driver.findElement(id("gsz-type-input-options"));
    }

    @Override
    public List<WebElement> getElementFromDropDownList() {
        return getDropDownList().findElements(tagName("li"));
    }
}
