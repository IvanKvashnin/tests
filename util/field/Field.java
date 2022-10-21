package ru.sber.gsz.autotests.util.field;

import ru.sber.gsz.autotests.util.wrappers.WebElementWrapper;

public interface Field<V, F extends Field<V, F>> extends WebElementWrapper {
    F input(V value);
}
