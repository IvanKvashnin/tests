package ru.sber.gsz.autotests.util.createGsz;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ru.sber.gsz.autotests.util.wrappers.AlertWrapper;
import ru.sber.gsz.autotests.util.wrappers.WebElementWrapper;

import java.util.List;

import static java.time.Duration.ofMillis;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.openqa.selenium.By.id;

@Getter
public class GszCreateFormKK implements WebElementWrapper, AlertWrapper {
    private final WebDriver driver;
    private final WebElement createForm;
    private final WebElement createButton;
    private final WebElement clearButton;
    private final HeaderNewNameGszField headerNewNameGszField;
    private final HeaderNewTypeGszField headerNewTypeGszField;
    private final MainOrganizationField mainOrgField;
    private final OtherOrganizationField otherOrgField;


    public GszCreateFormKK(WebDriver driver) {
        this.driver = driver;
        createForm = driver.findElement(id("gsz-create-form"));
        createButton = createForm.findElement(id("create-gsz-btn"));
        clearButton = createForm.findElement(id("clear-btn"));
        headerNewNameGszField = new HeaderNewNameGszField(createForm.findElement(id("new-gsz-name-input")));
        headerNewTypeGszField = new HeaderNewTypeGszField(createForm.findElement(id("new-gsz-type-select")));
        mainOrgField = new MainOrganizationField(createForm.findElement(id("main-org-input")));
        otherOrgField = new OtherOrganizationField(createForm.findElement(id("other-orgs-form")));
        if (!createForm.isDisplayed()) throw new IllegalStateException("Форма создания ГСЗ не отобразилась");
    }

    public boolean isCreateGszButtonEnabled() {
        return createButton.isEnabled();
    }

    public void createGsz() {
        new Actions(driver).pause(ofMillis(1)).moveToElement(createButton).click(createButton).build().perform();
    }

    public void clearForm() {
        clearButton.click();
    }

    @Override
    public String getAlertId() {
        return "gsz-create-error-alert";
    }

    @Override
    public WebElement getWebElement() {
        return createForm;
    }

    public List<OtherOrganizationField> getOtherOrgFilledFields() {
        return createForm.findElements(id("other-org-input")).stream()
                .map(OtherOrganizationField::new)
                .collect(toUnmodifiableList());
    }
}
