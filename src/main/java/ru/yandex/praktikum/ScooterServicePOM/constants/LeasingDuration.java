package ru.yandex.praktikum.ScooterServicePOM.constants;

import jdk.jfr.Description;

@Description("Список длительности аренды для заказа")
public enum LeasingDuration {
    DAY("сутки"),
    TWO_DAYS("двое суток"),
    TREE_DAYS("трое суток"),
    FOUR_DAYS("четверо суток"),
    FIVE_DAYS("пятеро суток"),
    SIX_DAYS("шестеро суток"),
    SEVEN_DAYS("семеро суток");

    private final String value;

    private LeasingDuration(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
