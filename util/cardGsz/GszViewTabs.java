package ru.sber.gsz.autotests.util.cardGsz;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;
import static ru.sber.gsz.autotests.util.constants.UUIDPattern.UUID_REGEX;

public class GszViewTabs {
    private final WebElement tabs;

    public GszViewTabs(WebDriver driver) {
        assertThat(driver.getCurrentUrl()).matches("http://[a-zA-Z0-9.:\\-]*/gsz-ui/gsz/view/" + UUID_REGEX +"");
        tabs = driver.findElement(xpath("//div[@role='tablist']"));
    }

    public void openGszCard() {
        openTab("gsz-card-tab-btn");
    }

    public void openGszStructure() {
        openTab("gsz-structure-tab-btn");
    }

    public void openGszHistory() {
        openTab("gsz-history-tab-btn");
    }

    private void openTab(String id) {
        tabs.findElement(id(id)).click();
    }
}
