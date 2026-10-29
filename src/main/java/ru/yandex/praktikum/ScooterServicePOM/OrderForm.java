package ru.yandex.praktikum.ScooterServicePOM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderForm {

    private WebDriver driver;

    // Поле "Имя"
    private final By nameInputLocator = By.xpath(".//input[@placeholder='* Имя']");
    // Поле "Фамилия"
    private final By lastNameInputLocator = By.xpath(".//input[@placeholder='* Фамилия']");
    // Поле "Адрес"
    private final By addressInputLocator = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    // Поле "Станция Метро"
    private final By metroInputLocator = By.xpath(".//input[@placeholder='* Станция метро']");
    // Поле "Телефон"
    private final By phoneInputLocator = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка "Заказать"
    private final By orderScooterBtnLocator = By.xpath(".//button[@class='Button_Button__ra12g' and text()='Заказать']");

    public OrderForm(WebDriver driver) {
        this.driver = driver;
    }

    public void fillOrderForm(String name, String lastName, String address, String metro, String phone) {
        driver.findElement(nameInputLocator).sendKeys(name);
        driver.findElement(lastNameInputLocator).sendKeys(lastName);
        driver.findElement(addressInputLocator).sendKeys(address);
        driver.findElement(metroInputLocator).sendKeys(metro);
        driver.findElement(phoneInputLocator).sendKeys(phone);
    }

    public void orderBtnClick() {

    }
}
