
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.ScooterServicePOM.MainPage;

import java.awt.*;
import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScooterServiceSeleniumTests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        //driver = new ChromeDriver(); // Не заработал "Unable to find an exact match for CDP version 156"
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new FirefoxDriver(options);
    }

    @ParameterizedTest
    @MethodSource("contentOfImportantListOfThings")
    public void contentOfImportantListOfThingsDoesNotChanged(String listItemQuestionLocator, List<String> expected) throws InterruptedException {
        MainPage mainPage = new MainPage(driver);

        mainPage.open();

        List<String> actualResult = mainPage.getListOfImportantThingsItemTexts(listItemQuestionLocator);
        assertEquals(expected, actualResult, "Текст вопроса или ответа поменялся");
    }

    static Stream<Arguments> contentOfImportantListOfThings() {
        return Stream.of(
                Arguments.of("accordion__heading-0",
                        List.of(
                                "Сколько это стоит? И как оплатить?",
                                "Сутки — 400 рублей. Оплата курьеру — наличными или картой."
                        )),
                Arguments.of("accordion__heading-1",
                        List.of(
                                "Хочу сразу несколько самокатов! Так можно?",
                                "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."
                        )),
                Arguments.of("accordion__heading-2",
                        List.of(
                                "Как рассчитывается время аренды?",
                                "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."
                        )),
                Arguments.of("accordion__heading-3",
                        List.of(
                                "Можно ли заказать самокат прямо на сегодня?",
                                "Только начиная с завтрашнего дня. Но скоро станем расторопнее."
                        )),
                Arguments.of("accordion__heading-4",
                        List.of(
                                "Можно ли продлить заказ или вернуть самокат раньше?",
                                "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."
                        )),
                Arguments.of("accordion__heading-5",
                        List.of(
                                "Вы привозите зарядку вместе с самокатом?",
                                "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."
                        )),
                Arguments.of("accordion__heading-6",
                        List.of(
                                "Можно ли отменить заказ?",
                                "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."
                        )),
                Arguments.of("accordion__heading-7",
                        List.of(
                                "Я жизу за МКАДом, привезёте?",
                                "Да, обязательно. Всем самокатов! И Москве, и Московской области."
                        ))
        );
    }

    @AfterEach
    public void  tearDown() {
        if (driver != null) {
            driver.quit();
        }
        else {
            System.out.println("Бразер почему-то закрыт.");
        }
    }
}
