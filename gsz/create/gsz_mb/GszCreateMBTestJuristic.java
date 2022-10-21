package ru.sber.gsz.autotests.gsz.create.gsz_mb;

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
import ru.sber.gsz.autotests.client.criteria.CriterionClient;
import ru.sber.gsz.autotests.client.gsz.GszClient;
import ru.sber.gsz.autotests.ui.GszTab;

import static java.lang.String.format;
import static ru.sber.gsz.autotests.dto.criterion.CriterionDto.criterionWithTypeWithShuffle;
import static ru.sber.gsz.autotests.dto.criterion.CriterionType.JURISTIC;
import static ru.sber.gsz.autotests.dto.gsz.common.GszType.GSZ_MB;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.randomNumber;

@ExtendWith({SeleniumExtension.class})
@Import({TmCommonClientConfig.class, GszClient.class, CriterionClient.class})
@SpringBootTest
public class GszCreateMBTestJuristic {
    @Autowired
    private GszTab gszTab;
    @Autowired
    private CriterionClient criterionClient;

    private final String originGszName = format("test-create-gsz-%d", randomNumber(6));

    @BeforeEach
    public void prepareConditionToTest(WebDriver driver) {
        driver.get(gszTab.getCreateUrl());
    }

    // FIXME: 12.10.2022 https://jira.sberbank.ru/browse/RCIBGSZ-1262
    @Test
    @Description("Create GSZ type MB juristic link (SUCCESS)")
    public void testCreateGszTypeMBJuristicLinkSuccess(WebDriver driver) {
        var criterionList = criterionClient.getAllCriteria().getItems();
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setNewLinkToGsz(1, criterionWithTypeWithShuffle(criterionList, JURISTIC).getExtId(), originGszName)
                .createGsz()
                .assertThatGszKKIsCreate(originGszName);
    }

    @Test
    @Description("Create GSZ type MB add 2 juristic links without 1 org link")
    public void testCreateGszTypeMBAddTwoJuristicLinkWithoutOneOrgLink(WebDriver driver) {
        var criterionList = criterionClient.getAllCriteria().getItems();
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(2)
                .checkCreateAndAddButtonsIsDisabled()
                .setNewLinkToGsz(1, criterionWithTypeWithShuffle(criterionList, JURISTIC).getExtId(), "Test")
                .createGsz()
                .assertErrorAlertOrgNotLinks();
    }

    @Test
    @Description("Create GSZ type MB add 3 juristic links and delete all")
    public void testCreateGszTypeMBAddThreeJuristicLinksAndDeleteAll(WebDriver driver) {
        var criterionList = criterionClient.getAllCriteria().getItems();
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(5)
                .checkCreateAndAddButtonsIsDisabled()
                .setNewLinkToGsz(1, criterionWithTypeWithShuffle(criterionList, JURISTIC).getExtId(), "Test")
                .addLinkFields()
                .setNewLinkToGsz(2, criterionWithTypeWithShuffle(criterionList, JURISTIC).getExtId(), "Test")
                .addLinkFields()
                .setNewLinkToGsz(3, criterionWithTypeWithShuffle(criterionList, JURISTIC).getExtId(), "Test")
                .deleteAllLinkFields()
                .assertDeleteLinksFields();
    }

    @Test
    @Description("Create GSZ type MB juristic check Clear Button")
    public void testCreateGszTypeMBCheckClearButton(WebDriver driver) {
        var criterionList = criterionClient.getAllCriteria().getItems();
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setNewLinkToGsz(1, criterionWithTypeWithShuffle(criterionList, JURISTIC).getExtId(), "Test")
                .clearAllFields()
                .assertAllFieldsAreEmptyAfterClearButton()
                .checkCreateGszButtonIsDisabled();
    }
}
