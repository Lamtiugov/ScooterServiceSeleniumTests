package ru.yandex.praktikum.ScooterServicePOM;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderForm {

    private WebDriver driver;
    private boolean isOrderCreated;

    // Первая форма заказа
    // Поле "Имя"
    private By nameInputLocator = By.xpath(".//input[@placeholder='* Имя']");
    // Поле "Фамилия"
    private By lastNameInputLocator = By.xpath(".//input[@placeholder='* Фамилия']");
    // Поле "Адрес"
    private By addressInputLocator = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    // Поле "Станция Метро"
    private By metroInputLocator = By.xpath(".//input[@placeholder='* Станция метро']");
    // Поле "Телефон"
    private By phoneInputLocator = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    // Кнопка "Далее"
    private By nextBtnLocator = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    // Вторая форма заказа
    // Дата доставки
    private By deliveryDateInputLocator = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    // Поле срок аренды
    private By leaseDurationLocator = By.className("Dropdown-root");
    // Поле срок аренды. Список значений.
    private By listLeasingDurations = By.className("Dropdown-menu");
    // Цвет самоката. "Чёрный жемчуг"
    private By blackScooterInputLocator = By.id("black");
    // Цвет самоката. "серая безысходность"
    private By greyScooterInputLocator = By.id("grey");
    // Комментарий для курьера
    private By comment4CourierInputLocator = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    // Кнопка "Заказать"
    private By orderBtnLocator = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    // Кнопка подтверждения заказа
    private By confirmOrderLocator = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Да']");
    // Сообщение об успешном оформлении заказа
    private By orderIsConfirmedLocator = By.xpath(".//div[@class='Order_ModalHeader__3FDaJ' and starts-with(text(), 'Заказ оформлен')]");
    // Кнопка посмотреть статус заказа
    private By showOrderStateBtnLocator = By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Посмотреть статус']");

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

    public void fillOrderSecondForm(String deliveryDate, String leasingDuration, String scooterColor, String commentText) {

        if (!deliveryDate.isEmpty()) {
            driver.findElement(deliveryDateInputLocator).sendKeys(deliveryDate);
            Actions keyAction = new Actions(driver);
            keyAction.sendKeys(Keys.ENTER).perform();

            driver.findElement(leaseDurationLocator).click();


            By leasingDurationValueLocator = By.xpath(".//div[@class='Dropdown-option' and text()='" + leasingDuration + "']");
            WebElement leasingDurationValue = driver.findElement(leasingDurationValueLocator);
            scrollToElement(leasingDurationValue);
            leasingDurationValue.click();
        }

        if (scooterColor.equals("black")) {
            driver.findElement(blackScooterInputLocator).click();
        } else if (scooterColor.equals("grey")) {
            driver.findElement(greyScooterInputLocator).click();
        }

        driver.findElement(comment4CourierInputLocator).sendKeys(commentText);
    }

    public void clickOrderBtn() {
        driver.findElement(orderBtnLocator).click();
    }

    public void clickConfirmOrderBtn() {
        driver.findElement(confirmOrderLocator).click();
        if (driver.findElement(orderIsConfirmedLocator).isEnabled()) {
            this.isOrderCreated = true;
        }
    }



    public boolean isOrderCreated() {
        return isOrderCreated;
    }

    // Можно в super вынести наверное
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
    }
}

