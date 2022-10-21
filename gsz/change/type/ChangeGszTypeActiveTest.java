package ru.sber.gsz.autotests.gsz.change.type;

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

import static java.lang.String.format;
import static ru.sber.gsz.autotests.dto.gsz.common.GszStatus.ACTIVE;
import static ru.sber.gsz.autotests.dto.gsz.common.GszType.*;
import static ru.sber.gsz.autotests.gsz.change.GszChangeUtils.createGsz;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.randomNumber;

@ExtendWith({SeleniumExtension.class})
@Import({TmCommonClientConfig.class, GszClient.class})
@SpringBootTest
public class ChangeGszTypeActiveTest {
    @Autowired
    private GszClient gszClient;
    @Autowired
    private GszTab gszTab;

    @BeforeEach
    public void prepareConditionToTest(WebDriver driver) {
        var originGszName = format("test-gsz-type-change-%d", randomNumber(6));
        var gsz = createGsz(originGszName, ACTIVE);
        gszClient.saveGsz(gsz);
        driver.get(gszTab.getViewUrl(gsz.getRoot().getId()));
    }

    @Test
    @Description("Gsz change type to ГСЗ МБ")
    public void testUiGszChangeTypeToGszMb(WebDriver driver) {
        new GszTypeChangeScenarioUtils(driver)
                .openTypeDialog()
                .checkSaveButtonDisable()
                .changeGszType(GSZ_MB)
                .save()
                .assertGszType("ГСЗ МБ");
    }

    @Test
    @Description("Gsz change type to Банковская группа")
    public void testUiGszChangeTypeToBankGroup(WebDriver driver) {
        new GszTypeChangeScenarioUtils(driver)
                .openTypeDialog()
                .checkSaveButtonDisable()
                .changeGszType(BANK_GROUP)
                .save()
                .assertGszType("Банковская группа");
    }

    @Test
    @Description("Gsz change type to ГСЗ КК")
    public void testUiGszChangeTypeToGszKK(WebDriver driver) {
        new GszTypeChangeScenarioUtils(driver)
                .openTypeDialog()
                .checkSaveButtonDisable()
                .changeGszType(BANK_GROUP)
                .save()
                .assertGszType("Банковская группа")
                .openTypeDialog()
                .checkSaveButtonDisable()
                .changeGszType(GSZ_KK)
                .save()
                .assertGszType("ГСЗ КК");
    }

    @Test
    @Description("Check cancel button when Gsz change type")
    public void testCheckCancelButtonWhenGszChangeType(WebDriver driver) {
        new GszTypeChangeScenarioUtils(driver)
                .openTypeDialog()
                .checkSaveButtonDisable()
                .changeGszType(GSZ_MB)
                .cancel()
                .assertGszType("ГСЗ КК");
    }
}
