package ru.sber.gsz.autotests.gsz.change.gkm;

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
import ru.sber.gsz.autotests.client.gsz.GszClient;
import ru.sber.gsz.autotests.ui.GszTab;

import static java.lang.String.format;
import static ru.sber.gsz.autotests.dto.gsz.common.GszStatus.ACTIVE;
import static ru.sber.gsz.autotests.gsz.change.GszChangeUtils.createGsz;
import static ru.sber.gsz.autotests.gsz.change.GszChangeUtils.getEmployeeDto;
import static ru.sber.gsz.autotests.gsz.change.gkm.GszGkmChangeScenarioUtils.valueFromGkmField;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.randomNumber;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.randomString;

@ExtendWith({SeleniumExtension.class})
@Import({TmCommonClientConfig.class, GszClient.class})
@SpringBootTest
public class ChangeGszGkmActiveTest {
    @Autowired
    private GszClient gszClient;
    @Autowired
    private GszTab gszTab;

    String employeeExtId;
    String employeeFullName;
    String originGszName;


    @BeforeEach
    public void prepareConditionToTest(WebDriver driver) {
        originGszName = format("test-gsz-gkm-change-%d", randomNumber(6));
        var gsz = createGsz(originGszName, ACTIVE);
        gszClient.saveGsz(gsz);
        driver.get(gszTab.getViewUrl(gsz.getRoot().getId()));
        var employeeDto = getEmployeeDto();
        employeeExtId = employeeDto.getPersonalNumber();
        employeeFullName = employeeDto.getLastName() + " " + employeeDto.getFirstName() + " " + employeeDto.getMiddleName();
    }

    @Test
    @Description("Gsz GKM change name by type personalNumber (SUCCESS))")
    public void testUiGszGkmChangeNameTypePersonalNumberSuccess(WebDriver driver) {
        new GszGkmChangeScenarioUtils(driver)
                .openGkmDialog()
                .checkSaveButtonDisable()
                .typeDialogField(employeeExtId)
                .assertEmployeeFullNameAlert(employeeFullName)
                .save()
                .assertNameFieldGkm(employeeFullName);
    }

    // FIXME: 19.08.2022  https://jira.sberbank.ru/browse/RCIBGSZ-1220
    @Test
    @Disabled
    @Description("Gsz GKM check ErrorAlert when type over 50 symbols")
    public void testUiGszGkmChangeErrorAlertWhenType50symbolsSuccess(WebDriver driver) {
        new GszGkmChangeScenarioUtils(driver)
                .openGkmDialog()
                .checkSaveButtonDisable()
                .typeDialogField(randomString(50))
                .assertErrorAlert()
                .save()
                .assertNameFieldGkm(employeeFullName);
    }

    @Test
    @Description("Check cancel button when Gsz GKM change name by type personalNumber)")
    public void testCheckCancelButtonWhenChangeName(WebDriver driver) {
        new GszGkmChangeScenarioUtils(driver)
                .openGkmDialog()
                .checkSaveButtonDisable()
                .typeDialogField(employeeExtId)
                .assertEmployeeFullNameAlert(employeeFullName)
                .cancel()
                .assertNameFieldGkm(valueFromGkmField);
    }
}
