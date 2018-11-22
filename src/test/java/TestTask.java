import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import pages.BasicPage;
import pages.WrikeFooter;
import pages.WrikeMainPage;
import pages.WrikeResendEmailPage;

import java.util.concurrent.TimeUnit;

public class TestTask {

    private WebDriver driver;
    private WrikeMainPage wrikeMainPage;
    private WrikeResendEmailPage wrikeResendEmailPage;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void beforeTest() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        BasicPage.driver = driver;
        wrikeMainPage = PageFactory.initElements(driver, WrikeMainPage.class);
        wrikeMainPage.startFreeTrialPopUp =  PageFactory.initElements(driver, WrikeMainPage.StartFreeTrialPopUp.class);
        wrikeResendEmailPage = PageFactory.initElements(driver, WrikeResendEmailPage.class);
        wrikeResendEmailPage.selectionForm = PageFactory.initElements(driver, WrikeResendEmailPage.SelectionForm.class);
        wrikeResendEmailPage.wrikeFooter = PageFactory.initElements(driver, WrikeFooter.class);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
        wrikeMainPage.openSite();
        wrikeMainPage.getStartedForFree();
        wrikeMainPage.startFreeTrialPopUp.fillEmailField();
        wrikeMainPage.startFreeTrialPopUp.createWrikeAccount();
        wrikeResendEmailPage.checkResendEmailPageIsOpened();
        wrikeResendEmailPage.closeIframe();
        wrikeResendEmailPage.selectionForm.fillQASection();
        wrikeResendEmailPage.selectionForm.checkThatCorrectButtonsAreSelected();
        wrikeResendEmailPage.selectionForm.submitSelections();
        wrikeResendEmailPage.checkAnswersAreSubmitted();
        wrikeResendEmailPage.resendEmail();
        wrikeResendEmailPage.checkEmailIsResend();
        wrikeResendEmailPage.wrikeFooter.checkFollowUsSectionContainsTwitterButtonAndHaveCorrectURL();
        wrikeResendEmailPage.wrikeFooter.checkTwitterButtonCorrectIcon();
    }
}
