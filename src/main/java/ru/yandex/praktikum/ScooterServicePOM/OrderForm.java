package ru.yandex.praktikum.ScooterServicePOM;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    // Кнопка "Далее"
    private final By nextBtnLocator = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    public OrderForm(WebDriver driver) {
        this.driver = driver;
    }

    public void fillOrderForm(String name, String lastName, String address, String metro, String phone) {
        driver.findElement(nameInputLocator).sendKeys(name);
        driver.findElement(lastNameInputLocator).sendKeys(lastName);
        driver.findElement(addressInputLocator).sendKeys(address);
        WebElement webElement = driver.findElement(metroInputLocator);
        webElement.click();
        webElement.sendKeys(metro);
        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.DOWN).perform();
        new WebDriverWait(driver, Duration.ofSeconds(5));
        keyDown.sendKeys(Keys.ENTER).perform();
        new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(phoneInputLocator).sendKeys(phone);
    }

    public void nextBtnClick() {
        WebElement nextBtn = driver.findElement(nextBtnLocator);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", nextBtn);
        nextBtn.click();
    }
}
