package ru.sber.gsz.autotests.gsz.create.gsz_kk;

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

import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static ru.sber.gsz.autotests.dto.gsz.common.GszStatus.ACTIVE;
import static ru.sber.gsz.autotests.dto.gsz.common.GszType.GSZ_KK;
import static ru.sber.gsz.autotests.util.constants.AlertMessage.*;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.*;
import static ru.sber.gsz.autotests.utils.gsz.GszUtils.createDefaultGszTreeWithLinks;

@ExtendWith({SeleniumExtension.class})
@Import({TmCommonClientConfig.class, GszClient.class, CriterionClient.class})
@SpringBootTest
public class GszCreateKKTest {
    @Autowired
    private GszClient gszClient;
    @Autowired
    private GszTab gszTab;
    @Autowired
    private CriterionClient criterionClient;


    private final String originGszName = format("test-create-gsz-%d", randomNumber(6));
    private final String usedName = format("test-create-gsz-%d", randomNumber(6));
    private final String orgEpkId = valueOf(randomNumber(5, 6));
    private final String mainOrgLetterName = generateOrgName(parseLong(orgEpkId), 5);
    private final String errorOrgIncludeInGsz = "Организация " + mainOrgLetterName + " уже добавлена в состав новой ГСЗ";


    @BeforeEach
    public void prepareConditionToTest(WebDriver driver) {
        driver.get(gszTab.getCreateUrl());
    }

    @Test
    @Description("Create GSZ type KK (SUCCESS)")
    public void testCreateGszTypeKKSuccess(WebDriver driver) {
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgRandomName()
                .checkCreateGszButtonIsDisabled()
                .setOtherOrgsRandomName(2)
                .createGsz()
                .assertThatGszMBIsCreate();
    }

    @Test
    @Description("Create GSZ type KK without GSZ name")
    public void testCreateGszTypeKKWithoutGSZName(WebDriver driver) {
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(" ")
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgRandomName()
                .checkCreateGszButtonIsDisabled()
                .setOtherOrgsRandomName(2)
                .checkCreateGszButtonIsDisabled();
    }

    @Test
    @Description("Create GSZ type KK clear form")
    public void testCreateGszTypeKKSuccessClearForm(WebDriver driver) {
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgRandomName()
                .checkCreateGszButtonIsDisabled()
                .setOtherOrgsRandomName(2)
                .clearForm()
                .assertAllFieldsAreEmptyWithTypeGszFiled()
                .checkCreateGszButtonIsDisabled();
    }

    @Test
    @Description("Create GSZ type KK with english char instead epkId in mainOrgField and otherOrgField")
    public void testCreateGszTypeKKWithEnglishCharInsteadEpkIdMainOrgFieldOtherOrgField(WebDriver driver) {
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgWithOutTypeProcess("TestEnglishChar")
                .checkCreateGszButtonIsDisabled()
                .setOtherOrgWithOutTypeByProcess("TestEnglishChar")
                .assertMainOrgFieldError(ALERT_EPK_ID)
                .assertOtherOrgFieldError(ALERT_EPK_ID)
                .checkCreateGszButtonIsDisabled();
    }

    @Test
    @Description("Create GSZ type KK with rus char instead epkId in mainOrgField and otherOrgField")
    public void testCreateGszTypeKKWithRusCharInsteadEpkIdMainOrgFieldOtherOrgField(WebDriver driver) {
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgWithOutTypeProcess("ТестРусскиеСимволы")
                .checkCreateGszButtonIsDisabled()
                .setOtherOrgWithOutTypeByProcess("ТестРусскиеСимволы")
                .assertMainOrgFieldError(ALERT_EPK_ID)
                .assertOtherOrgFieldError(ALERT_EPK_ID)
                .checkCreateGszButtonIsDisabled();
    }

