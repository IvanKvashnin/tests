package ru.sber.gsz.autotests.util;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface DropDownList<T> {
    T openDropDownList();

    WebElement getDropDownList();

    List<WebElement> getElementFromDropDownList();
}
