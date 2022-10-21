package ru.sber.gsz.autotests.util.diaog;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.SeleniumUtils;
import ru.sber.gsz.autotests.util.wrappers.WebElementWrapper;

public class Dialog implements WebElementWrapper {
    protected final WebElement dialogWindow;

    private Dialog() {
        dialogWindow = null;
    }

    public Dialog(WebDriver driver, String dialogId) {
        dialogWindow = SeleniumUtils.waitUntilElementIsVisibleById(driver, dialogId);
        if (!dialogWindow.isDisplayed()) throw new IllegalStateException("Модальное окно не отобразилось");
    }

    @Override
    public WebElement getWebElement() {
        return dialogWindow;
    }
}





