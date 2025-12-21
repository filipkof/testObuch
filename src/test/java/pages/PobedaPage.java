package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import static org.awaitility.Awaitility.await;

public class PobedaPage {
    private final WebDriverWait wait;
    private final WebDriver driver;


    public PobedaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By languageButton = By.cssSelector(".dp-1qw5me8-root-root");
    private final By englishLanguage = By.xpath("//div[text()='English']");
    private final By slides = By.cssSelector("button.dp-12jpsjh-root");
    private final By activeBullet = By.cssSelector("button.dp-19vkeuu-bullet[data-active='true']");
    private final By pobedaLogo = By.xpath("//img[contains(@src,'logo-rus-white')]");
    private final By informationButtonMenu = By.xpath("//a[@href='/information']");
    private final By readyToFlightButtonInInformationButtonMenu = By.xpath("//a[@href='/information#flight']");
    private final By usefulInformationButtonInInformationButtonMenu = By.xpath("//a[@href='/information#useful']");
    private final By aboutCompanyButtonInInformationButtonMenu = By.xpath("//a[@href='/information#company']");


    public PobedaPage open() {
        driver.get("https://www.flypobeda.ru/");
        wait.until(ExpectedConditions.urlContains("flypobeda.ru"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(languageButton));
        return this;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getTextFromReadyToFlightButton() {
        return getTextFromElement(readyToFlightButtonInInformationButtonMenu);
    }

    public String getTextFromUsefulInformationButton() {
        return getTextFromElement(usefulInformationButtonInInformationButtonMenu);
    }

    public String getTextFromAboutCompanyButton() {
        return getTextFromElement(aboutCompanyButtonInInformationButtonMenu);
    }

    public PobedaPage hoverToInformationButtonMenu() {
        WebElement wd = wait.until(
                ExpectedConditions.visibilityOfElementLocated(informationButtonMenu)
        );

        new Actions(driver).moveToElement(wd).perform();
        return this;
    }

    public PobedaPage checkLoadLogo() {
        await()
                .atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofMillis(500))
                .until(() -> !driver.findElements(pobedaLogo).isEmpty());
        return this;
    }

    public PobedaPage clickLanguageButton() {
        wait.until(ExpectedConditions.elementToBeClickable(languageButton))
                .click();
        return this;
    }

    public PobedaPage clickEnglishLanguage() {
        wait.until(ExpectedConditions.elementToBeClickable(englishLanguage))
                .click();
        return this;
    }

    public PobedaPage checkTextInPage(String text) {
        wait.until(driver -> driver.getPageSource().contains(text));
        return this;
    }

    private String getTextFromElement(By element) {
        WebElement wd = wait.until(
                ExpectedConditions.visibilityOfElementLocated(element)
        );
        return wd.getText();
    }


    public PobedaPage waitUntilSlideIsActive(String titleText) {
        By titleInSlide = By.xpath(
                ".//div[contains(@class,'dp-1ihjhh6-root') and normalize-space()='" + titleText + "']"
        );
        wait.until(driver -> {
            List<WebElement> slideButtons = driver.findElements(slides);
            if (slideButtons.isEmpty()) {
                return false;
            }
            int slideIdx = IntStream.range(0, slideButtons.size())
                    .filter(i -> !slideButtons.get(i).findElements(titleInSlide).isEmpty())
                    .findFirst()
                    .orElse(-1);

            if (slideIdx == -1) {
                return false;
            }

            WebElement bullet = driver.findElement(activeBullet);
            String idxStr = bullet.getAttribute("data-idx");

            if (idxStr == null) {
                return false;
            }
            int activeIdx = Integer.parseInt(idxStr);
            return activeIdx == slideIdx;
        });

        return this;
    }
}

