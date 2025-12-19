package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PikabuMainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public PikabuMainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By headerTitle = By.xpath("//*[@title][1]");
    private final By loginButtonRightMenu = By.cssSelector("button.header-right-menu__login-button");
    private final By loginPopUp = By.cssSelector("div.popup__content");
    private final By loginInputInLoginPopUp = By.xpath("//div[contains(@class,'popup__content')]//input[@placeholder='Логин']");
    private final By passwordInputInLoginPopUp = By.xpath("//div[contains(@class,'popup__content')]//input[@placeholder='Пароль']");
    private final By submitButtonInLoginPopUp = By.xpath("//div[contains(@class,'popup__content')]//button[.//span[normalize-space()='Войти']]");
    private final By errorLoginTextInLoginPopUp = By.xpath("//div[contains(@class,'popup__content')]//span[contains(@class,'auth__error_top')]");


    public PikabuMainPage open() {
        driver.get("https://pikabu.ru/");
        return this;
    }

    public String getTextFromHeaderTitle() {
        return driver.getTitle();
    }

    public PikabuMainPage clickLoginButtonRightMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButtonRightMenu))
                .click();
        return this;
    }

    public PikabuMainPage checkVisibleLoginPopUp() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPopUp));
        return this;
    }

    public PikabuMainPage checkVisibleLoginInputFromLoginPopUp() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginInputInLoginPopUp));
        return this;
    }

    public PikabuMainPage checkVisiblePasswordInputFromLoginPopUp() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputInLoginPopUp));
        return this;
    }

    public PikabuMainPage checkVisibleSubmitButtonInputFromLoginPopUp() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(submitButtonInLoginPopUp));
        return this;
    }

    public PikabuMainPage enterTextInLoginInputFromLoginPopUp(String text) {
        WebElement wd = wait.until(ExpectedConditions.visibilityOfElementLocated(loginInputInLoginPopUp));
        wd.clear();
        wd.sendKeys(text);
        return this;
    }

    public PikabuMainPage enterTextInPasswordInputFromLoginPopUp(String text) {
        WebElement wd = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputInLoginPopUp));
        wd.clear();
        wd.sendKeys(text);
        return this;
    }

    public PikabuMainPage clickSubmitButtonFromLoginPopUp() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButtonInLoginPopUp))
                .click();
        return this;
    }

    public String getErrorTextFromLoginPopUp() {
        WebElement wd = wait.until(ExpectedConditions.visibilityOfElementLocated(errorLoginTextInLoginPopUp));
        return wd.getText();
    }
}
