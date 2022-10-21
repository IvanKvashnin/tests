package ru.sber.gsz.autotests.util.input;

public interface Input<T, W> {
    W processInput(T value);
}