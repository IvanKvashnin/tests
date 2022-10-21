package ru.sber.gsz.autotests.gsz.create.gsz_mb;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import ru.sber.gsz.autotests.dto.criterion.CriterionDto;
import ru.sber.gsz.autotests.ui.GszTab;

import java.util.List;

import static ru.sber.gsz.autotests.dto.criterion.CriterionDto.criterionWithTypeWithShuffle;
import static ru.sber.gsz.autotests.dto.criterion.CriterionType.ECONOMIC;
import static ru.sber.gsz.autotests.dto.gsz.common.GszType.GSZ_MB;
import static ru.sber.gsz.autotests.util.constants.AlertMessage.*;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.randomNumber;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.randomString;

@ExtendWith({SeleniumExtension.class})
@Import({TmCommonClientConfig.class, GszClient.class, CriterionClient.class})
@SpringBootTest
public class GszCreateMBCheckNewNameField {
    @Autowired
    private GszTab gszTab;

    @Autowired
    private CriterionClient criterionClient;

    private String criterionTypeEconomic;
    private final String commentText = "Test";

    @BeforeEach
    public void prepareConditionToTest(WebDriver driver) {
        List<CriterionDto> criterionList = criterionClient.getAllCriteria().getItems();
        criterionTypeEconomic = criterionWithTypeWithShuffle(criterionList, ECONOMIC).getExtId();
        driver.get(gszTab.getCreateUrl());
    }

    @Test
    @Description("Create GSZ type MB without GSZ name")
    public void testCreateGszTypeMBWithoutGSZName(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszType(GSZ_MB)
                .setNewGszName(" ")
                .setMainOrgRandomName()
                .setOtherOrgsRandomName(1)
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .createGsz()
                .assertNewNameGszError(ALERT_GSZ_NAME_MUST_BEGIN_WITH_LETTER);
    }

    @Test
    @Description("Create GSZ type BM with name less than 3 char")
    public void testCreateGszTypeBMWithNameLessThanThreeChar(WebDriver driver) {
        var shortGszName = "a" + randomNumber(1);
        new GszCreateMBScenarioUtils(driver)
                .setNewGszType(GSZ_MB)
                .setNewGszName(shortGszName)
                .setMainOrgRandomName()
                .setOtherOrgsRandomName(1)
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .createGsz()
                .assertNewNameGszError(ALERT_GSZ_NAME_MUST_BE_MORE_THAN_3_CHAR);
    }

    @Test
    @Description("Create GSZ type MB - gsz name start with no letter ")
    public void testCreateGszTypeMBWGszNameStartWithLetter(WebDriver driver) {
        String leadingNonLetterGszName = randomNumber(10) + "TEST-" + randomNumber(10);
        new GszCreateMBScenarioUtils(driver)
                .setNewGszType(GSZ_MB)
                .setNewGszName(leadingNonLetterGszName)
                .setMainOrgRandomName()
                .setOtherOrgsRandomName(1)
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .createGsz()
                .assertNewNameGszError(ALERT_GSZ_NAME_MUST_BEGIN_WITH_LETTER);
    }

    @Test
    @Description("Create GSZ type MB with name has leading space")
    public void testCreateGszTypeMBWithNameHasLeadingSpace(WebDriver driver) {
        var leadingSpaceGszName = " " + "TEST-" + randomNumber(10);
        new GszCreateMBScenarioUtils(driver)
                .setNewGszType(GSZ_MB)
                .setNewGszName(leadingSpaceGszName)
                .setMainOrgRandomName()
                .setOtherOrgsRandomName(1)
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .createGsz()
                .assertNewNameGszError(ALERT_GSZ_NAME_MUST_BEGIN_WITH_LETTER);
    }


    @Test
    @Description("Create GSZ type MB with name has consecutive space")
    public void testCreateGszTypeMBWithNameHasConsecutiveSpace(WebDriver driver) {
        var consecutiveSpaceGszName = "TEST-" + "  " + randomNumber(10);
        new GszCreateMBScenarioUtils(driver)
                .setNewGszType(GSZ_MB)
                .setNewGszName(consecutiveSpaceGszName)
                .setMainOrgRandomName()
                .setOtherOrgsRandomName(1)
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .createGsz()
                .assertNewNameGszError(ALERT_GSZ_NAME_SHOULD_NOT_CONTAIN_SPACE);
    }

    @Test
    @Description("Create GSZ type MB with name has illegal char")
    public void testCreateGszTypeMBWithNameHasChar(WebDriver driver) {
        var illegalSymbolSpaceGszName = "TEST-" + "!@#$%^&*()" + randomNumber(10);
        new GszCreateMBScenarioUtils(driver)
                .setNewGszType(GSZ_MB)
                .setNewGszName(illegalSymbolSpaceGszName)
                .setMainOrgRandomName()
                .setOtherOrgsRandomName(1)
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .createGsz()
                .assertNewNameGszError(ALERT_GSZ_NAME_CONTAINS_LETTER_UMBER_CHAR_SPACE);
    }

    // FIXME: 02.09.2022 Должна быть ошибка у поля "Название ГСЗ" https://jira.sberbank.ru/browse/RCIBGSZ-1148
    @Disabled
    @Test
    @Description("Create GSZ type MB with name less more than 256 char")
    public void testCreateGszTypeMBWithNameLessThan256Char(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszType(GSZ_MB)
                .setNewGszName(randomString(256))
                .setMainOrgRandomName()
                .setOtherOrgsRandomName(1)
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .createGsz()
                .assertNewNameGszError("");
    }
}

