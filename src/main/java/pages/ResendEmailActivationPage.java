package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ResendEmailActivationPage extends BasicPage {
    public SelectionForm selectionForm;

    private MainPage.StartFreeTrialPopUp startFreeTrialPopUp;
    private String email = startFreeTrialPopUp.email;
    private final String expectedTextOfEmailActivation = "We’ve sent you an activation email\nat " + email +
            " again. Please check your inbox and follow the link to complete your registration.";

    @FindBy(xpath = "//iframe[@name]")
    private WebElement iframe;
    @FindBy(xpath = "(//span[@class='RveJvd snByac'])[2]")
    private WebElement iframeYES;
    @FindBy(xpath = "(//span[@class='RveJvd snByac'])[1]")
    private WebElement iframeNO;
    @FindBy(xpath = "//div[@class='survey-success']")
    private WebElement successSubmittedWindow;
    @FindBy(xpath = "//div[@class='wg-grid']//p/button")
    private WebElement resendEmailButton;
    @FindBy(xpath = "//p[@class='h4 subtitle']")
    private WebElement textOfEmailActivation;

    @Step("Check that \"Resend Email Page\" is opened")
    public void checkResendEmailActivationPageIsOpened() {
        wait.until(ExpectedConditions.titleIs("Thank you for choosing Wrike!"));

        assertThat(driver.getCurrentUrl())
                .as("")
                .isEqualTo("https://www.wrike.com/resend/");

        assertThat(driver.getTitle())
                .as("")
                .isEqualTo("Thank you for choosing Wrike!");
    }

    @Step("Click \"No\" button on Google Iframe")
    public void closeIframe() {
        try {
            wait.until(ExpectedConditions.visibilityOf(iframe));
            driver.switchTo().frame(iframe);
            iframeNO.click();
            driver.switchTo().defaultContent();
        } catch (TimeoutException ignored) {}
    }
    @Step("Check that answers are submitted")
    public void checkAnswersAreSubmitted() {
        wait.until(ExpectedConditions.visibilityOf(successSubmittedWindow));
        assertThat(successSubmittedWindow.isDisplayed())
                .as("Answers on QA sections are submitted")
                .isTrue();
    }
    @Step("Click on \"Resend email\"")
    public void resendEmail() {
        resendEmailButton.click();
    }
    @Step("Check that email is resend")
    public void checkEmailIsResend() {
        wait.until(ExpectedConditions.invisibilityOf(resendEmailButton));
        assertThat(resendEmailButton.isDisplayed())
                .as("Check resend email")
                .isFalse();

        assertThat(textOfEmailActivation.getText())
                .as("")
                .isEqualTo(expectedTextOfEmailActivation);
    }
    public static class SelectionForm {

        int randInterestInSolution;
        int randTeamMembersButtons;
        int randManagingWork;

        @FindBy(xpath = "//div[@data-code='interest_in_solution']//button")
        private List<WebElement> interestInSolutionButtons;
        @FindBy(xpath = "//div[@data-code='interest_in_solution']//input")
        private List<WebElement> interestInSolutionButtonsInput;
        @FindBy(xpath = "//div[@data-code='team_members']//button")
        private List<WebElement> teamMembersButtons;
        @FindBy(xpath = "//div[@data-code='team_members']//input")
        private List<WebElement> teamMembersButtonsInput;
        @FindBy(xpath = "//div[@data-code='primary_business']//button")
        private List<WebElement> managingWorkProcessButtons;
        @FindBy(xpath = "//div[@data-code='primary_business']//input")
        private List<WebElement> managingWorkProcessButtonsInput;
        @FindBy(xpath = "//div[@class='survey']//button[@type='submit']")
        private WebElement submit;

        @Step("Fill in the Q&A section with random generated answers)")
        public void fillQASection() {
            randInterestInSolution = new Random().nextInt(2);
            randTeamMembersButtons = new Random().nextInt(5);
            randManagingWork = new Random().nextInt(3);

            interestInSolutionButtons.get(randInterestInSolution).click();
            teamMembersButtons.get(randTeamMembersButtons).click();
            managingWorkProcessButtons.get(randManagingWork).click();
        }
        @Step("Check that right buttons are selected")
        public void checkThatRightButtonsAreSelected() {
            assertThat(interestInSolutionButtonsInput.get(randInterestInSolution).isSelected())
                    .as("check that selected buttons are selected")
                    .isTrue();

            assertThat(teamMembersButtonsInput.get(randTeamMembersButtons).isSelected())
                    .as("check that selected buttons are selected")
                    .isTrue();

            assertThat(managingWorkProcessButtonsInput.get(randManagingWork).isSelected())
                    .as("check that selected buttons are selected")
                    .isTrue();
        }
        @Step("Submit answers")
        public void submitSelections() {
            submit.click();
        }
    }
}