package ru.sber.gsz.autotests.util.input;

import lombok.Getter;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.interactions.Actions;

import static ru.sber.gsz.autotests.util.SeleniumUtils.waitUntilElementIsClickable;

@Getter
public class TextInput implements Input<String, WebElement> {
    private final WebElement input;
    private final WebDriver driver;

    public static TextInput textInput(WebElement input) {
        return new TextInput(input);
    }

    public TextInput(WebElement input) {
        if (!"input".equals(input.getTagName())) throw new IllegalStateException("Отсутствует тег <input>");
        this.input = input;
        this.driver = ((WrapsDriver) this.input).getWrappedDriver();
    }

    @Override
    public WebElement processInput(String value) {
        new Actions(driver).moveToElement(input).perform();
        waitUntilElementIsClickable(driver, input);
        input.sendKeys(Keys.SHIFT, Keys.ARROW_UP);
        input.sendKeys(Keys.DELETE);
        input.sendKeys(value);
        return input;
    }
}
