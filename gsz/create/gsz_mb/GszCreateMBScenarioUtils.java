package ru.sber.gsz.autotests.gsz.create.gsz_mb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import ru.sber.gsz.autotests.gsz.create.GszCreateGszUtils;
import ru.sber.gsz.autotests.util.createGsz.GszCreateFormKK;
import ru.sber.gsz.autotests.util.createGsz.GszCreateFormLinksInNewGsz;
import ru.sber.gsz.autotests.util.createGsz.SuccessAlertDialog;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static ru.sber.gsz.autotests.util.constants.FieldLabels.*;

public class GszCreateMBScenarioUtils extends GszCreateGszUtils<GszCreateMBScenarioUtils> {
    public GszCreateMBScenarioUtils(WebDriver driver) {
        super(driver);
    }

    public GszCreateMBScenarioUtils setLinkMainOrg() {
        var mainOrgNameWithSegment = getGszCreateFormKK().getMainOrgField().getInputWebElement().getAttribute("value");
        getGszCreateFormLinksInNewGsz().getLinkMainOrgField().openDropDownList().input(cutSegment(mainOrgNameWithSegment));
        return this;
    }

    public GszCreateMBScenarioUtils setLinkLinkedOrg(int numberOrgInListOtherOrgs) {
        var listDataToSetToRelatedOrgField = pullDataFromOtherOrgsFieldsAndMatchWithLinkedOrgInputList();
        if (numberOrgInListOtherOrgs > listDataToSetToRelatedOrgField.size() || numberOrgInListOtherOrgs <= 0)
            throw new IllegalStateException("Выбранное значение из списка больше чем длинна списка или явялется отрицательным");

        new Actions(getDriver()).moveToElement(getGszCreateFormLinksInNewGsz().getLinkedOrgField().getField()).perform();
        getGszCreateFormLinksInNewGsz().getLinkedOrgField().input(listDataToSetToRelatedOrgField.get(numberOrgInListOtherOrgs - 1));
        return this;
    }

    public GszCreateMBScenarioUtils setLinkCriterion(String criterionId) {
        new Actions(getDriver()).moveToElement(getGszCreateFormLinksInNewGsz().getCriterionField().getField()).perform();
        getGszCreateFormLinksInNewGsz().getCriterionField().openDropDownList().input(criterionId);
        return this;
    }

    public GszCreateMBScenarioUtils setLinkComment(String text) {
        new Actions(getDriver()).moveToElement(getGszCreateFormLinksInNewGsz().getCommentField().getField()).perform();
        getGszCreateFormLinksInNewGsz().getCommentField().input(text);
        return this;
    }

    public GszCreateMBScenarioUtils setNewLinkToGsz(int numberOrgInListOtherOrgs, String criterionId, String commentText) {
        var mainOrgNameWithSegment = getGszCreateFormKK().getMainOrgField().getInputWebElement().getAttribute("value");
        getGszCreateFormLinksInNewGsz().getLinkMainOrgField().openDropDownList().input(cutSegment(mainOrgNameWithSegment));

        var listDataToSetToRelatedOrgField = pullDataFromOtherOrgsFieldsAndMatchWithLinkedOrgInputList();
        if (numberOrgInListOtherOrgs > listDataToSetToRelatedOrgField.size() || numberOrgInListOtherOrgs <= 0)
            throw new IllegalStateException("Выбранное значение из списка больше чем длинна списка или явялется отрицательным");

        getGszCreateFormLinksInNewGsz().getLinkedOrgField().input(listDataToSetToRelatedOrgField.get(numberOrgInListOtherOrgs - 1));
        getGszCreateFormLinksInNewGsz().getCriterionField().openDropDownList().input(criterionId);
        getGszCreateFormLinksInNewGsz().getCommentField().input(commentText);

        return this;
    }

    public GszCreateMBScenarioUtils addLinkFields() {
        new Actions(getDriver()).moveToElement(getGszCreateFormLinksInNewGsz().getAddButton()).click().perform();
        return this;
    }

