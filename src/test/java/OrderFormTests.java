import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.yandex.praktikum.ScooterServicePOM.MainPage;
import ru.yandex.praktikum.ScooterServicePOM.OrderForm;
import ru.yandex.praktikum.ScooterServicePOM.constants.LeasingDuration;
import ru.yandex.praktikum.ScooterServicePOM.constants.ScooterColor;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class OrderFormTests {
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

    @AfterEach
    public void  tearDown() {
        if (driver != null) {
            //driver.quit();
        }
        else {
            System.out.println("Браузер почему-то закрыт.");
        }
    }

    @Description("Позитивный тест заказа самоката. Только валидные значения полей.")
    @ParameterizedTest
    @MethodSource("validOrderFields")
    public void orderScooter(String name, String surname, String address, String metro, String phone,
                            String date, String leasingDuration, String scooterColor, String orderComment) {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        mainPage.clickOrderBtn();
        mainPage.waitForLoadOrderForm();

        OrderForm orderForm = new OrderForm(driver);
        orderForm.fillOrderFirstForm(name, surname, address, metro, phone);
        orderForm.clickNextBtnOnFirstOrderForm();

        orderForm.fillOrderSecondForm(date, leasingDuration, scooterColor, orderComment);
        orderForm.clickOrderBtn();
        orderForm.clickConfirmOrderBtn();

        assertTrue(orderForm.isOrderCreated(), "Заказ с правильными полями не получилось создать");
    }

    @Description("Негативный сценарий для пустого поля Дата доставки")
    @Test
    public void emptyDateDoesNotAllowToMakeOrder() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        mainPage.clickOrderBtn();
        mainPage.waitForLoadOrderForm();

        OrderForm orderForm = new OrderForm(driver);
        orderForm.fillOrderFirstForm("Евгений", "Ламтюгов", "Долгопрудный Дирижабельная 11 кв 15",
                "Чистрые пруды", "89037840019");
        orderForm.clickNextBtnOnFirstOrderForm();

        orderForm.fillOrderSecondForm("", LeasingDuration.SEVEN_DAYS.getValue(), ScooterColor.BLACK.getValue(), "Позвонить заранее");
        orderForm.clickOrderBtn();
        try {
            orderForm.clickConfirmOrderBtn();
        } catch (Exception e) {
            // Повторяю полностью позитивный сценарий, поэтому приходится глушить исключение
        }

        assertFalse(orderForm.isOrderCreated(), "Заказ с пустым полем даты доставки получилось создать");
    }

    @Description("Проверем, что для пустого поля Имя выдается сообщение 'Введите корректное имя'")
    @Test
    public void shouldValidateNameMsgShows() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickOrderBtn();

        OrderForm orderForm = new OrderForm(driver);
        orderForm.clickNextBtnOnFirstOrderForm();

        assertTrue(orderForm.isValidateNameMsgShowed(), "Ожидалось, что покажется сообщени о проверке поля Имя");
    }

    @Description("Проверем, что для пустого поля Фамили выдается сообщение 'Введите корректную фамилию'")
    @Test
    public void shouldValidateSurnameMsgShows() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickOrderBtn();

        OrderForm orderForm = new OrderForm(driver);
        orderForm.clickNextBtnOnFirstOrderForm();

        assertTrue(orderForm.isValidateSurnameMsgShowed(), "Ожидалось, что покажется сообщени о проверке поля Фамилия");
    }

    @Description("Проверем, что для пустого поля Метро выдается сообщение 'Выберите станцию'")
    @Test
    public void shouldValidateMetroMsgShows() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickOrderBtn();

        OrderForm orderForm = new OrderForm(driver);
        orderForm.clickNextBtnOnFirstOrderForm();

        assertTrue(orderForm.isValidateMetroMsgShowed(), "Ожидалось, что покажется сообщени о проверке поля Метро");
    }

    @Description("Проверем, что для пустого поля Телефон выдается сообщение 'Введите корректный номер'")
    @Test
    public void shouldValidatePhoneMsgShows() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickOrderBtn();

        OrderForm orderForm = new OrderForm(driver);
        orderForm.clickNextBtnOnFirstOrderForm();

        assertTrue(orderForm.isValidatePhoneMsgShowed(), "Ожидалось, что покажется сообщени о проверке поля Телефон");
    }

    @Description("Если нажать на логотип «Самоката», попадёшь на главную страницу «Самоката»")
    @Test
    public void scooterLogoDoesToMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickOrderBtn();

        OrderForm orderForm = new OrderForm(driver);
        orderForm.clickScooterLogoLink();

        assertEquals(mainPage.getUrl(), driver.getCurrentUrl(), "Ожидался переход на главнужю страницу");
    }

    @Description("Значения полей для успешного заказа")
    static Stream<Arguments> validOrderFields() {
        return Stream.of(
                Arguments.of("Евгений", "Ламтюгов", "Долгопрудный Дирижабельная 11 кв 15", "Чистрые пруды", "89037840019",
                        "31.10.2026", LeasingDuration.SEVEN_DAYS.getValue(), ScooterColor.BLACK.getValue(), "Позвонить заранее"),
                Arguments.of("Алёна", "Ламтюгова", "Долгопрудный Дирижабельная 11 кв 15", "Алтуфьево", "+79037840019",
                        "10.11.2026", LeasingDuration.TWO_DAYS.getValue(), ScooterColor.GREY.getValue(), "Звонить с 14:30 до 21:00")
        );
    }
}
