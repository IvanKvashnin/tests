package ru.sber.gsz.autotests.util.input;

import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

import static org.openqa.selenium.By.xpath;
import static ru.sber.gsz.autotests.util.SeleniumUtils.waitUntilElementIsVisibleByXpath;


@Getter
public abstract class SelectInput<T> implements Input<T, WebElement> {
    private final WebElement input;
    private final WebDriver driver;

    public SelectInput(WebElement input) {
        this.input = input;
        this.driver = ((WrapsDriver) this.input).getWrappedDriver();
    }

    @Override
    public WebElement processInput(T value) {
        if (value instanceof Enum) {
            driver.findElement(xpath("//li[@data-value='" + value + "']")).click();
        } else if (value instanceof String) {
            var element = waitUntilElementIsVisibleByXpath(driver, "//*[contains(text(), '" + value + "')]");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            element.click();
        } else
            throw new IllegalStateException("Значение не ссылается на тип Enum или String");
        return input;
    }
}


