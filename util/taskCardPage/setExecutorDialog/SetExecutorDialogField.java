package ru.sber.gsz.autotests.util.taskCardPage.setExecutorDialog;

import org.openqa.selenium.WebElement;
import ru.sber.gsz.autotests.util.field.AbstractField;
import ru.sber.gsz.autotests.util.input.TextInput;

public class SetExecutorDialogField extends AbstractField<String, TextInput, SetExecutorDialogField> {

    public SetExecutorDialogField(WebElement field) {
        super(field, TextInput::new);
    }

    @Override
    public String getInputText() {
        return "input-text-executor-dialog";
    }
}
