package ru.yandex.praktikum.ScooterServicePOM;

import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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
        WebElement question = driver.findElement(listItemQuestionLocator);

        scrollToElement(question);

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(question));
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
    }
}
