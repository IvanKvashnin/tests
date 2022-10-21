package ru.sber.gsz.autotests.util.href;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

import static org.openqa.selenium.By.xpath;
import static ru.sber.gsz.autotests.util.SeleniumUtils.waitUntilElementIsVisible;

public class Href {
    private final WebDriver driver;
    private final WebElement element;

    public static Href hrefPathContains(WebDriver driver, String contains) {
        return new Href(driver.findElement(xpath("//a[contains(@href,'" + contains + "')]")));
    }

    public Href(WebElement element) {
        this.element = element;
        driver = ((WrapsDriver) element).getWrappedDriver();
    }

    public void click() {
        waitUntilElementIsVisible(driver, element).click();
    }
}
