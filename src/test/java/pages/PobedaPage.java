package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

public class PobedaPage {
    private final WebDriverWait wait;

    public PobedaPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(400));
    }

    private final By moveToMainButton = By.xpath("//a[@href='/']");
    private final By languageButton = By.cssSelector(".dp-1qw5me8-root-root");
    private final By englishLanguage = By.xpath("//div[text()='English']");
    private final By slides = By.cssSelector("button.dp-12jpsjh-root");
    private final By activeBullet = By.cssSelector("button.dp-19vkeuu-bullet[data-active='true']");


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

