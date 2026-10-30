
import jdk.jfr.Description;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.*;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.yandex.praktikum.ScooterServicePOM.MainPage;
import ru.yandex.praktikum.ScooterServicePOM.OrderForm;
import ru.yandex.praktikum.ScooterServicePOM.constants.LeasingDuration;
import ru.yandex.praktikum.ScooterServicePOM.constants.ScooterColor;

import java.util.List;
import java.util.stream.Stream;


public class ScooterServiceSeleniumTests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        //driver = new ChromeDriver(); // Не заработал "Unable to find an exact match for CDP version 156"
        FirefoxOptions options = new FirefoxOptions();
        //options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        //driver = new FirefoxDriver();
    }

    @Test
    public void orderScooter() throws Exception {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        mainPage.clickOrderBtn();
        mainPage.waitForLoadOrderForm();

        OrderForm orderForm = new OrderForm(driver);
        orderForm.fillOrderFirstForm("Евгений", "Ламтюгов",
                "Долгопрудный Дирижабельная 11 кв 15", "Чистрые пруды", "89037840019");
        orderForm.clickNextBtnOnFirstOrderForm();

        orderForm.fillOrderSecondForm("31.10.2026", LeasingDuration.SEVEN_DAYS.getValue(), ScooterColor.BLACK.getValue(), "Позвонить заранее");
    }

    @AfterEach
    public void  tearDown() {
        if (driver != null) {
            //driver.quit();
        }
        else {
            System.out.println("Браузер почему-то закрыт.");
        }
    }
}
