import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.GooglePage;
import pages.PobedaPage;

public class PobedaTests {

    private static WebDriver driver;
    private static PobedaPage pobedaPage;
    private static GooglePage googlePage;

    @BeforeAll
    static void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        pobedaPage = new PobedaPage(driver);
        googlePage = new GooglePage(driver);
    }


    @Test
    void test() {
        googlePage.open()
                .clickGoogleSearchInput()
                .searchInGoogle("Сайт компании Победа")
                .clickPobedaWebSite();
        pobedaPage
                .waitUntilSlideIsActive("Полетели в Калининград!")
                .clickLanguageButton()
                .clickEnglishLanguage()
                .checkTextInPage("Ticket search")
                .checkTextInPage("Online check-in")
                .checkTextInPage("Manage my booking");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
