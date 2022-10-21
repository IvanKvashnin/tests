package ru.sber.gsz.autotests.util.alert;

import org.openqa.selenium.WebElement;

import static ru.sber.gsz.autotests.util.constants.CssAttributes.BACK_GROUND_COLOR;
import static ru.sber.gsz.autotests.util.constants.CssAttributes.SUCCESS_ALERT_COLOR;

public class SuccessAlert extends Alert {
    public SuccessAlert(WebElement alert) {
        super(alert);
        if (!alert.getCssValue(BACK_GROUND_COLOR).equals(SUCCESS_ALERT_COLOR))
            throw new IllegalStateException("Цвет \"Success-Alert'a\" должен быть зеленый");
    }
}
