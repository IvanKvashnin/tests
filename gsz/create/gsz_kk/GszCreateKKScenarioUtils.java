package ru.sber.gsz.autotests.gsz.create.gsz_kk;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.gsz.create.GszCreateGszUtils;
import ru.sber.gsz.autotests.util.createGsz.ErrorAlertDialog;
import ru.sber.gsz.autotests.util.createGsz.GszCreateFormKK;
import ru.sber.gsz.autotests.util.field.AbstractField;
import ru.sber.gsz.autotests.util.input.Input;

import java.util.function.Function;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.sber.gsz.autotests.util.constants.AlertMessage.ALERT_TASK_NOT_CREATED_GSZ_NAME_ALREADY_EXIST;
import static ru.sber.gsz.autotests.util.constants.FieldLabels.*;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.randomNumber;

public class GszCreateKKScenarioUtils extends GszCreateGszUtils<GszCreateKKScenarioUtils> {
    private final WebDriver driver;

    public GszCreateKKScenarioUtils(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public GszCreateKKScenarioUtils setDataIntoSpecificFieldOtherOrg(int numberRandomName, String typeData, int setNumberFieldWhereTypeData) {
        if (setNumberFieldWhereTypeData < 0 || numberRandomName < 0)
            throw new IllegalStateException("Нельзя вписать usedEpkId отрицательное по счету поле");
        for (int i = 1; i <= numberRandomName; i++) {
            if (i == setNumberFieldWhereTypeData) {
                getGszCreateFormKK().getOtherOrgField()
                        .input(typeData)
                        .process();
                break;
            } else {
                getGszCreateFormKK().getOtherOrgField()
                        .input(valueOf(randomNumber(6)))
                        .process();
            }
        }
        return this;
    }

    public GszCreateKKScenarioUtils assertErrorAlertUsedGszName(String gszName) {
        assertThat(ALERT_TASK_NOT_CREATED_GSZ_NAME_ALREADY_EXIST.apply(gszName))
                .isEqualTo(new ErrorAlertDialog(driver).getErrorAlert().getDescription());
        return this;
    }

    private <V, I extends Input<V, WebElement>, F extends AbstractField<V, I, F>>
    GszCreateKKScenarioUtils emptyFieldsAsserter(Function<GszCreateFormKK, F> getter, String fieldLabel) {
        F field = getter.apply(getGszCreateFormKK());
        var getField = field.getField();
        assertThat(fieldLabel).isEqualTo(getField.getText());
        return this;
    }

    public GszCreateKKScenarioUtils assertAllFieldsAreEmpty() {
        emptyFieldsAsserter(GszCreateFormKK::getHeaderNewNameGszField, LABEL_NEW_GSZ_NAME);
        emptyFieldsAsserter(GszCreateFormKK::getMainOrgField, LABEL_EPK_ID_ORG);
        emptyFieldsAsserter(GszCreateFormKK::getOtherOrgField, LABEL_EPK_ID_ORG);
        return this;
    }

    public GszCreateKKScenarioUtils assertAllFieldsAreEmptyWithTypeGszFiled() {
        emptyFieldsAsserter(GszCreateFormKK::getHeaderNewNameGszField, LABEL_NEW_GSZ_NAME);
        emptyFieldsAsserter(GszCreateFormKK::getHeaderNewTypeGszField, LABEL_NEW_GSZ_TYPE);
        emptyFieldsAsserter(GszCreateFormKK::getMainOrgField, LABEL_EPK_ID_ORG);
        emptyFieldsAsserter(GszCreateFormKK::getOtherOrgField, LABEL_EPK_ID_ORG);
        return this;
    }

    public GszCreateKKScenarioUtils assertDialogErrorAlert(String errorDescription) {
        assertThat(errorDescription)
                .isEqualTo(new ErrorAlertDialog(driver).getErrorAlert().getDescription());
        return this;
    }

    public GszCreateKKScenarioUtils assertOtherOrgFieldError(String fieldError) {
        fieldErrorAsserter(getGszCreateFormKK(), GszCreateFormKK::getOtherOrgField, fieldError);
        return this;
    }

    public GszCreateKKScenarioUtils assertMainOrgFieldError(String fieldError) {
        fieldErrorAsserter(getGszCreateFormKK(), GszCreateFormKK::getMainOrgField, fieldError);
        return this;
    }
}

