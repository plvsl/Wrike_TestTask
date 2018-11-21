import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import pages.Page;

public class TestTask {

    private WebDriver driver;
    private Page page;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void beforeTest() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        page = PageFactory.initElements(driver, Page.class);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
        page.openSite(driver);
        page.getStartedForFree();
        page.fillEmailField(driver);
        page.createWrikeAccount(driver);
        page.fillQASection(driver);
    }
}
