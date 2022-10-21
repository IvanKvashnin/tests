package ru.sber.gsz.autotests.util.view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.By.id;
import static ru.sber.gsz.autotests.util.button.Button.buttonByName;
import static ru.sber.gsz.autotests.util.input.TextInput.textInput;

public class SearchItemView {
    private final WebDriver driver;
    private final WebElement pageBlock;

    public static SearchItemView searchItemView(WebDriver driver, String id) {
        return new SearchItemView(driver, id);
    }

    public SearchItemView(WebDriver driver, String id) {
        this.driver = driver;
        pageBlock = driver.findElement(id(id));
    }

    public void findSearchInputElementByIdAndProcess(String id, String processInput) {
        textInput(driver.findElement(id(id))).processInput(processInput);
    }

    public void findSearchButtonAndClick(String buttonName) {
        buttonByName(driver, buttonName).click();
    }
}
