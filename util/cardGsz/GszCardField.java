package ru.sber.gsz.autotests.util.cardGsz;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.wrappers.WebElementWrapper;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

@RequiredArgsConstructor
public class GszCardField implements WebElementWrapper {
    private final WebElement field;

    public void edit() {
        field.findElement(id("gsz-field-edit-btn")).click();
    }

    public String getValue() {
        return field.findElement(xpath("./input")).getAttribute("value");
    }

    @Override
    public WebElement getWebElement() {
        return field;
    }
}