    public GszCreateMBScenarioUtils deleteAllLinkFields() {
        var list = getDriver().findElements(id(getGszCreateFormLinksInNewGsz().getDeleteFormButtonId()));
        for (int i = list.size() - 1; i >= 0; i--) {
            list.get(i).click();
        }
        return this;
    }

    public GszCreateMBScenarioUtils checkCreateAndAddButtonsIsDisabled() {
        assertThat(new GszCreateFormLinksInNewGsz(getDriver()).getAddButton().isEnabled()).isFalse();
        assertThat(getGszCreateFormKK().isCreateGszButtonEnabled()).isFalse();
        return this;
    }

    public GszCreateMBScenarioUtils assertThatGszKKIsCreate(String gszName) {
        assertThat(new SuccessAlertDialog(getDriver()).getSuccessAlert().getDescription())
                .matches("ГСЗ " + "\"" + gszName + "\"" + " \\(" +
                        "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}" + "\\) "
                        + "успешно создана"
                );
        return this;
    }

    public GszCreateMBScenarioUtils assertErrorAlertOrgNotLinks() {
        assertThat("Для каждой связанной ораганизации должен быть указан хотя бы один критерий с главной организацией")
                .isEqualTo(getGszCreateFormKK().getErrorAlert().getDescription());
        return this;
    }

    public GszCreateMBScenarioUtils assertDeleteLinksFields() {
        getGszCreateFormLinksInNewGsz().getNewLinkForm().isDisplayed();
        boolean resultLinkListViewForm = getDriver().findElements(id(getGszCreateFormLinksInNewGsz().getLinkListViewFormId())).isEmpty();
        assertTrue("Связи ГСЗ не удалились", resultLinkListViewForm);
        return this;
    }

    public GszCreateMBScenarioUtils assertCreateFormKKIsVisible() {
        getGszCreateFormKK().getCreateForm().isDisplayed();
        return this;
    }

    public GszCreateMBScenarioUtils assertCreateFormMBIsVisible() {
        getGszCreateFormLinksInNewGsz().getCreateFormLinksInNewGsz().isDisplayed();
        return this;
    }

    public GszCreateMBScenarioUtils assertMainOrgFieldInNewGszIsEmpty() {
        assertThat(LABEL_EPK_ID_ORG).isEqualTo(getGszCreateFormKK().getMainOrgField().getField().getText());
        return this;
    }

    public GszCreateMBScenarioUtils assertOtherOrgFieldInNewGszIsEmpty() {
        assertThat(LABEL_EPK_ID_ORG).isEqualTo(getGszCreateFormKK().getOtherOrgField().getField().getText());
        return this;
    }

    public GszCreateMBScenarioUtils assertError1000charsCommentField() {
        assertThat("Длина комментария не должна превышать 1000 символов")
                .isEqualTo(getGszCreateFormLinksInNewGsz().getCommentField().getInputHelper().getText());
        return this;
    }

    public GszCreateMBScenarioUtils assertDropDownListLinkMainOrg() {
        boolean resultLinkMainOrg = new GszCreateFormLinksInNewGsz(getDriver()).getLinkMainOrgField()
                .openDropDownList().getElementFromDropDownList().stream()
                .allMatch(ulElement -> ulElement.getText().equals("Не выбрано"));
        if (!resultLinkMainOrg) throw new IllegalStateException("В дропдауне есть другие организации");
        return this;
    }

    public GszCreateMBScenarioUtils assertDropDownListLinkedOrgIsEmpty() {
        boolean resultLinkedOrg = new GszCreateFormLinksInNewGsz(getDriver()).getLinkedOrgField()
                .openDropDownList().getElementFromDropDownList().stream()
                .allMatch(ulElement -> ulElement.getText().equals("Не выбрано"));
        if (!resultLinkedOrg) throw new IllegalStateException("В дропдауне есть другие организации");
        return this;
    }

