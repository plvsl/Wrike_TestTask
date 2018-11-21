package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

public class Footer extends ResendEmailActivationPage {

    @FindBy(xpath = "//div[@class='wg-footer__social-block']//a[@href='https://twitter.com/wrike']")
    private WebElement twitterLink;
    @FindBy(css = "a[href='https://twitter.com/wrike'] use")
    private WebElement twitterIcon;

    @Step("Check that section \"Follow us\" on footer contains the \"Twitter\" button with correct url")
    public void checkFollowUsSectionContainsTwitterButtonAndHaveCorrectURL() {
        assertThat(twitterLink.getAttribute("href"))
                .as("'Twitter' button has correct url")
                .isEqualTo("https://twitter.com/wrike");
    }
    @Step("Check that \"Twitter\" icon is displayed")
    public void checkTwitterButtonCorrectIcon() {
        assertThat(twitterIcon.isDisplayed())
                .as("'Twitter' icon is displayed")
                .isTrue();
    }
}


