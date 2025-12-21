import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.GooglePage;
import pages.PobedaPage;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

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
    void testLoadMainPage() {
        String titleText = pobedaPage
                .open()
                .checkLoadLogo()
                .getTitle();
        Assertions.assertEquals("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками", titleText);
    }

    @Test
    void testCheckInfoMenu() {
       pobedaPage
                .open()
                .hoverToInformationButtonMenu();
        assertSoftly(softly -> {
            softly.assertThat(pobedaPage.getTextFromReadyToFlightButton())
                    .isEqualTo("Подготовка к полёту");

            softly.assertThat(pobedaPage.getTextFromUsefulInformationButton())
                    .isEqualTo("Полезная информация");

            softly.assertThat(pobedaPage.getTextFromAboutCompanyButton())
                    .isEqualTo("О компании");
        });
    }

    @Test
    void testVisibleOfSearchPanel() {
        pobedaPage
                .open()
                .scrollToSearchPanel()
                .checkVisibleSearchPanel();
    }

    @Test
    void chekFlkSearchPanel() {
        pobedaPage
                .open()
                .setFromAndToTicketInput("Москва", "Саратов")
                .clickSearchPanelSubmitButton()
                .checkErrorDateFromFindTicketInput();
    }

    @Test
    void testBookingManagement() {
        String errorTxt = pobedaPage
                .open()
                .clickBookingButton()
                .checkVisibleBookingManagementPanel()
                .setSurnameAndBookingNumberInput("Qwerty", "XXXXXX")
                .clickBookingManagementSubmitButton()
                .switchToNewTab()
                .clickCheckBox()
                .clickFindOrderButton()
                .getErrorMessage();
        Assertions.assertEquals("Заказ с указанными параметрами не найден", errorTxt);
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
