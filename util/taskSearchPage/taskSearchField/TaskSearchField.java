package ru.sber.gsz.autotests.util.taskSearchPage.taskSearchField;

import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.field.AbstractField;
import ru.sber.gsz.autotests.util.input.TextInput;

public class TaskSearchField extends AbstractField<String, TextInput, TaskSearchField> {
    public static TaskSearchField taskSearchField(WebElement field){
        return new TaskSearchField(field);
    }

    public TaskSearchField(WebElement field) {
        super(field, TextInput::new);
    }

    @Override
    public String getInputText() {
        return getInputWebElement().getCssValue("value");
    }
}
