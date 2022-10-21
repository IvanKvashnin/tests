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
import ru.sber.gsz.autotests.dto.gsz.DefaultTree;
import ru.sber.gsz.autotests.gsz.create.GszCreateGszUtils;
import ru.sber.gsz.autotests.ui.GszTab;

import java.util.List;

import static java.lang.String.format;
import static ru.sber.gsz.autotests.dto.criterion.CriterionDto.criterionWithTypeWithShuffle;
import static ru.sber.gsz.autotests.dto.criterion.CriterionType.ECONOMIC;
import static ru.sber.gsz.autotests.dto.gsz.common.GszStatus.ACTIVE;
import static ru.sber.gsz.autotests.dto.gsz.common.GszType.GSZ_KK;
import static ru.sber.gsz.autotests.dto.gsz.common.GszType.GSZ_MB;
import static ru.sber.gsz.autotests.util.constants.AlertMessage.ALERT_GSZ_NAME_ALREADY_EXIST;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.*;
import static ru.sber.gsz.autotests.utils.gsz.GszUtils.createDefaultGszTreeWithLinks;

@ExtendWith({SeleniumExtension.class})
@Import({TmCommonClientConfig.class, GszClient.class, CriterionClient.class})
@SpringBootTest
public class GszCreateMBTestEconomic {
    @Autowired
    private GszTab gszTab;
    @Autowired
    private CriterionClient criterionClient;
    @Autowired
    private GszClient gszClient;

    private final String originGszName = format("test-create-gsz-%d", randomNumber(6));
    private final String usedName = format("test-create-gsz-%d", randomNumber(6));
    private final String commentText = "Test";
    private String criterionTypeEconomic;

    @BeforeEach
    public void prepareConditionToTest(WebDriver driver) {
        List<CriterionDto> criterionList = criterionClient.getAllCriteria().getItems();
        criterionTypeEconomic = criterionWithTypeWithShuffle(criterionList, ECONOMIC).getExtId();
        driver.get(gszTab.getCreateUrl());
    }

    @Test
    @Description("Create GSZ type MB economic link (SUCCESS)")
    public void testCreateGszTypeMBEconomicLinkSuccess(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .createGsz()
                .assertThatGszKKIsCreate(originGszName);
    }

    @Test
    @Description("Create GSZ type MB add 2 economic links without 1 org link")
    public void testCreateGszTypeMBAddTwoEconomicLinkWithoutOneOrgLink(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(2)
                .checkCreateAndAddButtonsIsDisabled()
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .createGsz()
                .assertErrorAlertOrgNotLinks();
    }

    @Test
    @Description("Create GSZ type MB add 3 economic links and delete all")
    public void testCreateGszTypeMBAddThreeEconomicLinksAndDeleteAll(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(5)
                .checkCreateAndAddButtonsIsDisabled()
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .addLinkFields()
                .setNewLinkToGsz(2, criterionTypeEconomic, commentText)
                .addLinkFields()
                .setNewLinkToGsz(3, criterionTypeEconomic, commentText)
                .addLinkFields()
                .deleteAllLinkFields()
                .assertDeleteLinksFields()
                .checkCreateGszButtonIsDisabled();
    }

    @Test
    @Description("Create GSZ type MB economic check Clear Button")
    public void testCreateGszTypeMBCheckClearButton(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .clearAllFields()
                .assertAllFieldsAreEmptyAfterClearButton()
                .checkCreateGszButtonIsDisabled();
    }

    @Test
    @Description("Create GSZ type MB economic with empty field Main Org in Link in new GSZ")
    public void testCreateGszTypeMBEconomicWithEmptyFieldMainOrgInLinkInNewGSZ(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setLinkLinkedOrg(1)
                .setLinkCriterion(criterionTypeEconomic)
                .setLinkComment("Test")
                .checkCreateAndAddButtonsIsDisabled();
    }

    @Test
    @Description("Create GSZ type MB economic with empty field Related Org in Link in new GSZ")
    public void testCreateGszTypeMBEconomicWithEmptyFieldRelatedOrgInLinkInNewGSZ(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setLinkMainOrg()
                .setLinkCriterion(criterionTypeEconomic)
                .setLinkComment(commentText)
                .checkCreateAndAddButtonsIsDisabled();
    }

    @Test
    @Description("Create GSZ type MB economic with empty field criterion in Link in new GSZ")
    public void testCreateGszTypeMBEconomicWithEmptyFieldCriterionInLinkInNewGSZ(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setLinkMainOrg()
                .setLinkComment(commentText)
                .checkCreateAndAddButtonsIsDisabled();
    }

