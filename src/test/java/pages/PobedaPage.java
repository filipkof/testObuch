package pages;

import org.openqa.selenium.*;
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
    private final By fromFindTicketInput = By.xpath("//input[@placeholder='Откуда']");
    private final By toFindTicketInput = By.xpath("//input[@placeholder='Куда']");
    private final By dateFromFindTicketInputWrapper = By.xpath("//input[@placeholder='Туда']/ancestor::div[contains(@class,'dp-1dr6zbu-root')][1]");
    private final By dateFromFindTicketInput = By.xpath("//input[@placeholder='Туда']");
    private final By dateToFindTicketInput = By.xpath("//input[@placeholder='Обратно']");
    private final By searchPanel = By.cssSelector(".dp-s7nf6s-root");
    private final By searchPanelSubmitButton = By.cssSelector("button.dp-14pgyec-root-root-root");


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

    public PobedaPage scrollToSearchPanel() {
        scrollTo(searchPanel);
        return this;
    }

    public PobedaPage checkVisibleSearchPanel() {
        awaitVisibleElement(fromFindTicketInput);
        awaitVisibleElement(toFindTicketInput);
        awaitVisibleElement(dateFromFindTicketInput);
        awaitVisibleElement(dateToFindTicketInput);
        return this;
    }

    public PobedaPage setFromAndToTicketInput(String from, String to) {
        setTextToElement(fromFindTicketInput, from);
        setEnter(fromFindTicketInput);
        setTextToElement(toFindTicketInput, to);
        setEnter(toFindTicketInput);
        return this;
    }

    public PobedaPage clickSearchPanelSubmitButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchPanelSubmitButton))
                .click();
        return this;
    }

    public PobedaPage checkErrorDateFromFindTicketInput() {
        awaitInputSearchPanelHasError(dateFromFindTicketInputWrapper);
        return this;
    }

    public PobedaPage checkLoadLogo() {
        awaitVisibleElement(pobedaLogo);
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

    private void setTextToElement(By element, String text) {
        WebElement wd = wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        wd.clear();
        wd.sendKeys(text);
    }

    private void setEnter(By element) {
        WebElement wd = wait.until(ExpectedConditions.elementToBeClickable(element));
        wd.sendKeys(Keys.ENTER);
    }

    private void awaitVisibleElement(By element) {
        await()
                .atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofMillis(200))
                .ignoreExceptions()
                .until(() -> driver.findElements(element).stream().anyMatch(WebElement::isDisplayed));
    }

    private void scrollTo(By locator) {
        await()
                .atMost(Duration.ofSeconds(10))
                .until(() -> !driver.findElements(locator).isEmpty());

        WebElement element = driver.findElement(locator);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    private void awaitInputSearchPanelHasError(By locator) {
        await()
                .atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofMillis(200))
                .until(() -> driver.findElement(locator).getAttribute("data-failed") != null);
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

