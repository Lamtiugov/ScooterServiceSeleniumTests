package ru.yandex.praktikum.ScooterServicePOM;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderForm {

    private WebDriver driver;

    // Первая форма заказа
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

    // Вторая форма заказа
    // Дата доставки
    private final By deliveryDateInputLocator = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    // Поле срок аренды. Стрелка.
    private final By leaseInputArrowLocator = By.xpath(".//div[@class='Dropdown-arrow-wrapper']");
    // Поле срок аренды. Список значений.
    private final By listLeasingDurations = By.xpath(".//div[@class='Dropdown-menu']");
    // Цвет самоката. "Чёрный жемчуг"
    private final By blackScooterInputLocator = By.id("black");
    // Цвет самоката. "серая безысходность"
    private final By greyScooterInputLocator = By.id("grey");
    // Комментарий для курьера
    private final By comment4CourierInputLocator = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    // Кнопка "Заказать"
    private final By orderBtnLocator = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");


    public OrderForm(WebDriver driver) {
        this.driver = driver;
    }

    public void fillOrderFirstForm(String name, String lastName, String address, String metro, String phone) {
        driver.findElement(nameInputLocator).sendKeys(name);
        driver.findElement(lastNameInputLocator).sendKeys(lastName);
        driver.findElement(addressInputLocator).sendKeys(address);

        // Заполняем трёхнутое поле Метро, которое почему-то не Select. Видимо красота требует жертв.
        WebElement metroInput = driver.findElement(metroInputLocator);
        metroInput.click();
        metroInput.sendKeys(metro);
        Actions keyAction = new Actions(driver);
        keyAction.sendKeys(Keys.DOWN).perform();
        keyAction.sendKeys(Keys.ENTER).perform();

        driver.findElement(phoneInputLocator).sendKeys(phone);
    }

    public void clickNextBtnOnFirstOrderForm() {
        WebElement nextBtn = driver.findElement(nextBtnLocator);
        scrollToElement(nextBtn);
        nextBtn.click();
    }

    public void fillOrderSecondForm(String deliveryDate) {
        driver.findElement(deliveryDateInputLocator).sendKeys(deliveryDate);
        Actions keyAction = new Actions(driver);
        keyAction.sendKeys(Keys.ENTER).perform();
    }

    // Можно в super вынести
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
    }
}

