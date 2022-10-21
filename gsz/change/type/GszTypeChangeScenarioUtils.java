package ru.sber.gsz.autotests.gsz.change.type;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import ru.sber.gsz.autotests.dto.gsz.common.GszType;
import ru.sber.gsz.autotests.util.diaog.ConfirmDialog;
import ru.sber.gsz.autotests.util.cardGsz.GszCard;
import ru.sber.gsz.autotests.util.cardGsz.GszTypeChangeDialog;
import ru.sber.gsz.autotests.util.cardGsz.GszViewTabs;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class GszTypeChangeScenarioUtils {
    private final WebDriver driver;
    private GszTypeChangeDialog dialog;

    public GszTypeChangeScenarioUtils openTypeDialog() {
        new GszViewTabs(driver).openGszCard();
        new GszCard(driver).getGszCardField("gszType").edit();
        return this;
    }

    public GszTypeChangeScenarioUtils checkSaveButtonDisable() {
        dialog = new GszTypeChangeDialog(driver);
        assertThat(dialog.isSaveButtonEnabled()).isFalse();
        return this;
    }

    public GszTypeChangeScenarioUtils changeGszType(GszType gszType) {
        if (isNull(dialog)) throw new IllegalStateException("Field GszTypeChangeDialog dialog is null");
        new GszTypeChangeDialog(driver).openDropDownList().getSelectInput().processInput(gszType);
        return this;
    }

    public GszTypeChangeScenarioUtils save() {
        if (isNull(dialog)) throw new IllegalStateException("Field GszGkmChangeDialog dialog is null");
        new GszTypeChangeDialog(driver).save();
        new ConfirmDialog(driver, "confirm-dialog").approve();
        return this;
    }

    public GszTypeChangeScenarioUtils cancel() {
        if (isNull(dialog)) throw new IllegalStateException("Field GszGkmChangeDialog dialog is null");
        dialog.cancel();
        return this;
    }

    public GszTypeChangeScenarioUtils assertGszType(String gszType) {
        driver.navigate().refresh();
        new GszViewTabs(driver).openGszCard();
        assertThat(new GszCard(driver).getGszCardField("gszType").getValue()).isEqualTo(gszType);
        return this;
    }

    public GszTypeChangeScenarioUtils checkGszNameError(String list) {
        assertThat(list).contains(dialog.getErrorAlert().getDescription());
        return this;
    }
}
