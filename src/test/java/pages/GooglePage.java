package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GooglePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public GooglePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By searchInput = By.name("q");
    private final By pobedaWebSite = By.xpath("//a[@href='https://www.flypobeda.ru/']");


    public GooglePage open() {
        driver.get("https://google.com");
        return this;
    }

    public GooglePage clickGoogleSearchInput() {
        wait.until(ExpectedConditions.elementToBeClickable(searchInput))
                .click();
        return this;
    }

    public GooglePage searchInGoogle(String text) {
        WebElement wd = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        wd.clear();
        wd.sendKeys(text);
        wd.sendKeys(Keys.ENTER);
        return this;
    }

    public GooglePage clickPobedaWebSite() {
        wait.until(ExpectedConditions.elementToBeClickable(pobedaWebSite))
                .click();
        return this;
    }


}
