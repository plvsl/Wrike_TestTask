import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import pages.BasicPage;
import pages.Footer;
import pages.MainPage;
import pages.ResendEmailActivationPage;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestTask {
    //TODO
    static Properties prop = new Properties();

    private WebDriver driver;
    private MainPage mainPage;
    private ResendEmailActivationPage resendEmailActivationPage;

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
        mainPage = PageFactory.initElements(driver, MainPage.class);
        mainPage.startFreeTrialPopUp =  PageFactory.initElements(driver, MainPage.StartFreeTrialPopUp.class);
        resendEmailActivationPage = PageFactory.initElements(driver, ResendEmailActivationPage.class);
        resendEmailActivationPage.selectionForm = PageFactory.initElements(driver, ResendEmailActivationPage.SelectionForm.class);
        resendEmailActivationPage.footer = PageFactory.initElements(driver, Footer.class);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
        mainPage.openSite();
        mainPage.getStartedForFree();
        mainPage.startFreeTrialPopUp.fillEmailField();
        mainPage.startFreeTrialPopUp.createWrikeAccount();
        resendEmailActivationPage.checkResendEmailActivationPageIsOpened();
        resendEmailActivationPage.closeIframe();
        resendEmailActivationPage.selectionForm.fillQASection();
        resendEmailActivationPage.selectionForm.checkThatRightButtonsAreSelected();
        resendEmailActivationPage.selectionForm.submitSelections();
        resendEmailActivationPage.checkAnswersAreSubmitted();
        resendEmailActivationPage.resendEmail();
        resendEmailActivationPage.checkEmailIsResend();
        resendEmailActivationPage.footer.checkFollowUsSectionContainsTwitterButtonAndHaveCorrectURL();
        resendEmailActivationPage.footer.checkTwitterButtonCorrectIcon();
    }
}
