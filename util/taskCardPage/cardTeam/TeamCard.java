package ru.sber.gsz.autotests.util.taskCardPage.cardTeam;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.wrappers.WebElementWrapper;

import java.util.List;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;
import static ru.sber.lm.CollectionUtils.map;

@Getter
public class TeamCard implements WebElementWrapper {
    private final WebElement card;
    private final List<TeamCardField> fields;

    public static TeamCard teamCard(WebDriver driver) {
        return new TeamCard(driver);
    }

    public TeamCard(WebDriver driver) {
        driver.navigate().refresh();
        this.card = driver.findElement(id("team-container"));
        this.fields = map(card.findElements(xpath("./child::*")), TeamCardField::createTeamField);
    }

    @Override
    public WebElement getWebElement() {
        return card;
    }
}
