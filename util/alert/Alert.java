package ru.sber.gsz.autotests.util.alert;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import ru.sber.gsz.autotests.util.wrappers.WebElementWrapper;

import static org.openqa.selenium.By.xpath;
import static ru.sber.gsz.autotests.util.SeleniumUtils.waitUntilElementIsVisible;

@Getter
@RequiredArgsConstructor
public class Alert implements WebElementWrapper {
    private final WebElement alert;
    private final WebDriver driver;

    public Alert(WebElement alert) {
        this.alert = alert;
        this.driver = ((WrapsDriver) alert).getWrappedDriver();
    }

    public String getDescription() {
        return waitUntilElementIsVisible(driver, alert.findElement(xpath("./div[2]"))).getText();
    }

    @Override
    public WebElement getWebElement() {
        return getAlert();
    }
}