    @Test
    @Description("Create GSZ type MB economic with empty field comment in Link in new GSZ")
    public void testCreateGszTypeMBEconomicWithEmptyFieldCommentInLinkInNewGSZ(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setLinkMainOrg()
                .setLinkLinkedOrg(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setLinkCriterion(criterionTypeEconomic)
                .checkCreateAndAddButtonsIsDisabled();
    }

    // FIXME: 23.09.2022 https://jira.sberbank.ru/browse/RCIBGSZ-1221
    // TODO: 01.09.2022 Слишком много времени занимает ввод в поле 1002 символа, подумать как ускорить
    @Disabled
    @Test
    @Description("Create GSZ type MB economic 1002 char in comment field check error")
    public void testCreateGszTypeMBEconomic1002CharInCommentFieldCheckError(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setLinkMainOrg()
                .setLinkLinkedOrg(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setLinkCriterion(criterionTypeEconomic)
                .setLinkComment(randomString(1002))
                .assertError1000charsCommentField()
                .checkCreateAndAddButtonsIsDisabled();
    }

    @Test
    @Description("Create GSZ type MB economic delete MainOrg Field in New Gsz")
    public void testCreateGszTypeMBEconomicDeleteMainOrgFieldInNewGSZ(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(3)
                .checkCreateAndAddButtonsIsDisabled()
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .addLinkFields()
                .setNewLinkToGsz(2, criterionTypeEconomic, commentText)
                .addLinkFields()
                .setNewLinkToGsz(3, criterionTypeEconomic, commentText)
                .deleteOrgInMainOrgFieldNewGsz()
                .assertMainOrgFieldInNewGszIsEmpty()
                .assertFieldsInLinkGszFormIsEmpty()
                .assertDeleteLinksFields()
                .checkCreateAndAddButtonsIsDisabled();
    }

    @Test
    @Description("Create GSZ type MB economic delete OtherOrg Field in New Gsz")
    public void testCreateGszTypeMBEconomicDeleteOtherOrgFieldInNewGSZ(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .deleteOtherOrgs()
                .assertOtherOrgFieldInNewGszIsEmpty()
                .assertDeleteLinksFields()
                .checkCreateAndAddButtonsIsDisabled();
    }

    // FIXME: 02.09.2022 https://jira.sberbank.ru/browse/RCIBGSZ-1143
    @Disabled
    @Test
    @Description("Create GSZ type MB change -> KK -> MB assert links in new GSZ (SUCCESS)")
    public void testCreateGszTypeMBChangeToTypeKKToMBAssertLinksInNewGSZSuccess(WebDriver driver) {
        var commentText = "Test";
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .setNewGszType(GSZ_KK)
                .assertCreateFormKKIsVisible()
                .setNewGszType(GSZ_MB)
                .assertCreateFormMBIsVisible()
                .assertFieldsInLinkGszFormAfterSwapMBToKKToMB(criterionTypeEconomic, commentText)
                .createGsz()
                .assertThatGszMBIsCreate();
    }

    @Test
    @Description("Create GSZ type MB change to type KK after completed fields new links (SUCCESS)")
    public void testCreateGszTypeMBChangeToTypeKKAfterCompletedFieldsNewLinks(WebDriver driver) {
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(1)
                .checkCreateAndAddButtonsIsDisabled()
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .setNewGszType(GSZ_KK)
                .createGsz()
                .assertThatGszMBIsCreate();
    }

    @Test
    @Description("Create GSZ type MB with used GSZ name")
    public void testCreateGszTypeKKWithUsedGszName(WebDriver driver) {
        var gsz = GszCreateGszUtils.createGszDto(usedName, ACTIVE);
        gszClient.saveGsz(gsz);
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(usedName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateAndAddButtonsIsDisabled()
                .setMainOrgRandomName()
                .checkCreateAndAddButtonsIsDisabled()
                .setOtherOrgsRandomName(2)
                .checkCreateAndAddButtonsIsDisabled()
                .setNewLinkToGsz(1, criterionTypeEconomic, commentText)
                .createGsz()
                .assertErrorAlertUsedGszName(ALERT_GSZ_NAME_ALREADY_EXIST.apply(usedName));
    }


//     FIXME: 13.09.2022 https://jira.sberbank.ru/browse/RCIBGSZ-1165
    @Disabled
    @Test
    @Description("Create GSZ type MB with used otherOrg epkId")
    public void testCreateGszTypeMBWithUsedOtherOrgEpkId(WebDriver driver) {
        List<CriterionDto> criteria = criterionClient.getAllCriteria().getItems();
        DefaultTree gszTree = createDefaultGszTreeWithLinks(criteria);
        var usedEpkIdOrg = gszTree.getRootWithChildren().getOrganizations().get(0).getEpkId();
        var usedLetterNameOrg = generateOrgName(Long.parseLong(usedEpkIdOrg), 5);
        var includeGszName = gszTree.getRootWithChildren().getRoot().getName();
        var error = "Организация " + usedLetterNameOrg + " включена в ГСЗ " + includeGszName;
        gszClient.saveGsz(gszTree);
        driver.get(gszTab.getCreateUrl());
        new GszCreateMBScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_MB)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgTypeProcess(usedEpkIdOrg)
                .assertMainOrgFieldError(error)
                .checkCreateGszButtonIsDisabled()
                .setOtherOrgTypeByProcess(usedEpkIdOrg)
                .assertOtherOrgFieldError(error)
                .assertDropDownListLinkedOrgIsEmpty()
                .assertDropDownListLinkMainOrg()
                .checkCreateGszButtonIsDisabled();
    }
}
