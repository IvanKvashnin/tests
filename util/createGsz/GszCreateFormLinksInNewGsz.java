package ru.sber.gsz.autotests.util.createGsz;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.By.id;
import static ru.sber.gsz.autotests.util.SeleniumUtils.waitUntilElementIsVisible;

@Getter
public class GszCreateFormLinksInNewGsz {
    private final WebElement createFormLinksInNewGsz;
    private final LinkMainOrgField linkMainOrgField;
    private final LinkedOrgField linkedOrgField;
    private final CriterionField criterionField;
    private final CommentField commentField;
    private final WebElement newLinkForm;
    private final WebElement addButton;
    private final String deleteFormButtonId;
    private final String linkListViewFormId;

    public GszCreateFormLinksInNewGsz(WebDriver driver) {
        createFormLinksInNewGsz = waitUntilElementIsVisible(driver, driver.findElement(id("create-form-link-new-gsz")));
        linkMainOrgField = new LinkMainOrgField(createFormLinksInNewGsz.findElement(id("new-link-main-org-field")));
        linkedOrgField = new LinkedOrgField(createFormLinksInNewGsz.findElement(id("new-link-sa-org-field")));
        criterionField = new CriterionField(createFormLinksInNewGsz.findElement(id("new-link-crit-field")));
        commentField = new CommentField(createFormLinksInNewGsz.findElement(id("new-link-comment-field")));
        newLinkForm = createFormLinksInNewGsz.findElement(id("new-link-form"));
        addButton = createFormLinksInNewGsz.findElement(id("add-link-criterion-btn"));
        deleteFormButtonId = "link-delete-button";
        linkListViewFormId = "link-list-view-form";
        if (!(createFormLinksInNewGsz.isDisplayed()))
            throw new IllegalStateException("Форма созданя ГСЗ не отобразилась");
    }
}
