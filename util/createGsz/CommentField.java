package ru.sber.gsz.autotests.util.createGsz;

import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.field.AbstractField;
import ru.sber.gsz.autotests.util.input.InputHelper;
import ru.sber.gsz.autotests.util.input.TextInput;

import static org.openqa.selenium.By.id;

public class CommentField extends AbstractField<String, TextInput, CommentField> implements InputHelper {
    public CommentField(WebElement field) {
        super(field, TextInput::new);
    }

    @Override
    public String getInputText() {
        return getField().findElement(id("new-link-criterion-comment-input")).getAttribute("value");
    }

    @Override
    public WebElement getInputHelper() {
        return getField().findElement(id("new-link-criterion-comment-input-helper-text"));
    }
}