    public GszCreateMBScenarioUtils assertFieldsInLinkGszFormAfterSwapMBToKKToMB(String extIdCriterion, String commentText) {
        assertThat(cutSegment(getGszCreateFormKK().getMainOrgField().getInputText()))
                .isEqualTo(cutSegment(getGszCreateFormLinksInNewGsz().getLinkMainOrgField().getInputText()));
        assertThat(cutSegment(getGszCreateFormKK().getOtherOrgField().getInputText()))
                .isEqualTo(cutSegment(getGszCreateFormLinksInNewGsz().getLinkedOrgField().getInputText()));
        assertThat(extIdCriterion).isEqualTo(getGszCreateFormLinksInNewGsz().getCriterionField().getInputText());
        assertThat(commentText).isEqualTo(getGszCreateFormLinksInNewGsz().getCommentField().getInputText());
        return this;
    }

    public GszCreateMBScenarioUtils assertErrorAlertUsedGszName(String errorAlert) {
        assertThat(errorAlert)
                .isEqualTo(getGszCreateFormKK().getErrorAlert().getDescription());
        return this;
    }

    private String cutSegment(String orgNameWithSegment) {
        return orgNameWithSegment.replace(" ", "")
                .substring(0, orgNameWithSegment.lastIndexOf("(") - 1);
    }

    private List<String> pullDataFromOtherOrgsFieldsAndMatchWithLinkedOrgInputList() {
        getGszCreateFormLinksInNewGsz().getLinkedOrgField().openDropDownList();
        return getGszCreateFormKK().getOtherOrgFilledFields().stream()
                .map(otherOrgField -> cutSegment(otherOrgField.getInputWebElement().getAttribute("value")))
                .filter(attribute -> getDriver().findElement(xpath("//*[text()='" + attribute + "']")).isDisplayed())
                .collect(toUnmodifiableList());
    }

    public GszCreateMBScenarioUtils assertAllFieldsAreEmptyAfterClearButton() {
        fieldAsserter(getGszCreateFormKK(), GszCreateFormKK::getHeaderNewNameGszField, LABEL_NEW_GSZ_NAME);
        fieldAsserter(getGszCreateFormKK(), GszCreateFormKK::getHeaderNewTypeGszField, LABEL_NEW_GSZ_TYPE);
        fieldAsserter(getGszCreateFormKK(), GszCreateFormKK::getMainOrgField, LABEL_EPK_ID_ORG);
        fieldAsserter(getGszCreateFormKK(), GszCreateFormKK::getOtherOrgField, LABEL_EPK_ID_ORG);
        return this;
    }

    public GszCreateMBScenarioUtils assertFieldsInLinkGszFormIsEmpty() {
        fieldAsserter(getGszCreateFormLinksInNewGsz(),
                GszCreateFormLinksInNewGsz::getLinkMainOrgField, LABEL_MAIN_ORG);
        fieldAsserter(getGszCreateFormLinksInNewGsz(),
                GszCreateFormLinksInNewGsz::getLinkedOrgField, LABEL_LINKED_ORG);
        fieldAsserter(getGszCreateFormLinksInNewGsz(),
                GszCreateFormLinksInNewGsz::getCriterionField, LABEL_CRITERION);
        fieldAsserter(getGszCreateFormLinksInNewGsz(),
                GszCreateFormLinksInNewGsz::getCommentField, LABEL_COMMENT);
        return this;
    }

    public GszCreateMBScenarioUtils assertNewNameGszError(String fieldError) {
        createGszAlertAsserter(getGszCreateFormKK(), GszCreateFormKK::getErrorAlert, fieldError);
        return this;
    }

    public GszCreateMBScenarioUtils assertOtherOrgFieldError(String fieldError) {
        fieldErrorAsserter(getGszCreateFormKK(), GszCreateFormKK::getOtherOrgField, fieldError);
        return this;
    }

    public GszCreateMBScenarioUtils assertMainOrgFieldError(String fieldError) {
        fieldErrorAsserter(getGszCreateFormKK(), GszCreateFormKK::getMainOrgField, fieldError);
        return this;
    }
}

