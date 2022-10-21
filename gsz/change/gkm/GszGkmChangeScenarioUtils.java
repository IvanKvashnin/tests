package ru.sber.gsz.autotests.gsz.change.gkm;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.util.diaog.ConfirmDialog;
import ru.sber.gsz.autotests.util.cardGsz.GszCard;
import ru.sber.gsz.autotests.util.cardGsz.GszGkmChangeDialog;
import ru.sber.gsz.autotests.util.cardGsz.GszViewTabs;

import java.util.List;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.Keys.ENTER;

@RequiredArgsConstructor
public class GszGkmChangeScenarioUtils {
    private final WebDriver driver;
    private GszGkmChangeDialog dialog;
    protected static String valueFromGkmField;

    public GszGkmChangeScenarioUtils openGkmDialog() {
        new GszViewTabs(driver).openGszCard();
        var gszCard = new GszCard(driver);
        valueFromGkmField = gszCard.getGszCardField("gkmExtId").getValue();
        new GszCard(driver).getGszCardField("gkmExtId").edit();
        return this;
    }

    public GszGkmChangeScenarioUtils checkSaveButtonDisable() {
        dialog = new GszGkmChangeDialog(driver);
        assertThat(dialog.isSaveButtonEnabled()).isFalse();
        return this;
    }

    public GszGkmChangeScenarioUtils typeDialogField(String employeeExtId) {
        dialog.getInput().processInput(employeeExtId).sendKeys(ENTER);
        return this;
    }

    public GszGkmChangeScenarioUtils assertEmployeeFullNameAlert(String employeeFullName) {
        if (isNull(dialog)) throw new IllegalStateException("Field GszGkmChangeDialog dialog is null");
        assertThat(dialog.getInfoAlert().getFullName()).isEqualTo(employeeFullName);
        return this;
    }

    public GszGkmChangeScenarioUtils assertErrorAlert() {
        assertThat(dialog.getErrorAlert().getDescription()).isEqualTo("");
        return this;
    }

    public GszGkmChangeScenarioUtils save() {
        if (isNull(dialog)) throw new IllegalStateException("Field GszGkmChangeDialog dialog is null");
        dialog.save();
        new ConfirmDialog(driver, "confirm-dialog").approve();
        return this;
    }

    public GszGkmChangeScenarioUtils cancel() {
        if (isNull(dialog)) throw new IllegalStateException("Field GszGkmChangeDialog dialog is null");
        dialog.cancel();
        return this;
    }

    public GszGkmChangeScenarioUtils assertNameFieldGkm(String employeeFullName) {
        driver.navigate().refresh();
        new GszViewTabs(driver).openGszCard();
        var gkmFullName = new GszCard(driver).getGszCardField("gkmExtId").getValue();
        assertThat(employeeFullName).isEqualTo(gkmFullName);
        return this;
    }

    public GszGkmChangeScenarioUtils checkGkmNameError(List<String> list) {
        assertThat(list).contains(dialog.getErrorAlert().getDescription());
        return this;
    }
}
