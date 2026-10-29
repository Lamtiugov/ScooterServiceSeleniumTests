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

public class MainPage {
    private final WebDriver driver;

    private final String url = "https://qa-scooter.education-services.ru/";

    private final By headTitleLocator = By.xpath(".//div[@class='Header_Disclaimer__3VEni']");
    private final By listOfImportantThingsLocator = By.cssSelector(".accordion__item");
    private final By orderScooterBtnLocator = By.xpath(".//button[@class='Button_Button__ra12g' and text()='Заказать']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(this.url);

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(headTitleLocator));
    }

    public int getListOfImportantThingsSize() {
        return driver.findElements(listOfImportantThingsLocator).size();
    }

    @Description("Возвращает вопрос-ответ из списка О важном")
    public List<String> getListOfImportantThingsItemTexts(String listItemQuestionIDLocator) {
        WebElement question = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id(listItemQuestionIDLocator)));

        scrollToElement(question);

        // Element <div id="accordion__heading-5" class="accordion__button"> is not clickable at point (1035,343) because another element <img src="/assets/scooter.png"> obscures it
        // При этом isDisplayed == true
        System.out.println(question.isDisplayed()); // Не ясно, толи elementToBeClickable не срабатывает, толи скролл

        question.click();
        String answer = driver.findElement(
                By.xpath(".//div[@aria-labelledby='" + listItemQuestionIDLocator + "']/p"))
                .getText();
        return List.of(question.getText(), answer);
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        new WebDriverWait(driver, Duration.ofSeconds(2));
    }
}
