package ru.sber.gsz.autotests.gsz.change.name;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Import;
import ru.sber.gsz.autotests.SeleniumExtension;
import ru.sber.gsz.autotests.client.common.TmCommonClientConfig;
import ru.sber.gsz.autotests.client.gsz.GszClient;
import ru.sber.gsz.autotests.ui.GszTab;

import static com.google.common.collect.ImmutableList.of;
import static java.lang.String.format;
import static ru.sber.gsz.autotests.dto.gsz.common.GszStatus.EDIT;
import static ru.sber.gsz.autotests.gsz.change.GszChangeUtils.createGsz;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.randomNumber;

@ExtendWith({SeleniumExtension.class})
@Import({TmCommonClientConfig.class, GszClient.class})
@SpringBootTest
public class ChangeGszNameEditTest {
    @Autowired
    private GszClient gszClient;
    @Autowired
    private GszTab gszTab;

    private final String originGszName = format("test-gsz-type-change-%d", randomNumber(6));

    @BeforeEach
    public void prepareConditionToTest(WebDriver driver) {
        var gsz = createGsz(originGszName, EDIT);
        gszClient.saveGsz(gsz);
        driver.get(gszTab.getViewUrl(gsz.getRoot().getId()));
    }

    @Test
    @Description("Gsz name change in status EDIT")
    public void testUiGszNameChangeStatusEdit(WebDriver driver) {
        var gszName = format("TEST-%d", randomNumber(3));
        new GszNameChangeScenarioUtils(driver)
                .openNameDialog()
                .checkSaveButtonDisable()
                .changeGszName(gszName)
                .save()
                .checkGszNameError(of("Нельзя редактировать ГСЗ в статусах NEW или EDIT"))
                .assertGszName(originGszName);
    }
}
