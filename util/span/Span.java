package ru.sber.gsz.autotests.util.span;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.By.id;

@Getter
public class Span {
    private final WebElement webElement;

    public static Span spanById(WebDriver driver, String id) {
        return new Span(driver.findElement(id(id)));
    }

    public Span(WebElement webElement) {
        this.webElement = webElement;
    }
}
