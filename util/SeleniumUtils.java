package ru.sber.gsz.autotests.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static java.util.regex.Pattern.compile;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SeleniumUtils {
    private static final Long TIMEOUT_IN_SEC = 5L;

    public static void waitUntilTextIs(WebDriver webDriver, String id, String text) {
        new WebDriverWait(webDriver, TIMEOUT_IN_SEC).until(textToBe(id(id), text));
    }

    public static boolean waitUntilTextIsMatch(WebDriver webDriver, String id, String regex) {
        return new WebDriverWait(webDriver, TIMEOUT_IN_SEC).until(textMatches(id(id), compile(regex)));
    }

    public static WebElement waitUntilElementIsVisible(WebDriver webDriver, WebElement webElement) {
        return new WebDriverWait(webDriver, TIMEOUT_IN_SEC).until(visibilityOf(webElement));
    }

    public static WebElement waitUntilElementIsVisibleByXpath(WebDriver webDriver, String xpath) {
        return new WebDriverWait(webDriver, TIMEOUT_IN_SEC).until(visibilityOfElementLocated(xpath(xpath)));
    }

    public static WebElement waitUntilElementIsVisibleById(WebDriver webDriver, String id) {
        return new WebDriverWait(webDriver, TIMEOUT_IN_SEC).until(visibilityOfElementLocated(id(id)));
    }

    public static List<WebElement> waitUntilNestedElementsIsVisibleByXpath(WebDriver webDriver, WebElement webElement, String xpath) {
        return new WebDriverWait(webDriver, TIMEOUT_IN_SEC).until(visibilityOfNestedElementsLocatedBy(webElement, xpath(xpath)));
    }

    public static WebElement waitUntilElementIsClickable(WebDriver webDriver, WebElement webElement) {
        return new WebDriverWait(webDriver, TIMEOUT_IN_SEC).until(ExpectedConditions.elementToBeClickable(webElement));

    }

    public static void waitForPageLoaded(WebDriver webDriver) {
        new WebDriverWait(webDriver, TIMEOUT_IN_SEC).until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .toString()
                        .equals("complete")
        );
    }

    public static void clearLocalStorage(WebDriver webDriver) {
        ((JavascriptExecutor) webDriver)
                .executeScript("window.localStorage.clear()");
    }
}
