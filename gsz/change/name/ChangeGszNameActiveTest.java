package ru.sber.gsz.autotests.gsz.change.name;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.sber.gsz.autotests.SeleniumExtension;
import ru.sber.gsz.autotests.client.common.TmCommonClientConfig;
import ru.sber.gsz.autotests.client.gsz.GszClient;
import ru.sber.gsz.autotests.ui.GszTab;

import static com.google.common.collect.ImmutableList.of;
import static java.lang.String.format;
import static ru.sber.gsz.autotests.dto.gsz.common.GszStatus.ACTIVE;
import static ru.sber.gsz.autotests.dto.gsz.common.GszStatus.CLOSE;
import static ru.sber.gsz.autotests.gsz.change.GszChangeUtils.createGsz;
import static ru.sber.gsz.autotests.util.constants.AlertMessage.*;
import static ru.sber.gsz.autotests.util.constants.CssAttributes.*;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.randomNumber;


@ExtendWith({SeleniumExtension.class})
@Import({TmCommonClientConfig.class, GszClient.class})
@SpringBootTest
public class ChangeGszNameActiveTest {
    @Autowired
    private GszClient gszClient;
    @Autowired
    private GszTab gszTab;

    String originGszName;

    @BeforeEach
    public void prepareConditionToTest(WebDriver driver) {
        originGszName = format("test-gsz-name-change-%d", randomNumber(6));
        var gsz = createGsz(originGszName, ACTIVE);
        gszClient.saveGsz(gsz);
        driver.get(gszTab.getViewUrl(gsz.getRoot().getId()));
    }

    @Test
    @Description("Gsz name change (SUCCESS))")
    public void testUiGszNameChangeSuccess(WebDriver driver) {
        var gszName = format("TEST-%d", randomNumber(3));
        new GszNameChangeScenarioUtils(driver)
                .openNameDialog()
                .checkSaveButtonDisable()
                .changeGszName(gszName)
                .save()
                .assertGszName(gszName);
    }

    @Test
    @Description("Смена названия ГСЗ (SHORT_NAME)")
    public void testUiGszNameChangeShortName(WebDriver driver) {
        var shortGszName = "a" + randomNumber(1);
        new GszNameChangeScenarioUtils(driver)
                .openNameDialog()
                .checkSaveButtonDisable()
                .changeGszName(shortGszName)
                .save()
                .checkGszNameError(of(ALERT_GSZ_NAME_MUST_BE_MORE_THAN_3_CHAR))
                .assertGszName(originGszName);
    }

    @Test
    @Description("[UI] Смена названия ГСЗ (LEADING_NON_LETTER)")
    public void testUiGszNameChangeLeadingNonLetter(WebDriver driver) {
        String leadingNonLetterGszName = randomNumber(10) + "TEST-" + randomNumber(10);
        new GszNameChangeScenarioUtils(driver)
                .openNameDialog()
                .checkSaveButtonDisable()
                .changeGszName(leadingNonLetterGszName)
                .save()
                .checkGszNameError(of(ALERT_GSZ_NAME_MUST_BEGIN_WITH_LETTER))
                .assertGszName(originGszName);
    }

    @Test
    @Description("[UI] Смена названия ГСЗ (LEADING_SPACE)")
    public void testUiGszNameChangeLeadingSpace(WebDriver driver) {
        var leadingSpaceGszName = " " + "TEST-" + randomNumber(10);
        new GszNameChangeScenarioUtils(driver)
                .openNameDialog()
                .checkSaveButtonDisable()
                .changeGszName(leadingSpaceGszName)
                .save()
                .checkGszNameError(
                        of(ALERT_GSZ_NAME_MUST_BEGIN_WITH_LETTER, ALERT_GSZ_NAME_MUST_NOT_START_WITH_SPACE))
                .assertGszName(originGszName);
    }

    @Test
    @Description("[UI] Смена названия ГСЗ (TRAILING_SPACE)")
    public void testUiGszNameChangeTrailingSpace(WebDriver driver) {
        var trailingSpaceGszName = "TEST-" + randomNumber(10) + " ";
        new GszNameChangeScenarioUtils(driver)
                .openNameDialog()
                .checkSaveButtonDisable()
                .changeGszName(trailingSpaceGszName)
                .save()
                .checkGszNameError(of("Название ГСЗ не должно заканчиваться пробелом"))
                .assertGszName(originGszName);
    }

    @Test
    @Description("[UI] Смена названия ГСЗ (CONSECUTIVE_SPACES)")
    public void testUiGszNameChangeConsecutiveSpaces(WebDriver driver) {
        var consecutiveSpaceGszName = "TEST-" + "  " + randomNumber(10);
        new GszNameChangeScenarioUtils(driver)
                .openNameDialog()
                .checkSaveButtonDisable()
                .changeGszName(consecutiveSpaceGszName)
                .save()
                .checkGszNameError(of(ALERT_GSZ_NAME_SHOULD_NOT_CONTAIN_SPACE))
                .assertGszName(originGszName);
    }

    @Test
    @Description("[UI] Смена названия ГСЗ (ILLEGAL_SYMBOL)")
    public void testUiGszNameChangeIllegalSymbol(WebDriver driver) {
        var illegalSymbolSpaceGszName = "TEST-" + "!@#$%^&*()" + randomNumber(10);
        new GszNameChangeScenarioUtils(driver)
                .openNameDialog()
                .checkSaveButtonDisable()
                .changeGszName(illegalSymbolSpaceGszName)
                .save()
                .checkGszNameError(of(ALERT_GSZ_NAME_CONTAINS_LETTER_UMBER_CHAR_SPACE))
                .assertGszName(originGszName);
    }

    @Test
    @Description("[UI] Смена названия ГСЗ (UNIQUE_NAME)[ACTIVE]")
    public void testUiGszNameChangeUniqueNameStatusActive(WebDriver driver) {
        var uniqueGszName = "TEST-" + randomNumber(10);
        gszClient.saveGsz(createGsz(uniqueGszName, ACTIVE));
        new GszNameChangeScenarioUtils(driver)
                .openNameDialog()
                .checkSaveButtonDisable()
                .changeGszName(uniqueGszName)
                .save()
                .checkGszNameError(of(ALERT_GSZ_NAME_ALREADY_EXIST.apply(uniqueGszName)))
                .assertGszName(originGszName);
    }

    @Test
    @Description("[UI] Смена названия ГСЗ (UNIQUE_NAME)[CLOSE]")
    public void testUiGszNameChangeUniqueNameStatusClose(WebDriver driver) {
        var uniqueNameGszName = "TEST-" + randomNumber(10);
        gszClient.saveGsz(createGsz(uniqueNameGszName, CLOSE));
        new GszNameChangeScenarioUtils(driver)
                .openNameDialog()
                .checkSaveButtonDisable()
                .changeGszName(uniqueNameGszName)
                .save()
                .checkGszNameError(of(ALERT_GSZ_NAME_ALREADY_EXIST.apply(uniqueNameGszName)))
                .assertGszName(originGszName);
    }

    @Test
    @Description("Check cancel button when Gsz name change)")
    public void testCheckCancelButtonWhenGszNameChange(WebDriver driver) {
        var gszName = format("TEST-%d", randomNumber(3));
        new GszNameChangeScenarioUtils(driver)
                .openNameDialog()
                .checkSaveButtonDisable()
                .changeGszName(gszName)
                .cancel()
                .assertGszName(originGszName);
    }
}
