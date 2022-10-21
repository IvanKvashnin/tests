package ru.sber.gsz.autotests.util.wrappers;

import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.alert.ErrorAlert;
import ru.sber.gsz.autotests.util.alert.SuccessAlert;

import static org.openqa.selenium.By.id;

public interface AlertWrapper extends WebElementWrapper {
    String getAlertId();

    default ErrorAlert getErrorAlert() {
        return new ErrorAlert(getAlert());
    }

    default SuccessAlert getSuccessAlert() {
        return new SuccessAlert(getAlert());
    }

    private WebElement getAlert() {
        return getWebElement().findElement(id(getAlertId()));
    }
}
