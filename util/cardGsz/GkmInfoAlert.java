package ru.sber.gsz.autotests.util.cardGsz;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.alert.InfoAlert;

import static org.openqa.selenium.By.id;

@Getter
public class GkmInfoAlert extends InfoAlert {
    private final String fullName;

    public GkmInfoAlert(WebElement alert) {
        super(alert);
        fullName = alert.findElement(id("gsz-gkm-infoAlert-fullName")).getText();
    }
}