    @Test
    @Description("Create GSZ type KK with symbol in mainOrg and otherOrg field ")
    public void testCreateGszTypeKKWithSymbolInMainAndOtherOrgFields(WebDriver driver) {
        var gsz = GszCreateGszUtils.createGszDto(ACTIVE);
        gszClient.saveGsz(gsz);
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(usedName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgWithOutTypeProcess(orgEpkId + "-")
                .checkCreateGszButtonIsDisabled()
                .setOtherOrgWithOutTypeByProcess(orgEpkId + "-")
                .assertMainOrgFieldError(ALERT_EPK_ID)
                .assertOtherOrgFieldError(ALERT_EPK_ID)
                .checkCreateGszButtonIsDisabled();
    }

    @Test
    @Description("Create GSZ type KK with same epkId mainOrg and otherOrg")
    public void testCreateGszTypeKKWithSameEpkIdMainOrgAndOtherOrg(WebDriver driver) {
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgTypeProcess(orgEpkId)
                .checkCreateGszButtonIsDisabled()
                .setOtherOrgTypeByProcess(orgEpkId)
                .assertOtherOrgFieldError(errorOrgIncludeInGsz)
                .checkCreateGszButtonIsDisabled();
    }

    @Test
    @Description("Create GSZ type KK with same epkId otherOrg and mainOrg")
    public void testCreateGszTypeKKWithSameEpkIdOtherOrgAndMainOrg(WebDriver driver) {
        var orgEpkId = valueOf(randomNumber(5, 6));
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgTypeProcess(orgEpkId)
                .checkCreateGszButtonIsDisabled()
                .setOtherOrgTypeByProcess(orgEpkId)
                .assertMainOrgFieldError(errorOrgIncludeInGsz)
                .checkCreateGszButtonIsDisabled();
    }


    @Test
    @Description("Create GSZ type KK with used GSZ name")
    public void testCreateGszTypeKKWithUsedGszName(WebDriver driver) {
        var gsz = GszCreateGszUtils.createGszDto(usedName, ACTIVE);
        gszClient.saveGsz(gsz);
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(usedName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgRandomName()
                .checkCreateGszButtonIsDisabled()
                .setOtherOrgsRandomName(3)
                .createGsz()
                .assertErrorAlertUsedGszName(usedName);
    }

    @Test
    @Description("Create GSZ type KK with empty main and other org fields")
    public void testCreateGszTypeKKWithEmptyMainAndOtherOrgFields(WebDriver driver) {
        var gsz = GszCreateGszUtils.createGszDto(usedName, ACTIVE);
        gszClient.saveGsz(gsz);
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(usedName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgTypeProcess(" ")
                .assertMainOrgFieldError(ALERT_NO_MASSAGE_AVAILABLE)
                .checkCreateGszButtonIsDisabled()
                .setOtherOrgTypeByProcess(" ")
                .assertOtherOrgFieldError(ALERT_NO_MASSAGE_AVAILABLE)
                .checkCreateGszButtonIsDisabled();
    }

    @Test
    @Description("Create GSZ type KK with used otherOrg epkId")
    public void testCreateGszTypeKKWithUsedOtherOrgEpkId(WebDriver driver) {
        List<CriterionDto> criteria = criterionClient.getAllCriteria().getItems();
        DefaultTree gszTree = createDefaultGszTreeWithLinks(criteria);
        var usedEpkIdOrg = gszTree.getRootWithChildren().getOrganizations().get(0).getEpkId();
        var usedLetterNameOrg = generateOrgName(parseLong(usedEpkIdOrg), 5);
        var includeGszName = gszTree.getRootWithChildren().getRoot().getName();
        var error = "Организация " + usedLetterNameOrg + " включена в ГСЗ " + includeGszName;
        gszClient.saveGsz(gszTree);
        driver.get(gszTab.getCreateUrl());
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgTypeProcess(usedEpkIdOrg)
                .assertMainOrgFieldError(error)
                .checkCreateGszButtonIsDisabled()
                .setDataIntoSpecificFieldOtherOrg(3, usedEpkIdOrg, 2)
                .assertOtherOrgFieldError(error)
                .checkCreateGszButtonIsDisabled();
    }

    @Test
    @Description("Create GSZ type KK with name less than 3 char")
    public void testCreateGszTypeKKWithNameLessThanThreeChar(WebDriver driver) {
        var shortGszName = "a" + randomNumber(1);
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(shortGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgRandomName()
                .setOtherOrgsRandomName(1)
                .createGsz()
                .assertDialogErrorAlert(ALERT_TASK_NOT_CREATED_GSZ_NAME_MUST_BE_MORE_THAN_3_CHAR);
    }

    @Test
    @Description("Create GSZ type KK with name has leading space")
    public void testCreateGszTypeKKWithNameHasLeadingSpace(WebDriver driver) {
        var leadingSpaceGszName = " " + "TEST-" + randomNumber(10);
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(leadingSpaceGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgRandomName()
                .setOtherOrgsRandomName(1)
                .createGsz()
                .assertDialogErrorAlert(ALERT_TASK_NOT_CREATED_GSZ_NAME_MUST_START_WITH_LETTER);
    }

    @Test
    @Description("Create GSZ type KK with name has consecutive space")
    public void testCreateGszTypeKKWithNameHasConsecutiveSpace(WebDriver driver) {
        var consecutiveSpaceGszName = "TEST-" + "  " + randomNumber(10);
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(consecutiveSpaceGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgRandomName()
                .setOtherOrgsRandomName(1)
                .checkCreateGszButtonIsDisabled()
                .createGsz()
                .assertDialogErrorAlert(ALERT_TASK_NOT_CREATED_GSZ_NAME_SHOULD_NOT_CONTAIN_SPACE);
    }

    @Test
    @Description("Create GSZ type KK with name has illegal char")
    public void testCreateGszTypeKKWithNameHasChar(WebDriver driver) {
        var illegalSymbolSpaceGszName = "TEST-" + "!@#$%^&*()" + randomNumber(10);
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(illegalSymbolSpaceGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgRandomName()
                .setOtherOrgsRandomName(1)
                .checkCreateGszButtonIsDisabled()
                .createGsz()
                .assertDialogErrorAlert(ALERT_TASK_NOT_CREATED_GSZ_NAME_CONTAINS_LETTER_UMBER_CHAR_SPACE);
    }

    // FIXME: 02.09.2022 Должна быть ошибка у поля "Название ГСЗ" https://jira.sberbank.ru/browse/RCIBGSZ-1148
    @Disabled
    @Test
    @Description("Create GSZ type KK with name less more than 256 char")
    public void testCreateGszTypeKKWithNameLessThan256Char(WebDriver driver) {
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(randomString(256))
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgRandomName()
                .setOtherOrgsRandomName(1)
                .checkCreateGszButtonIsDisabled()
                .createGsz()
                .assertDialogErrorAlert("");
    }

    @Test
    @Description("Create GSZ type KK add MainOrg with OtherOrg and Delete")
    public void testCreateGszTypeKKAddAndDeleteMainOrg(WebDriver driver) {
        new GszCreateKKScenarioUtils(driver)
                .setNewGszName(originGszName)
                .checkCreateGszButtonIsDisabled()
                .setNewGszType(GSZ_KK)
                .checkCreateGszButtonIsDisabled()
                .setMainOrgRandomName()
                .deleteMainOrg()
                .checkCreateGszButtonIsDisabled()
                .setOtherOrgsRandomName(2)
                .deleteOtherOrgs()
                .assertAllFieldsAreEmpty()
                .checkCreateGszButtonIsDisabled();
    }
}
