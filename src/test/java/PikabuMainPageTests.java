import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.PikabuMainPage;

import java.time.Duration;

public class PikabuMainPageTests {

    private static WebDriver driver;
    private static PikabuMainPage pikabuMainPage;

    @BeforeAll
    static void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        pikabuMainPage = new PikabuMainPage(driver);
    }

    @Test
    void openMainPageTest() {
        String titleText = pikabuMainPage
                .open()
                .getTextFromHeaderTitle();
        Assertions.assertEquals("Горячее – самые интересные и обсуждаемые посты | Пикабу", titleText);
    }

    @Test
    void openMainPageTest2() {
        String errorText = pikabuMainPage.open()
                .clickLoginButtonRightMenu()
                .checkVisibleLoginPopUp()
                .checkVisibleLoginInputFromLoginPopUp()
                .checkVisiblePasswordInputFromLoginPopUp()
                .checkVisibleSubmitButtonInputFromLoginPopUp()
                .enterTextInLoginInputFromLoginPopUp("Qwerty")
                .enterTextInPasswordInputFromLoginPopUp("Qwerty")
                .clickSubmitButtonFromLoginPopUp()
                .getErrorTextFromLoginPopUp();
        Assertions.assertEquals("Ошибка. Вы ввели неверные данные авторизации", errorText);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}