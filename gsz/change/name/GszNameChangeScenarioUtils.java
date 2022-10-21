package ru.sber.gsz.autotests.gsz.change.name;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.util.diaog.ConfirmDialog;
import ru.sber.gsz.autotests.util.cardGsz.GszCard;
import ru.sber.gsz.autotests.util.cardGsz.GszNameChangeDialog;
import ru.sber.gsz.autotests.util.cardGsz.GszTitle;
import ru.sber.gsz.autotests.util.cardGsz.GszViewTabs;

import java.util.List;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class GszNameChangeScenarioUtils {
    private final WebDriver driver;
    private GszNameChangeDialog dialog;

    public GszNameChangeScenarioUtils openNameDialog() {
        new GszViewTabs(driver).openGszCard();
        new GszCard(driver).getGszCardField("name").edit();
        return this;
    }

    public GszNameChangeScenarioUtils checkSaveButtonDisable() {
        dialog = new GszNameChangeDialog(driver);
        assertThat(dialog.isSaveButtonEnabled()).isFalse();
        return this;
    }

    public GszNameChangeScenarioUtils changeGszName(String gszName) {
        if (isNull(dialog)) throw new IllegalStateException("Field GszNameChangeDialog dialog is null");
        dialog.getInput().processInput(gszName);
        return this;
    }

    public GszNameChangeScenarioUtils save() {
        if (isNull(dialog)) throw new IllegalStateException("Field GszGkmChangeDialog dialog is null");
        dialog.save();
        new ConfirmDialog(driver, "confirm-dialog").approve();
        return this;
    }

    public GszNameChangeScenarioUtils cancel() {
        if (isNull(dialog)) throw new IllegalStateException("Field GszGkmChangeDialog dialog is null");
        dialog.cancel();
        return this;
    }

    public GszNameChangeScenarioUtils assertGszName(String gszName) {
        driver.navigate().refresh();
        assertThat(new GszTitle(driver).getGszTitleName()).isEqualTo(gszName);
        new GszViewTabs(driver).openGszCard();
        assertThat(new GszCard(driver).getGszCardField("name").getValue()).isEqualTo(gszName);
        return this;
    }

    public GszNameChangeScenarioUtils checkGszNameError(List<String> list) {
        assertThat(list).contains(dialog.getErrorAlert().getDescription());
        return this;
    }
}