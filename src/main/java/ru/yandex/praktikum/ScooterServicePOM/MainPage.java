package ru.yandex.praktikum.ScooterServicePOM;

import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainPage {
    private WebDriver driver;

    private String url = "https://qa-scooter.education-services.ru/";

    // Заголовок страницы
    private By headTitleLocator = By.xpath(".//div[@class='Header_Disclaimer__3VEni']");

    // Элемент списока "О Важном"
    private By listOfImportantThingsLocator = By.cssSelector(".accordion__item");

    // Кнопка "Заказать"
    private By orderBtnLocator = By.xpath(".//button[@class='Button_Button__ra12g' and text()='Заказать']");

    // Форма заказа самоката
    private By orderFormLocator = By.xpath(".//div[@class='Order_Content__bmtHS']");

    // Картинка самоката
    private By scooterImgLocator = By.xpath(".//img[@src='/assets/scooter.png']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(url);

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(headTitleLocator));
    }

    public int getListOfImportantThingsSize() {
        return driver.findElements(listOfImportantThingsLocator).size();
    }

    @Description("Возвращает вопрос-ответ из списка О важном")
    public List<String> getListOfImportantThingsItemTexts(String listItemQuestionIDLocatorString) {
        By listItemQuestionLocator = By.id(listItemQuestionIDLocatorString);
        WebElement question = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(listItemQuestionLocator));

        scrollToElement(question);
//       WebElement scooterImg = driver.findElement(scooterImgLocator);
//        System.out.println(scooterImg.findElement(By.xpath("./..")).getTagName());
//        System.out.println(scooterImg.findElement(By.xpath("./..")).isDisplayed());
        question.click();

        By answerLocator = By.xpath(".//div[@aria-labelledby='" + listItemQuestionIDLocatorString + "']/p");
        WebElement answer = driver.findElement(answerLocator);

        return List.of(question.getText(), answer.getText());
    }

    public void clickOrderBtn() {
        driver.findElement(orderBtnLocator).click();
    }

    // метод ожидания прогрузки формы заказа
    public void waitForLoadOrderForm() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(orderFormLocator));
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        new WebDriverWait(driver, Duration.ofNanos(1000));
    }
}
