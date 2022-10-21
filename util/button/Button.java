package ru.sber.gsz.autotests.util.button;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

@Getter
public class Button {
    private final WebElement element;

    public static Button buttonById(WebDriver driver, String id) {
        return new Button(driver.findElement(id(id)));
    }

    public static Button buttonByName(WebDriver driver, String name) {
        return new Button(driver.findElement(xpath("//button[normalize-space()=\"" + name + "\"]")));
    }

    public Button(WebElement element) {
        this.element = element;
    }

    public void click() {
        element.click();
    }

    public void isDisabled() {
       assertFalse(element.isEnabled());
    }
}
