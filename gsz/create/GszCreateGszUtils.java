package ru.sber.gsz.autotests.gsz.create;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ru.sber.gsz.autotests.dto.gsz.GszCreateDto;
import ru.sber.gsz.autotests.dto.gsz.GszTreeWithOrgDto;
import ru.sber.gsz.autotests.dto.gsz.common.GszStatus;
import ru.sber.gsz.autotests.dto.gsz.common.GszType;
import ru.sber.gsz.autotests.util.alert.Alert;
import ru.sber.gsz.autotests.util.createGsz.GszCreateFormKK;
import ru.sber.gsz.autotests.util.createGsz.GszCreateFormLinksInNewGsz;
import ru.sber.gsz.autotests.util.createGsz.SuccessAlertDialog;
import ru.sber.gsz.autotests.util.field.AbstractField;
import ru.sber.gsz.autotests.util.input.Input;
import ru.sber.gsz.autotests.util.input.InputHelper;

import java.util.function.Function;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.sber.gsz.autotests.dto.gsz.common.GszType.GSZ_KK;
import static ru.sber.gsz.autotests.dto.gsz.common.GszType.GSZ_MB;
import static ru.sber.gsz.autotests.util.constants.CssAttributes.ERROR_INPUT_HELPER_COLOR;
import static ru.sber.gsz.autotests.util.constants.UUIDPattern.UUID_REGEX;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.randomNumber;

@Getter
public class GszCreateGszUtils<U extends GszCreateGszUtils<U>> {
    private final WebDriver driver;
    private GszCreateFormKK gszCreateFormKK;
    private GszCreateFormLinksInNewGsz gszCreateFormLinksInNewGsz;


    public GszCreateGszUtils(WebDriver driver) {
        this.driver = driver;
        gszCreateFormKK = new GszCreateFormKK(driver);
    }

    public static GszTreeWithOrgDto createGszDto(String gszName, GszStatus gszStatus, GszType type) {
        var gszId = randomUUID();
        return GszTreeWithOrgDto.builder()
                .root(GszCreateDto.builder()
                        .id(gszId)
                        .gszStatus(gszStatus)
                        .gszType(type)
                        .name(gszName)
                        .build())
                .build();
    }

    public static GszTreeWithOrgDto createGszDto(String gszName, GszStatus gszStatus) {
        return createGszDto(gszName, gszStatus, GSZ_KK);
    }

    public static GszTreeWithOrgDto createGszDto(GszStatus gszStatus) {
        return createGszDto(format("test-create-gsz-%d", randomNumber(6)), gszStatus, GSZ_KK);
    }

    public static void deleteOtherOrganizations(GszCreateFormKK gszCreateFormKK) {
        for (int i = gszCreateFormKK.getOtherOrgFilledFields().size(); i > 0; i--) {
            gszCreateFormKK.getOtherOrgFilledFields().get(i - 1).process();
        }
    }

    public U setNewGszName(String gszName) {
        gszCreateFormKK.getHeaderNewNameGszField().input(gszName);
        return (U) this;
    }

    public U setNewGszType(GszType gszType) {
        gszCreateFormKK.getHeaderNewTypeGszField().openDropDownList();
        gszCreateFormKK.getHeaderNewTypeGszField().input(gszType);
        if (gszType.equals(GSZ_MB)) {
            gszCreateFormLinksInNewGsz = new GszCreateFormLinksInNewGsz(driver);
        } else gszCreateFormKK = new GszCreateFormKK(driver);
        return (U) this;
    }

    public U setMainOrgTypeProcess(String string) {
        gszCreateFormKK.getMainOrgField()
                .input(string)
                .process();
        return (U) this;
    }

    public U setMainOrgWithOutTypeProcess(String string) {
        gszCreateFormKK.getMainOrgField()
                .input(string);
        return (U) this;
    }

    public U setMainOrgRandomName() {
        gszCreateFormKK.getMainOrgField()
                .input(valueOf(randomNumber(6)))
                .process();
        return (U) this;
    }

    public U setOtherOrgTypeByProcess(String string) {
        gszCreateFormKK.getOtherOrgField()
                .input(string)
                .process();
        return (U) this;
    }

    public U setOtherOrgWithOutTypeByProcess(String string) {
        gszCreateFormKK.getOtherOrgField()
                .input(string);
        return (U) this;
    }

    public U setOtherOrgsRandomName(int numberRandomName) {
        if (numberRandomName <= 0) throw new IllegalStateException("Кол-во организаций должно быть больше нуля");
        for (int i = 1; i <= numberRandomName; i++) {
            var randomEpkId = valueOf(randomNumber(6));
            gszCreateFormKK.getOtherOrgField()
                    .input(randomEpkId)
                    .process();
        }
        return (U) this;
    }

    public U deleteOrgInMainOrgFieldNewGsz() {
        gszCreateFormKK.getMainOrgField().process();
        return (U) this;
    }

    public U checkCreateGszButtonIsDisabled() {
        assertThat(gszCreateFormKK.isCreateGszButtonEnabled()).isFalse();
        return (U) this;
    }

    public U createGsz() {
        gszCreateFormKK.createGsz();
        return (U) this;
    }

    public U clearForm() {
        gszCreateFormKK.clearForm();
        return (U) this;
    }

    public U deleteMainOrg() {
        gszCreateFormKK.getMainOrgField().process();
        return (U) this;
    }

    public U deleteOtherOrgs() {
        deleteOtherOrganizations(gszCreateFormKK);
        return (U) this;
    }

    public U clearAllFields() {
        gszCreateFormKK.clearForm();
        return (U) this;
    }

    public U assertThatGszMBIsCreate() {
        assertThat(new SuccessAlertDialog(driver).getSuccessAlert().getDescription())
                .matches("Задача " + UUID_REGEX + " успешно создана и назначена на [0-9]{8}");
        return (U) this;
    }

    public <V, I extends Input<V, WebElement>, F extends AbstractField<V, I, F> & InputHelper, G>
    U fieldErrorAsserter(G form, Function<G, F> formGetter, String fieldError) {
        F field = formGetter.apply(form);
        assertThat(fieldError).isEqualTo(field.getInputHelperText());
        assertThat(ERROR_INPUT_HELPER_COLOR).isEqualTo(field.getInputHelperColor());
        assertThat(field.getField().isEnabled()).isTrue();
        return (U) this;
    }

    public <G, F extends Alert>
    U createGszAlertAsserter(G form, Function<G, F> formGetter, String fieldError) {
        F field = formGetter.apply(form);
        assertThat(fieldError).isEqualTo(field.getDescription());
        return (U) this;
    }

    public <V, I extends Input<V, WebElement>, F extends AbstractField<V, I, F>, G>
    U fieldAsserter(G form, Function<G, F> fieldGetter, String label) {
        var field = fieldGetter.apply(form);
        var getText = field.getField().getText();
        assertThat(label).isEqualTo(getText);
        return (U) this;
    }
}
