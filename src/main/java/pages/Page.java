package pages;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class Page {

    @FindBy(xpath = "//div[@class='r']//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//label[@class='modal-form-trial__label']/input")
    private WebElement emailTextBox;

    @FindBy(xpath = "//label[@class='modal-form-trial__label'] /button")
    private WebElement createWrikeAccountButton;

    @FindBy(xpath = "//div[@data-code='interest_in_solution']//button")
    private List<WebElement> interestInSolutionButtons;

    @FindBy(xpath = "//div[@data-code='team_members']//button")
    private List<WebElement> teamMembersButtons;

    @FindBy(xpath = "//div[@data-code='primary_business']//button")
    private List<WebElement> managingWorkProcessButtons;

    @FindBy(xpath = "//div[@data-code='interest_in_solution']//input")
    private List<WebElement> interestInSolutionButtonsI;

    @FindBy(xpath = "//div[@data-code='team_members']//input")
    private List<WebElement> teamMembersButtonsI;

    @FindBy(xpath = "//div[@data-code='primary_business']//input")
    private List<WebElement> managingWorkProcessButtonsI;

    @FindBy(xpath = "//iframe[@name]")
    private WebElement iframe;

    @FindBy(xpath = "(//span[@class='RveJvd snByac'])[2]")
    private WebElement iframeYES;

    @FindBy(xpath = "(//span[@class='RveJvd snByac'])[1]")
    private WebElement iframeNO;

    @FindBy(xpath = "//div[@class='survey']//button[@type='submit']")
    private WebElement submit;

    @FindBy(xpath = "//div[@class='survey-success']")
    private WebElement successSubmittedWindow;

    @FindBy(xpath = "//div[@class='wg-grid']//p/button")
    private WebElement resendEmailButton;

    @FindBy(xpath = "//p[@class='h4 subtitle']")
    private WebElement textOfEmailActivation;

    @FindBy(xpath = "//div[@class='wg-footer__social-block']//a[@href='https://twitter.com/wrike']")
    private WebElement twitterLink;

    @FindBy(xpath = "//a[@href='https://twitter.com/wrike']//use")
    private WebElement twitterIcon;

    String generatedString = RandomStringUtils.randomAlphabetic(10);
    String email = generatedString + "@wriketask.qaa";
    String expectedTextOfEmailActivation = "We’ve sent you an activation email\nat " + email +
            " again. Please check your inbox and follow the link to complete your registration.";

    //Open url: wrike.com;
    public void openSite(WebDriver driver) {
        driver.get("https://www.wrike.com/");
    }

    //Click "Get started for free" button near "Login" button;
    public void getStartedForFree() {
        submitButton.click();
    }

    //Fill in the email field with random generated value of email with mask
    //“<random_text>+wpt@wriketask.qaa” (e.g. “abcdef+wpt@wriketask.qaa”);
    public void fillEmailField(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(emailTextBox));
        emailTextBox.sendKeys(email);
    }
    //Click on "Create my Wrike account" button + check with assertion
    // that you are moved to the next page;
    public void createWrikeAccount(WebDriver driver) {
        createWrikeAccountButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.titleIs("Thank you for choosing Wrike!"));

        assertThat(driver.getCurrentUrl())
                .as("")
                .isEqualTo("https://www.wrike.com/resend/");

        assertThat(driver.getTitle())
                .as("")
                .isEqualTo("Thank you for choosing Wrike!");

    }

    //Fill in the Q&A section at the left part of page (like random generated answers)
    // + check with assertion that your answers are submitted;
    public void fillQASection(WebDriver driver) {
        int randInterestInSolution = new Random().nextInt(1);
        int randTeamMembersButtons = new Random().nextInt(4);
        int randManagingWork = new Random().nextInt(2);

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(iframe));
        driver.switchTo().frame(iframe);
        iframeNO.click();
        driver.switchTo().defaultContent();

        interestInSolutionButtons.get(randInterestInSolution).click();
        teamMembersButtons.get(randTeamMembersButtons).click();
        managingWorkProcessButtons.get(randManagingWork).click();


        //check that all selected buttons are selected
        assertThat(interestInSolutionButtonsI.get(randInterestInSolution).isSelected())
                .as("check that selected buttons are selected")
                .isTrue();

        assertThat(teamMembersButtonsI.get(randTeamMembersButtons).isSelected())
                .as("check that selected buttons are selected")
                .isTrue();

        assertThat(managingWorkProcessButtonsI.get(randManagingWork).isSelected())
                .as("check that selected buttons are selected")
                .isTrue();

        //submit selections
        submit.click();

        //check with assertion that your answers are submitted;
        wait.until(ExpectedConditions.visibilityOf(successSubmittedWindow));
        assertThat(successSubmittedWindow.isDisplayed())
                .as("Answers on QA sections are submitted")
                .isTrue();

        //Click on "Resend email" + check it with assertion;
        resendEmailButton.click();

        wait.until(ExpectedConditions.invisibilityOf(resendEmailButton));
        assertThat(resendEmailButton.isDisplayed())
                .as("Check resend email")
                .isFalse();

        assertThat(textOfEmailActivation.getText())
                .as("")
                .isEqualTo(expectedTextOfEmailActivation);

        //Check that section "Follow us" at the site footer contains
        //the "Twitter" button that leads to the correct url and has the correct icon.

        assertThat(twitterLink.getAttribute("href"))
                .as("")
                .isEqualTo("https://twitter.com/wrike");

        assertThat(twitterIcon.isDisplayed())
                .as("")
                .isTrue();

    }
}
