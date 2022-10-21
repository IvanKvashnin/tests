package ru.sber.gsz.autotests.util.field;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import ru.sber.gsz.autotests.util.input.Input;
import ru.sber.gsz.autotests.util.wrappers.WebElementWrapper;

import java.util.function.Function;

import static org.openqa.selenium.By.tagName;
import static ru.sber.gsz.autotests.util.SeleniumUtils.waitUntilElementIsClickable;

@Getter
public abstract class AbstractField<V, I extends Input<V, WebElement>, F extends AbstractField<V, I, F>> implements Field<V, F>, WebElementWrapper {
    private final WebDriver driver;
    private final WebElement inputWebElement;
    private final WebElement field;
    private final I input;

    public AbstractField(WebElement field, Function<WebElement, I> inputGetter) {
        this.field = field;
        driver = ((WrapsDriver) field).getWrappedDriver();
        inputWebElement = field.findElement(tagName("input"));
        input = inputGetter.apply(field.findElement(tagName("input")));
    }

    public F input(V value) {
        waitUntilElementIsClickable(driver, field);
        input.processInput(value);
        return (F) this;
    }

    @Override
    public WebElement getWebElement() {
        return getField();
    }

    public abstract String getInputText();
}
