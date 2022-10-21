package ru.sber.gsz.autotests.util.cardGsz;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class GszTitle {
    public final WebElement gszTitle;

    public GszTitle(WebDriver driver) {
        gszTitle = new WebDriverWait(driver, 5)
                .until(presenceOfElementLocated(id("gsz-name")));
    }

    public String getGszTitleName() {
        return gszTitle.getText();
    }
}
