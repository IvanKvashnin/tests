package ru.sber.gsz.autotests.util.alert;

import org.openqa.selenium.WebElement;

import static ru.sber.gsz.autotests.util.constants.CssAttributes.BACK_GROUND_COLOR;
import static ru.sber.gsz.autotests.util.constants.CssAttributes.INFO_ALERT_COLOR;

public class InfoAlert extends Alert {
    public InfoAlert(WebElement alert) {
        super(alert);
        if (!alert.getCssValue(BACK_GROUND_COLOR).equals(INFO_ALERT_COLOR))
            throw new IllegalStateException("Цвет \"Info-Alert'a\" должен быть синий");
    }
}
