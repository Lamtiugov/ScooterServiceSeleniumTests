package ru.yandex.praktikum.ScooterServicePOM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderStatePage {
    private WebDriver driver;

    // Сообщение, что заказ не найден
    private By notFoundMsgLocator = By.xpath("//img[@alt='Not found']");

    public OrderStatePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isValidOrderNumber() {
        return driver.findElements(notFoundMsgLocator).isEmpty();
    }
}
