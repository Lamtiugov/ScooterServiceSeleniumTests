package ru.yandex.praktikum.ScooterServicePOM.constants;

public enum ScooterColor {
    BLACK("black"),
    GREY("grey");

    private String value;
    private ScooterColor(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
