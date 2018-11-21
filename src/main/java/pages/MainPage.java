package pages;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasicPage {
    public StartFreeTrialPopUp startFreeTrialPopUp;

    @FindBy(xpath = "//div[@class='r']//button[@type='submit']")
    private WebElement submitButton;

    @Step("Open url: wrike.com")
    public void openSite() {
        driver.get("https://www.wrike.com/");
    }
    @Step("Click \"Get started for free\" button")
    public void getStartedForFree() {
        submitButton.click();
    }

    public static class StartFreeTrialPopUp {

        static private String generatedString = RandomStringUtils.randomAlphabetic(10);
        static String email = generatedString + "@wriketask.qaa";

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
