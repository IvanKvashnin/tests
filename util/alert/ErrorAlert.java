package ru.sber.gsz.autotests.util.alert;

import lombok.Getter;
import org.openqa.selenium.WebElement;

import static ru.sber.gsz.autotests.util.constants.CssAttributes.BACK_GROUND_COLOR;
import static ru.sber.gsz.autotests.util.constants.CssAttributes.ERROR_ALERT_COLOR;

@Getter
public class ErrorAlert extends Alert {
    public ErrorAlert(WebElement alert) {
        super(alert);
        if (!alert.getCssValue(BACK_GROUND_COLOR).equals(ERROR_ALERT_COLOR))
            throw new IllegalStateException("Цвет \"Error-Alert'a\" должен быть красный");
    }

}
