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
import static ru.sber.gsz.autotests.dto.gsz.common.GszStatus.EDIT;
import static ru.sber.gsz.autotests.dto.gsz.common.GszType.GSZ_KK;
import static ru.sber.gsz.autotests.dto.gsz.common.GszType.GSZ_MB;
import static ru.sber.gsz.autotests.gsz.change.GszChangeUtils.createGsz;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.randomNumber;

@ExtendWith({SeleniumExtension.class})
@Import({TmCommonClientConfig.class, GszClient.class})
@SpringBootTest
public class ChangeGszTypeEditTest {
    @Autowired
    private GszClient gszClient;
    @Autowired
    private GszTab gszTab;

//    @BeforeEach
//    public void prepareConditionToTest(WebDriver driver) {
//
//    }

    @Test
    @Description("Gsz change type in status EDIT")
    public void testUiGszChangeTypeStatusEdit(WebDriver driver) {
        var originGszName = format("test-gsz-type-change-%d", randomNumber(6));
        var gsz = createGsz(originGszName, EDIT, GSZ_KK);
//        gsz.getRoot().getGkm().getExtId();
        gszClient.saveGsz(gsz);
        driver.get(gszTab.getViewUrl(gsz.getRoot().getId()));
        new GszTypeChangeScenarioUtils(driver)
                .openTypeDialog()
                .checkSaveButtonDisable()
                .changeGszType(GSZ_MB)
                .save()
                 .checkGszNameError("Нельзя редактировать ГСЗ в статусах NEW или EDIT")
                .assertGszType("ГСЗ КК");
    }
}
