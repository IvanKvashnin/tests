package ru.sber.gsz.autotests.gsz.change.gkm;

import com.sbt.bpspe.core.dto.entity.EmployeeIdDto;
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
import static com.sbt.bpspe.core.dto.common.EmployeeIdType.PERSONAL_NUMBER;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static ru.sber.gsz.autotests.dto.gsz.common.GszStatus.EDIT;
import static ru.sber.gsz.autotests.gsz.change.GszChangeUtils.createGsz;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.randomNumber;
import static ru.sber.gsz.autotests.utils.employee.EmployeeUtils.buildEmployee;

@ExtendWith({SeleniumExtension.class})
@Import({TmCommonClientConfig.class, GszClient.class})
@SpringBootTest
public class ChangeGszGkmEditTest {
    @Autowired
    private GszClient gszClient;
    @Autowired
    private GszTab gszTab;

    private final String originGszName = format("test-gsz-gkm-change-%d", randomNumber(6));
    String fullName;


    @BeforeEach
    public void prepareConditionToTest(WebDriver driver) {
        var gsz = createGsz(originGszName, EDIT);
        var gkmExtId = gsz.getRoot().getGkm().getExtId();
        var dto = buildEmployee(EmployeeIdDto.builder()
                .employeeId(gkmExtId)
                .type(PERSONAL_NUMBER)
                .build());
        fullName = dto.getLastName() + " " + dto.getFirstName() + " " + dto.getMiddleName();
        gszClient.saveGsz(gsz);
        driver.get(gszTab.getViewUrl(gsz.getRoot().getId()));
    }

    @Test
    @Description("Gsz GKM change name by type personalNumber (SUCCESS))")
    public void testUiGszGkmChangeNameTypePersonalNumberSuccess(WebDriver driver) {
        new GszGkmChangeScenarioUtils(driver)
                .openGkmDialog()
                .checkSaveButtonDisable()
                .typeDialogField(valueOf(randomNumber(8)))
                .save()
                .checkGkmNameError((of("Нельзя редактировать ГКМ в статусах EDIT или CLOSE")))
                .assertNameFieldGkm(fullName);
    }
}
