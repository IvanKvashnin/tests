package ru.sber.gsz.autotests.util.taskCardPage.cardTeam;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

import static ru.sber.gsz.autotests.util.SeleniumUtils.waitUntilNestedElementsIsVisibleByXpath;

@Getter
public class TeamCardField {
    private final WebDriver driver;
    private final String name;
    private final String department;
    private final String role;
    private final String status;

    public static TeamCardField createTeamField(WebElement field) {
        return new TeamCardField(field);
    }

    public TeamCardField(WebElement field) {
        this.driver = ((WrapsDriver) field).getWrappedDriver();
        var elements = waitUntilNestedElementsIsVisibleByXpath(driver, field, ".//p");
        this.name = elements.get(0).getText();
        this.department = splitAndTrim(elements.get(1).getText());
        this.role = splitAndTrim(elements.get(2).getText());
        this.status = splitAndTrim(elements.get(3).getText());
    }

    private static String splitAndTrim(String value) {
        return value.split(":")[1].trim();
    }
}
