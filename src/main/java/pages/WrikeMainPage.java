package pages;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WrikeMainPage extends BasicPage {
    public StartFreeTrialPopUp startFreeTrialPopUp;

    private final String URL = "https://www.wrike.com/";

    @FindBy(xpath = "//div[@class='r']//button[@type='submit']")
    private WebElement submitButton;

    @Step("Open url: wrike.com")
    public void openSite() {
        driver.get(URL);
    }

    @Step("Click \"Get started for free\" button")
    public void getStartedForFree() {
        submitButton.click();
    }

    public static class StartFreeTrialPopUp {
        static final String EMAIL_LAST_PART = "@wriketask.qaa";
        static private String generatedString = RandomStringUtils.randomAlphabetic(10);
        static String email = generatedString + EMAIL_LAST_PART;

        @FindBy(xpath = "//label[@class='modal-form-trial__label']/input")
        private WebElement emailTextBox;
        @FindBy(xpath = "//label[@class='modal-form-trial__label'] /button")
        private WebElement createWrikeAccountButton;

        @Step("Fill email field with random generated value")
        public void fillEmailField() {
            emailTextBox.sendKeys(email);
        }

        @Step("Click on \"Create my Wrike account\" button")
        public void createWrikeAccount() {
            createWrikeAccountButton.click();
        }
    }
}
