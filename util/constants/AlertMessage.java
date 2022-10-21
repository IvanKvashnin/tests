package ru.sber.gsz.autotests.util.constants;

import java.util.function.Function;

public class AlertMessage {
    public static final String ALERT_EPK_ID = "ЕПК ID организации должно состоять из цифр от 1 до 19 символов длиной";
    public static final String ALERT_NO_MASSAGE_AVAILABLE = "No message available";
    public static final Function<String, String> ALERT_GSZ_NAME_ALREADY_EXIST = (gszName -> "ГСЗ с названием " + gszName + " уже существует");
    public static final Function<String, String> ALERT_TASK_NOT_CREATED_GSZ_NAME_ALREADY_EXIST = (gszName ->
            "Задача не создана: ГСЗ с названием " + gszName + " уже существует. " + "Скорректируйте наименование создаваемой ГСЗ и повторите запрос.");
    public static final String ALERT_GSZ_NAME_MUST_BEGIN_WITH_LETTER = "Название ГСЗ должно начинаться с буквы";
    public static final String ALERT_GSZ_NAME_MUST_BE_MORE_THAN_3_CHAR = "Название ГСЗ должно быть 3 или более символов длиной";
    public static final String ALERT_GSZ_NAME_MUST_NOT_START_WITH_SPACE = "Название ГСЗ не должно начинаться с пробела";
    public static final String ALERT_GSZ_NAME_SHOULD_NOT_CONTAIN_SPACE = "В названии ГСЗ не должно быть подряд идущих пробелов";
    public static final String ALERT_GSZ_NAME_CONTAINS_LETTER_UMBER_CHAR_SPACE = "Название ГСЗ может содержать только буквы, цифры, символ пробела или символы .,-_+";
    public static final String ALERT_TASK_NOT_CREATED_GSZ_NAME_CONTAINS_LETTER_UMBER_CHAR_SPACE = "Задача не создана: " +
            "Название ГСЗ может содержать только буквы, цифры, символ пробела или символы .,-_+." +
            " Скорректируйте наименование создаваемой ГСЗ и повторите запрос.";
    public static final String ALERT_EXECUTOR_NOT_AVAILABLE = "Смена исполнителя недоступна";
    public static final String ALERT_TASK_NOT_CREATED_GSZ_NAME_MUST_START_WITH_LETTER = "Задача не создана: Название " +
            "ГСЗ должно начинаться с буквы. Скорректируйте наименование создаваемой ГСЗ и повторите запрос.";
    public static final String ALERT_TASK_NOT_CREATED_GSZ_NAME_MUST_BE_MORE_THAN_3_CHAR = "Задача не создана: Название " +
            "ГСЗ должно быть 3 или более символов длиной. Скорректируйте наименование создаваемой ГСЗ и повторите запрос.";
    public static final String ALERT_TASK_NOT_CREATED_GSZ_NAME_SHOULD_NOT_CONTAIN_SPACE = "Задача не создана: В названии " +
            "ГСЗ не должно быть подряд идущих пробелов. Скорректируйте наименование создаваемой ГСЗ и повторите запрос.";

}
