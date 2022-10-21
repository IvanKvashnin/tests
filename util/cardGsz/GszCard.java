package ru.sber.gsz.autotests.util.cardGsz;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.wrappers.WebElementWrapper;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;


public class GszCard implements WebElementWrapper {
    private final WebElement card;

    public GszCard(WebDriver driver) {
        card = driver.findElement(id("gsz-card"));
    }

    public GszCardField getGszCardField(String fieldId) {
        return new GszCardField(card.findElement(xpath("//*[@id=\"" + fieldId + "\"]/..")));
    }

    @Override
    public WebElement getWebElement() {
        return card;
    }
}
